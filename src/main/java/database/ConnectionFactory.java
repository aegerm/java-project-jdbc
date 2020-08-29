package database;

import configuration.ConfigurationProperties;
import database.exception.DatabaseException;

import java.sql.*;

public class ConnectionFactory {

    private static final String URL = ConfigurationProperties.getInstance().getProperties("db.url");
    private static final String USER = ConfigurationProperties.getInstance().getProperties("db.user");
    private static final String PASSWORD = ConfigurationProperties.getInstance().getProperties("db.password");
    private static final String DRIVER = ConfigurationProperties.getInstance().getProperties("db.driver");

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException exception) {
                throw new DatabaseException(exception.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                throw new DatabaseException(exception.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException exception) {
                throw new DatabaseException(exception.getMessage());
            }
        }
    }

    public static void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException exception) {
                throw new DatabaseException(exception.getMessage());
            }
        }
    }
}