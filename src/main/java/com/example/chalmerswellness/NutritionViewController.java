package com.example.chalmerswellness;

/*import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.SimpleSkin;*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NutritionViewController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane modalPanel;
    @FXML
    private AnchorPane gaugePane;

   /* Gauge gauge = new Gauge();*/

    public NutritionViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionView.fxml"));

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
        modalPanel.setDisable(true);
        /*setGaugeSettings();
        gaugePane.getChildren().addAll(gauge);*/
    }

    /*private void setGaugeSettings() {
        gauge.setSkin(new SimpleSkin(gauge));
        gauge.setTitle("1800 Kcal left");
        gauge.setAnimated(true);
        gauge.setBarColor(Color.rgb(58,80,107));
        gauge.setValue(1337);
    }*/
    @FXML
    private void loadNutritionSearchView(ActionEvent event) throws IOException {
        modalPanel.getChildren().add(new NutritionSearchViewController(modalPanel));
        modalPanel.setDisable(false);
    }

}
