package Javafxtest;

import DatabaseConnection.Database;
import DatabaseConnection.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProductosController {

    @FXML
   private TableView <Producto> tableProductos;
    @FXML
    private TableColumn <Producto, Integer> ColumnID;
    @FXML
    private TableColumn <Producto, Long> ColumnCodigo;
    @FXML
    private TableColumn <Producto, String> ColumnNombre;
    @FXML
    private  TableColumn <Producto, Float> ColumnPrecio;
    @FXML
    private TableColumn <Producto, Integer> ColumnStock;
    @FXML
    private TableColumn <Producto, String> ColumnMarca;
    @FXML
    private TableColumn <Producto, String> ColumnCategoria;
    @FXML
    private  TableColumn <Producto, Date> ColumnVencimiento;
    @FXML
    private TableColumn <Producto, String> ColumnCtoNeto;

    public void initialize() {
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("barCodigo"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        ColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        ColumnVencimiento.setCellValueFactory(new PropertyValueFactory<>("vencimiento"));
        ColumnCtoNeto.setCellValueFactory(new PropertyValueFactory<>("ctoNeto"));

        Database.establishConnection();

        ResultSet resultSet = Database.querryAllFromTable("producto");
        ObservableList <Producto> data = loadProductData(resultSet);

        tableProductos.setItems(data);

        Database.closeConnection();
    }

    private ObservableList<Producto> loadProductData(ResultSet resultSet){
        ObservableList<Producto> data = FXCollections.observableArrayList();

        try{
            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString("IdProducto"));
                long barCodigo = Long.parseLong(resultSet.getString("CodBarraProducto"));
                String nombre = resultSet.getString("NbrProducto");
                float precio = Float.parseFloat(resultSet.getString("PrecProducto"));
                int stock = Integer.parseInt(resultSet.getString("StockProducto"));
                String marca = resultSet.getString("MarcProducto");
                String categoria = resultSet.getString("CatProducto");
                java.sql.Date sqlVencimiento = resultSet.getDate("VencProducto");
                Date vencimiento = sqlVencimiento != null ? new Date(sqlVencimiento.getTime()) : null;
                String ctoNeto = resultSet.getString("CNetoProducto");

                data.add(new Producto(id, barCodigo, nombre, precio, stock, marca, categoria, vencimiento, ctoNeto));
            }
        }catch (SQLException e){
            Database.closeConnection();
            e.printStackTrace();
        }
        return data;
    }


}
