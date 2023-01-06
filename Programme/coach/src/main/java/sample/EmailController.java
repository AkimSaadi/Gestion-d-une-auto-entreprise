package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.ConnectionUtilis;

import static sample.ClientsController.currentIdClient;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmailController implements Initializable {

    @FXML
    private Label emailClient;

    @FXML
    private TextField mailField;

    @FXML
    private Label errorLabel;
    String SQL = "SELECT mail FROM client WHERE idClient = ?";

    PreparedStatement preparedStatement;
    Connection connection;
    public EmailController() {
        connection = ConnectionUtilis.conDB();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ResultSet rs;
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, currentIdClient);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String mail = rs.getString("mail");
                emailClient.setText(mail);
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void returnToClientPageHandlerEvent(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void mailFieldHandler(KeyEvent keyEvent) {
        if (keyEvent.getCode().getCode() == 10) {
            if(canSendMailHandler()) {
                Node node = (Node) keyEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

            }
        }
    }

    public void sendButtHandler(MouseEvent mouseEvent) {
        if(canSendMailHandler()) {
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

        }
    }

    public boolean canSendMailHandler(){
        if (mailField.getText().isEmpty()){
            errorLabel.setTextFill(Color.TOMATO);
            errorLabel.setText("Please fill in the field");
            return false;
        }
        else {
            return true;
        }
    }
}
