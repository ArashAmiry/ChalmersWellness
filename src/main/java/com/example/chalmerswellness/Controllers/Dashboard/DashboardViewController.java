package com.example.chalmerswellness.Controllers.Dashboard;

import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.calorieAPI.FoodFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    FoodFacade foodFacade = new FoodFacade();

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
        QuotesAPIConnector apiConnector = new QuotesAPIConnector();
        String motivationalQuote = apiConnector.getRandomQuoteAsStringFromAPI("motivational");
        String sportsAndCompetitionQuote = apiConnector.getRandomQuoteAsStringFromAPI("sports&competition");
        dashboardQuote2.setText(motivationalQuote);
        dashboardQuote1.setText(sportsAndCompetitionQuote);
        calorieLeft.setText(foodFacade.getTodaysCalories() + " KCAL");
    }

    @Override
    public void update(Observable observable) {
        calorieLeft.setText(foodFacade.getTodaysCalories() + " KCAL");
    }
}
