package Javafxtest;

import DatabaseConnection.Database;
import DatabaseConnection.Producto;
import ModalsController.ProductoModalController;
import Observers.DatabaseObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductosController implements DatabaseObserver {
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
    private TableColumn  ColumnEditar;
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

        //Do not touch ↓↓↓
        ColumnEditar.setCellFactory(param ->{
            final TableCell<Producto, String> cell = new TableCell<>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    }else{
                        final Button editButton = new Button("Editar");
                        editButton.setOnAction(event -> {
                            Producto producto = getTableView().getItems().get(getIndex());
                            handleEditarButton(producto);
                        });
                        setGraphic(editButton);
                        setText(null);
                    }
                }
            };
            return cell;
        });

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

    @Override
    public void update() {
        tableProductos.refresh();
    }

    @FXML
    protected void handleAgregarButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModalsController/modalProducto.fxml"));
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(fxmlLoader.load()));

            secondStage.show();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    protected void handleEditarButton(Producto producto){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModalsController/modalProducto.fxml"));
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(fxmlLoader.load()));

            ProductoModalController controller = fxmlLoader.getController();
            controller.loadProducto(producto);

            fxmlLoader.setController(controller);
            secondStage.setResizable(false);
            secondStage.show();
        }catch (IOException exception){
            exception.printStackTrace();
        }
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
        String[] colors = {"#E06032", "#E49921", "#58AF58", "#44A3BE", "#3C51B7"};
        int indexColor=0;
        try{
            while (resultSet.next() && i < gridCategoria.getRowCount() && j < gridCategoria.getColumnCount()){
                String categoria = resultSet.getString(1);
                long cantidad = resultSet.getLong(2);

                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                box.setStyle("-fx-background-color: "+ colors[indexColor] +"; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-size: 14px;");

                Text txtCategoria = new Text(categoria);
                Text txtCantidad = new Text(String.valueOf(cantidad));

                box.getChildren().add(txtCategoria);
                box.getChildren().add(txtCantidad);
                gridCategoria.add(box, j, i);

                indexColor++;
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
