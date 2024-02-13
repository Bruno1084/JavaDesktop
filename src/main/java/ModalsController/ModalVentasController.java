package ModalsController;

import DatabaseConnection.Database;
import DatabaseConnection.ModalDetalle_venta;
import DatabaseConnection.Producto;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
    private final List<Producto> productos = new ArrayList<>();


    public void initialize(){
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

    }

    @FXML
    protected void handleButtonAgregarProducto(){
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
        textFieldStockDisp.setText("");
    }

    @FXML
    protected void handleTextFieldProducto(){
        System.out.println(textFieldProducto.getText());
        Database.establishConnection();
        productoContextMenu.getItems().clear();

        querrySearchedProductos();

        Database.closeConnection();
        onClickTextFieldProducto();
    }

    @FXML
    protected void onClickTextFieldProducto(){
        productoContextMenu.show(textFieldProducto, Side.BOTTOM, 0, 0);
    }


    protected void querrySearchedProductos(){
        try{
            ResultSet resultSet = Database.querryWhereContains("producto", "NbrProducto", textFieldProducto.getText());

            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString("IdProducto"));
                long barCodigo = Long.parseLong(resultSet.getString("CodBarraProducto"));
                String nombre = resultSet.getString("NbrProducto");
                float precio = Float.parseFloat(resultSet.getString("PrecProducto"));
                int stock = Integer.parseInt(resultSet.getString("StockProducto"));
                String marca = resultSet.getString("MarcProducto");
                String categoria = resultSet.getString("CatProducto");
                String ctoNeto = resultSet.getString("CNetoProducto");

                Producto producto = new Producto(id, barCodigo, nombre, precio, stock, marca, categoria, ctoNeto);
                productos.add(producto);

                CustomMenuItem itemProducto = new CustomMenuItem(producto.getNombre(), producto);
                itemProducto.setOnAction(event -> {
                    textFieldProducto.setText(producto.getNombre());
                    textFieldPrecio.setText(String.valueOf(producto.getPrecio()));
                    textFieldCodigo.setText(String.valueOf(producto.getBarCodigo()));
                    textFieldStockDisp.setText(String.valueOf(producto.getStock()));
                });
                productoContextMenu.getItems().add(itemProducto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
