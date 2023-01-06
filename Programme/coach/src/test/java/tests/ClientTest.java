
import org.junit.jupiter.api.*;
import util.ConnectionUtilis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    String SQL = "CREATE TABLE `clientTest` (\n" +
            "  `idClient` int(100) NOT NULL,\n" +
            "  `prenom` text NOT NULL,\n" +
            "  `nom` text NOT NULL,\n" +
            "  `dateNaiss` text NOT NULL,\n" +
            "  `adresse` text NOT NULL,\n" +
            "  `mail` text NOT NULL,\n" +
            "  `telephone` text NOT NULL,\n" +
            "  `taille` double NOT NULL,\n" +
            "  `poids` double NOT NULL,\n" +
            "  `typeSportif` text NOT NULL,\n" +
            "  `objectif` text NOT NULL,\n" +
            "  `lieuPratique` text NOT NULL,\n" +
            "  `programme` text NOT NULL\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
    String SQLAdd = "INSERT INTO clientTest (prenom,nom, dateNaiss, adresse, mail, telephone, taille, poids, typeSportif, objectif, lieuPratique, programme ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    String SQLUpdate = "UPDATE clientTest SET prenom = ?";
    String SQLPrimaryKey = "ALTER TABLE `clientTest`\n" +
                            "  ADD PRIMARY KEY (`idClient`);";
    String SQLAutoIncrement = "ALTER TABLE `clientTest`  MODIFY `idClient` int(100) NOT NULL AUTO_INCREMENT";
    String SQLSelect = "SELECT prenom,nom, dateNaiss, adresse, mail, telephone, taille, poids, typeSportif, objectif, lieuPratique, programme FROM clientTest";
    String SQLDrop = "DROP TABLE IF EXISTS clientTest";
    PreparedStatement preparedStatement;
    Connection connection;

    String prenomExpected = "Georges";
    String nomExpected = "Bardaghji";
    String dateNaissExpected = "27/06/1997";
    String adresseExpected = "Marseille";
    String mailExpected = "george@gmail.com";
    String telephoneExpected = "00000000";
    String tailleExpected = "190";
    String poidsExpected = "85";
    String typeSportifExpected = "Sportif débutant";
    String objectifExpected = "perte de poids";
    String lieuPratiqueExpected = "En salle";
    String programmeExpected = "Fitness";

    String prenomActual = "";
    String nomActual = "";
    String dateNaissActual = "";
    String adresseActual = "";
    String mailActual = "";
    String telephoneActual = "";
    String tailleActual = "";
    String poidsActual = "";
    String typeSportifActual = "";
    String objectifActual = "";
    String lieuPratiqueActual = "";
    String programmeActual = "";

    @BeforeEach
    void setUp() {
        connection = (Connection) ConnectionUtilis.conDB();
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQL);
            preparedStatement.execute();
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLPrimaryKey);
            preparedStatement.execute();
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAutoIncrement);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @AfterEach
    void tearDown() {
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLDrop);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    void addClient(){
        try{
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,prenomExpected);
            preparedStatement.setString(2,nomExpected);
            preparedStatement.setString(3,dateNaissExpected);
            preparedStatement.setString(4,adresseExpected);
            preparedStatement.setString(5,mailExpected);
            preparedStatement.setString(6,telephoneExpected);
            preparedStatement.setString(7,tailleExpected);
            preparedStatement.setString(8,poidsExpected);
            preparedStatement.setString(9,typeSportifExpected);
            preparedStatement.setString(10,objectifExpected);
            preparedStatement.setString(11,lieuPratiqueExpected);
            preparedStatement.setString(12,programmeExpected);
            preparedStatement.execute();
            ResultSet rs;
            rs = preparedStatement.executeQuery(SQLSelect);

            while (rs.next()){
                prenomActual = rs.getString("prenom");
                nomActual = rs.getString("nom");
                dateNaissActual = rs.getString("dateNaiss");
                adresseActual = rs.getString("adresse");
                mailActual = rs.getString("mail");
                telephoneActual = rs.getString("telephone");
                tailleActual = rs.getString("taille");
                poidsActual = rs.getString("poids");
                typeSportifActual = rs.getString("typeSportif");
                objectifActual = rs.getString("objectif");
                lieuPratiqueActual = rs.getString("lieuPratique");
                programmeActual = rs.getString("programme");

            }
            assertEquals(prenomExpected, prenomActual);
            assertEquals(nomExpected, nomActual);
            assertEquals(dateNaissExpected, dateNaissActual);
            assertEquals(adresseExpected, adresseActual);
            assertEquals(mailExpected, mailActual);
            assertEquals(telephoneExpected, telephoneActual);
            assertEquals(tailleExpected, tailleActual);
            assertEquals(poidsExpected, poidsActual);
            assertEquals(typeSportifExpected, typeSportifActual);
            assertEquals(objectifExpected, objectifActual);
            assertEquals(lieuPratiqueExpected, lieuPratiqueActual);
            assertEquals(programmeExpected, programmeActual);
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @Test
    void modifierPrenomClient(){
        try{
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,prenomExpected);
            preparedStatement.setString(2,nomExpected);
            preparedStatement.setString(3,dateNaissExpected);
            preparedStatement.setString(4,adresseExpected);
            preparedStatement.setString(5,mailExpected);
            preparedStatement.setString(6,telephoneExpected);
            preparedStatement.setString(7,tailleExpected);
            preparedStatement.setString(8,poidsExpected);
            preparedStatement.setString(9,typeSportifExpected);
            preparedStatement.setString(10,objectifExpected);
            preparedStatement.setString(11,lieuPratiqueExpected);
            preparedStatement.setString(12,programmeExpected);
            preparedStatement.execute();
            String prenomExpected = "Jaques";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLUpdate);
            preparedStatement.setString(1,prenomExpected);
            preparedStatement.execute();
            ResultSet rs;
            rs = preparedStatement.executeQuery(SQLSelect);
            while (rs.next()){
                prenomActual = rs.getString("prenom");
                nomActual = rs.getString("nom");
                dateNaissActual = rs.getString("dateNaiss");
                adresseActual = rs.getString("adresse");
                mailActual = rs.getString("mail");
                telephoneActual = rs.getString("telephone");
                tailleActual = rs.getString("taille");
                poidsActual = rs.getString("poids");
                typeSportifActual = rs.getString("typeSportif");
                objectifActual = rs.getString("objectif");
                lieuPratiqueActual = rs.getString("lieuPratique");
                programmeActual = rs.getString("programme");
            }
            assertEquals(prenomExpected, prenomActual);

        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    void deleteClient(){
        try{
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,prenomExpected);
            preparedStatement.setString(2,nomExpected);
            preparedStatement.setString(3,dateNaissExpected);
            preparedStatement.setString(4,adresseExpected);
            preparedStatement.setString(5,mailExpected);
            preparedStatement.setString(6,telephoneExpected);
            preparedStatement.setString(7,tailleExpected);
            preparedStatement.setString(8,poidsExpected);
            preparedStatement.setString(9,typeSportifExpected);
            preparedStatement.setString(10,objectifExpected);
            preparedStatement.setString(11,lieuPratiqueExpected);
            preparedStatement.setString(12,programmeExpected);
            preparedStatement.execute();
            ResultSet  rs;
            preparedStatement = (PreparedStatement) connection.prepareStatement("SELECT COUNT(prenom) FROM clientTest");
            rs =preparedStatement.executeQuery();
            int numberExpected = 1;
            int numberActual = -1;
            while (rs.next()){
                numberActual = parseInt(rs.getString("COUNT(prenom)"));
            }
            assertEquals(numberExpected,numberActual);
            preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM clientTest WHERE prenom = 'Georges'");
            preparedStatement.execute();
            preparedStatement = (PreparedStatement) connection.prepareStatement("SELECT COUNT(prenom) FROM clientTest");
            rs =preparedStatement.executeQuery();
            numberExpected = 0;
            numberActual = -1;
            while (rs.next()){
                numberActual = parseInt(rs.getString("COUNT(prenom)"));
            }
            assertEquals(numberExpected,numberActual);
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
