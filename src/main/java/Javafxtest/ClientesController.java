package Javafxtest;

import DatabaseConnection.Cliente;
import DatabaseConnection.Database;
import DatabaseConnection.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientesController {
    @FXML
    private TableView <Cliente> tableClientes;
    @FXML
    private TableColumn <Cliente, Integer> ColumnIDCliente;
    @FXML
    private TableColumn <Cliente, String> ColumnNombreCliente;
    @FXML
    private TableColumn <Cliente, String> ColumnDireccionCliente;
    @FXML
    private TableColumn <Cliente, Long> ColumnTelefonoCliente;

    @FXML
    private TableView <Venta> tableVentas;
    @FXML
    private TableColumn <Venta, Integer> ColumnIDVenta;
    @FXML
    private TableColumn <Venta, Float> ColumnPrecioVenta;
    @FXML
    private TableColumn <Venta, String> ColumnTipoPagoVenta;
    @FXML
    private TableColumn <Venta, Boolean> ColumnPagadoVenta;
    @FXML
    private TableColumn <Venta, Date> ColumnFechaVenta;

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private TextField textFieldDireccion;
    @FXML
    private TextField textFieldID;

    @FXML
    private Button btnEditarCliente;
    @FXML
    private Button btnAniadirCliente;
    @FXML
    private Button btnEliminarCliente;


    public void initialize(){
        ColumnIDCliente.setCellValueFactory(new PropertyValueFactory<>("IdCliente"));
        ColumnNombreCliente.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        ColumnDireccionCliente.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        ColumnTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("Telefono"));

        ColumnIDVenta.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ColumnPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        ColumnTipoPagoVenta.setCellValueFactory(new PropertyValueFactory<>("TipoPago"));
        ColumnPagadoVenta.setCellValueFactory(new PropertyValueFactory<>("Pagado"));
        ColumnFechaVenta.setCellValueFactory(new PropertyValueFactory<>("Fecha"));

        Database.establishConnection();
        ResultSet resultSet = Database.querryAllFromTable("cliente");
        ObservableList<Cliente> data = loadClientesData(resultSet);
        Database.closeConnection();
        tableClientes.setItems(data);

        tableClientes.setOnMouseClicked(event ->{
            if(!tableClientes.getSelectionModel().isEmpty()){
                Cliente cliente = tableClientes.getSelectionModel().getSelectedItem();
                textFieldNombre.setText(cliente.getNombre());
                textFieldTelefono.setText(String.valueOf(cliente.getTelefono()));
                textFieldDireccion.setText(cliente.getDireccion());
                textFieldID.setText(String.valueOf(cliente.getIdCliente()));

                Database.establishConnection();
                ResultSet clienteVentas = Database.querryWhereEquals("venta", "IdCliente", String.valueOf(cliente.getIdCliente()));
                ObservableList<Venta> dataVenta = loadVentasData(clienteVentas);
                Database.closeConnection();
                tableVentas.setItems(dataVenta);
            }
        });
    }

    @FXML
    public void handleBtnAniadirCliente(){
        if (textFieldNombre.getText().isEmpty() || textFieldDireccion.getText().isEmpty() || textFieldTelefono.getText().isEmpty()){
            return;
        }

        String nombre = textFieldNombre.getText();
        String direccion = textFieldDireccion.getText();
        long numero = Long.parseLong(textFieldTelefono.getText());

        Database.establishConnection();
        Database.insertCliente(nombre, direccion, numero);
        ResultSet resultSet = Database.querryAllFromTable("cliente");
        ObservableList<Cliente> data = loadClientesData(resultSet);
        tableClientes.setItems(data);
        Database.closeConnection();

        textFieldNombre.setText("");
        textFieldDireccion.setText("");
        textFieldTelefono.setText("");
        textFieldID.setText("");
    }

    @FXML
    public void handleBtnEditarCliente(){
        if(!textFieldID.getText().isEmpty() && !textFieldNombre.getText().isEmpty()){
            int id = Integer.parseInt(textFieldID.getText());
            String nombre = textFieldNombre.getText();
            String direccion = textFieldDireccion.getText();
            long telefono = Long.parseLong(textFieldTelefono.getText());

            Database.establishConnection();
            Database.updateCliente(id, nombre, direccion, telefono);
            ResultSet resultSet = Database.querryAllFromTable("cliente");
            ObservableList<Cliente> data = loadClientesData(resultSet);
            tableClientes.setItems(data);
            Database.closeConnection();
        }
    }

    @FXML
    public void handleBtnEliminarCliente(){
        if(!textFieldID.getText().isEmpty() && !textFieldNombre.getText().isEmpty()){
            int id = Integer.parseInt(textFieldID.getText());
            Database.establishConnection();
            Database.deleteFromTable("cliente", "IdCliente", id);
            ResultSet resultSet = Database.querryAllFromTable("cliente");
            ObservableList<Cliente> data = loadClientesData(resultSet);
            tableClientes.setItems(data);
            Database.closeConnection();

            textFieldNombre.setText("");
            textFieldID.setText("");
            textFieldTelefono.setText("");
            textFieldDireccion.setText("");
        }
    }

    public ObservableList<Cliente> loadClientesData(ResultSet resultSet){
        ObservableList<Cliente> data = FXCollections.observableArrayList();

        try {
            while (resultSet.next()){
                int id = resultSet.getInt("IdCLiente");
                String nombre = resultSet.getString("NbrCliente");
                String direccion = resultSet.getString("DirCliente");
                long telefono = resultSet.getLong("TelCliente");
                Cliente cliente = new Cliente(id, nombre, direccion);
                cliente.setTelefono(telefono);
                data.add(cliente);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return data;
    }

    public ObservableList <Venta> loadVentasData(ResultSet resultSet){
        ObservableList<Venta> data = FXCollections.observableArrayList();

        try {
            while (resultSet.next()){
                int id = resultSet.getInt("IdVenta");
                float precio = resultSet.getFloat("PrecTotalVenta");
                String tipoPago = resultSet.getString("TPagoVenta");
                boolean isPagado = resultSet.getBoolean("PagVenta");
                Date fecha = resultSet.getDate("FechVenta");

                data.add(new Venta(id, Integer.valueOf(textFieldID.getText()), textFieldNombre.getText(), precio, tipoPago, isPagado, fecha));
            }
        }catch (SQLException exception){
            exception.getErrorCode();
        }
        return data;
    }

}
