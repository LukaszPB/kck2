module com.example.projekt2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.projekt2 to javafx.fxml;
    exports com.example.projekt2;
    exports com.example.projekt2.Controllers;
    opens com.example.projekt2.Controllers to javafx.fxml;
}