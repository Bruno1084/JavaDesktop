package ModalsController;

import DatabaseConnection.*;
import Javafxtest.VentasController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModalDetalleVentaController {
    @FXML
    private TableView <ModalDetalle_venta> tableDetalleProductos;
    @FXML
    private TableColumn <ModalDetalle_venta, Long> columnCodigo;
    @FXML
    private TableColumn <ModalDetalle_venta, String> columnNombre;
    @FXML
    private TableColumn <ModalDetalle_venta, String> columnMarca;
    @FXML
    private TableColumn <ModalDetalle_venta, Integer> columnCantidad;
    @FXML
    private TableColumn <ModalDetalle_venta, Float> columnPrecio;

    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDireccion;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private TextField textFieldPago;
    @FXML
    private TextField textFieldPagado;
    private Venta venta;


    public void initialize(){
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marcaProducto"));
        columnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));

    }

    public ObservableList<ModalDetalle_venta> loadTableDetalle_ventas(ResultSet resultSet){
        ObservableList <ModalDetalle_venta> data = FXCollections.observableArrayList();

        try{
            while (resultSet.next()){
                long codigo = resultSet.getLong("CodBarraProducto");
                String nombre = resultSet.getString("NbrProducto");
                String marca = resultSet.getString("MarcProducto");
                int cantidad = resultSet.getInt("CantVenta_detalle");
                float precio = cantidad * resultSet.getFloat("PrecProducto");


                ModalDetalle_venta modalDetalleVenta = new ModalDetalle_venta(codigo, nombre, cantidad, precio, marca);
                data.add(modalDetalleVenta);
            }
        }catch (SQLException exception){
            System.out.println("There is an error on method loadVentasData()");
            Database.closeConnection();
            exception.printStackTrace();
        }
        return data;
    }

    public void loadClienteInfo(){
        Database.establishConnection();
        ResultSet clienteVenta = Database.querryWhereEquals("cliente", "IdCliente", String.valueOf(venta.getIdCliente()));
        try{
            clienteVenta.next();
            textFieldDireccion.setText(clienteVenta.getString("DirCliente"));
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        Database.closeConnection();
        textFieldID.setText(String.valueOf(venta.getId()));
        textFieldNombre.setText(venta.getNombreCliente());
        textFieldPrecio.setText(String.valueOf(venta.getPrecio()));
        textFieldPago.setText(venta.getTipoPago());
        textFieldPagado.setText(String.valueOf(venta.getPagado()));
    }

    public void setVenta(Venta venta){
        this.venta = venta;

        String [] columns = {"CantVenta_detalle", "CodBarraProducto", "NbrProducto", "MarcProducto", "PrecProducto"};
        Database.establishConnection();
        ResultSet resultSet = Database.queryInnerJoinWhereEquals(columns, "detalle_venta", "producto", "IdProducto", "IdProducto", "IdVenta", String.valueOf(venta.getId()));
        ObservableList <ModalDetalle_venta> data = loadTableDetalle_ventas(resultSet);
        tableDetalleProductos.setItems(data);
        Database.closeConnection();

        loadClienteInfo();
    }
}