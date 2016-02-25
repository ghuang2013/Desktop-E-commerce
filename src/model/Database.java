package model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Guan Huang on 11/7/2015.
 */
public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String db_URL = "jdbc:mysql://localhost:3306/shopping_cart";
    static final String db_username = "root";
    static final String db_password = "";

    private static Database database;
    private Connection connection;
    private Statement statement = null;

    private Database() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(db_URL, db_username, db_password);
    }

    public ArrayList<String> executeSelectStatement(String query) {
        ResultSet resultSet = null;
        ArrayList resultList = new ArrayList();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData resultMetaData = resultSet.getMetaData();

            int colCount = resultMetaData.getColumnCount();
            while (resultSet.next()) {
                String resultString = "";
                for (int i = 1; i <= colCount; ++i) {
                    resultString += resultMetaData.getColumnName(i)
                            + '=' + resultSet.getString(i);
                    if (i != colCount) {
                        resultString += '&';
                    }
                }
                resultList.add(resultString);
            }
        } catch (SQLException e) {
            System.out.println("SQL select statement throws");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                System.out.println("unable to close the result");
                e.printStackTrace();
            }
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                System.out.println("unable to close the statement");
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public StatusCode executeStatement(String query) {
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return StatusCode.FAILURE;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                System.out.println("unable to close the statement");
                e.printStackTrace();
                return StatusCode.FAILURE;
            }
        }
        return StatusCode.SUCCESS;
    }

    static public Database getInstance() {
        //ensure singleton pattern
        if (database == null) {
            try {
                database = new Database();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return database;
    }

    public void finalize() throws Throwable {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong when closing the database connection");
            e.printStackTrace();
        } catch (Throwable e) {
            throw e;
        } finally {
            super.finalize();
        }
    }
}
