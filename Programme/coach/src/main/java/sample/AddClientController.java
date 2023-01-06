package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.ConnectionUtilis;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddClientController  implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField firstname;

    @FXML
    private TextField mail;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;

    @FXML
    private TextField height;

    @FXML
    private TextField weight;

    @FXML
    private ComboBox<String> typeAthlete;

    @FXML
    private ComboBox<String> typeGoal;

    @FXML
    private ComboBox<String> placePractice;

    @FXML
    private ComboBox<String> program;

    @FXML
    private Label error;
    @FXML
    private DatePicker age;

    String sql1 = "INSERT INTO client (firstname,name, birthday, address, mail, phone, height, weight, typeAthlete, goal, placePractice, program ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    PreparedStatement preparedStatement;
    Connection connection;

    public AddClientController() {
        connection = ConnectionUtilis.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeAthlete.setItems(FXCollections.observableArrayList(initializeTypeAthleteComboBox()));
        typeGoal.setItems(FXCollections.observableArrayList(initializeGoalComboBox()));
        placePractice.setItems(FXCollections.observableArrayList(initializePlacePracticeComboBox()));
        program.setItems(FXCollections.observableArrayList(initializeProgramComboBox()));
    }

    public void addClientHandlerEvent() {
        if (name.getText().isEmpty() || firstname.getText().isEmpty() || age.getEditor().getText().isEmpty() || address.getText().isEmpty() || mail.getText().isEmpty() || phone.getText().isEmpty()  || height.getText().isEmpty() || weight.getText().isEmpty() || typeAthlete.getValue() == null|| typeGoal.getValue() == null|| placePractice.getValue() == null|| program.getValue() == null ) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please fill in the fields");
        }else if(!phone.getText().matches("[0-9]+") || phone.getText().length()!= 10){
            error.setTextFill(Color.TOMATO);
            error.setText("Please enter the correct format for the phone number");
        }else if(!height.getText().matches("[0-9]+")){
            error.setTextFill(Color.TOMATO);
            error.setText("c");
        }else if(!weight.getText().matches("[0-9]+")){
            error.setTextFill(Color.TOMATO);
            error.setText("Please enter the correct format for the weight");
        }
        else {
            addClient();
        }
    }

    public void returnToClientPageHandlerEvent(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/clients.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    public void addClient(){
        try {
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, name.getText());
            preparedStatement.setString(2, firstname.getText());
            preparedStatement.setString(3, age.getEditor().getText());
            preparedStatement.setString(4, address.getText());
            preparedStatement.setString(5, mail.getText());
            preparedStatement.setString(6, phone.getText());
            preparedStatement.setString(7, height.getText());
            preparedStatement.setString(8, weight.getText());
            preparedStatement.setString(9, typeAthlete.getValue());
            preparedStatement.setString(10, typeGoal.getValue());
            preparedStatement.setString(11, placePractice.getValue());
            preparedStatement.setString(12, program.getValue());

            preparedStatement.executeUpdate();
            error.setTextFill(Color.GREEN);
            error.setText("Client added successfully");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            error.setTextFill(Color.TOMATO);
            error.setText("Please enter the correct format");
        }
    }

    public List<String> initializeTypeAthleteComboBox(){
        String sql2 = "SELECT nameType FROM typeAthlete ";
        ResultSet resultSet ;
        List<String> options = new ArrayList<>();

        try{
            preparedStatement = connection.prepareStatement(sql2);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                options.add(resultSet.getString("nameType"));
            }
            return options;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            error.setTextFill(Color.TOMATO);
            error.setText(ex.getMessage());
            return null;
        }
    }

    public List<String> initializeGoalComboBox() {
        String sql3 = "SELECT goal FROM goal ";
        ResultSet resultSet;
        List<String> options = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql3);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                options.add(resultSet.getString("goal"));
            }
            return options;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            error.setTextFill(Color.TOMATO);
            error.setText(ex.getMessage());
            return null;
        }
    }

    public List<String> initializePlacePracticeComboBox() {
        String sql4 = "SELECT place FROM placePractice ";
        ResultSet resultSet;
        List<String> options = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql4);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                options.add(resultSet.getString("place"));
            }
            return options;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            error.setTextFill(Color.TOMATO);
            error.setText(ex.getMessage());
            return null;
        }
    }

    public List<String> initializeProgramComboBox() {
        String sql5 = "SELECT nameProgram FROM programs ";
        ResultSet resultSet;
        List<String> options = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql5);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                options.add(resultSet.getString("nameProgram"));
            }
            return options;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            error.setTextFill(Color.TOMATO);
            error.setText(ex.getMessage());
            return null;
        }
    }
}