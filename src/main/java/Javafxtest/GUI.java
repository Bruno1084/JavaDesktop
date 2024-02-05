package Javafxtest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class GUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("GUI-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Desktop App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //Database.establishConnection();
        //Database.querryAllFromTable("producto");
        //Database.closeConnection();
        launch();

    }
}