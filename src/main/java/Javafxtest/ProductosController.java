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
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        ColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        Database.establishConnection();

        ResultSet resultSet = Database.querryAllFromTable("producto");
        loadProductData(resultSet);
    }

    private void loadProductData(ResultSet resultSet){
        ObservableList<Producto> data = FXCollections.observableArrayList();

        try{
            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString("IdProducto"));
                String nombre = resultSet.getString("NbrProducto");
                float precio = Float.parseFloat(resultSet.getString("PrecProducto"));
                int stock = Integer.parseInt(resultSet.getString("StockProducto"));
                String marca = resultSet.getString("MarcProducto");
                String categoria = resultSet.getString("CatProducto");
                String vencimiento = resultSet.getString("VencProducto");
                String ctoNeto = resultSet.getString("CNetoProducto");

                data.add(new Producto(id, nombre, precio, stock, marca, categoria, null, ctoNeto));
                System.out.println(
                        "Id: " + id + "  " +
                        "Nombre: " + nombre + "  " +
                        "Precio: " + precio + "  " +
                        "Stock: " + stock + "  " +
                        "Marca: " + marca + "  " +
                        "Categoria: " + categoria + "  "
                );
            }
        }catch (SQLException e){
            Database.closeConnection();
            e.printStackTrace();
        }

        Database.closeConnection();
    }


}
