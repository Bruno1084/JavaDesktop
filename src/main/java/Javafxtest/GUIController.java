package Javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

public class GUIController {
    @FXML
    private InicioController inicioController;
    @FXML
    private VentasController ventasController;
    @FXML
    private ProductosController productosController;
    @FXML
    private ClientesController clientesController;
    @FXML
    private CalendarioController calendarioController;


    //Side menu buttons
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnVentas;
    @FXML
    private Button btnProductos;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnCalendario;

    //Tabs elementes
    @FXML
    private TabPane mainTabPane;



    @FXML
    protected void handleBtnInicio(ActionEvent event){
        mainTabPane.getSelectionModel().select(0);
    }
    @FXML
    protected void handleBtnVentas(ActionEvent event){
        mainTabPane.getSelectionModel().select(1);
    }
    @FXML
    protected void handleBtnProductos(ActionEvent event){
        mainTabPane.getSelectionModel().select(2);
    }
    @FXML
    protected void handleBtnClientes(ActionEvent event){
        mainTabPane.getSelectionModel().select(3);
    }
    @FXML
    protected void handleCalendario(ActionEvent event){
        mainTabPane.getSelectionModel().select(4);
    }

}