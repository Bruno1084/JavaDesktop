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

    public static ResultSet querryInnerJoin(String[] columns, String leftTable, String rightTable, String leftJoin, String rightJoin){
        String querryColumns = String.join(", ", columns);
        String querry = "SELECT " + querryColumns +
                        " FROM " + leftTable + " INNER JOIN " +
                        rightTable + " ON " + leftTable + "." + leftJoin + " = " +
                        rightTable + "." + rightJoin;
        ResultSet resultSet = null;
        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException exception){
            System.out.println("There is an error on QuerryInnerJoin method");
            exception.printStackTrace();
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

    public static int insertVenta(int idCliente, float precioTotal, String tipoPago, boolean isPagado, Date fechaVenta){
        String querry = "INSERT INTO venta(IdCliente, PrecTotalVenta, TPagoVenta, PagVenta, FechVenta)" +
                        "VALUES (?, ?, ?, ?, ?)";
        ResultSet resultSet;
        int idVenta = 0;

        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, idCliente);
            preparedStatement.setFloat(2, precioTotal);
            preparedStatement.setString(3, tipoPago);
            preparedStatement.setBoolean(4, isPagado);
            preparedStatement.setDate(5, fechaVenta);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()){
                System.out.println("IdVenta inserted: " + resultSet.getInt(1));
                idVenta = resultSet.getInt(1);
            }

            System.out.println("Venta ingresada en la base de datos");
        }catch (SQLException exception){
            System.out.println("Error on database method insertVenta()");
            exception.printStackTrace();
        }
        return idVenta;
    }

    public static void insertDetalle_venta( int idVenta, int idProducto, int cantidad){
        String querry = "INSERT INTO detalle_venta(IdVenta, IdProducto, CantVenta_detalle)" +
                        "VALUES (?, ?, ?)";

        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.setInt(1, idVenta);
            preparedStatement.setInt(2, idProducto);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.executeUpdate();

            System.out.println("Detalle_venta added to Database");
        }catch (SQLException exception){
            System.out.println("There is an error on insertDetalle_venta()");
            exception.printStackTrace();
        }
    }

    public static int countSellsByPeriod(String periodo){
        String querry = "SELECT VentasPorPeriodo('" + periodo + "')";
        ResultSet resultSet = null;
        int cantVentas = 0;
        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cantVentas = resultSet.getInt(1);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return cantVentas;
    }

    public static float countIncomeByPeriod(String periodo){
        String querry = "SELECT VentasPorPeriodo(" + periodo + ")";
        ResultSet resultSet = null;
        float cantVentas = 0;
        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cantVentas = resultSet.getFloat(1);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return cantVentas;
    }

}
