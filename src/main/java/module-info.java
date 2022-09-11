module com.example.chalmerswellness {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires java.sql;

    opens com.example.chalmerswellness to javafx.fxml;
    exports com.example.chalmerswellness;
}