package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Guan Huang on 11/11/2015.
 */
public interface SQLQuery {
    /**
     * SQL SELECT * statement
     *
     * @param tableName schema name
     * @param columns   attributes to be selected
     * @return a row in the table
     */
    ArrayList<String> selectStatement(String tableName, String... columns);

    /**
     * SQL SELECT statement with where clause
     *
     * @param tableName     schema name
     * @param columns       attributes to be selected
     * @param keyValuePairs tuple that specifies the condition
     * @return a row in the table that fulfills the criteria
     */
    ArrayList<String> selectStatementWithWhereClause(String tableName, String[] columns,
                                                     HashMap<String, Object> keyValuePairs);

    /**
     * SQL INSERT INTO statement
     *
     * @param tableName schema name
     * @param values    tuple to be inserted
     * @return success or throw a SQL exception
     */
    StatusCode insertStatement(String tableName, HashMap<String, Object> primaryKeyFields, Object[] values);

    /**
     * SQL UPDATE statement
     *
     * @param tableName       schema name
     * @param keyValuePair    fields to be updated
     * @return success or throw a SQL exception
     */
    StatusCode updateStatement(String tableName, HashMap<String, Object> primaryKeyFields,
                               HashMap<String, Object> keyValuePair);

    /**
     * SQL Delete statement
     *
     * @param tableName       schema name
     * @return success or throw a SQL exception
     */
    StatusCode deleteStatement(String tableName, HashMap<String, Object> primaryKeyFields);
}
