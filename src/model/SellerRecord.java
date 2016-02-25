package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Guan Huang on 11/30/2015.
 */
public class SellerRecord {

    public SellerRecord(){
        this.soldItems = new ArrayList<>();
    }

    public SellerRecord(ArrayList<SoldItem> soldItems){
        this.soldItems = soldItems;
    }

    public ArrayList<String> getItems(){
        ArrayList<String> itemDataStrings = new ArrayList<>();
        for (SoldItem sold : soldItems) {
            String productDataString = sold.getItem();
            if(!productDataString.equals("")) {
                itemDataStrings.add(productDataString);
            }
        }
        return itemDataStrings;
    }

    public static void saveSoldItem(String sellerName, String buyerName,
                             int productId, int quantitySelected, double totalPrice) {
        SoldItem soldItem = new SoldItem(sellerName, buyerName, productId,
                quantitySelected, totalPrice);
        soldItem.insertIntoDatabase();
    }

    public double getRevenue(){
        double total = 0;
        for(SoldItem soldItem:soldItems){
            total += soldItem.getTotalPrice();
        }
        return total;
    }

    private ArrayList<SoldItem> soldItems;
}

class SoldItem extends Model {

    private int transactionId = AUTOINCREMENT;
    private String username;
    private String buyername;
    private int productId;
    private int quantitySelected;
    private double totalPrice;
    private String date = SQLDate;

    public SoldItem(String dataString) {
        super("transactionId");
        System.out.println(dataString);
        update(dataString);
    }

    public SoldItem(String sellerName, String buyerName,
                    int productId, int quantitySelected, double totalPrice) {
        super("transactionId");
        this.username = sellerName;
        this.buyername = buyerName;
        this.productId = productId;
        this.quantitySelected = quantitySelected;
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public int getQuantitySelected(){
        return quantitySelected;
    }

    public String getItem() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("productId", productId);
        final ArrayList<String> dataStringFromDatabase =
                Product.selectDataStringFromDatabase(Product.class,
                new String[]{"productName","sellingPrice","cost"}, hashMap);
        if (dataStringFromDatabase.size() == 1) {
            String productDataString = dataStringFromDatabase.get(0);
            return String.format("%s&%s", productDataString, toDataString());
        } else {
            return "";
        }
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

    @Override
    protected void update(String fieldName, String value)
            throws ReflectiveOperationException {
        Field property = className.getDeclaredField(fieldName);
        System.out.println(fieldName);
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
        return new Object[]{transactionId, username, buyername, productId,
                quantitySelected, totalPrice, date};
    }

    @Override
    protected HashMap<String, Object> toKeyValuePairs(ArrayList<String> properties)
            throws ReflectiveOperationException {
        return null;
    }

    @Override
    public Object getPrimaryKeysValue() {
        return transactionId;
    }
}
