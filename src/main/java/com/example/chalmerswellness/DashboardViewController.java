package com.example.chalmerswellness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class DashboardViewController extends AnchorPane {
    @FXML Text dashboardQuote2;
    @FXML Text dashboardQuote1;

    public DashboardViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DashboardView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        QuotesAPIConnector apiConnector = new QuotesAPIConnector();
        /*String motivationalQuote = apiConnector.getRandomQuoteAsStringFromAPI("motivational");
        String athleticQuote = apiConnector.getRandomQuoteAsStringFromAPI("sports&competition");


        try {
            fxmlLoader.load();
            dashboardQuote2.setText(motivationalQuote);
            dashboardQuote1.setText(athleticQuote);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

         */
    }
}
