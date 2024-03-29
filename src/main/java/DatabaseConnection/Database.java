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

    public static ResultSet querryWhereEquals(String tableName, String columnTable, String searchInput){
        String querry = "SELECT * FROM " + tableName + " WHERE " + columnTable + " = ?";
        ResultSet resultSet = null;
        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.setString(1, searchInput);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException exception){
            System.out.println("There is an error on querryWhereEquals method");
            exception.printStackTrace();
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

    public static ResultSet queryInnerJoinWhere(String[] columns, String leftTable, String rightTable, String leftJoin, String rightJoin, String columnSearch, String searchInput) {
        String querryColumns = String.join(", ", columns);
        String querry = "SELECT " + querryColumns +
                " FROM " + leftTable + " INNER JOIN " +
                rightTable + " ON " + leftTable + "." + leftJoin + " = " +
                rightTable + "." + rightJoin +
                " WHERE " + rightTable + "." + columnSearch + " LIKE ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.setString(1, "%" + searchInput + "%");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException exception) {
            System.out.println("There is an error on QueryInnerJoinWhere method");
            exception.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet queryInnerJoinWhereEquals(String[] columns, String leftTable, String rightTable, String leftJoin, String rightJoin, String columnSearch, String searchInput) {
        String querryColumns = String.join(", ", columns);
        String querry = "SELECT " + querryColumns +
                " FROM " + leftTable + " INNER JOIN " +
                rightTable + " ON " + leftTable + "." + leftJoin + " = " +
                rightTable + "." + rightJoin +
                " WHERE " + columnSearch + "= ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.setString(1, searchInput);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException exception) {
            System.out.println("There is an error on QueryInnerJoinWhereEquals method");
            exception.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet querryProductsByGroup(){
        String querry = "SELECT DISTINCT CatProducto, COUNT(*) FROM producto GROUP BY CatProducto";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException exception){
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

    public static ResultSet querrySellsByPeriod(String periodo){
        String querry = "SELECT "+ periodo +" (FechVenta) AS Periodo, SUM(PrecTotalVenta) AS Total FROM Venta GROUP BY "+ periodo +"(FechVenta) ORDER BY Periodo";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException exception){
            System.out.println("There is an error on querrySellsByPeriod");
            exception.printStackTrace();
        }

        return resultSet;
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
                idVenta = resultSet.getInt(1);
            }

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

        }catch (SQLException exception){
            System.out.println("There is an error on insertDetalle_venta()");
            exception.printStackTrace();
        }
    }

    public static void insertCliente(String nombre, String direccion, long telefono){
        String querry = "INSERT INTO Cliente(NbrCliente, DirCliente, TelCliente)" +
                        " VALUES(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, direccion);
            preparedStatement.setFloat(3, telefono);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("There is an error on insertCliente method");
            exception.printStackTrace();
        }
    }

    public static void insertProducto(long codigo, String nombre, float precio, int stock, String marca, String categoria, String cNeto){
        String querry = "INSERT INTO producto(NbrProducto, PrecProducto, StockProducto, MarcProducto, CatProducto, CNetoProducto, CodBarraProducto)" +
                        " VALUES(?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.setString(1, nombre);
            preparedStatement.setFloat(2, precio);
            preparedStatement.setInt(3, stock);
            preparedStatement.setString(4, marca);
            preparedStatement.setString(5, categoria);
            preparedStatement.setString(6, cNeto);
            preparedStatement.setLong(7, codigo);
            preparedStatement.executeUpdate();

        }catch (SQLException exception){
            System.out.println("There is an error on insertVenta method");
            exception.printStackTrace();
        }
    }

    public static void updateCliente(int id, String nombre,String direccion, long telefono){
        String querry = "UPDATE cliente SET NbrCliente = '"+ nombre +
                        "', DirCliente = '"+ direccion +
                        "', TelCliente = "+ telefono +
                        " WHERE IdCLiente = "+ id;
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("There is an error on updateCliente method");
            exception.printStackTrace();
        }
    }

    public static void updateProducto(int id, long codigo, String nombre, float precio, int stock, String marca, String categoria, String cNeto){
        String query = "UPDATE producto SET NbrProducto = ?, PrecProducto = ?, StockProducto = ?, MarcProducto = ?, CatProducto = ?, CNetoProducto = ?, CodBarraProducto = ? WHERE IdProducto = ?";
        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, nombre);
            preparedStatement.setFloat(2, precio);
            preparedStatement.setInt(3, stock);
            preparedStatement.setString(4, marca);
            preparedStatement.setString(5, categoria);
            preparedStatement.setString(6, cNeto);
            preparedStatement.setLong(7, codigo);
            preparedStatement.setInt(8, id);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("There is an error on updateProducto method");
            exception.printStackTrace();
        }
    }

    public static void deleteFromTable(String tableName, String columnName, int id){
        String querry = "DELETE FROM "+ tableName + " WHERE "+ columnName +" = ?";
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(querry);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("There is an error on deleteFromTable");
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
        String querry = "SELECT TotalVentasPorPeriodo('" + periodo + "')";
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

    public static ResultSet querryGroupByMonthVentas(){
        ResultSet resultSet = null;
        String query = """
                SELECT\s
                    COUNT(IdVenta) AS CantidadVentas,
                    MONTH(FechVenta) AS Mes,
                    YEAR(FechVenta) AS Año
                FROM\s
                    Venta
                GROUP BY\s
                    YEAR(FechVenta),
                    MONTH(FechVenta)
                ORDER BY\s
                    Año,
                    Mes;""";
        try{
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException exception){
            exception.printStackTrace();
        }

        return resultSet;
    }
}