module com.javafxtest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens Javafxtest to javafx.fxml;
    opens DatabaseConnection to javafx.base;

    exports Javafxtest;
}