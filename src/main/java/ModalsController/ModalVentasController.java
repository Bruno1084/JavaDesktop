package ModalsController;

import DatabaseConnection.Database;
import DatabaseConnection.ModalDetalle_venta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;


public class ModalVentasController{
    @FXML
    private TextField textFieldCodigo;
    @FXML
    private TextField textFieldProducto;
    @FXML
    private ContextMenu productoContextMenu;
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
    private TableColumn <ModalDetalle_venta, Integer> ColumnID;
    @FXML
    private TableColumn <ModalDetalle_venta, Long> ColumnCodigo;
    @FXML
    private TableColumn <ModalDetalle_venta, String> ColumnNombre;
    @FXML
    private TableColumn <ModalDetalle_venta, Integer> ColumnCantidad;
    @FXML
    private TableColumn <ModalDetalle_venta, Float> ColumnPrecio;
    @FXML
    private TableView<ModalDetalle_venta> tableProductos;
    private ResultSet productos;




    public void initialize(){
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));


    }

    @FXML
    protected void handleButtonAgregarProducto(ActionEvent event){
        String codigoProductoText = textFieldCodigo.getText();
        String nombreProducto = textFieldProducto.getText();
        String cantidadProductoText = textFieldCantidad.getText();
        String precioProductoText = textFieldPrecio.getText();

        if (codigoProductoText.isEmpty() || nombreProducto.isEmpty() || cantidadProductoText.isEmpty() || precioProductoText.isEmpty()) {
            return;
        }

        long codigoProducto = Long.parseLong(codigoProductoText);
        int cantidadProducto = Integer.parseInt(cantidadProductoText);
        float precioProducto = Float.parseFloat(precioProductoText);

        textFieldCodigo.setText("");
        textFieldProducto.setText("");
        textFieldCantidad.setText("");
        textFieldPrecio.setText("");
    }

    @FXML
    protected void handleTextFieldProducto(){
        Database.establishConnection();
        int cantProductos = Database.querryCountRows("producto", "NbrProducto", textFieldProducto.getText());
        System.out.println(cantProductos);
        Database.closeConnection();
    }

    @FXML
    protected void onClickTextFieldProducto(){
        productoContextMenu.show(textFieldProducto, Side.BOTTOM, 0, 0);
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

    public ContextMenu getProductoContextMenu() {
        return productoContextMenu;
    }
}
