package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.ConnectionUtilis;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private DatePicker age ;

    @FXML
    private TextField address;

    @FXML
    private TextField firstname;

    @FXML
    private TextField mail;

    @FXML
    private TextField phone;

    @FXML
    private TextField height;

    @FXML
    private TextField weight;

    @FXML
    private ComboBox<String> programChange;

    @FXML
    private ComboBox<String> comboTypeAthlete;

    @FXML
    private ComboBox<String> ComboGoal;

    @FXML
    private ComboBox<String> comboPlacePractice;

    @FXML
    private TextField searchLastName;

    @FXML
    private Button editButton;

    @FXML
    private Label error;

    @FXML
    private TableView<ObservableList> table;

    PreparedStatement preparedStatement;
    Connection connection;
    private ObservableList<ObservableList> data;
    String SQL = "SELECT firstname,name,mail, typeAthlete, goal, placePractice, program  FROM client";
    String sql3 = " SELECT firstname,name, birthdate, address, mail, phone, height, weight, typeAthlete, goal, placePractice, program FROM client WHERE idClient = ?  ";
    String sql2 = " SELECT idClient FROM client WHERE firstname = ? and name = ? ";
    String sql4 = "UPDATE client SET firstname = ?,name = ?, birthdate =? , address = ?, mail = ?, phone = ?, height = ?, weight = ?,typeAthlete = ?, goal = ?,placePractice = ?, program = ? WHERE idClient = ?";
    String sql5 = "DELETE FROM client WHERE name = ? ";
    public static String currentIdClient;

    public ClientsController() {
        connection = ConnectionUtilis.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        programChange.setItems(FXCollections.observableArrayList(initializeProgramComboBox()));
        comboTypeAthlete.setItems(FXCollections.observableArrayList(initializeTypeAthleteComboBox()));
        comboPlacePractice.setItems(FXCollections.observableArrayList(initializePlacePracticeComboBox()));
        ComboGoal.setItems(FXCollections.observableArrayList(initializeGoalComboBox()));
        fetColumnList();
        fetRowList();
    }

    public void updateClientHandlerEvent()  {
        if (name.getText().isEmpty() || firstname.getText().isEmpty() || age.getEditor().getText().isEmpty() || editButton.getText().isEmpty() || mail.getText().isEmpty() || phone.getText().isEmpty() || height.getText().isEmpty() || weight.getText().isEmpty() ) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please fill in the fields");
        }else if(!phone.getText().matches("[0-9]+") || phone.getText().length()!= 10){
            error.setTextFill(Color.TOMATO);
            error.setText("Please enter the correct format for the phone number");
        }else if(!height.getText().matches("[0-9]+")){
            error.setTextFill(Color.TOMATO);
            error.setText("Please enter the correct format for the height");
        }else if(!weight.getText().matches("[0-9]+")){
            error.setTextFill(Color.TOMATO);
            error.setText("Please enter the correct format for the weight");
        }
        else {
            updateClient();
        }
    }

    public void deleteHandlerEvent(){
        deleteClient();
    }

    public void tableHandler(){
        getClientInfoToForm();
    }

    public void addButtHandlerEvent(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/addClient.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    public void searchClientHandlerEvent(){
        searchBarHandlerEvent();
    }

    public void returnToHomePageHandlerEvent(MouseEvent mouseEvent) throws IOException {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Home.fxml"))));
        stage.setScene(scene);
        stage.show();
    }

    public void updateClient(){
        try{
            ResultSet rs ;
            preparedStatement = connection.prepareStatement(sql2);
            String firstnameSelected = (String) table.getSelectionModel().getSelectedItems().get(0).get(1);
            String nameSelected = (String) table.getSelectionModel().getSelectedItems().get(0).get(0);
            preparedStatement.setString(1, nameSelected);
            preparedStatement.setString(2, firstnameSelected);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                currentIdClient = rs.getString("idClient");
            }
            preparedStatement = connection.prepareStatement(sql4);
            preparedStatement.setString(1, firstname.getText());
            preparedStatement.setString(2, name.getText());
            preparedStatement.setString(3, ( age.getEditor()).getText());
            preparedStatement.setString(4, address.getText());
            preparedStatement.setString(5, mail.getText());
            preparedStatement.setString(6, phone.getText());
            preparedStatement.setString(7, height.getText());
            preparedStatement.setString(8, weight.getText());
            preparedStatement.setString(9, comboTypeAthlete.getValue());
            preparedStatement.setString(10, ComboGoal.getValue());
            preparedStatement.setString(11, comboPlacePractice.getValue());
            preparedStatement.setString(12, programChange.getValue());
            preparedStatement.setString(13, currentIdClient);
            preparedStatement.executeUpdate();
            error.setTextFill(Color.GREEN);
            error.setText("Client edited successfully");

            fetRowList();
            clearFields();

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            error.setTextFill(Color.TOMATO);
            error.setText(ex.getMessage());
        }
    }

    public void deleteClient(){
        try{
            preparedStatement = connection.prepareStatement(sql5);
            preparedStatement.setString(1, name.getText());
            preparedStatement.executeUpdate();
            error.setTextFill(Color.GREEN);
            error.setText("Client delete");

            fetRowList();
            clearFields();

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void clearFields() {
        name.clear();
        firstname.clear();
        age.setValue(null);
        address.clear();
        mail.clear();
        phone.clear();
        height.clear();
        weight.clear();
        comboPlacePractice.setValue("");
        ComboGoal.setValue("");
        comboTypeAthlete.setValue("");
        age.getEditor().setText("");
    }

    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL);
            while (rs.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);

            }
            table.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void fetColumnList() {
        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                table.getColumns().removeAll(col);
                table.getColumns().addAll(col);
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    private void getClientInfoToForm(){
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            preparedStatement = connection.prepareStatement(sql2);
            String firstnameSelected = (String) table.getSelectionModel().getSelectedItems().get(0).get(1);
            String nameSelected = (String) table.getSelectionModel().getSelectedItems().get(0).get(0);
            preparedStatement.setString(1, nameSelected);
            preparedStatement.setString(2, firstnameSelected);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                currentIdClient = rs.getString("idClient");
            }
            preparedStatement = connection.prepareStatement(sql3);
            preparedStatement.setString(1, currentIdClient);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                name.setText(rs.getString("name"));
                firstname.setText(rs.getString("firstname"));
                age.getEditor().setText(rs.getString("birthdate"));
                address.setText(rs.getString("address"));
                mail.setText(rs.getString("mail"));
                phone.setText(rs.getString("phone"));
                height.setText(rs.getString("height"));
                weight.setText(rs.getString("weight"));
                comboTypeAthlete.setValue(rs.getString("typeAthlete"));
                ComboGoal.setValue(rs.getString("goal"));
                comboPlacePractice.setValue(rs.getString("placePractice"));
                programChange.setValue(rs.getString("program"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void searchBarHandlerEvent(){
        if (searchLastName.getText().isEmpty()){
            SQL = "SELECT  firstname,name,mail, typeAthlete, goal, placePractice, program  FROM client";
        }
        else{
            SQL = "SELECT firstname,name,mail, typeAthlete, goal, placePractice, program FROM client WHERE name LIKE '%"+searchLastName.getText()+"%' OR firstname LIKE '%"+searchLastName.getText()+"%' ";
        }
        fetRowList();
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

    public List<String> initializeTypeAthleteComboBox() {
        String sql5 = "SELECT nameType FROM typeAthlete ";
        ResultSet resultSet;
        List<String> options = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql5);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                options.add(resultSet.getString("nameType"));
            }
            return options;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            error.setTextFill(Color.TOMATO);
            error.setText(ex.getMessage());
            return null;
        }
    }

    public List<String> initializeGoalComboBox() {
        String sql5 = "SELECT goal FROM goal ";
        ResultSet resultSet;
        List<String> options = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql5);
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
        String sql5 = "SELECT place FROM placePractice ";
        ResultSet resultSet;
        List<String> options = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql5);
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

    public void mailHandler (){
        if (name.getText().isEmpty() || firstname.getText().isEmpty() || age.getEditor().getText().isEmpty() || editButton.getText().isEmpty() || mail.getText().isEmpty() || phone.getText().isEmpty() || height.getText().isEmpty() || weight.getText().isEmpty() ) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please select a client");
        } else {
            try {
                Scene mailScene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/email.fxml"))));
                Stage stage = new Stage();
                stage.getIcons().add(new Image("images/attachment_88415434.jpg"));
                stage.setTitle("Send a mail");
                stage.setScene(mailScene);
                ResultSet rs ;
                preparedStatement = connection.prepareStatement(sql2);
                String firstnameSelected = (String) table.getSelectionModel().getSelectedItems().get(0).get(1);
                String nameSelected = (String) table.getSelectionModel().getSelectedItems().get(0).get(0);
                preparedStatement.setString(1, nameSelected);
                preparedStatement.setString(2, firstnameSelected);
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    currentIdClient = rs.getString("idClient");
                }
                stage.showAndWait();
            }catch (IOException|SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}