package model;

import java.util.*;

/**
 * Created by Guan Huang on 11/11/2015.
 */
public class DatabaseQueryBuilder implements SQLQuery {
    private Database database;

    public DatabaseQueryBuilder(Database database) {
        this.database = database;
    }

    public ArrayList<String> selectStatement(String tableName, String... columns) {
        String query = "", comma_sep_list = "";

        if (columns.length == 0) {
            query = String.format("SELECT * FROM %s;", tableName);
        } else {
            comma_sep_list = make_comma_separated_string(columns);
            query = String.format("SELECT %s FROM %s", comma_sep_list, tableName);
        }

        return database.executeSelectStatement(query);
    }

    public ArrayList<String> selectStatementWithInnerJoin(String tableLeft, String tableRight,
                                                          String commonField,
                                                          HashMap<String, Object> keyValuePairs) {
        String condition = make_key_value_pair_string(keyValuePairs, "AND");
        String query = String.format("SELECT * FROM %s INNER JOIN %s ON %s.%s = %s.%s WHERE %s", tableLeft,
                tableRight, tableLeft, commonField, tableRight, commonField, condition);
        System.out.println(query);
        return database.executeSelectStatement(query);
    }

    public ArrayList<String> selectStatementWithWhereClause(String tableName, String[] columns,
                                                            HashMap<String, Object> keyValuePairs) {
        String comma_sep_list = "";
        if (columns != null) {
            comma_sep_list = make_comma_separated_string(columns);
        }

        String conditions = make_key_value_pair_string(keyValuePairs, "AND");

        String query = "";

        if (!comma_sep_list.equals("")) {
            query = String.format("SELECT %s FROM %s WHERE %s", comma_sep_list, tableName, conditions);
        } else {
            query = String.format("SELECT * FROM %s WHERE %s", tableName, conditions);
        }
        System.out.println(query);
        return database.executeSelectStatement(query);
    }

    public StatusCode insertStatement(String tableName, HashMap primaryKeyFields, Object[] values) {
        if (!values[0].equals(Model.AUTOINCREMENT) &&
                isDuplicateEntry(tableName, primaryKeyFields)) {
            return StatusCode.DUPLICATED_ENTRY;
        }
        String comma_sep_list = make_comma_separated_string(values);
        String query = String.format("INSERT INTO %s VALUES (%s)", tableName, comma_sep_list);
        System.out.println(query);
        return database.executeStatement(query);
    }

    public StatusCode updateStatement(String tableName, HashMap<String, Object> primaryKeyFields,
                                      HashMap<String, Object> keyValuePair) {
        String tuple = make_key_value_pair_string(keyValuePair, ",");
        String condition = make_key_value_pair_string(primaryKeyFields, "AND");
        String query = String.format("UPDATE %s SET %s WHERE %s", tableName, tuple, condition);
        System.out.println(query);
        return database.executeStatement(query);
    }

    public StatusCode deleteStatement(String tableName, HashMap<String, Object> primaryKeyFields) {
        String condition = make_key_value_pair_string(primaryKeyFields, "AND");
        String query = String.format("DELETE FROM %s WHERE %s",
                tableName, condition);
        System.out.println(query);
        return database.executeStatement(query);
    }

    private boolean isDuplicateEntry(String tableName, HashMap hashMap) {
        ArrayList result = selectStatementWithWhereClause(tableName, null, hashMap);
        return result.size() > 0;
    }

    private String make_comma_separated_string(Object[] objects) {
        String comma_sep_list = "";
        for (int i = 0; i < objects.length; ++i) {
            if (objects[i].equals(Model.AUTOINCREMENT)) {
                comma_sep_list += "NULL";
            } else if (objects[i].equals(Model.SQLDate)) {
                comma_sep_list += "NOW()";
            } else if (objects[i] instanceof String) {
                comma_sep_list += String.format("\'%s\'", objects[i]);
            } else if (objects[i] instanceof Boolean) {
                comma_sep_list += (objects[i].equals(true)) ? "TRUE" : "FALSE";
            } else {
                comma_sep_list += objects[i];
            }
            if (i != objects.length - 1) {
                comma_sep_list += ',';
            }
        }
        return comma_sep_list;
    }

    private String make_comma_separated_string(String[] columns) {
        String comma_sep_list = "";
        for (int i = 0; i < columns.length; ++i) {
            comma_sep_list += columns[i];
            if (i != columns.length - 1) {
                comma_sep_list += ',';
            }
        }
        return comma_sep_list;
    }

    private String make_key_value_pair_string(HashMap<String, Object> keyValuePairs, String delimeter) {
        String conditions = "";

        for (Iterator<Map.Entry<String, Object>> iterator = keyValuePairs.entrySet().iterator();
             iterator.hasNext(); ) {
            Map.Entry<String, Object> entry = iterator.next();

            String key = entry.getKey();
            Object value = entry.getValue();
            conditions += String.format("%s = ", key);

            if (value.equals(Model.AUTOINCREMENT)) {
                conditions += "NULL";
            } else if (value instanceof String) {
                conditions += String.format("\'%s\'", value);
            } else if (value instanceof Boolean) {
                conditions += value.equals(true) ? "TRUE" : "FALSE";
            } else {
                conditions += value;
            }
            if (iterator.hasNext()) {
                conditions += String.format(" %s ", delimeter);
            }
        }
        return conditions;
    }
}
