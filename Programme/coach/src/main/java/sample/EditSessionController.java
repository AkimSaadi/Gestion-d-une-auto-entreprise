package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import mod.Client;
import mod.placePractice;
import mod.Program;

import util.ConnectionUtilis;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditSessionController extends PlanningController implements Initializable {



    @FXML
    private ComboBox<Client> clientEditComboBox;

    @FXML
    private ComboBox<Program> programmeEditComboBox;

    @FXML
    private ComboBox<String> hourDebutEditComboBox;

    @FXML
    private ComboBox<String> hourFinEditComboBox;

    @FXML
    private DatePicker datePickerSessionEditComboBox;

    @FXML
    private ComboBox<placePractice> placeEditComboBox;

    @FXML
    private Button approveEditButton;

    PreparedStatement preparedStatement;
    Connection connection;

    public EditSessionController() {
        connection = ConnectionUtilis.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillEditClientsComboBox(clientEditComboBox);

        fillDateEditComboBox(hourDebutEditComboBox, 0, false);
        String startIncrementation = selectedSession.getDateStart().substring(selectedSession.getDateStart().indexOf(" ")+1, selectedSession.getDateStart().indexOf(":"));
        fillDateEditComboBox(hourFinEditComboBox, Integer.parseInt(startIncrementation) + 1, true);
        setDatePickerSessionEditComboBox(datePickerSessionEditComboBox);
        fillProgrammeEditComboBox(programmeEditComboBox);
        fillLieuEditComboBox(placeEditComboBox);


    }

    private void fillDateEditComboBox(ComboBox<String> hourComboBox, int startIncrementation, boolean end) {
        fillDateComboBox(hourComboBox, startIncrementation, end);
        String dateComplete = end ? selectedSession.getDateEnd() : selectedSession.getDateStart();
        String hour = dateComplete.substring(dateComplete.indexOf(" ")+1, dateComplete.indexOf(":", dateComplete.indexOf(":") + 1));
        hourComboBox.getSelectionModel().select(hour);
    }

    private void setDatePickerSessionEditComboBox(DatePicker datePickerSessionEditComboBox) {
        setDatePickerMinValue(datePickerSessionEditComboBox);
        datePickerSessionEditComboBox.setValue(LocalDate.parse(selectedSession.getDateStart().split(" ")[0]));
    }

    private void fillProgrammeEditComboBox(ComboBox<Program> programmeEditComboBox) {
        fillProgramComboBox(programmeEditComboBox);
        for(int i = 0; i < programmeEditComboBox.getItems().size(); i++) {
            Program program = programmeEditComboBox.getItems().get(i);
            if(program.getIdProgramme() == selectedSession.getIdProgram()) {
                programmeEditComboBox.getSelectionModel().select(i);
                break;
            }
        }
    }

    private void fillLieuEditComboBox(ComboBox<placePractice> lieuEditComboBox) {
        fillPlaceComboBox(lieuEditComboBox);
        for(int i = 0; i < lieuEditComboBox.getItems().size(); i++) {
            placePractice lieu = lieuEditComboBox.getItems().get(i);
            if(lieu.getIdPlace() == selectedSession.getIdPlace()) {
                lieuEditComboBox.getSelectionModel().select(i);
                break;
            }
        }
    }

    private void fillEditClientsComboBox(ComboBox<Client> clientEditComboBox) {
        fillClientsComboBox(clientEditComboBox);
        clientEditComboBox.getSelectionModel().select(selectedSession.getClient());
    }

    @FXML
    void beginHourSelectedEdit() {
        int debutBoucle = Integer.parseInt(hourDebutEditComboBox.getValue().split(":")[0]) + 1;
        hourFinEditComboBox.getItems().clear();
        fillDateComboBox(hourFinEditComboBox, debutBoucle, true);
    }

    @FXML
    void updateEdit() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to edit this session?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> resultAlert = alert.showAndWait();

        if (resultAlert.isPresent() && resultAlert.get() == ButtonType.OK) {
            Client client = clientEditComboBox.getValue();
            placePractice lieu = placeEditComboBox.getValue();
            Program program = programmeEditComboBox.getValue();
            LocalDate dateSession = datePickerSessionEditComboBox.getValue();
            String hourStartValue = hourDebutEditComboBox.getValue();
            String hourEndValue = hourFinEditComboBox.getValue();

            String dateDebutComplete = dateSession + " " + hourStartValue + ":00";
            String dateFinComplete = dateSession + " " + hourEndValue + ":00";

            String requestUpdate = "UPDATE planning " +
                    "SET idClient = ?, idProgram = ?, idPlace = ?, dateStart = ?, dateEnd = ? WHERE idPlanning = ?";

            preparedStatement = connection.prepareStatement(requestUpdate);
            preparedStatement.setInt(1, client.getIdClient());
            preparedStatement.setInt(2, program.getIdProgramme());
            preparedStatement.setInt(3, lieu.getIdPlace());
            preparedStatement.setString(4, dateDebutComplete);
            preparedStatement.setString(5, dateFinComplete);
            preparedStatement.setInt(6, selectedSession.getIdPlanning());
            String checkRequest = "SELECT idPlanning, dateStart, dateEnd FROM planning WHERE ? < dateEnd AND ? > dateStart AND idPlanning != ?";
            List<String> incoherence = getIncoherenceSession(dateDebutComplete,dateFinComplete, checkRequest, true, selectedSession.getIdPlanning());
            if(incoherence.size() > 0 ) {
                StringBuilder message = new StringBuilder();
                for(String s : incoherence) {message.append(s); }
                message.append("Are you sure you edit/add this session ?");
                Alert incoherenceAlert = new Alert(Alert.AlertType.WARNING, message.toString(), ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> resultIncoherence = incoherenceAlert.showAndWait();
                if(resultIncoherence.isPresent() && resultIncoherence.get() == ButtonType.OK) {
                    preparedStatement.executeUpdate();
                    Alert confirmAlert = new Alert(Alert.AlertType.NONE, "The session has been scheduled successfully.", ButtonType.OK);
                    confirmAlert.showAndWait();
                    Stage stage = (Stage) approveEditButton.getScene().getWindow();
                    stage.close();
                }
            }
            else {
                preparedStatement.executeUpdate();
                Alert confirmAlert = new Alert(Alert.AlertType.NONE, "The session has been scheduled successfully.", ButtonType.OK);
                confirmAlert.show();
                Stage stage = (Stage) approveEditButton.getScene().getWindow();
                stage.close();
            }
        }
    }
}
