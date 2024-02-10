package ModalsController;

import DatabaseConnection.Database;
import DatabaseConnection.Detalle_venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;


public class ModalVentasController{
    @FXML
    private TextField textFieldCodigo;
    @FXML
    private TextField textFieldProducto;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private TextField textFieldCantidad;
    @FXML
    private TextField textFieldStockDisp;
    @FXML
    private TextField textFieldClienteId;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldTotalPagar;
    @FXML
    private Button buttonHacerCompra;
    @FXML
    private Button buttonAgregarProducto;

    @FXML
    private TableColumn <Detalle_venta, Integer> ColumnID;
    @FXML
    private TableColumn <Detalle_venta, Long> ColumnCodigo;
    @FXML
    private TableColumn <Detalle_venta, String> ColumnNombre;
    @FXML
    private TableColumn <Detalle_venta, Integer> ColumnCantidad;
    @FXML
    private TableColumn <Detalle_venta, Float> ColumnPrecio;
    @FXML
    private TableView<Detalle_venta> tableProducto;

    private final ObservableList<Detalle_venta> ventasData = FXCollections.observableArrayList();
    private final ObservableList<String> productNames = FXCollections.observableArrayList();


    public void initialize(){
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tableProducto.setItems(ventasData);

        Database.establishConnection();
        ResultSet resultSetProductos = Database.querryAllFromTable("producto");
        Database.closeConnection();
    }

    @FXML
    protected void handleButtonAgregarProducto(ActionEvent event){
        long codigoProducto = Long.parseLong(textFieldCodigo.getText());
        String nombreProducto = textFieldProducto.getText();
        int cantidadProducto = Integer.parseInt(textFieldCantidad.getText());
        float precioProducto = Float.parseFloat(textFieldPrecio.getText());

        textFieldCodigo.setText("");
        textFieldProducto.setText("");
        textFieldCantidad.setText("");
        textFieldPrecio.setText("");
    }

    @FXML
    protected void handleTextFieldProducto(){

    }

    public TextField getTextFieldCodigo() {
        return textFieldCodigo;
    }

    public TextField getTextFieldProducto() {
        return textFieldProducto;
    }

    public TextField getTextFieldPrecio() {
        return textFieldPrecio;
    }

    public TextField getTextFieldCantidad() {
        return textFieldCantidad;
    }

    public TextField getTextFieldStockDisp() {
        return textFieldStockDisp;
    }

    public TextField getTextFieldClienteId() {
        return textFieldClienteId;
    }

    public TextField getTextFieldNombre() {
        return textFieldNombre;
    }

    public TextField getTextFieldTotalPagar() {
        return textFieldTotalPagar;
    }
}
