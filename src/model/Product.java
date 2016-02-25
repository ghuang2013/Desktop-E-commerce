package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by michael on 11/13/2015.
 */
public class Product extends Model {

    public Product() {
        super("productId");
    }

    public Product(String dataString) {
        super("productId");
        update(dataString);
    }

    public void setOwner(String username) {
        this.username = username;
    }

    public double getPrice(int quantitySelected) {
        return sellingPrice * quantitySelected;
    }

    public void decrementQuantity(int quantityPurchased){
        quantity -= quantityPurchased;
    }

    public boolean isAvailable(){
        return quantity != 0;
    }

    public void markSoldOut(){
        sold = true;
    }

    /**
     * Write the changes to a map
     *
     * @param properties Data fields that are modified during the run time
     * @return the primaryKey value pair containing the data field's name and its value
     * @throws ReflectiveOperationException if the data field's name is incorrect
     */
    @Override
    public HashMap<String, Object> toKeyValuePairs(ArrayList<String> properties)
            throws ReflectiveOperationException {
        HashMap<String, Object> pairs = new HashMap<>();

        for (String property : properties) {
            pairs.put(property, className.getDeclaredField(property).get(this));
        }
        return pairs;
    }

    @Override
    protected Object[] toTuple() {
        return new Object[]{productId, productName, sellingPrice, cost, quantity,
                imagePath, category, username, description, sold};
    }

    @Override
    public Object getPrimaryKeysValue() {
        return productId;
    }

    @Override
    public String toString() {
        return String.format("Product Name: %-30s\nPrice: $%-30.2f\nSeller: %-10s\n",
                productName, sellingPrice, username);
    }

    /**
     * update the selected field of a model object according to its declared type
     *
     * @param fieldName property's name
     * @param value     property's value to be updated
     * @throws ReflectiveOperationException
     */
    @Override
    public void update(String fieldName, String value) throws ReflectiveOperationException {
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

    private int productId = AUTOINCREMENT;
    private String productName;
    private double sellingPrice;
    private double cost;
    private int quantity = 1;
    private String imagePath = "default.jpg";
    private String category;
    private String username;
    private String description = "no description";
    private boolean sold = false;
}