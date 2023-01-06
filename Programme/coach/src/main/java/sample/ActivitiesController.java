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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.ConnectionUtilis;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static sample.ProgramsController.currentIdActivity;

public class ActivitiesController implements Initializable {

    @FXML
    public Label error;

    @FXML
    public TableView<ObservableList> activitiesTable;

    @FXML
    public TextField nameActivity;

    @FXML
    public TextArea descriptionActivity;
    @FXML
    public Button createActivityButton;

    @FXML
    public Button deleteActivityButton;

    @FXML
    public Button modifyActivityButton;

    @FXML
    public Button backHomeButton;

    @FXML
    public TextField searchbar;


    PreparedStatement preparedStatement;
    Connection connection;
    private ObservableList<ObservableList> data;
    String SQL = "SELECT nameActivity, descriptionActivity FROM activities";
    String SQLId = "SELECT idActivity FROM activities WHERE nameActivity = ?";
    String SQLIdNomDesc = "SELECT nameActivity, descriptionActivity FROM activities WHERE idActivity = ?";
    String deleteActivityQuery = "DELETE FROM activities WHERE idActivity = ?";
    String updateActivityQuery = "UPDATE activities SET nameActivity = ?, descriptionActivity = ? WHERE  idActivity = ?" ;


    public ActivitiesController() {
        connection =  ConnectionUtilis.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fetColumnList();
        fetRowList();
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
            activitiesTable.setItems(data);
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
                activitiesTable.getColumns().removeAll(col);
                activitiesTable.getColumns().addAll(col);
            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    public void createActivityYHandler(javafx.scene.input.MouseEvent mouseEvent) {
        try{
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/addActivity.fxml"))));
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteActivityEvent() {
        if (activitiesTable.getSelectionModel().getSelectedItems().isEmpty()) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please select an activity");
        }else{
            try {
                ResultSet rs;
                preparedStatement = connection.prepareStatement(SQLId);
                String nameActivitySelect = (String) activitiesTable.getSelectionModel().getSelectedItems().get(0).get(0);
                preparedStatement.setString(1, nameActivitySelect);
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    currentIdActivity = rs.getString("idActivity");
                }
                preparedStatement = connection.prepareStatement(deleteActivityQuery);
                preparedStatement.setString(1, currentIdActivity);
                preparedStatement.execute();
                clearFields();
                fetRowList();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private void clearFields(){
        nameActivity.clear();
        descriptionActivity.clear();
    }

    public void returnToHomePageHandlerEvent(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            clearFields();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Home.fxml"))));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateActivityHandlerEvent() {
        if (nameActivity.getText().isEmpty()) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please fill in all the information");
        } else {
            try {
                preparedStatement = connection.prepareStatement(updateActivityQuery);
                preparedStatement.setString(1, nameActivity.getText());
                preparedStatement.setString(2, descriptionActivity.getText());
                preparedStatement.setString(3, currentIdActivity);
                preparedStatement.execute();
                clearFields();
                fetRowList();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void activitiesTableHandler() {
        data=FXCollections.observableArrayList();
        ResultSet rs;
        try {
            preparedStatement = connection.prepareStatement(SQLId);
            String nameActivitySelect = (String) activitiesTable.getSelectionModel().getSelectedItems().get(0).get(0);
            preparedStatement.setString(1, nameActivitySelect);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                currentIdActivity = rs.getString("idActivity");
            }
            preparedStatement = connection.prepareStatement(SQLIdNomDesc);
            preparedStatement.setString(1, currentIdActivity);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                nameActivity.setText(rs.getString("nameActivity"));
                descriptionActivity.setText(rs.getString("descriptionActivity"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void searchBarHandlerEvent(){
        if (searchbar.getText().isEmpty()){
            SQL = "SELECT nameActivity, descriptionActivity FROM Activities";
        }
        else{
            SQL = "SELECT nameActivity, descriptionActivity FROM Activities WHERE nameActivity LIKE '%"+searchbar.getText()+"%'";
        }
        fetRowList();
    }

}
