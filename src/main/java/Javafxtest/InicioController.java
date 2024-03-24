package Javafxtest;

import DatabaseConnection.Database;
import DatabaseConnection.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioController{
    @FXML
    private TableView <Venta> tableVentas;
    @FXML
    private TableColumn<Venta, Integer> ColumnID;
    @FXML
    private TableColumn <Venta, String> ColumnNombre;
    @FXML
    private TableColumn <Venta, Float> ColumnPrecio;
    @FXML
    private TableColumn <Venta, String> ColumnTipoPago;
    @FXML
    private  TableColumn <Venta, Boolean> ColumnPagado;
    @FXML
    private TableColumn <Venta, Date> ColumnFecha;
    @FXML
    private TableColumn  ColumnEditar;

    @FXML
    private TextField searchBar;
    @FXML
    private Text daySellingsText;
    @FXML
    private Text monthSellingsText;

    @FXML
    private final NumberAxis xAxis = new NumberAxis();
    @FXML
    private final NumberAxis yAxis = new NumberAxis();
    @FXML
    private LineChart<Number, Number> graphChart = new LineChart<>(xAxis, yAxis);

    private final String [] columns = {"IdVenta", "venta.IdCliente", "NbrCliente", "PrecTotalVenta", "TPagoVenta", "PagVenta", "FechVenta"};

    public void initialize() {
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ColumnTipoPago.setCellValueFactory(new PropertyValueFactory<>("tipoPago"));
        ColumnPagado.setCellValueFactory(new PropertyValueFactory<>("pagado"));
        ColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        //Do not touch ↓↓↓
        ColumnEditar.setCellFactory(param ->{
            final TableCell<Venta, String> cell = new TableCell<>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    }else{
                        final Button editButton = new Button("Editar");
                        editButton.setOnAction(event -> {
                            Venta venta = getTableView().getItems().get(getIndex());
                            handleEditarButton();
                        });
                        setGraphic(editButton);
                        setText(null);
                    }
                }
            };
            return cell;
        });

        String venta = "venta";
        String cliente = "cliente";
        String leftJoin = "IdCliente";
        String rightJoin = "IdCliente";


        Database.establishConnection();
        ResultSet resultSet = Database.querryInnerJoin(columns, venta, cliente, leftJoin, rightJoin);
        ObservableList<Venta> ventas = loadVentasData(resultSet);
        daySellingsText.setText("$ "+ Database.countIncomeByPeriod("D"));
        monthSellingsText.setText("$ "+ Database.countIncomeByPeriod("M"));
        tableVentas.setItems(ventas);

        ResultSet sellsByPeriod = Database.querrySellsByPeriod("MONTH");
        loadLineChart(sellsByPeriod);
        Database.closeConnection();
    }

    @FXML
    public void handleAgregarButton(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModalsController/modalVentas.fxml"));
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(fxmlLoader.load()));
            secondStage.show();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    @FXML
    public void listenerSearchBar(){
        if(searchBar.getText() != ""){
            String textBar = searchBar.getText();
            ResultSet resultSet;
            Database.establishConnection();
            resultSet = Database.queryInnerJoinWhere(columns, "venta", "cliente", "IdCliente", "IdCliente", "NbrCliente", textBar);
            ObservableList <Venta> inputVentas = loadVentasData(resultSet);
            tableVentas.setItems(inputVentas);
            Database.closeConnection();
        }

    }

    public ObservableList<Venta> loadVentasData(ResultSet resultSet){
        ObservableList <Venta> data = FXCollections.observableArrayList();

        try{
            while (resultSet.next()){
                int idVenta = resultSet.getInt("IdVenta");
                int idCliente = resultSet.getInt("IdCliente");
                String nombreCliente = resultSet.getString("NbrCliente");
                float precio = resultSet.getFloat("PrecTotalVenta");
                String tipoPago = resultSet.getString("TPagoVenta");
                boolean pagado = resultSet.getBoolean("PagVenta");
                Date fecha = resultSet.getDate("FechVenta");

                data.add(new Venta(idVenta, idCliente, nombreCliente, precio, tipoPago, pagado, fecha));
            }
        }catch (SQLException exception){
            System.out.println("There is an error on method loadVentasData()");
            Database.closeConnection();
            exception.printStackTrace();
        }
        return data;
    }

    public void loadLineChart(ResultSet resultSet){
        XYChart.Series series = new XYChart.Series();
        try {
            while (resultSet.next()){
                int mes = resultSet.getInt(1);
                float total = resultSet.getFloat(2);

                series.getData().add(new XYChart.Data(mes, total));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        graphChart.getData().add(series);
    }

    protected void handleEditarButton(){


    }
}
