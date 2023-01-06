package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class ProgramsController implements Initializable {
    @FXML
    private TableView<ObservableList> table;

    @FXML
    private TableView<ObservableList> tableActivity;

    @FXML
    private TextField description;

    @FXML
    private TextField name;

    @FXML
    private Label error;

    @FXML
    private TextField searchbar;


    PreparedStatement preparedStatement;
    Connection connection;
    private ObservableList<ObservableList> data;
    public static String currentIdProgramme;
    public static String currentIdActivity;

    String SQLNom = "SELECT nameProgram FROM programs";
    String SQLId = "SELECT idProgram FROM programs WHERE nameProgram = ? ";
    String SQLIdNomDesc = "SELECT nameProgram, descriptionProgram FROM programs WHERE idProgram = ?";
    String deleteQuery = "DELETE FROM programs WHERE idProgram = ? ";
    String updateQuery = "UPDATE programs SET nameProgram = ?, descriptionProgram = ? WHERE  idProgram = ?";
    String createQueryView = "CREATE OR REPLACE VIEW actpro AS ";
    String createQueryView2 = "CREATE OR REPLACE VIEW actprospe AS ";
    String selectQueryView = "SELECT p.nameProgram, a.nameActivity, ap.quantityActivity, ap.repetitionActivity, ap.restActivity ";
    String fromQueryView = "FROM programs p, activities a, activitiesPrograms ap ";
    String whereQueryView = "WHERE ap.idProgram = p.idProgram AND ap.idActivity = a.idActivity";
    String whereQueryView2 = " AND p.idProgram = ?";
    String SQLIdActivitiesPrograms = "SELECT idActivitiesPrograms FROM activitiesPrograms WHERE idProgram = ? AND  idActivity = ? AND quantityActivity = ? AND repetitionActivity = ?";
    String deleteActivitiesProgramme = "DELETE FROM activitiesPrograms WHERE idActivitiesPrograms = ?";
    String createOrUpdateView = createQueryView+selectQueryView+fromQueryView+whereQueryView;
    String createOrUpdateViewOfProg = createQueryView2+selectQueryView+fromQueryView+whereQueryView+whereQueryView2;
    String SQL3 = "SELECT nameActivity, quantityActivity, repetitionActivity, restActivity FROM actpro";
    String SQL4 = "SELECT nameActivity, quantityActivity, repetitionActivity, restActivity FROM actprospe";


    public ProgramsController() {
        connection = ConnectionUtilis.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fetColumnList();
        fetRowList();
        fetActivityColumnList();
    }

    private void fetActivityColumnList() {
        try {
            preparedStatement = connection.prepareStatement(createOrUpdateView);
            preparedStatement.execute();
            ResultSet rs = connection.createStatement().executeQuery(SQL3);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                tableActivity.getColumns().removeAll(col);
                tableActivity.getColumns().addAll(col);

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }
    public void fetActivityRowList(){
        ObservableList<ObservableList> data2 = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            preparedStatement = connection.prepareStatement(createOrUpdateViewOfProg);
            preparedStatement.setString(1,currentIdProgramme);
            preparedStatement.execute();
            rs = connection.createStatement().executeQuery(SQL4);
            while (rs.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data2.add(row);
            }
            tableActivity.setItems(data2);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void deleteHandlerEvent() {
        if (table.getSelectionModel().getSelectedItems().isEmpty()) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please select a program");
        }else {
            try {
                ResultSet rs;
                preparedStatement = connection.prepareStatement(SQLId);
                String nameProgram = (String) table.getSelectionModel().getSelectedItems().get(0).get(0);
                preparedStatement.setString(1, nameProgram);
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    currentIdProgramme = rs.getString("idProgram");
                }
                preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, currentIdProgramme);
                preparedStatement.execute();
                clearFields();
                fetRowList();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }


    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQLNom);

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
            ResultSet rs = connection.createStatement().executeQuery(SQLNom);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
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

    public void tableHandler() {
        data=FXCollections.observableArrayList();
        ResultSet rs;
        try {
            preparedStatement = connection.prepareStatement(SQLId);
            String nameProgram = (String) table.getSelectionModel().getSelectedItems().get(0).get(0);
            preparedStatement.setString(1, nameProgram);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                currentIdProgramme = rs.getString("idProgram");
            }

            preparedStatement = connection.prepareStatement(SQLIdNomDesc);
            preparedStatement.setString(1, currentIdProgramme);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                name.setText(rs.getString("nameProgram"));
                description.setText(rs.getString("descriptionProgram"));
            }
            fetActivityRowList();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateProgramHandlerEvent() {
        if (name.getText().isEmpty() || description.getText().isEmpty()) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please complete all fields");
        } else {
            try {
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, name.getText());
                preparedStatement.setString(2, description.getText());
                preparedStatement.setString(3, currentIdProgramme);
                preparedStatement.execute();
                clearFields();
                fetRowList();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
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
    private void clearFields(){
        name.clear();
        description.clear();
    }
    public void addButtHandlerEvent(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            clearFields();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/addProgram.fxml"))));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void searchBarHandlerEvent(){
        if (searchbar.getText().isEmpty()){
            SQLNom = "SELECT nameProgram FROM programs";
        }
        else{
            SQLNom = "SELECT nameProgram FROM programs WHERE nameProgram LIKE '%"+searchbar.getText()+"%'";
        }
        fetRowList();
    }

    public void addActivityHandlerEvent(javafx.scene.input.MouseEvent mouseEvent){
        if (name.getText().isEmpty() || description.getText().isEmpty()){
            error.setTextFill(Color.TOMATO);
            error.setText("Please complete all fields");
        }
        else {
            if (table.getSelectionModel().getSelectedItems().isEmpty()) {
                error.setTextFill(Color.TOMATO);
                error.setText("Please select a program");
            }else {
                try {
                    Node node = (Node) mouseEvent.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/addActivitiesProgrammes.fxml"))));
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }


    public void deleteActivityHandlerEvent() {
        String idActivitiesPrograms="";
        if (table.getSelectionModel().getSelectedItems().isEmpty()) {
            error.setTextFill(Color.TOMATO);
            error.setText("Please select a program");
        }else {
            if (tableActivity.getSelectionModel().getSelectedItems().isEmpty()) {
                error.setTextFill(Color.TOMATO);
                error.setText("Please select an activity");
            }else {
                try {
                    ResultSet rs;
                    String nameActivity = (String) tableActivity.getSelectionModel().getSelectedItems().get(0).get(0);
                    String quantityActivity = (String) tableActivity.getSelectionModel().getSelectedItems().get(0).get(1);
                    String repetitionActivity = (String) tableActivity.getSelectionModel().getSelectedItems().get(0).get(2);
                    preparedStatement = connection.prepareStatement("SELECT idActivity FROM activities WHERE nameActivity = ?");
                    preparedStatement.setString(1, nameActivity);
                    rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        currentIdActivity = rs.getString("idActivity");
                    }
                    preparedStatement = connection.prepareStatement(SQLIdActivitiesPrograms);
                    preparedStatement.setString(1,currentIdProgramme);
                    preparedStatement.setString(2, currentIdActivity);
                    preparedStatement.setString(3,quantityActivity);
                    preparedStatement.setString(4,repetitionActivity);
                    rs = preparedStatement.executeQuery();
                    while (rs.next()){
                        idActivitiesPrograms = rs.getString("idActivitiesPrograms");
                    }
                    preparedStatement = connection.prepareStatement(deleteActivitiesProgramme);
                    preparedStatement.setString(1,idActivitiesPrograms);
                    preparedStatement.execute();
                    fetActivityRowList();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}