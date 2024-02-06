package Javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class VentasController {
    @FXML
    Button agregarButton;


    @FXML
    protected void handleAgregarButton(ActionEvent event){
        System.out.println("AgregarButton presionado");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modals/modalVentas.fxml"));
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(fxmlLoader.load()));
            secondStage.show();
            System.out.println("Second stage agregada");
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

}
