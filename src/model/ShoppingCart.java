package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Guan Huang on 11/28/2015.
 */
public class ShoppingCart {
    public ShoppingCart(ArrayList<ShoppingCartItem> items) {
        this.shoppingCartItems = items;
    }

    public ShoppingCart() {
        this.shoppingCartItems = new ArrayList<>();
    }

    public ArrayList<String> getItems() {
        ArrayList<String> itemDataStrings = new ArrayList<>();
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            String productDataString = shoppingCartItem.getItem();
            if(!productDataString.equals("")) {
                itemDataStrings.add(productDataString);
            }
        }
        return itemDataStrings;
    }

    public StatusCode saveItem(ShoppingCartItem shoppingCartItem) {
        //permanent storage
        StatusCode statusCode = shoppingCartItem.insertIntoDatabase();

        if (statusCode.equals(StatusCode.SUCCESS)) {
            shoppingCartItems.add(shoppingCartItem);
        }
        return statusCode;
    }

    public StatusCode deleteItem(ShoppingCartItem shoppingCartItem) {
        shoppingCartItems.remove(shoppingCartItem);
        return shoppingCartItem.deleteFromDatabase();
    }

    public StatusCode updateItem(ShoppingCartItem shoppingCartItem,
                                 ArrayList<String> changedFields)
            throws ReflectiveOperationException {
        int index = shoppingCartItems.indexOf(shoppingCartItem);
        shoppingCartItems.set(index, shoppingCartItem);
        return shoppingCartItem.updateDatabase(changedFields);
    }

    public double getTotalPrice() {
        double total = 0;
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            total += shoppingCartItem.getTotalPrice();
        }
        return total;
    }

    public StatusCode deleteAll() {
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            shoppingCartItem.deleteFromDatabase();
        }
        shoppingCartItems.clear();
        return StatusCode.SUCCESS;
    }

    public ShoppingCartItem getShoppingCartItem(String dataString) {
        return new ShoppingCartItem(dataString);
    }

    //total price of the entire transaction
    private ArrayList<ShoppingCartItem> shoppingCartItems;
}

class ShoppingCartItem extends Model {

    public ShoppingCartItem(String dataString) {
        super(new String[]{"username", "productId"});
        update(dataString);
    }

    public ShoppingCartItem(String username, int productId,
                            int quantitySelected, double totalPrice) {
        super(new String[]{"username", "productId"});
        this.username = username;
        this.productId = productId;
        this.quantitySelected = quantitySelected;
        this.totalPrice = totalPrice;
    }

    public String getItem() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("productId", productId);
        hashMap.put("sold", false);
        final ArrayList<String> dataStringFromDatabase = Product.selectDataStringFromDatabase(Product.class,
                null, hashMap);
        if (dataStringFromDatabase.size() == 1) {
            String productDataString = dataStringFromDatabase.get(0);
            return String.format("%s&%s", productDataString, toDataString());
        } else {
            return "";
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String toString() {
        return String.format("Quantity: %d\nTotal: $%.2f\n",
                quantitySelected, totalPrice);
    }

    public String toDataString() {
        String result = "";
        Field[] fields = this.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; ++i) {
            try {
                result += fields[i].getName();
                result += "=";
                result += fields[i].get(this).toString();
                if (i != fields.length - 1) {
                    result += "&";
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * update shopping cart object
     *
     * @param fieldName only quantitySelected is allowed to be updated
     * @param value     new value
     * @throws ReflectiveOperationException only quantitySelected is allowed to be updated
     */
    @Override
    protected void update(String fieldName, String value)
            throws ReflectiveOperationException {
        Field property = className.getDeclaredField(fieldName);
        Class type = property.getType();
        if (type.equals(String.class)) {
            property.set(this, value);
        } else if (type.equals(int.class)) {
            property.set(this, Integer.parseInt(value));
        } else if (type.equals(double.class)) {
            property.set(this, Double.parseDouble(value));
        }
    }

    @Override
    protected Object[] toTuple() {
        return new Object[]{username, productId, quantitySelected, totalPrice};
    }

    @Override
    protected HashMap<String, Object> toKeyValuePairs(ArrayList<String> properties)
            throws ReflectiveOperationException {
        HashMap<String, Object> pairs = new HashMap<>();
        for (String property : properties) {
            pairs.put(property, className.getDeclaredField(property).get(this));
        }
        return pairs;
    }

    @Override
    public Object[] getPrimaryKeysValue() {
        return new Object[]{username, productId};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartItem)) return false;

        ShoppingCartItem that = (ShoppingCartItem) o;
        return !(that.username == null) &&
                username.equals(that.username) &&
                productId == that.productId;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + productId;
        return result;
    }

    private String username;
    private int productId;
    private int quantitySelected = 1;
    private double totalPrice = 0.0; //totalPrice = sellingPrice * quantitySelected
}
