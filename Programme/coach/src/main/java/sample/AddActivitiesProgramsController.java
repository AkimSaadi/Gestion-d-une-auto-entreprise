package sample;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mod.Activity;
import util.ConnectionUtilis;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static sample.ProgramsController.currentIdProgramme;
public class AddActivitiesProgramsController implements Initializable {

    @FXML
    ComboBox <Activity> comboBoxActivity;

    @FXML
    TextField repetitionTextField;

    @FXML
    TextField quantityTextField;

    @FXML
    TextField restTextField;

    @FXML
    Label  error;

    PreparedStatement preparedStatement;
    Connection connection;
    String SQL = "SELECT * FROM activities";
    String addActivityQuery = "INSERT INTO activitiesPrograms(idProgram, idActivity, quantityActivity, repetitionActivity, restActivity) VALUES (? , ? , ?, ?, ?)";
    String SQL2 = "SELECT * FROM activitiesPrograms WHERE idProgram = ? AND idActivity = ?";

    public AddActivitiesProgramsController(){
        connection = (Connection) ConnectionUtilis.conDB();
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        comboBoxHandler();
    }

    private void comboBoxHandler() {
        ResultSet rs;
        try {

            preparedStatement = (PreparedStatement) connection.prepareStatement(SQL);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Activity activity = new Activity(rs.getString("idActivity"),
                        rs.getString("nameActivity"), rs.getString("descriptionActivity"));
                comboBoxActivity.getItems().add(activity);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void returnToProgramPageHandlerEvent(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/programs.fxml"))));
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void addActivitiesProgramHandlerEvent(MouseEvent mouseEvent) {
        ResultSet rs;
        if (quantityTextField.getText().isEmpty()||repetitionTextField.getText().isEmpty()|| comboBoxActivity.getSelectionModel().isEmpty()||restTextField.getText().isEmpty()) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please fill in the fields");
        }else {
            try {
                preparedStatement = (PreparedStatement) connection.prepareStatement(SQL2);
                preparedStatement.setString(1, currentIdProgramme);
                preparedStatement.setString(2, String.valueOf(comboBoxActivity.getSelectionModel().getSelectedIndex() + 1));
                rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    error.setTextFill(Color.TOMATO);
                    error.setText("This program already contains this activity");
                }else{
                    if (!repetitionTextField.getText().matches("[0-9]+")){
                        error.setTextFill(Color.TOMATO);
                        error.setText("Please enter the correct format for the number of repetitions");
                    } else {
                        if (!quantityTextField.getText().matches("[0-9]+")) {
                            error.setTextFill(Color.TOMATO);
                            error.setText("Please enter the correct format for the number of quantities");
                        } else {
                            if (!restTextField.getText().matches("[0-9]+")){
                                error.setTextFill(Color.TOMATO);
                                error.setText("Please enter the correct format for the rest time");
                            }else {
                                try {
                                    preparedStatement = (PreparedStatement) connection.prepareStatement(addActivityQuery);
                                    preparedStatement.setString(1, currentIdProgramme);
                                    preparedStatement.setString(2, String.valueOf(comboBoxActivity.getSelectionModel().getSelectedIndex() + 1));
                                    preparedStatement.setString(3, quantityTextField.getText());
                                    preparedStatement.setString(4, repetitionTextField.getText());
                                    preparedStatement.setString(5, restTextField.getText());
                                    preparedStatement.executeUpdate();
                                    clearFields();
                                    Node node = (Node) mouseEvent.getSource();
                                    Stage stage = (Stage) node.getScene().getWindow();
                                    stage.close();
                                    Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/programs.fxml"))));
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (SQLException | IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void clearFields() {
        error.setText("");
        repetitionTextField.clear();
        quantityTextField.clear();
        restTextField.clear();
        comboBoxActivity.getSelectionModel().clearSelection();
    }

    public void cancelProgramHandlerEvent(javafx.scene.input.MouseEvent mouseEvent) {
        returnToProgramPageHandlerEvent(mouseEvent);
    }

    public void quantityTextFieldHandlerEvent(KeyEvent keyEvent) {
        textFieldHandlerEvent(keyEvent);
    }

    public void repetitionTextFieldHandlerEvent(KeyEvent keyEvent) {
        textFieldHandlerEvent(keyEvent);
    }

    public void restTextFieldHandlerEvent(KeyEvent keyEvent) {
        textFieldHandlerEvent(keyEvent);
    }

    private void textFieldHandlerEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().getCode() == 10) {
            ResultSet rs;
            if (quantityTextField.getText().isEmpty() || repetitionTextField.getText().isEmpty() || restTextField.getText().isEmpty()|| comboBoxActivity.getSelectionModel().isEmpty()) {
                error.setTextFill(Color.TOMATO);
                error.setText("Please fill in the fields");
            } else {
                try {
                    preparedStatement = (PreparedStatement) connection.prepareStatement(SQL2);
                    preparedStatement.setString(1, currentIdProgramme);
                    preparedStatement.setString(2, String.valueOf(comboBoxActivity.getSelectionModel().getSelectedIndex() + 1));
                    rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        error.setTextFill(Color.TOMATO);
                        error.setText("This program already contains this activity");
                    } else {
                        if (!repetitionTextField.getText().matches("[0-9]+")) {
                            error.setTextFill(Color.TOMATO);
                            error.setText("Please enter the correct format for the number of repetitions");
                        } else {
                            if (!quantityTextField.getText().matches("[0-9]+")) {
                                error.setTextFill(Color.TOMATO);
                                error.setText("Please enter the correct format for the number of quantities");
                            } else {
                                if (!restTextField.getText().matches("[0-9]+")) {
                                    error.setTextFill(Color.TOMATO);
                                    error.setText("Please enter the correct format for the rest time");
                                }else {
                                    try {
                                        preparedStatement = (PreparedStatement) connection.prepareStatement(addActivityQuery);
                                        preparedStatement.setString(1, currentIdProgramme);
                                        preparedStatement.setString(2, String.valueOf(comboBoxActivity.getSelectionModel().getSelectedIndex() + 1));
                                        preparedStatement.setString(3, quantityTextField.getText());
                                        preparedStatement.setString(4, repetitionTextField.getText());
                                        preparedStatement.setString(5, restTextField.getText());
                                        preparedStatement.executeUpdate();
                                        clearFields();
                                        Node node = (Node) keyEvent.getSource();
                                        Stage stage = (Stage) node.getScene().getWindow();
                                        stage.close();
                                        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/programs.fxml"))));
                                        stage.setScene(scene);
                                        stage.show();
                                    } catch (SQLException | IOException ex) {
                                        System.out.println(ex.getMessage());
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
