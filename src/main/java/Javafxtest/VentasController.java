package Javafxtest;

import DatabaseConnection.Database;
import DatabaseConnection.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentasController {
    @FXML
    private Text ventasTotales_Text;
    @FXML
    private TableView <Venta> tableVentas;
    @FXML
    private TableColumn <Venta, Integer> ColumnID;
    @FXML
    private TableColumn <Venta, String> ColumnNombre;
    @FXML
    private TableColumn <?, Button> ColumnCompra;
    @FXML
    private TableColumn <Venta, Float> ColumnPrecio;
    @FXML
    private TableColumn <Venta, String> ColumnTipoPago;
    @FXML
    private  TableColumn <Venta, Boolean> ColumnPagado;
    @FXML
    private TableColumn <Venta, Date> ColumnFecha;

    @FXML
    private Button agregarButton;

    public void initialize(){
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ColumnTipoPago.setCellValueFactory(new PropertyValueFactory<>("tipoPago"));
        ColumnPagado.setCellValueFactory(new PropertyValueFactory<>("pagado"));
        ColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        String [] columns = {"IdVenta", "venta.IdCliente", "NbrCliente", "PrecTotalVenta", "TPagoVenta", "PagVenta", "FechVenta"};
        String venta = "venta";
        String cliente = "cliente";
        String leftJoin = "IdCliente";
        String rightJoin = "IdCliente";

        Database.establishConnection();
        ResultSet resultSet = Database.querryInnerJoin(columns, venta, cliente, leftJoin, rightJoin);
        ObservableList <Venta> ventas = loadVentasData(resultSet);
        tableVentas.setItems(ventas);
        Database.closeConnection();

        handleTotalVentas_Dia();
    }

    @FXML
    protected void handleAgregarButton(ActionEvent event){
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
    protected void handleTotalVentas_Dia(){
        Database.establishConnection();
        int ventasDia =  Database.countSellsByPeriod("D");
        Database.closeConnection();
        ventasTotales_Text.setText(String.valueOf(ventasDia));
    }
    @FXML
    protected void handleTotalVentas_Mes(){
        Database.establishConnection();
        int ventasMes = Database.countSellsByPeriod("M");
        Database.closeConnection();
        ventasTotales_Text.setText(String.valueOf(ventasMes));
    }
    @FXML
    protected void handleTotalVentas_Año(){
        Database.establishConnection();
        int ventasAño = Database.countSellsByPeriod("A");
        Database.closeConnection();
        ventasTotales_Text.setText(String.valueOf(ventasAño));
    }

    public static ObservableList<Venta> loadVentasData(ResultSet resultSet){
        ObservableList <Venta> data = FXCollections.observableArrayList();

        try{
            while (resultSet.next()){
                int idVenta = resultSet.getInt("IdVenta");
                int idCliente = resultSet.getInt("IdCliente");
                String nombreCliente = resultSet.getString("NbrCliente");
                float precio = resultSet.getFloat("PrecTotalVenta");
                String tipoPago = resultSet.getString("TPagoVenta");
                boolean isPagado = resultSet.getBoolean("PagVenta");
                Date fecha = resultSet.getDate("FechVenta");

                data.add(new Venta(idVenta, idCliente, nombreCliente, precio, tipoPago, isPagado, fecha));
            }
        }catch (SQLException exception){
            System.out.println("There is an error on method loadVentasData()");
            Database.closeConnection();
            exception.printStackTrace();
        }
        return data;
    }


}
