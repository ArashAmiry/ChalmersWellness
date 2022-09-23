module com.example.chalmerswellness {
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires com.google.gson;
    requires javafx.fxml;

    opens com.example.chalmerswellness to javafx.fxml;
    exports com.example.chalmerswellness;
    exports com.example.chalmerswellness.Controllers;
    opens com.example.chalmerswellness.Controllers to javafx.fxml;
    exports com.example.chalmerswellness.Models;
    opens com.example.chalmerswellness.Models to javafx.fxml;
    exports com.example.chalmerswellness.ObjectModels;
    opens com.example.chalmerswellness.ObjectModels to javafx.fxml;
}