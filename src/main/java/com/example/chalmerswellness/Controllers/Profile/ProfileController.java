package com.example.chalmerswellness.Controllers.Profile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ProfileController extends AnchorPane {

    private Calendar calendar;

    @FXML
    Label firstMonthLabel;

    @FXML
    Label secondMonthLabel;

    @FXML
    Label thirdMonthLabel;

    @FXML
    GridPane firstMonth;

    @FXML
    GridPane secondMonth;

    @FXML
    GridPane thirdMonth;

    public ProfileController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profileView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        drawCalendar();
    }

    private void drawCalendar(){
        calendar = new GregorianCalendar();
        calendar.set(Calendar.DATE, 1);
        drawMonthCalendar(firstMonth, firstMonthLabel);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        drawMonthCalendar(secondMonth, secondMonthLabel);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        drawMonthCalendar(thirdMonth, thirdMonthLabel);
    }

    private void drawMonthCalendar(GridPane gridPane, Label monthLabel) {
        monthLabel.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        drawPreviousMonthDays(dayOfWeek, gridPane);
        int row = 0;
        for (int i = day; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            Text tDate = new Text(String.valueOf(day));
            gridPane.add(tDate, dayOfWeek - 1, row);
            day++;
            dayOfWeek++;
        }
    }

    private void drawPreviousMonthDays(int dayOfWeek, GridPane gridPane){
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        int daysInPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (dayOfWeek != 1){
            drawDaysInPreviousMonth(gridPane, daysInPreviousMonth, dayOfWeek);
        }
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
    }

    private void drawDaysInPreviousMonth(GridPane gridPane, int daysInPreviousMonth, int dayOfWeek) {
        int columnCounter = 0;
        if (daysInPreviousMonth == 31){
            drawDaysInCurrentMonth(gridPane, daysInPreviousMonth, dayOfWeek, columnCounter);
        }
        else if (daysInPreviousMonth == 30){
            drawDaysInCurrentMonth(gridPane, daysInPreviousMonth, dayOfWeek, columnCounter);
        }
        else if (daysInPreviousMonth == 28){
            drawDaysInCurrentMonth(gridPane, daysInPreviousMonth, dayOfWeek, columnCounter);
        }
    }

    private void drawDaysInCurrentMonth(GridPane gridPane, int daysInPreviousMonth, int dayOfWeek, int columnCounter) {
        for (int day = daysInPreviousMonth + 1 - Math.abs(dayOfWeek - 1); day <= daysInPreviousMonth; day++){
            addTextToGridPane(gridPane, columnCounter, day);
            columnCounter++;
        }
    }

    private void addTextToGridPane(GridPane gridPane, int columnCounter, int day) {
        Text tDate = new Text(String.valueOf(day));
        gridPane.add(tDate, columnCounter, 0);
    }
}
