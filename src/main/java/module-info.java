module com.example.chalmerswellness {
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.net.http;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires org.json;

    opens com.example.chalmerswellness to javafx.fxml;
    exports com.example.chalmerswellness;
    exports com.example.chalmerswellness.Models;
    opens com.example.chalmerswellness.Models to javafx.fxml;
    exports com.example.chalmerswellness.ObjectModels;
    opens com.example.chalmerswellness.ObjectModels to javafx.fxml;
    exports com.example.chalmerswellness.Controllers.Workout;
    opens com.example.chalmerswellness.Controllers.Workout to javafx.fxml;
    exports com.example.chalmerswellness.Controllers.Workout.SearchPane;
    opens com.example.chalmerswellness.Controllers.Workout.SearchPane to javafx.fxml;
    exports com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;
    opens com.example.chalmerswellness.Controllers.Workout.TodaysWorkout to javafx.fxml;
    exports com.example.chalmerswellness.Controllers.Nutrition;
    opens com.example.chalmerswellness.Controllers.Nutrition to javafx.fxml;
    exports com.example.chalmerswellness.Services;
    opens com.example.chalmerswellness.Services to javafx.fxml;
    exports com.example.chalmerswellness.Interfaces;
    opens com.example.chalmerswellness.Interfaces to javafx.fxml;
    exports com.example.chalmerswellness.Controllers.Dashboard;
    opens com.example.chalmerswellness.Controllers.Dashboard to javafx.fxml;
    exports com.example.chalmerswellness.Controllers.Settings;
    opens com.example.chalmerswellness.Controllers.Settings to javafx.fxml;
    exports com.example.chalmerswellness.calorieAPI;
    opens com.example.chalmerswellness.calorieAPI to javafx.fxml;
    exports com.example.chalmerswellness.Controllers.Friends;
    opens com.example.chalmerswellness.Controllers.Friends;
    exports com.example.chalmerswellness.Controllers.Workout.CreateWorkout;
    opens com.example.chalmerswellness.Controllers.Workout.CreateWorkout to javafx.fxml;
}