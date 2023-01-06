package sample;


import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import mod.PlanningSession;
import util.ConnectionUtilis;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.List;

public class planningWeekController extends PlanningController implements Initializable, Printable {

    @FXML
    private TitledPane mondayPane;

    @FXML
    private TableView<PlanningSession> mondayTableView;

    @FXML
    private TitledPane tuesdayPane;

    @FXML
    private TableView<PlanningSession> tuesdayTableView;

    @FXML
    private TitledPane wednesdayPane;

    @FXML
    private TableView<PlanningSession> wednesdayTableView;

    @FXML
    private TitledPane thursdayPane;

    @FXML
    private TableView<PlanningSession> thursdayTableView;

    @FXML
    private TitledPane fridayPane;

    @FXML
    private TableView<PlanningSession> fridayTableView;

    @FXML
    private TitledPane saturdayPane;

    @FXML
    private TableView<PlanningSession> saturdayTableView;

    @FXML
    private TitledPane sundayPane;

    @FXML
    private Label weekLabel;

    @FXML
    private TableView<PlanningSession> sundayTableView;


    Connection connection;
    LocalDate currentDate;
    int weekNumber;
    WeekFields weekFields;
    List<TitledPane> accordionPanes = new ArrayList<>();
    List<TableView<PlanningSession>> weekTables = new ArrayList<>();
    public planningWeekController() {
        connection = ConnectionUtilis.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accordionPanes.add(mondayPane);
        accordionPanes.add(tuesdayPane);
        accordionPanes.add(wednesdayPane);
        accordionPanes.add(thursdayPane);
        accordionPanes.add(fridayPane);
        accordionPanes.add(saturdayPane);
        accordionPanes.add(sundayPane);
        currentDate = LocalDate.now();
        fetchColumnWeekPlanning(currentDate);
        weekFields = WeekFields.of(Locale.FRENCH);
        weekNumber = currentDate.get(weekFields.weekOfWeekBasedYear()) - 1;
        editAccordionLabel(currentDate);
        editWeekLabel(currentDate);
        getSessionPerWeek(weekNumber);
    }

    public void fetchColumnWeekPlanning(LocalDate date) {
        weekTables.add(sundayTableView);
        weekTables.add(mondayTableView);
        weekTables.add(tuesdayTableView);
        weekTables.add(wednesdayTableView);
        weekTables.add(thursdayTableView);
        weekTables.add(fridayTableView);
        weekTables.add(saturdayTableView);
        for(TableView<PlanningSession> t : weekTables) {
            fetchColumns(date, t);
        }
    }

    public void editAccordionLabel(LocalDate date) {
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
        LocalDate firstDayOfWeek = date.with(fieldISO, 1);
        for(int i = 0; i < 7; i++) {
            LocalDate tempDate = firstDayOfWeek.plusDays(i);
            String nameDay = tempDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);
            int numberDay = tempDate.getDayOfMonth();
            String month = tempDate.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE);
            int year = tempDate.getYear();
            accordionPanes.get(i).setText(nameDay + " " + numberDay + " " + month + " " + year);
        }
    }

    public void editWeekLabel(LocalDate date) {
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
        LocalDate firstDayOfWeek = date.with(fieldISO, 1);
        LocalDate lastDayOfWeek = date.with(fieldISO, 7);
        weekLabel.setText("Week from " + firstDayOfWeek.getDayOfMonth() +
                " " + firstDayOfWeek.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE)  +
                " " + firstDayOfWeek.getYear() + " to " +
                lastDayOfWeek.getDayOfMonth() + " " + lastDayOfWeek.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE) +
                " " + lastDayOfWeek.getYear());
    }

    public void clearWeekTables() {
        for(TableView<PlanningSession> t : weekTables) {
            t.getItems().clear();
        }
    }

    public void getSessionPerWeek(int weekNumber) {
        String request1 = "SELECT pl.idPlanning, cl.name, cl.firstname, cl.mail, pr.nameProgram, pp.place, pl.dateStart, pl.dateEnd, pl.idClient, pl.idProgram, pl.idPlace, DAYOFWEEK(dateStart) " +
                "FROM planning pl, client cl, programs pr, placePractice pp " +
                "WHERE pl.idClient = cl.idClient AND pl.idProgram = pr.idProgram AND pl.idPlace = pp.idPlace " +
                "AND WEEK(pl.dateStart) = ? AND DAYOFWEEK(dateStart) > 1 " +
                "ORDER BY pl.dateStart";
        String request2 = "SELECT pl.idPlanning, cl.name, cl.firstname, cl.mail, pr.nameProgram, pp.place, pl.dateStart, pl.dateEnd, pl.idClient, pl.idProgram, pl.idPlace, DAYOFWEEK(dateStart) " +
                "FROM planning pl, client cl, programs pr, placePractice pp " +
                "WHERE pl.idClient = cl.idClient AND pl.idProgram = pr.idProgram AND pl.idPlace = pp.idPlace " +
                "AND WEEK(pl.dateStart) = ? AND DAYOFWEEK(dateStart) = 1 " +
                "ORDER BY pl.dateStart";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(request1);
            preparedStatement.setInt(1, weekNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PlanningSession pls =
                        new PlanningSession(resultSet.getInt(1), resultSet.getString(2),
                                resultSet.getString(3),resultSet.getString(4),
                                resultSet.getString(5), resultSet.getString(6),
                                resultSet.getString(7), resultSet.getString(8),
                                resultSet.getInt(9), resultSet.getInt(10), resultSet.getInt(11));
                int day_of_week = resultSet.getInt(12);
                weekTables.get(day_of_week - 1).getItems().add(pls);
            }
        }
        catch (Exception e) {e.printStackTrace();}

        try {
            PreparedStatement preparedStatement2 = connection.prepareStatement(request2);
            preparedStatement2.setInt(1, weekNumber + 1);
            ResultSet resultSet = preparedStatement2.executeQuery();
            while (resultSet.next()) {
                PlanningSession pls =
                        new PlanningSession(resultSet.getInt(1), resultSet.getString(2),
                                resultSet.getString(3),resultSet.getString(4),
                                resultSet.getString(5), resultSet.getString(6),
                                resultSet.getString(7), resultSet.getString(8),
                                resultSet.getInt(9), resultSet.getInt(10), resultSet.getInt(11));
                int day_of_week = resultSet.getInt(12);
                weekTables.get(day_of_week - 1).getItems().add(pls);
            }
        }
        catch (Exception e) {e.printStackTrace();}
    }

    @FXML
    void moveBackWeekPlanningEvent() {
        currentDate = currentDate.minusDays(7);
        editAccordionLabel(currentDate);
        editWeekLabel(currentDate);
        weekNumber = currentDate.get(weekFields.weekOfWeekBasedYear()) - 1;
        clearWeekTables();
        getSessionPerWeek(weekNumber);
    }

    @FXML
    void moveForwardWeekEvent() {
        currentDate = currentDate.plusDays(7);
        editAccordionLabel(currentDate);
        editWeekLabel(currentDate);
        weekNumber = currentDate.get(weekFields.weekOfWeekBasedYear()) - 1;
        clearWeekTables();
        getSessionPerWeek(weekNumber);

    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        pageFormat.setOrientation(PageFormat.LANDSCAPE);
        return PAGE_EXISTS;
    }
}
