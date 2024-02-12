package Javafxtest;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioController implements Initializable {

    @FXML
    private TextField searchBar;
    @FXML
    private MenuButton filterButton;
    @FXML
    private Text daySellingsText;
    @FXML
    private Text monthSellingsText;
    @FXML
    private LineChart graphChart;
    @FXML
    private TableView tableVentas;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listenerSearchBar();

    }

    public void listenerSearchBar(){
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {

        });
    }
}
