package com.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

public class GUIController {
    @FXML
    private InicioController inicioController;
    @FXML
    private VentasController ventasController;


    //Side menu buttons
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnVentas;
    @FXML
    private Button btnProductos;
    @FXML
    private Button btnCompras;
    @FXML
    private Button btnCalendario;

    //Tabs elementes
    @FXML
    private TabPane mainTabPane;



    @FXML
    protected void handleBtnInicio(ActionEvent event){
        System.out.println(mainTabPane.getTabs().get(0).getId());
        mainTabPane.getSelectionModel().select(0);
    }
    @FXML
    protected void handleBtnVentas(ActionEvent event){
        System.out.println(mainTabPane.getTabs().get(1).getId());
        mainTabPane.getSelectionModel().select(1);
    }
    @FXML
    protected void handleBtnProductos(ActionEvent event){
        System.out.println(mainTabPane.getTabs().get(2).getId());
        mainTabPane.getSelectionModel().select(2);
    }
    @FXML
    protected void handleBtnCompras(ActionEvent event){
        System.out.println(mainTabPane.getTabs().get(3).getId());
        mainTabPane.getSelectionModel().select(3);
    }
    @FXML
    protected void handleCalendario(ActionEvent event){
        System.out.println(mainTabPane.getTabs().get(4).getId());
        mainTabPane.getSelectionModel().select(4);
    }

}