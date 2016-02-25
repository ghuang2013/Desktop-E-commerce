package model;

import global.Session;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Guan Huang on 11/7/2015.
 */
public class User extends Model {
    public User() {
        super("username");
        this.isLoggedIn = true;
    }

    public User(String dataString) {
        super("username");
        update(dataString);
        this.isLoggedIn = true;
    }

    public boolean checkCredential(String passwordTyped) {
        HashMap<String, Object> pair = new HashMap<>();
        pair.put("username", username);
        pair.put("password", passwordTyped);

        ArrayList<String> resultSet = selectDataStringFromDatabase(User.class, null, pair);
        //Should return exactly one row. username is unique for each user
        if (resultSet.size() == 1) {
            //Log in successfully update user information
            update(resultSet.get(0));
            setActive();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getInventory() {
        HashMap<String, Object> keyValuePair = new HashMap<>();
        keyValuePair.put("username", username);
        return selectDataStringFromDatabase(Product.class, null, keyValuePair);
    }

    public ArrayList<String> getShoppingCartItems() {
        return shoppingCart.getItems();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public ArrayList<String> getSellerRecordItems() {
        return sellerRecord.getItems();
    }

    public SellerRecord getSellerRecord() {
        return sellerRecord;
    }

    public StatusCode addNewProductToInventory(Product product) {
        product.setOwner(username);
        return product.insertIntoDatabase();
    }

    public StatusCode addProductToShoppingCart(Product product, int quantitySelected) {
        Double price = product.getPrice(quantitySelected);
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(username,
                (int) product.getPrimaryKeysValue(), quantitySelected, price);
        return shoppingCart.saveItem(shoppingCartItem);
    }

    public StatusCode deleteProductFromShoppingCart(Product product, int quantitySelected) {
        Double price = product.getPrice(quantitySelected);
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(username,
                (int) product.getPrimaryKeysValue(), quantitySelected, price);
        return shoppingCart.deleteItem(shoppingCartItem);
    }

    public StatusCode updateProductQuantityFromShoppingCart(Product product,
                                                            int quantitySelected) {
        String[] changedProperties = {"quantitySelected", "totalPrice"};
        double totalPrice = product.getPrice(quantitySelected);
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(username,
                (int) product.getPrimaryKeysValue(), quantitySelected, totalPrice);
        try {
            return shoppingCart.updateItem(shoppingCartItem,
                    new ArrayList<>(Arrays.asList(changedProperties)));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return StatusCode.FAILURE;
        }
    }

    public void setActive() {
        Session.setCurrentLoggedInUser(this);
        shoppingCart = loadShoppingCart();
        sellerRecord = loadSellerRecord();
    }

    public String getPrimaryKeysValue() {
        return username;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public enum LoginType {
        Buyer, Seller
    }

    public String toString() {
        return String.format("username: %s\n" +
                        "password: %s\n" +
                        "firstname: %s\n" +
                        "lastname: %s\n" +
                        "address: %s\n" +
                        "phone #: %s",
                username, password,
                firstName, lastName,
                address, phoneNumber);
    }

    /**
     * update the selected field of a model object according to its declared type
     *
     * @param fieldName property's name
     * @param value     property's value to be updated
     * @throws ReflectiveOperationException
     */
    protected void update(String fieldName, String value) throws ReflectiveOperationException {
        Field property = className.getDeclaredField(fieldName);
        Class type = property.getType();
        if (type.equals(String.class)) {
            property.set(this, value);
        } else if (type.equals(boolean.class)) {
            property.set(this, Boolean.valueOf(value));
        }
    }

    /**
     * For updating the database
     * properties array keep track of the changed data fields
     */
    @Override
    protected HashMap<String, Object> toKeyValuePairs(ArrayList<String> properties)
            throws ReflectiveOperationException {
        HashMap<String, Object> pairs = new HashMap<>();
        for (String property : properties) {
            pairs.put(property, className.getDeclaredField(property).get(this));
        }
        return pairs;
    }

    /*
       For inserting into the database
     */
    @Override
    protected Object[] toTuple() {
        return new Object[]{username, password, firstName,
                lastName, address, phoneNumber, isLoggedIn};
    }

    /**
     * Fetch user's shopping cart from the database
     *
     * @return a shopping cart object
     */
    private ShoppingCart loadShoppingCart() {
        HashMap<String, Object> keyValuePair = new HashMap<>();
        keyValuePair.put("username", username);
        ArrayList<ShoppingCartItem> shoppingCartItems = (ArrayList<ShoppingCartItem>)
                selectFromDatabase(ShoppingCartItem.class, null, keyValuePair);
        if (shoppingCartItems.size() == 0) {
            return new ShoppingCart();
        }
        return new ShoppingCart(shoppingCartItems);
    }

    private SellerRecord loadSellerRecord() {
        HashMap<String, Object> keyValuePair = new HashMap<>();
        keyValuePair.put("username", username);
        ArrayList<SoldItem> soldItems = (ArrayList<SoldItem>) selectFromDatabase(SoldItem.class,
                null, keyValuePair);
        if (soldItems.size() == 0) {
            return new SellerRecord();
        }
        return new SellerRecord(soldItems);
    }

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean isLoggedIn = false;
    private LoginType loginType = LoginType.Buyer;
    private ShoppingCart shoppingCart;
    private SellerRecord sellerRecord;
}
