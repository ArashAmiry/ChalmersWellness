module com.example.chalmerswellness {

    requires java.sql;
    requires java.net.http;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    /*requires eu.hansolo.medusa;*/
    opens com.example.chalmerswellness to javafx.fxml;
    exports com.example.chalmerswellness;
    exports com.example.chalmerswellness.Controllers;
    opens com.example.chalmerswellness.Controllers to javafx.fxml;
    exports com.example.chalmerswellness.Models;
    opens com.example.chalmerswellness.Models to javafx.fxml;
    exports com.example.chalmerswellness.ObjectModels;
    opens com.example.chalmerswellness.ObjectModels to javafx.fxml;
}