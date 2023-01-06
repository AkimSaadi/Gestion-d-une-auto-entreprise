package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import javafx.scene.control.ComboBox;

import javafx.stage.Stage;
import util.ConnectionUtilis;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private ComboBox<String> statCombo;

    PreparedStatement preparedStatement;
    Connection connection;
    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    XYChart.Series<String, Double> series = new XYChart.Series<>();

    public StatisticsController() {
        connection = ConnectionUtilis.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBox();
    }

    public void backHome(javafx.scene.input.MouseEvent mouseEvent)throws IOException {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Home.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    public void loadStat(){
        if(statCombo.getValue().contentEquals("Athlete type")){
            pieChart.getData().removeAll(data);
            barChart.getData().removeAll(series);
            statTypeAthlete();
            statTypeAthletePieChart();
        }
        if (statCombo.getValue().contentEquals("Practice place")){
            pieChart.getData().removeAll(data);
            barChart.getData().removeAll(series);
            statPlacePractice();
            statPlacePracticePieChart();
        }
        if (statCombo.getValue().contentEquals("Goal requested")){
            pieChart.getData().removeAll(data);
            barChart.getData().removeAll(series);
            statGoal();
            statGoalPieChart();
        }
        if (statCombo.getValue().contentEquals("Allocated programs")){
            pieChart.getData().removeAll(data);
            barChart.getData().removeAll(series);
            statProgram();
            statProgramPieChart();
        }
    }

    public void statTypeAthletePieChart(){
        ResultSet resultSet;
        String sql = "SELECT typeAthlete, COUNT(typeAthlete) FROM client GROUP BY typeAthlete ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new PieChart.Data(resultSet.getString(1), resultSet.getDouble(2)));
            }
            pieChart.getData().addAll(data);
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void statTypeAthlete(){
        ResultSet resultSet;
        String sql = "SELECT typeAthlete, COUNT(typeAthlete) FROM client GROUP BY typeAthlete ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            series = new XYChart.Series<>();
            while (resultSet.next()){
                series.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getDouble(2)));
            }
            barChart.getData().add(series);
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void statPlacePracticePieChart(){
        ResultSet resultSet;
        String sql = "SELECT placePractice, COUNT(placePractice) FROM client GROUP BY placePractice ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new PieChart.Data(resultSet.getString(1), resultSet.getDouble(2)));
            }
            pieChart.getData().addAll(data);
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void statPlacePractice(){
        ResultSet resultSet ;
        String sql = "SELECT placePractice, COUNT(placePractice) FROM client GROUP BY placePractice ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            series = new XYChart.Series<>();
            while (resultSet.next()){
                series.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getDouble(2)));
            }
            barChart.getData().addAll(series);
        }catch (SQLException ex){
                System.err.println(ex.getMessage());
        }
    }

    public void statGoalPieChart(){
        ResultSet resultSet;
        String sql = "SELECT goal, COUNT(goal) FROM client GROUP BY goal ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new PieChart.Data(resultSet.getString(1), resultSet.getDouble(2)));
            }
            pieChart.getData().addAll(data);
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void statGoal(){
        ResultSet resultSet ;
        String sql = "SELECT goal, COUNT(goal) FROM client GROUP BY goal ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            series = new XYChart.Series<>();
            while (resultSet.next()){
                series.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getDouble(2)));
            }
            barChart.getData().add(series);

        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void statProgramPieChart(){
        ResultSet resultSet;
        String sql = "SELECT program, COUNT(program) FROM client GROUP BY program ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new PieChart.Data(resultSet.getString(1), resultSet.getDouble(2)));
            }
            pieChart.getData().addAll(data);
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void statProgram(){
        ResultSet resultSet ;
        String sql = "SELECT program, COUNT(program) FROM client GROUP BY program ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            series = new XYChart.Series<>();
            while (resultSet.next()){
                series.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getDouble(2)));
            }
            barChart.getData().add(series);

        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void initializeComboBox(){
        ObservableList<String> obList = FXCollections.observableArrayList("Athlete type","Practice place","Goal requested","Allocated programs");
        statCombo.setItems(obList);
    }
}