module com.example.chalmerswellness {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires com.google.gson;

    opens com.example.chalmerswellness to javafx.fxml;
    exports com.example.chalmerswellness;
}