package ModalsController;

import DatabaseConnection.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static DatabaseConnection.Database.insertDetalle_venta;


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
    private TableColumn  ColumnEditar;
    @FXML
    private TableView<ModalDetalle_venta> tableProductos;
    @FXML
    private Button buttonEditarDetalle;
    @FXML
    private Button buttonEliminarDetalle;

    private final List<Producto> productos = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();


    public void initialize(){
        ColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        ColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        ColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marcaProducto"));

        //Do not touch ↓↓↓
        Callback<TableColumn.CellDataFeatures<ModalDetalle_venta, String>, ObservableValue<String>> cellFactory = new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModalDetalle_venta, String> param) {
                // return an ObservableValue<String>
                return new SimpleStringProperty(param.getValue().getNombreProducto());
            }
        };

        ColumnEditar.setCellValueFactory(cellFactory);
        ColumnEditar.setCellFactory(param -> {
            final TableCell<ModalDetalle_venta, String> cell = new TableCell<>(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);

                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }else{
                        final Button editButton = new Button("Eliminar");
                        editButton.setOnAction(event ->{

                            ModalDetalle_venta modalDetalleVenta = getTableView().getItems().get(getIndex());
                            tableProductos.getItems().remove(modalDetalleVenta);
                            tableProductos.refresh();
                        });
                        setGraphic(editButton);
                        setText(null);
                    }
                }
            };
            return cell;
        });

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
        int idVenta;

        if (textFieldClienteId.getText().isEmpty() || textFieldNombre.getText().isEmpty() || textFieldDireccion.getText().isEmpty()){
            return;
        }

        Cliente cliente = new Cliente(idCliente, nombreCliente, dirCliente);
        idVenta = createVentaRecord(cliente);
        createDetalle_ventaRecord(idVenta);

        textFieldClienteId.setText("");
        textFieldNombre.setText("");
        textFieldDireccion.setText("");
        textFieldTotalPagar.setText("");
        textFieldPago.setText("");
        tableProductos.getItems().clear();
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

        pagoContextMenu.getItems().forEach(menuItem -> menuItem.setOnAction(event -> textFieldPago.setText(menuItem.getText())));
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

    protected int createVentaRecord(Cliente cliente){
        float precioTotal = Float.parseFloat(textFieldTotalPagar.getText());
        String tipoPago = textFieldPago.getText();
        int idVenta;
        Date fecha = new Date(System.currentTimeMillis());


        Venta venta = new Venta(0, cliente.getIdCliente(), cliente.getNombre(), precioTotal, tipoPago, false, fecha);

        Database.establishConnection();
        idVenta = Database.insertVenta(cliente.getIdCliente(), precioTotal, tipoPago, false, venta.getFecha());
        Database.closeConnection();

        return idVenta;
    }

    protected void createDetalle_ventaRecord(int idVenta){
        Database.establishConnection();
        tableProductos.getItems().forEach(producto -> {
            ResultSet resultSet = Database.querryWhereContains("producto", "CodBarraProducto", String.valueOf(producto.getCodigoProducto()));

            try{
                resultSet.next();
                int idProducto = resultSet.getInt(1);
                insertDetalle_venta(idVenta, idProducto, producto.getCantidadProducto());
            }catch (SQLException exception){
                System.out.println("There is an error in createDetalle_ventaRecord()");
                Database.closeConnection();
                exception.printStackTrace();
            }
        });
        Database.closeConnection();
    }
}