package DatabaseConnection;

import java.sql.*;

public class Database {

    private static Connection connection = null;

    public static Connection getConnection(){
        return connection;
    }

    public static void establishConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/stockapp";
            connection = DriverManager.getConnection(url, "root", "Toon");
            System.out.println("Conexión con la base de datos establecida");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("Conexión con la base de datos cerrada");
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

    
}
