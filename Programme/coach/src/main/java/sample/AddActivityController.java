package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.ConnectionUtilis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class AddActivityController {

    @FXML
    public Label error;

    @FXML
    public Button cancelButton;

    @FXML
    public Button addButton;

    @FXML
    public TextField nameActivity;

    @FXML
    public TextArea descriptionActivity;

    PreparedStatement preparedStatement;
    Connection connection;

    public AddActivityController() {
        connection = ConnectionUtilis.conDB();
    }

    public void createHandlerEvent(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if (nameActivity.getText().isEmpty()) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please enter the name of the activity");
        } else {
            createActivity();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/activities.fxml"))));
            stage.setScene(scene);
            stage.show();
        }
    }

    private void createActivity() {
        try {
            String st = "INSERT INTO Activities (nameActivity,descriptionActivity) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(st);
            preparedStatement.setString(1, nameActivity.getText());
            preparedStatement.setString(2, descriptionActivity.getText());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            error.setTextFill(Color.GREEN);
            error.setText("Activity added successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            error.setTextFill(Color.TOMATO);
            error.setText(ex.getMessage());
        }
    }

    public void activitiesHandler(javafx.scene.input.MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == cancelButton){
            try{
                Node node = (Node) mouseEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/activities.fxml"))));
                stage.setScene(scene);
                stage.show();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
