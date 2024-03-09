package Javafxtest;

import DatabaseConnection.Database;
import DatabaseConnection.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class ProductosController {
    @FXML
    PieChart pieChartProductos;
    @FXML
    private TableView <Producto> tableProductos;
    @FXML
    private TableColumn <Producto, Integer> ColumnID;
    @FXML
    private TableColumn <Producto, Long> ColumnCodigo;
    @FXML
    private TableColumn <Producto, String> ColumnNombre;
    @FXML
    private TableColumn <Producto, Float> ColumnPrecio;
    @FXML
    private TableColumn <Producto, Integer> ColumnStock;
    @FXML
    private TableColumn <Producto, String> ColumnMarca;
    @FXML
    private TableColumn <Producto, String> ColumnCategoria;
    @FXML
    private TableColumn <Producto, String> ColumnCtoNeto;
    @FXML
    private GridPane gridCategoria;

    public void initialize() {
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("barCodigo"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        ColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        ColumnCtoNeto.setCellValueFactory(new PropertyValueFactory<>("ctoNeto"));

        Database.establishConnection();
        ResultSet resultSet = Database.querryAllFromTable("producto");
        ObservableList <Producto> data = loadProductData(resultSet);
        tableProductos.setItems(data);

        ResultSet pieProductos = Database.querryProductsByGroup();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        loadPieChartProduct(pieChartData, pieProductos);


        loadProductsCategories(Database.querryProductsByGroup());

        Database.closeConnection();

    }

    private ObservableList<Producto> loadProductData(ResultSet resultSet){
        ObservableList<Producto> data = FXCollections.observableArrayList();

        try{
            while (resultSet.next()){
                int id = resultSet.getInt("IdProducto");
                long barCodigo = resultSet.getLong("CodBarraProducto");
                String nombre = resultSet.getString("NbrProducto");
                float precio = resultSet.getLong("PrecProducto");
                int stock = resultSet.getInt("StockProducto");
                String marca = resultSet.getString("MarcProducto");
                String categoria = resultSet.getString("CatProducto");
                String ctoNeto = resultSet.getString("CNetoProducto");

                data.add(new Producto(id, barCodigo, nombre, precio, stock, marca, categoria, ctoNeto));

            }
        }catch (SQLException e){
            Database.closeConnection();
            e.printStackTrace();
        }
        return data;
    }

    private void loadPieChartProduct(ObservableList<PieChart.Data> pieChartData, ResultSet resultSet){
        try {
            while (resultSet.next()){
                String categoria = resultSet.getString(1);
                int cantProductos = resultSet.getInt(2);

                pieChartData.add(new PieChart.Data(categoria, cantProductos));
            }
            pieChartProductos.setData(pieChartData);

        }catch (SQLException exception){
            System.out.println("There is an error on loadPieChartProduct method");
            exception.printStackTrace();
        }

    }

    private void loadProductsCategories(ResultSet resultSet){
        int i = 0;
        int j = 0;

        try{
            while (resultSet.next() && i < gridCategoria.getRowCount() && j < gridCategoria.getColumnCount()){
                String categoria = resultSet.getString(1);
                long cantidad = resultSet.getLong(2);

                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);

                Text txtCategoria = new Text(categoria);
                Text txtCantidad = new Text(String.valueOf(cantidad));

                box.getChildren().add(txtCategoria);
                box.getChildren().add(txtCantidad);

                gridCategoria.add(box, j, i);

                j++;
                if (j >= gridCategoria.getColumnCount()) {
                    j = 0;
                    i++;
                }
            }

        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }



}
