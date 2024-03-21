package ModalsController;

import DatabaseConnection.Database;
import DatabaseConnection.Producto;
import Javafxtest.ProductosController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ProductoModalController {
    @FXML
    private TextField textFieldCodigo;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private TextField textFieldStock;
    @FXML
    private TextField textFieldMarca;
    @FXML
    private TextField textFieldCategoria;
    @FXML
    private TextField textFieldCNeto;
    @FXML
    private Button buttonEditar;
    @FXML
    private Button buttonAñadir;
    private Producto producto;
    private ProductosController controladorPadre;
    public void initialize() {

    }


    @FXML
    public void insertProducto() {
        if (textFieldNombre.getText().isEmpty() || textFieldCodigo.getText().isEmpty() || textFieldPrecio.getText().isEmpty() || textFieldStock.getText().isEmpty() || textFieldMarca.getText().isEmpty() || textFieldCategoria.getText().isEmpty() || textFieldCNeto.getText().isEmpty()) {
            return;
        }

        Database.establishConnection();
        Database.insertProducto(Long.parseLong(textFieldCodigo.getText()), textFieldNombre.getText(), Float.parseFloat(textFieldPrecio.getText()), Integer.parseInt(textFieldStock.getText()), textFieldMarca.getText(), textFieldCategoria.getText(), textFieldCNeto.getText());
        Database.closeConnection();

        controladorPadre.update();
        Stage stage = (Stage) buttonAñadir.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void alterProducto() {
        if (textFieldNombre.getText().isEmpty() || textFieldCodigo.getText().isEmpty() || textFieldPrecio.getText().isEmpty() || textFieldStock.getText().isEmpty() || textFieldMarca.getText().isEmpty() || textFieldCategoria.getText().isEmpty() || textFieldCNeto.getText().isEmpty()) {
            return;
        }

        Database.establishConnection();
        Database.updateProducto(producto.getID(), Long.parseLong(textFieldCodigo.getText()), textFieldNombre.getText(), Float.parseFloat(textFieldPrecio.getText()), Integer.parseInt(textFieldStock.getText()), textFieldMarca.getText(), textFieldCategoria.getText(), textFieldCNeto.getText());
        Database.closeConnection();

        controladorPadre.update();
        Stage stage = (Stage) buttonEditar.getScene().getWindow();
        stage.close();
    }

    public void loadProducto(Producto producto) {
        setProducto(producto);
        textFieldCodigo.setText(String.valueOf(producto.getBarCodigo()));
        textFieldNombre.setText(producto.getNombre());
        textFieldPrecio.setText(String.valueOf(producto.getPrecio()));
        textFieldStock.setText(String.valueOf(producto.getStock()));
        textFieldMarca.setText(String.valueOf(producto.getMarca()));
        textFieldCategoria.setText(producto.getCategoria());
        textFieldCNeto.setText(producto.getCtoNeto());
    }

    // ↓↓ There has to be a better way to do this ↓↓
    public void setControladorPadre(ProductosController controladorPadre){
        this.controladorPadre = controladorPadre;
    }

    public void setProducto(Producto producto){
        this.producto = producto;
    }


}