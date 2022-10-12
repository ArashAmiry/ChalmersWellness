package com.example.chalmerswellness.Controllers.Profile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarController extends AnchorPane {
    private Calendar calendar;
    AnchorPane rootpane;
    int userId;

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

    public CalendarController(AnchorPane rootpane, int userId){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/calendar.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.rootpane = rootpane;
        this.userId = userId;
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
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        drawPreviousMonthDays(dayOfWeek, gridPane);
        int row = 0;
        for (int i = day; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            CalendarItemController calendarItemController = new CalendarItemController(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, day, rootpane, userId);
            gridPane.add(calendarItemController, dayOfWeek - 1, row);
            GridPane.setHalignment(calendarItemController, HPos.CENTER);
            day++;
            dayOfWeek++;
        }
        drawDaysInNextMonth(daysInMonth, dayOfWeek, firstDayOfWeek,  row, gridPane);
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
        CalendarItemController calendarItemController = new CalendarItemController(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, day, rootpane, userId);
        gridPane.add(calendarItemController, columnCounter, 0);
        GridPane.setHalignment(calendarItemController, HPos.CENTER);
    }

    private void drawDaysInNextMonth(int daysInMonth, int dayOfWeek, int firstDayOfWeek, int row, GridPane gridPane){
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        int daysInMonthLeft = Math.abs(daysInMonth + firstDayOfWeek - 43);
        for (int day = 1; day <= daysInMonthLeft; day++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }

            CalendarItemController calendarItemController = new CalendarItemController(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, day, rootpane, userId);
            gridPane.add(calendarItemController, dayOfWeek - 1, row);
            GridPane.setHalignment(calendarItemController, HPos.CENTER);
            dayOfWeek++;
        }
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
    }
}
