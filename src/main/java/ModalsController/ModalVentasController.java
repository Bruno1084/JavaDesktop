package ModalsController;

import DatabaseConnection.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private TextField textFieldPrecio;
    @FXML
    private TextField textFieldCantidad;
    @FXML
    private TextField textFieldStockDisp;
    @FXML
    private TextField textFieldMarca;
    @FXML
    private TextField textFieldClienteId;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldTotalPagar;
    @FXML
    private TextField textFieldDireccion;
    @FXML
    private TextField textFieldPago;
    @FXML
    private ContextMenu productoContextMenu;
    @FXML
    private ContextMenu clienteContextMenu;
    @FXML
    private ContextMenu pagoContextMenu;
    @FXML
    private Button buttonHacerCompra;
    @FXML
    private Button buttonAgregarProducto;
    @FXML
    private TableColumn <ModalDetalle_venta, Long> ColumnCodigo;
    @FXML
    private TableColumn <ModalDetalle_venta, String> ColumnNombre;
    @FXML
    private TableColumn <ModalDetalle_venta, Integer> ColumnCantidad;
    @FXML
    private TableColumn <ModalDetalle_venta, Float> ColumnPrecio;
    @FXML
    private TableColumn <ModalDetalle_venta, String> ColumnMarca;
    @FXML
    private TableView<ModalDetalle_venta> tableProductos;

    private final List<Producto> productos = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();


    public void initialize(){
        ColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        ColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        ColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marcaProducto"));

    }

    @FXML
    protected void handleButtonAgregarProducto(){
        String codigoProductoText = textFieldCodigo.getText();
        String nombreProducto = textFieldProducto.getText();
        String cantidadProductoText = textFieldCantidad.getText();
        String precioProductoText = textFieldPrecio.getText();
        String marcaProducto = textFieldMarca.getText();

        if (codigoProductoText.isEmpty() || nombreProducto.isEmpty() || cantidadProductoText.isEmpty() || precioProductoText.isEmpty() || marcaProducto.isEmpty()) {
            return;
        }

        long codigoProducto = Long.parseLong(codigoProductoText);
        int cantidadproducto = Integer.parseInt(cantidadProductoText);
        float precioProducto = Float.parseFloat(precioProductoText);

        ModalDetalle_venta detalle_venta = new ModalDetalle_venta(codigoProducto, nombreProducto, cantidadproducto, precioProducto, marcaProducto);
        tableProductos.getItems().add(detalle_venta);

        textFieldCodigo.setText("");
        textFieldProducto.setText("");
        textFieldCantidad.setText("");
        textFieldPrecio.setText("");
        textFieldStockDisp.setText("");
        textFieldMarca.setText("");

        calculateTotalPrice();
    }

    @FXML
    protected void handleButtonHacerCompra(){
        int idCliente = Integer.parseInt(textFieldClienteId.getText());
        String nombreCliente = textFieldNombre.getText();
        String dirCliente = textFieldDireccion.getText();

        if (textFieldClienteId.getText().isEmpty() || textFieldNombre.getText().isEmpty() || textFieldDireccion.getText().isEmpty()){
            return;
        }

        Cliente cliente = new Cliente(idCliente, nombreCliente, dirCliente);

        textFieldClienteId.setText("");
        textFieldNombre.setText("");
        textFieldDireccion.setText("");
    }

    @FXML
    protected void handleTextFieldProducto(){
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

    @FXML
    protected void handleTextFieldNombre(){
        Database.establishConnection();
        clienteContextMenu.getItems().clear();

        querrySearchedClientes();

        Database.closeConnection();
        onClickTextFieldCliente();
    }

    @FXML
    protected void onClickTextFieldCliente(){
        clienteContextMenu.show(textFieldNombre, Side.BOTTOM, 0, 0);
    }

    @FXML
    protected void onClickTextFieldPago(){
        pagoContextMenu.show(textFieldPago, Side.BOTTOM, 0, 0);

        pagoContextMenu.getItems().forEach(menuItem -> {
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    textFieldPago.setText(menuItem.getText());
                }
            });
        });
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
                    textFieldMarca.setText(producto.getMarca());
                });
                productoContextMenu.getItems().add(itemProducto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected void querrySearchedClientes(){
        try{
            ResultSet resultSet = Database.querryWhereContains("cliente", "NbrCliente", textFieldNombre.getText());

            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString("IdCliente"));
                String nombre = resultSet.getString("NbrCliente");
                String direccion = resultSet.getString("DirCliente");

                Cliente cliente = new Cliente(id, nombre, direccion);
                clientes.add(cliente);

                CustomMenuItem itemCliente = new CustomMenuItem(cliente.getNombre(), cliente);
                itemCliente.setOnAction(event -> {
                    textFieldClienteId.setText(String.valueOf(cliente.getIdCliente()));
                    textFieldNombre.setText(cliente.getNombre());
                    if (cliente.getDireccion() == null){
                        textFieldDireccion.setText("");
                    }else{
                        textFieldDireccion.setText(cliente.getDireccion());
                    }
                });
                clienteContextMenu.getItems().add(itemCliente);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    protected void calculateTotalPrice(){
        float totalPrice = 0;
        ObservableList<ModalDetalle_venta> listProductos = tableProductos.getItems();
        for (ModalDetalle_venta detalleVenta: listProductos){
            totalPrice += detalleVenta.getPrecioProducto() * detalleVenta.getCantidadProducto();
        }
        textFieldTotalPagar.setText(String.valueOf(totalPrice));
    }

    protected void createVentaRecord(){
        int idCliente = Integer.parseInt(textFieldClienteId.getText());
        String nombreCliente = textFieldNombre.getText();
        String dirCliente = textFieldDireccion.getText();
        float precioTotal = Float.parseFloat(textFieldTotalPagar.getText());

        //Venta venta = new Venta(0, idCliente, precioTotal, );
    }
}