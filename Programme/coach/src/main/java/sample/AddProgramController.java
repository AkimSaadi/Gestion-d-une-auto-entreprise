package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.ConnectionUtilis;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProgramController implements Initializable {

    @FXML
    private TextField nameAdd;

    @FXML
    private TextField descriptionAdd;

    @FXML
    private Label error;

    PreparedStatement preparedStatement;
    Connection connection;
    String SQL = "SELECT * FROM program WHERE nameProgram = ?";

    public AddProgramController(){
        connection = ConnectionUtilis.conDB();
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
    }

    public void addProgramHandlerEvent(MouseEvent mouseEvent){
        if(nameAdd.getText().isEmpty() || descriptionAdd.getText().isEmpty() ){
            error.setTextFill(Color.TOMATO);
            error.setText("Please fill in the fields");
        } else {
            try {
                String st = "INSERT INTO program (nomProgram,descriptionProgram) VALUES (?,?)";
                preparedStatement = connection.prepareStatement(st);
                preparedStatement.setString(1, nameAdd.getText());
                preparedStatement.setString(2, descriptionAdd.getText());

                preparedStatement.executeUpdate();
                clearFields();
                Node node = (Node) mouseEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/programs.fxml"))));
                stage.setScene(scene);
                stage.show();
            } catch (SQLException|IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void descriptionHandlerEvent(KeyEvent keyEvent) {
        textFieldHandlerEvent(keyEvent);
    }
    public void nomHandlerEvent(KeyEvent keyEvent) {
        textFieldHandlerEvent(keyEvent);
    }

    private void textFieldHandlerEvent(KeyEvent keyEvent){
        if (keyEvent.getCode().getCode() == 10) {
            if (nameAdd.getText().isEmpty() || descriptionAdd.getText().isEmpty()) {
                error.setTextFill(Color.TOMATO);
                error.setText("Please fill in the fields");
            } else {
                try {
                    ResultSet rs;
                    preparedStatement = connection.prepareStatement(SQL);
                    preparedStatement.setString(1, nameAdd.getText());
                    rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        error.setTextFill(Color.TOMATO);
                        error.setText("This program already exists");
                    } else {
                        try {
                            String st = "INSERT INTO program (nameProgram,descriptionProgram) VALUES (?,?)";
                            preparedStatement = connection.prepareStatement(st);
                            preparedStatement.setString(1, nameAdd.getText());
                            preparedStatement.setString(2, descriptionAdd.getText());
                            preparedStatement.executeUpdate();
                            clearFields();
                            Node node = (Node) keyEvent.getSource();
                            Stage stage = (Stage) node.getScene().getWindow();
                            stage.close();
                            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/programs.fxml"))));
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException | SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public void returnToProgramPageHandlerEvent(javafx.scene.input.MouseEvent mouseEvent){
        try {
            clearFields();
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


    private void clearFields(){
        nameAdd.clear();
        descriptionAdd.clear();
    }

}
