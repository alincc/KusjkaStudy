package lv.rtu.db;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    String url;
    String userName;
    String password ;
    String dbName;

    public DBConnector loadConfig() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("./config/DB.properties");
            prop.load(input);
            dbName = prop.getProperty("dbscheme");
            url = prop.getProperty("dburl");
            userName = prop.getProperty("dbuser");
            password = prop.getProperty("dbpassword");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    public Connection connection() {
        Connection connection = null;
        loadConfig();
        PreparedStatement statement;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url + dbName, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
