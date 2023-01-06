package tests;


import org.junit.jupiter.api.*;
import util.ConnectionUtilis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

public class programmeTest {
    String SQL = "CREATE TABLE programmeTest (idProgramme int(100) NOT NULL, nomProgramme text NOT NULL, descProgramme text NOT NULL)";
    String SQLAdd = "INSERT INTO programmeTest (nomProgramme, descProgramme) VALUES (?, ?)";
    String SQLUpdate = "UPDATE programmeTest SET nomProgramme = ?";
    String SQLPrimaryKey = "ALTER TABLE programmeTest ADD PRIMARY KEY (idProgramme);";
    String SQLAutoIncrement = "ALTER TABLE `programmeTest`  MODIFY `idProgramme` int(100) NOT NULL AUTO_INCREMENT";
    String SQLSelect = "SELECT nomProgramme, descProgramme FROM programmeTest";
    String SQLDrop = "DROP TABLE IF EXISTS programmeTest";
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
    void addProgramme(){
        try{
            String nomProgrammeExpected = "Cardio";
            String descriptionProgrammeExpected = "Le programme a pour but d'ameliorer sa condition physique";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,nomProgrammeExpected);
            preparedStatement.setString(2,descriptionProgrammeExpected);
            preparedStatement.execute();
            ResultSet rs;
            rs = preparedStatement.executeQuery(SQLSelect);
            String nomProgrammeActual = "";
            String descriptionProgrammeActual = "";
            while (rs.next()){
                nomProgrammeActual = rs.getString("nomProgramme");
                descriptionProgrammeActual = rs.getString("descProgramme");
            }
            assertEquals(nomProgrammeExpected,nomProgrammeActual);
            assertEquals(descriptionProgrammeExpected,descriptionProgrammeActual);

        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @Test
    void changeProgramme(){
        try{
            String nomProgramme = "Cardio";
            String descriptionProgrammeExpected = "Le programme a pour but d'ameliorer sa condition physique";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,nomProgramme);
            preparedStatement.setString(2,descriptionProgrammeExpected);
            preparedStatement.execute();
            String nomProgrammeExpected = "Fitness";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLUpdate);
            preparedStatement.setString(1,nomProgrammeExpected);
            preparedStatement.execute();
            ResultSet rs;
            rs = preparedStatement.executeQuery(SQLSelect);
            String nomProgrammeActual = "";
            String descriptionProgrammeActual = "";
            while (rs.next()){
                nomProgrammeActual = rs.getString("nomProgramme");
                descriptionProgrammeActual = rs.getString("descProgramme");
            }
            assertEquals(nomProgrammeExpected,nomProgrammeActual);
            assertEquals(descriptionProgrammeExpected,descriptionProgrammeActual);

        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    void deleteProgramme(){
        try{
            String nomProgramme = "Cardio";
            String descriptionProgramme = "Le programme a pour but d'ameliorer sa condition physique";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQLAdd);
            preparedStatement.setString(1,nomProgramme);
            preparedStatement.setString(2,descriptionProgramme);
            preparedStatement.execute();
            ResultSet  rs;
            preparedStatement = (PreparedStatement) connection.prepareStatement("SELECT COUNT(nomProgramme) FROM programmeTest");
            rs =preparedStatement.executeQuery();
            int numberExpected = 1;
            int numberActual = -1;
            while (rs.next()){
                numberActual = parseInt(rs.getString("COUNT(nomProgramme)"));
            }
            assertEquals(numberExpected,numberActual);
            preparedStatement = (PreparedStatement) connection.prepareStatement("DELETE FROM programmeTest WHERE nomProgramme = 'Cardio'");
            preparedStatement.execute();
            preparedStatement = (PreparedStatement) connection.prepareStatement("SELECT COUNT(nomProgramme) FROM programmeTest");
            rs =preparedStatement.executeQuery();
            numberExpected = 0;
            numberActual = -1;
            while (rs.next()){
                numberActual = parseInt(rs.getString("COUNT(nomProgramme)"));
            }
            assertEquals(numberExpected,numberActual);
        }catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
