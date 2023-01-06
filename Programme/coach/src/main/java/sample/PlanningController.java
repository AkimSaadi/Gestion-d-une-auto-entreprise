package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mod.Client;
import mod.placePractice;
import mod.PlanningSession;
import mod.Program;
import util.ConnectionUtilis;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlanningController implements Initializable {

    @FXML
    private ComboBox<Client> clientsComboBox;

    @FXML
    private ComboBox<Program> programsComboBox;

    @FXML
    private ComboBox<String> dateStartComboBox;

    @FXML
    private ComboBox<String> dateEndComboBox;

    @FXML
    private ComboBox<placePractice> placeComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView tableView;

    @FXML
    private Button editSessionButton;

    @FXML
    private Button deleteSessionButton;

    @FXML
    private DatePicker datePickerAddPlanning;

    @FXML
    private DatePicker datePickerPlanning;

    @FXML
    private Label planningLabel;


    PreparedStatement preparedStatement;
    Connection connection;
    static PlanningSession selectedSession;
    protected String SQL = "SELECT idClient, firstname,name,mail FROM client ORDER BY name";
    protected String SQL2 = "SELECT * FROM placePractice";
    protected String SQL3 = "SELECT * FROM programs";
    protected String request = "SELECT pl.idPlanning, cl.name, cl.firstname, cl.mail, pr.nameProgram, pp.place, pl.dateStart, pl.dateEnd, pl.idClient, pl.idProgram, pl.idPlace " +
            "FROM planning pl, client cl, programs pr, placePractice pp " +
            "WHERE pl.idClient = cl.idClient AND pl.idProgram = pr.idProgram AND pl.idPlace = pp.idPlace " +
            "AND DATE(pl.dateStart) = DATE(?)" +
            "ORDER BY pl.dateStart";

    String checkRequest = "SELECT idPlanning, dateStart, dateEnd FROM planning WHERE ? < dateEnd AND ? > dateStart";


    public PlanningController() {
        connection = ConnectionUtilis.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillClientsComboBox(clientsComboBox);
        fillDateComboBox(dateStartComboBox, 0, false);
        fillDateComboBox(dateEndComboBox, 1, true);
        setDatePickerMinValue(datePickerAddPlanning);
        datePickerPlanning.setValue(LocalDate.now());
        fillProgramComboBox(programsComboBox);
        fillPlaceComboBox(placeComboBox);
        fetchColumns(LocalDate.now(), tableView);
        getDaySessions(LocalDate.now());
        completeDateLabel(LocalDate.now());
    }

    /**
     * Génère dynamiquement les colonnes du tableau
     */
    protected void fetchColumns(LocalDate date, TableView tableView) {
        ResultSet rs;
        try {
            preparedStatement = connection.prepareStatement(request);
            preparedStatement.setString(1, date.toString());
            rs = preparedStatement.executeQuery();
            for (int i = 1; i < rs.getMetaData().getColumnCount() - 3; i++) {
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i + 1)));
                tableView.getColumns().add(col);
            }
        }
        catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Récupère la liste des sessions à la LocalDate date
     */
    private void getDaySessions(LocalDate date) {
        ResultSet rs;
        try {
            preparedStatement = connection.prepareStatement(request);
            preparedStatement.setString(1, date.toString());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                PlanningSession pls =
                        new PlanningSession(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7), rs.getString(8),
                                rs.getInt(9), rs.getInt(10), rs.getInt(11));
                tableView.getItems().add(pls);
            }
        }
        catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Retire les dates antérieures à la date du jour
     */
    protected void setDatePickerMinValue(DatePicker datepicker) {
        LocalDate minDate = LocalDate.now();
        datepicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate));
                    }});
        datepicker.setValue(LocalDate.now());
    }

    /**
     * Remplissage des combobox dateDebutCombobox et dateFinCombobox
     */
    protected void fillDateComboBox(ComboBox<String> comboBox, int debutIncrementation, boolean end) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, debutIncrementation);
        calendar.set(Calendar.MINUTE, 0);
        DateFormat df = new SimpleDateFormat("HH:mm");
        int endIncrementation = end ? 23 : 22;
        for(int i = debutIncrementation; i <= endIncrementation; i++) {
            comboBox.getItems().add(df.format(calendar.getTime()));
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        }
        comboBox.getSelectionModel().selectFirst();
    }

    /**
     * Le label du planning est générée pour afficher la semaine actuelle
     */
    private void completeDateLabel(LocalDate date) {
        String s_dateDuJour = date.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));
        planningLabel.setText("Planning du " + s_dateDuJour);
    }

    /**
     * Remplissage de programscombobox
     */
    protected void fillProgramComboBox(ComboBox programsComboBox) {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL3);
            while (rs.next()) {
                int idProgram = rs.getInt(1);
                String nomProgram = rs.getString(2);
                programsComboBox.getItems().add(new Program(idProgram, nomProgram));
            }
            programsComboBox.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        programsComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Remplissage de placeCombobox
     */
    protected void fillPlaceComboBox(ComboBox placeComboBox) {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL2);
            while (rs.next()) {
                int idLieu = rs.getInt(1);
                String nomLieu = rs.getString(2);
                placeComboBox.getItems().add(new placePractice(idLieu, nomLieu));
            }
            placeComboBox.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        placeComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Remplissage de clientsCombobox
     */
    protected void fillClientsComboBox(ComboBox clientsComboBox) {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL);
            while (rs.next()) {
                int idClient = rs.getInt(1);
                String firstname = rs.getString(2);
                String name = rs.getString(3);
                String mail = rs.getString(4);
                clientsComboBox.getItems().add(new Client(idClient, firstname, name, mail));
            }
            clientsComboBox.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void beginHourSelected() {
        int debutBoucle = Integer.parseInt(dateStartComboBox.getValue().split(":")[0]) + 1;
        dateEndComboBox.getItems().clear();
        fillDateComboBox(dateEndComboBox, debutBoucle, true);
    }

    /**
     * Trigger quand le bouton ajouter du formulaire est pressée
     * Ajoute la session dans la BDD s'il répond au critères demandées
     * Demande la confirmation de l'utilisateur avant
     */
    @FXML
    void addPlanningHandlerEvent() throws SQLException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to add this session?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Client client = clientsComboBox.getValue();
            placePractice lieu = placeComboBox.getValue();
            Program program = programsComboBox.getValue();
            LocalDate dateSession = datePickerAddPlanning.getValue();
            String hourStartValue = dateStartComboBox.getValue();
            String hourEndValue = dateEndComboBox.getValue();
            if (client == null || lieu == null || program == null || dateSession == null || hourStartValue == null || hourEndValue == null) {
                errorLabel.setTextFill(Color.TOMATO);
                errorLabel.setText("Please fill in all the information");
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);
                String dateDebutComplete = dateSession + " " + hourStartValue + ":00";
                String dateFinComplete = dateSession + " " + hourEndValue + ":00";
                String request = "INSERT INTO planning(idClient, idProgram, idPlace, dateStart, dateEnd) VALUES (?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(request);
                preparedStatement.setInt(1, client.getIdClient());
                preparedStatement.setInt(2, program.getIdProgramme());
                preparedStatement.setInt(3, lieu.getIdPlace());
                preparedStatement.setString(4, dateDebutComplete);
                preparedStatement.setString(5, dateFinComplete);
                List<String> incoherence = getIncoherenceSession(dateDebutComplete,dateFinComplete, checkRequest, false, 0);
                if(incoherence.size() > 0 ) {
                    StringBuilder message = new StringBuilder();
                    for(String s : incoherence) {message.append(s); }
                    message.append("Are you sure you want to add this session?");
                    Alert incoherenceAlert = new Alert(Alert.AlertType.WARNING, message.toString(), ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> resultIncoherence = incoherenceAlert.showAndWait();
                    if(resultIncoherence.isPresent() && resultIncoherence.get() == ButtonType.OK) {
                        preparedStatement.executeUpdate();
                        Alert confirmAlert1 = new Alert(Alert.AlertType.NONE, "The session has been scheduled successfully.", ButtonType.OK);
                        confirmAlert1.show();
                        datePickerPlanning.setValue(LocalDate.parse(dateSession.toString()));
                        tableView.getItems().clear();
                        getDaySessions(LocalDate.parse(dateSession.toString()));
                    }
                }
                else {
                    preparedStatement.executeUpdate();
                    Alert confirmAlert = new Alert(Alert.AlertType.NONE, "The session has been scheduled successfully.", ButtonType.OK);
                    confirmAlert.show();
                    datePickerPlanning.setValue(LocalDate.parse(dateSession.toString()));
                    tableView.getItems().clear();
                    getDaySessions(LocalDate.parse(dateSession.toString()));
                }
            }

        }
    }

    /**
     * Trigger quand la valeur du datePicker datePickerPlanning change.
     * Mets à jour le label titre dateLabel et les sessions dans le tableau
     */
    @FXML
    protected void datePickerGetDayPlanning() {
        tableView.getItems().clear();
        selectedSession = null;
        deleteSessionButton.setDisable(true);
        editSessionButton.setDisable(true);
        getDaySessions(datePickerPlanning.getValue());
        completeDateLabel(datePickerPlanning.getValue());
    }

    /**
     * Trigger quand un click est effectué sur le tableau
     * récupère la ligne dans une variable de type PlanningSession
     * Rend disponible le bouton de suppression de session
     */
    @FXML
    void getRowTablePlanning() {
        selectedSession = (PlanningSession) tableView.getSelectionModel().getSelectedItem();
        if(selectedSession != null) {
            editSessionButton.setDisable(false);
            deleteSessionButton.setDisable(false);
        }
    }

    /**
     * Trigger quand le bouton de suppression de session est pressée.
     * Demande confirmation avant suppression de la session ciblée.
     * Supprime la session ciblée.
     */
    @FXML
    void deleteSessionEvent() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to delete this session?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int idPlanning = selectedSession.getIdPlanning();
            String deleteRequest = "DELETE FROM planning WHERE idPlanning = ?";
            try {
                preparedStatement = connection.prepareStatement(deleteRequest);
                preparedStatement.setInt(1, idPlanning);
                preparedStatement.executeUpdate();
                Alert confirmAlert = new Alert(Alert.AlertType.NONE, "The session was successfully deleted.", ButtonType.OK);
                confirmAlert.showAndWait();
                datePickerGetDayPlanning();
            }
            catch (Exception e) {e.printStackTrace();}
        }
    }

    @FXML
    void editSessionEvent() throws IOException {
        //LocalDate lastDateEntered = datePickerPlanning.getValue();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("images/attachment_88415434.jpg"));
        stage.setTitle("Modifier une session");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/editSession.fxml"))));
        selectedSession = (PlanningSession) tableView.getSelectionModel().getSelectedItem();
        stage.setScene(scene);
        stage.showAndWait();
        //datePickerPlanning.setValue(lastDateEntered);
        datePickerGetDayPlanning();
    }

    @FXML
    void openPlanningEvent() throws IOException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("images/attachment_88415434.jpg"));
        stage.setTitle("Weekly planning");
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/planningWeek.fxml"))));
        stage.setScene(scene);
        stage.showAndWait();
    }


    protected List<String> getIncoherenceSession(String dateDebut, String dateFin, String checkRequest, boolean update, int idPlanning) {
        List<String> incoherence = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkRequest);
            preparedStatement.setString(1, dateDebut);
            preparedStatement.setString(2, dateFin);
            if(update) {preparedStatement.setInt(3, idPlanning);}
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                incoherence.add("You already have an appointment from " + resultSet.getString(2) + " to " + resultSet.getString(3) + '\n');
            }
        }
        catch (Exception e) {e.printStackTrace();}
        return incoherence;
    }

    public void returnToHomePageHandlerEvent(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Home.fxml"))));
        stage.setScene(scene);
        stage.show();
    }
}

