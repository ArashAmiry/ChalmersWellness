package com.example.chalmerswellness.Controllers.Dashboard;

import com.example.chalmerswellness.Controllers.Dashboard.QuotesAPIConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardViewController extends AnchorPane implements Initializable {
    @FXML Text dashboardQuote2;
    @FXML Text dashboardQuote1;

    public DashboardViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DashboardView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuotesAPIConnector apiConnector = new QuotesAPIConnector();
        String motivationalQuote = apiConnector.getRandomQuoteAsStringFromAPI("motivational");
        String sportsAndCompetitionQuote = apiConnector.getRandomQuoteAsStringFromAPI("sports&competition");
        dashboardQuote2.setText(motivationalQuote);
        dashboardQuote1.setText(sportsAndCompetitionQuote);
    }
}