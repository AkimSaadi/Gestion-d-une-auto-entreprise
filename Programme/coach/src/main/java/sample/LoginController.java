package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtilis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Label error;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;
    @FXML
    private Button loginButt;

    Connection conn;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public LoginController() {
        conn = ConnectionUtilis.conDB();
    }

    public void signInHandler (){
        try {
            Scene signInScene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/SignUp.fxml"))));
            Stage stage = new Stage();
            stage.getIcons().add(new Image("images/attachment_88415434.jpg"));
            stage.setTitle("Create an account");
            stage.setScene(signInScene);
            stage.show();
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void loginHandler(MouseEvent mouseEvent) throws SQLException {
        if(mouseEvent.getSource() == loginButt)
            if(login().equals("SUCCESS")) {
                try {
                    Node node = (Node) mouseEvent.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Home.fxml"))));
                    stage.setScene(scene);
                    stage.show();

                }
                catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            }

    }


    public String login() throws SQLException {
        String user = userName.getText();
        String pass = password.getText();

        String req = "select * from user where userName = ? and password = ?";
        preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, pass);
        resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()){
            error.setTextFill(javafx.scene.paint.Color.RED);
            error.setText("Username or password wrong");
            return "ERROR";
        }
        else {
            error.setTextFill(javafx.scene.paint.Color.GREEN);
            error.setText("Login with success");
            return "SUCCESS";
        }
    }


    public void passwordHandler(KeyEvent keyEvent) {
        textFieldHandler(keyEvent);
    }


    public void usernameHandler(KeyEvent keyEvent) {
        textFieldHandler(keyEvent);
    }

    private void textFieldHandler(KeyEvent keyEvent) {
        if (keyEvent.getCode().getCode() == 10){
            try {
                if(login().equals("SUCCESS")) {
                    try {
                        Node node = (Node) keyEvent.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Home.fxml"))));
                        stage.setScene(scene);
                        stage.show();

                    }
                    catch (IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
