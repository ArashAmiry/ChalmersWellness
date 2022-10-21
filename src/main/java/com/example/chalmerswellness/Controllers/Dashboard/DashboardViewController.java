package com.example.chalmerswellness.Controllers.Dashboard;

import com.example.chalmerswellness.Controllers.Profile.CalendarController;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.ObjectModels.User;
import com.example.chalmerswellness.Models.FoodModel.FoodFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardViewController extends AnchorPane implements Initializable, Observer {
    @FXML
    Text dashboardQuote2;
    @FXML
    Text dashboardQuote1;
    @FXML
    Text calorieLeft;
    @FXML
    AnchorPane rootpane;

    @FXML
    AnchorPane calendar;

    FoodFacade foodFacade = new FoodFacade();
    private User user = LoggedInUser.getInstance();

    public DashboardViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DashboardView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        foodFacade.subscribe(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardQuote2.setText(DashboardQuotes.getInstance().getMotivationalQuote());
        dashboardQuote1.setText(DashboardQuotes.getInstance().getSportsAndCompetitionQuote());
        calorieLeft.setText(foodFacade.getTodaysCalories() + " KCAL");
        calendar.getChildren().add(new CalendarController(rootpane, user.getId()));
    }

    @Override
    public void update(Observable observable) {
        calorieLeft.setText(foodFacade.getTodaysCalories() + " KCAL");
    }
}
