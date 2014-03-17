package lv.rtu.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTools extends DBConnector {

    private List<String> getDatabaseMetaData(Connection connection) {
        List<String> tableList = new ArrayList<String>();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            while (rs.next())
                tableList.add(rs.getString("TABLE_NAME"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    public boolean checkIsConnectionPossible() {
        try {
            checkConnection();
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public void clear(String dbName) {
        PreparedStatement preparedStatement;
        Connection connection = connection();
        try {
            connection.setAutoCommit(false);
            connection.prepareStatement("SET SQL_SAFE_UPDATES=0;").execute();
            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            connection.commit();
            try {
                for (String tableName : getDatabaseMetaData(connection)) {
                    preparedStatement = connection
                            .prepareStatement("DELETE FROM " + dbName + "." + tableName + ";");
                    preparedStatement.executeUpdate();
                }
                connection.commit();
                stmt.execute("SET FOREIGN_KEY_CHECKS=1");
                connection.commit();
            } catch (SQLException s) {
                System.out.println("Deleted All Rows In  Table Error. ");
                s.printStackTrace();
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}