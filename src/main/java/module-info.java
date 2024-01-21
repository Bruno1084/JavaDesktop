module com.javafxtest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.javafxtest to javafx.fxml;
    exports com.javafxtest;
}