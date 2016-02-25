package model;

import global.Observer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Guan Huang on 11/11/2015.
 */
public abstract class Model implements Observer {

    public static int AUTOINCREMENT = -999;
    public static String SQLDate = "SQLDate";
    protected Class className;
    protected int columnCount;
    //unique identifier to every model so that no duplicate record get inserted into the database
    private ArrayList<String> primaryKeys = new ArrayList<>(2);
    private static DatabaseQueryBuilder databaseQueryBuilder =
            new DatabaseQueryBuilder(Database.getInstance());

    public Model(String key) {
        this.primaryKeys.add(key);
        initializeModel();
    }

    public Model(String[] keys) {
        this.primaryKeys.addAll(Arrays.asList(keys));
        initializeModel();
    }

    private void initializeModel() {
        this.className = getClass();
   //     this.columnCount = calcColumnNumber();
    }

    /**
     * update class members from query string
     *
     * @param dataString query string
     */
    public final void update(String dataString) {
        String[] fields = dataString.split("&");
        for (String field : fields) {
            String[] pair = field.split("=");
            try {
                update(pair[0], pair[1]);
            } catch (ReflectiveOperationException e) {
            }
        }
    }

    /**
     * Template method
     *
     * @param fieldName
     * @param value
     * @throws ReflectiveOperationException
     */
    protected abstract void update(String fieldName, String value)
            throws ReflectiveOperationException;


    /**
     * update the appropriate changes in the database
     *
     * @param modifiedDataFields the ArrayList holds the names of the data fields that are
     *                           changed during the program runtime
     * @return the status code
     * @throws ReflectiveOperationException if the data field's name is incorrect
     */
    public StatusCode updateDatabase(ArrayList<String> modifiedDataFields)
            throws ReflectiveOperationException {
        return databaseQueryBuilder.updateStatement(className.getSimpleName(),
                toKeyValuePairs(primaryKeys), toKeyValuePairs(modifiedDataFields));
    }

    public StatusCode insertIntoDatabase() {
        try {
            return databaseQueryBuilder.insertStatement(className.getSimpleName(),
                    toKeyValuePairs(primaryKeys), toTuple());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return StatusCode.FAILURE;
        }
    }

    public StatusCode deleteFromDatabase() {
        try {
            return databaseQueryBuilder.deleteStatement(className.getSimpleName(),
                    toKeyValuePairs(primaryKeys));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return StatusCode.FAILURE;
        }
    }

    public static ArrayList<? extends Model> selectFromDatabase(Class<? extends Model> modelClass,
                                                                String[] columns, HashMap pairs) {
        ArrayList<String> dataStrings = databaseQueryBuilder.selectStatementWithWhereClause(
                modelClass.getSimpleName(), columns, pairs);
        return createModelsFromDataStrings(modelClass, dataStrings);
    }

    public static ArrayList<String> selectDataStringFromDatabase(Class<? extends Model> modelClass,
                                                                 String[] columns, HashMap pairs) {
        return databaseQueryBuilder.selectStatementWithWhereClause(
                modelClass.getSimpleName(), columns, pairs);
    }

    public static ArrayList<String> selectAllDataStringFromDatabase(Class<? extends Model> modelClass,
                                                                    String... columns) {
        return databaseQueryBuilder.selectStatement(modelClass.getSimpleName(), columns);
    }

    protected static ArrayList<Model> createModelsFromDataStrings(Class<? extends Model> modelClass,
                                                                  ArrayList<String> dataStrings) {
        ArrayList<Model> models = new ArrayList<>();
        try {
            Constructor constructor = modelClass.getConstructor(String.class);
            for (String dataString : dataStrings) {
                models.add((Model) constructor.newInstance(dataString));
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return models;
    }
/*

    private int calcColumnNumber() {
        Field[] fields = className.getDeclaredFields();
        int fieldCount = 0;
        for (Field field : fields) {
            if (field.getType().isPrimitive() || field.getType() == String.class) {
                fieldCount++;
            }
        }
        return fieldCount;
    }
*/

    /**
     * write an object into a tuple
     *
     * @return a tuple that represents the invoking object
     */
    protected abstract Object[] toTuple();

    /**
     * Write the changed properties of a class into a key-value pair
     */
    protected abstract HashMap<String, Object> toKeyValuePairs(ArrayList<String> properties)
            throws ReflectiveOperationException;

    public abstract Object getPrimaryKeysValue();
}