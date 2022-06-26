package db_util;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Map;

public class DataBaseUtils {
    private static Connection connection;

    /**
     * send query to the data-base
     *
     * @param query the query that will be sent to the data-base by String
     * @throws SQLException An exception that provides information on a database access error or other errors.
     *                      Each SQLException provides several kinds of information
     */
    public static void runQuery(String query) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            // get a query in JAVA-syntax and compile it to mySQL syntax
            PreparedStatement statement = connection.prepareStatement(query);
            // send the query to data-base
            statement.execute();
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    /**
     * send query to the data-base
     *
     * @param query  a string that is a part of the query that will be sent to the data-base, this query contains '?'
     * @param params the desire values instead of '?' , sett before the query is sent to data-base
     *               Integer indicates the number of column
     *               Object indicates the value of field
     * @throws SQLException An exception that provides information on a database access error or other errors.
     *                      Each SQLException provides several kinds of information
     */
    public static void runQuery(String query, Map<Integer, Object> params) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            // get a query in JAVA-syntax and compile it to mySQL syntax
            PreparedStatement statement = connection.prepareStatement(query);
            setStatmentParams(statement, params);
            // send the query to data-base
            statement.execute();
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    /**
     * send query to the data-base and gets back resultSet
     *
     * @param query the query that will be sent to the data-base by String
     * @return collection that contains the data from the data-base
     * @throws SQLException An exception that provides information on a database access error or other errors.
     *                      Each SQLException provides several kinds of information
     */
    public static Resultset runQueryForResult(String query) throws SQLException {
        Resultset resultset = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            // get a query in JAVA-syntax and compile it to mySQL syntax
            PreparedStatement statement = connection.prepareStatement(query);
            // send the query to data-base
            resultset = (Resultset) statement.executeQuery();
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
        return resultset;
    }

    /**
     * send query to the data-base and gets back resultSet
     *
     * @param query  a string that is a part of the query that will be sent to the data-base, this query contains '?'
     * @param params the desire values instead of '?' , sett before the query is sent to data-base
     *               Integer indicates the number of column
     *               Object indicates the value of field
     * @return collection that contains the data from the data-base
     * @throws SQLException An exception that provides information on a database access error or other errors.
     *                      Each SQLException provides several kinds of information
     */
    public static ResultSet runQueryForResult(String query, Map<Integer, Object> params) throws SQLException {
        ResultSet resultset = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            // get a query in JAVA-syntax and compile it to mySQL syntax
            PreparedStatement statement = connection.prepareStatement(query);
            setStatmentParams(statement, params);
            // send the query to data-base
            resultset = statement.executeQuery();
        } catch (InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
        return resultset;
    }

    /**
     * @param query  a string that is a part of the query that will be sent to the data-base, this query contains '?'
     * @param params the desire values instead of '?' , sett before the query is sent to data-base
     *               Integer indicates the number of column
     *               Object indicates the value of field
     * @return the id of the record that was insert to the table in data-base
     * @throws SQLException An exception that provides information on a database access error or other errors.
     *                      Each SQLException provides several kinds of information
     */
    public static int runQueryGetId(String query, Map<Integer, Object> params) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            // statment that will be sent to data-base for getting back the id of the record that has been insert to table
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setStatmentParams(statement, params);
            // get back how many rows has been affected by the query (we expect to get 1)
            int affectedRows = statement.executeUpdate();
            // if 0 rows was affected the record has not been created in table
            if (affectedRows == 0) {
                throw new SQLException("Creating record failed, no rows affected.");
            }
            // if there is a record that influenced and added to the table it returns a resultSet with the data
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // get the id of the record that was inserted to table
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating record failed, no ID obtained.");
                }
            }
        } catch (SQLException | InterruptedException error) {
            System.out.println(error.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
        return -1;
    }

    /**
     * a method that prepares a query before sending it to data-base
     *
     * @param statement a part of the query that will be sent to the data-base, this contains '?'
     * @param params    the desire values instead of '?'
     *                  Integer indicates the number of column
     *                  Object indicates the value of field
     */
    private static void setStatmentParams(PreparedStatement statement, Map<Integer, Object> params) {
        params.forEach((key, value) -> {
            try {
                if (value instanceof Integer) {
                    statement.setInt(key, (Integer) value);
                } else if (value instanceof String) {
                    statement.setString(key, String.valueOf(value));
                } else if (value instanceof Date) {
                    statement.setDate(key, (Date) value);
                } else if (value instanceof Double) {
                    statement.setDouble(key, (Double) value);
                } else if (value instanceof Boolean) {
                    statement.setBoolean(key, (Boolean) value);
                } else if (value instanceof Float) {
                    statement.setFloat(key, (Float) value);
                }
            } catch (SQLException error) {
                System.out.println(error.getMessage());
            }
        });
    }
}