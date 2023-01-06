package mod;

public class PlanningSession {
    private int idPlanning;
    private int idClient;
    private int idProgram;
    private int idPlace;
    private String name;
    private String firstname;
    private String mail;
    private String nameProgram;
    private String place;
    private String dateStart;
    private String dateEnd;
    private Client client;

    public PlanningSession(int idPlanning, String name, String firstname, String mail, String nameProgram, String place, String dateStart, String dateEnd, int idClient, int idProgram, int idPlace) {
        this.idPlanning = idPlanning;
        this.idClient = idClient;
        this.idProgram = idProgram;
        this.idPlace = idPlace;
        this.name = name;
        this.firstname = firstname;
        this.mail = mail;
        this.nameProgram = nameProgram;
        this.place = place;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.client = new Client(idClient, name, firstname, mail);
    }

    public PlanningSession(int idPlanning, String name, String firstname, String mail, String nameProgram, String place, String dateStart, String dateEnd) {
        this.idPlanning = idPlanning;
        this.name = name;
        this.firstname = firstname;
        this.mail = mail;
        this.nameProgram = nameProgram;
        this.place = place;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public int getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(int idPlanning) {
        this.idPlanning = idPlanning;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNameProgram() {
        return nameProgram;
    }

    public void setNameProgram(String nameProgram) {
        this.nameProgram = nameProgram;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client c) {
        this.client = c;
    }
}
