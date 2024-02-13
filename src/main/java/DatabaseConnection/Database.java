package DatabaseConnection;

import java.sql.*;

public class Database {

    private static Connection connection = null;

    public static Connection getConnection(){
        return connection;
    }

    public static void establishConnection(){
        try {
            String url = "jdbc:mysql://localhost:3306/stockapp";
            connection = DriverManager.getConnection(url, "root", "Toon");
      }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet querryAllFromTable(String tableName) {
        String querry = "SELECT * FROM " + tableName;
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            resultSet = preparedStatement.executeQuery();

            System.out.println(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet querryWhereContains(String tableName, String columnTable, String searchInput){
        String querry = "SELECT * FROM " + tableName + " WHERE " + columnTable + " LIKE ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.setString(1, "%" + searchInput + "%");
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public static int querryCountRows(String tablename, String columnTable, String searchInput){
        String querry = "SELECT COUNT(" + columnTable + ") FROM " + tablename + " WHERE " + columnTable + " LIKE ?";
        int cantProductos = 0;
        try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry)) {
            preparedStatement.setString(1, "%" + searchInput + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()){
                    cantProductos = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantProductos;
    }

    
}
