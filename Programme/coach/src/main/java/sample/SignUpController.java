package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtilis;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordBisField;

    @FXML
    private Label error;


    PreparedStatement preparedStatement;
    Connection connection;
    String SQL = "SELECT * FROM user WHERE userName = ?";
    String addUserQuery = "INSERT INTO user(userName, password) VALUES (? , ?)";


    @Override
    public void initialize (URL url, ResourceBundle rb){}

    public SignUpController(){
        connection = ConnectionUtilis.conDB();
    }

    public void cancelButtHandler(MouseEvent mouseEvent) {
        closeWindows(mouseEvent);
    }

    public void signUpButtHandler(MouseEvent mouseEvent) {
        if (usernameTextField.getText().isEmpty()||passwordField.getText().isEmpty()||passwordBisField.getText().isEmpty()){
            error.setTextFill(javafx.scene.paint.Color.RED);
            error.setText("Please complete all fields");
        }
        else {
            if (!passwordField.getText().equals(passwordBisField.getText())){
                error.setTextFill(javafx.scene.paint.Color.RED);
                error.setText("Passwords are not the same");
            }
            else {
                try {
                    ResultSet rs;
                    preparedStatement = connection.prepareStatement(SQL);
                    preparedStatement.setString(1, usernameTextField.getText());
                    rs = preparedStatement.executeQuery();
                    if (rs.next()){
                        error.setTextFill(javafx.scene.paint.Color.RED);
                        error.setText("Username has already been taken");
                    }
                    else {
                        try {
                            preparedStatement = connection.prepareStatement(addUserQuery);
                            preparedStatement.setString(1, usernameTextField.getText());
                            preparedStatement.setString(2, passwordField.getText());
                            preparedStatement.executeUpdate();
                            clearFields();
                            closeWindows(mouseEvent);
                        }catch (SQLException ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                }catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }

            }
        }
    }

    private void clearFields() {
        usernameTextField.clear();
        passwordField.clear();
        passwordBisField.clear();
    }
    private void closeWindows(MouseEvent mouseEvent){
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    private void closeWindows(KeyEvent keyEvent){
        Node node = (Node) keyEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void usernameTextFieldHandler(KeyEvent keyEvent) {
        textFieldHandler(keyEvent);
    }

    public void passwordFieldHandler(KeyEvent keyEvent) {
        textFieldHandler(keyEvent);
    }

    public void passwordBisFieldHandler(KeyEvent keyEvent) {
        textFieldHandler(keyEvent);
    }

    private void textFieldHandler(KeyEvent keyEvent) {
        if (keyEvent.getCode().getCode() == 10) {
            if (usernameTextField.getText().isEmpty()||passwordField.getText().isEmpty()||passwordBisField.getText().isEmpty()){
                error.setTextFill(javafx.scene.paint.Color.RED);
                error.setText("Please complete all fields");
            }
            else {
                if (!passwordField.getText().equals(passwordBisField.getText())){
                    error.setTextFill(javafx.scene.paint.Color.RED);
                    error.setText("Passwords are not the same");
                }
                else {
                    try {
                        ResultSet rs;
                        preparedStatement = connection.prepareStatement(SQL);
                        preparedStatement.setString(1, usernameTextField.getText());
                        rs = preparedStatement.executeQuery();
                        if (rs.next()){
                            error.setTextFill(javafx.scene.paint.Color.RED);
                            error.setText("Username has already been taken");
                        }
                        else {
                            try {
                                preparedStatement = connection.prepareStatement(addUserQuery);
                                preparedStatement.setString(1, usernameTextField.getText());
                                preparedStatement.setString(2, passwordField.getText());
                                preparedStatement.executeUpdate();
                                clearFields();
                                closeWindows(keyEvent);
                            }catch (SQLException ex){
                                System.out.println(ex.getMessage());
                            }
                        }
                    }catch (SQLException ex){
                        System.out.println(ex.getMessage());
                    }

                }
            }
        }
    }
}
