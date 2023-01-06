package tests;

import org.junit.jupiter.api.*;
import util.ConnectionUtilis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;


public class activiteTest {
    String SQL = "CREATE TABLE activiteTest (idActivite int(100) NOT NULL, nomActivite text NOT NULL, descActivite text NOT NULL)";
    String SQLAdd = "INSERT INTO activiteTest (nomActivite, descActivite) VALUES (?, ?)";
    String SQLUpdate = "UPDATE activiteTest SET nomActivite = ?";
    String SQLPrimaryKey = "ALTER TABLE activiteTest ADD PRIMARY KEY (idActivite);";
    String SQLAutoIncrement = "ALTER TABLE `activiteTest`  MODIFY `idActivite` int(100) NOT NULL AUTO_INCREMENT";
    String SQLSelect = "SELECT nomActivite, descActivite FROM activiteTest";
    String SQLDrop = "DROP TABLE IF EXISTS activiteTest";
    PreparedStatement preparedStatement;
    Connection connection;

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
    void addActivite(){
        try{
            String nomActiviteExpected = "Pompe";
            String descriptionActiviteExpected = "La pompe est un exercice physique de musculation qui sollicite principalement " +
                    "le grand pectoral, le deltoïde et les triceps. Cet exercice est populaire car il peut être pratiqué n'importe où, " +
                    "ne nécessitant pas de matériel.";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,nomActiviteExpected);
            preparedStatement.setString(2,descriptionActiviteExpected);
            preparedStatement.execute();
            ResultSet rs;
            rs = preparedStatement.executeQuery(SQLSelect);
            String nomActiviteActual = "";
            String descriptionActiviteActual = "";
            while (rs.next()){
                nomActiviteActual = rs.getString("nomActivite");
                descriptionActiviteActual = rs.getString("descActivite");
            }
            assertEquals(nomActiviteExpected,nomActiviteActual);
            assertEquals(descriptionActiviteExpected,descriptionActiviteActual);

        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @Test
    void updateActivite(){
        try{
            String nomActivite = "Pompes";
            String descriptionActiviteExpected = "La pompe est un exercice physique de musculation qui sollicite principalement " +
                    "le grand pectoral, le deltoïde et les triceps. Cet exercice est populaire car il peut être pratiqué n'importe où, " +
                    "ne nécessitant pas de matériel.";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,nomActivite);
            preparedStatement.setString(2,descriptionActiviteExpected);
            preparedStatement.execute();
            String nomActiviteExpected = "Pompe";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLUpdate);
            preparedStatement.setString(1,nomActiviteExpected);
            preparedStatement.execute();
            ResultSet rs;
            rs = preparedStatement.executeQuery(SQLSelect);
            String nomActiviteActual = "";
            String descriptionActiviteActual = "";
            while (rs.next()){
                nomActiviteActual = rs.getString("nomActivite");
                descriptionActiviteActual = rs.getString("descActivite");
            }
            assertEquals(nomActiviteExpected,nomActiviteActual);
            assertEquals(descriptionActiviteExpected,descriptionActiviteActual);

        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    void deleteActivite(){
        try{
            String nomActivite = "Pompes";
            String descriptionActivite = "La pompe est un exercice physique de musculation qui sollicite principalement " +
                    "le grand pectoral, le deltoïde et les triceps. Cet exercice est populaire car il peut être pratiqué n'importe où, " +
                    "ne nécessitant pas de matériel.";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,nomActivite);
            preparedStatement.setString(2,descriptionActivite);
            preparedStatement.execute();
            ResultSet  rs;
            preparedStatement = (PreparedStatement) connection.prepareStatement("SELECT COUNT(nomActivite) FROM activiteTest");
            rs =preparedStatement.executeQuery();
            int numberExpected = 1;
            int numberActual = -1;
            while (rs.next()){
                numberActual = parseInt(rs.getString("COUNT(nomActivite)"));
            }
            assertEquals(numberExpected,numberActual);
            preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM activiteTest WHERE nomActivite = 'Pompes'");
            preparedStatement.execute();
            preparedStatement = (PreparedStatement) connection.prepareStatement("SELECT COUNT(nomActivite) FROM activiteTest");
            rs =preparedStatement.executeQuery();
            numberExpected = 0;
            numberActual = -1;
            while (rs.next()){
                numberActual = parseInt(rs.getString("COUNT(nomActivite)"));
            }
            assertEquals(numberExpected,numberActual);
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}