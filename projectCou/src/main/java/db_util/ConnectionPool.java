package db_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    /**
     * number of maximum connections
     */
    private static final int NUMBER_OF_CONNECTIONS = DataBaseManager.MAX_CONNECTIONS;
    /**
     * making sure we use the same instance of ConnectionPool
     */
    private static ConnectionPool instance = null;
    /**
     * holds the existing connections to data-base in a stack (lifo - last in first out)
     */
    private final Stack<Connection> connections = new Stack<>();

    /**
     * connectionPool constructor
     *
     * @throws SQLException An exception that provides information on a database access error or other errors.
     *                      Each SQLException provides several kinds of information
     */
    private ConnectionPool() throws SQLException {
        openAllConnections();
    }

    /**
     * manage the connections to data-base
     *
     * @return a singleton class
     */
    public static ConnectionPool getInstance() {
        //before locking the critical code...
        if (instance == null) {
            //create the connection pool
            synchronized (ConnectionPool.class) {
                //before creating the code.....
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException error) {
                        System.out.println(error.getMessage());
                    }
                }
            }
        }
        return instance;
    }

    /**
     * get a single connection from the stack
     *
     * @return a single connection
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied,
     *                              and the thread is interrupted, either before or during the activity
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                //wait until we will get a connection back
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * @param connection gets a connection back to the stack
     */
    public void restoreConnection(Connection connection) {
        synchronized (connections) {
            //   System.out.println(ConsoleColors.RED_BOLD+"-.-.-.-. connection returned -.-.-.-.-."+ConsoleColors.RESET);
            connections.push(connection);
            //notify that we got back a connection from the user...
            connections.notify();
        }
    }

    /**
     * opens 10 connections to data-base
     *
     * @throws SQLException An exception that provides information on a database access error or other errors.
     *                      Each SQLException provides several kinds of information
     */
    private void openAllConnections() throws SQLException {
        for (int index = 0; index < NUMBER_OF_CONNECTIONS; index += 1) {
            Connection connection = DriverManager.getConnection(DataBaseManager.URL, DataBaseManager.USER_NAME, DataBaseManager.USER_PASS);
            connections.push(connection);
        }
    }

    /**
     * close all the connections to data-base
     *
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied,
     *                              and the thread is interrupted, either before or during the activity
     */
    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUMBER_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }

    public int getOpenedConnections() {
        return connections.size();
    }
}
