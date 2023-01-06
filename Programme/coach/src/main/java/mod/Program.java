package mod;

public class Program {
    private int idProgramme;
    private String nomProgramme;
    private String descProgramme;

    public Program(int idProgramme, String nomProgramme, String descProgramme) {
        this.idProgramme = idProgramme;
        this.nomProgramme = nomProgramme;
        this.descProgramme = descProgramme;
    }

    public Program(int idProgramme, String nomProgramme) {
        this.idProgramme = idProgramme;
        this.nomProgramme = nomProgramme;
    }

    public int getIdProgramme() {
        return idProgramme;
    }

    public void setIdProgramme(int idProgramme) {
        this.idProgramme = idProgramme;
    }

    public String getNomProgramme() {
        return nomProgramme;
    }

    public void setNomProgramme(String nomProgramme) {
        this.nomProgramme = nomProgramme;
    }

    public String getDescProgramme() {
        return descProgramme;
    }

    public void setDescProgramme(String descProgramme) {
        this.descProgramme = descProgramme;
    }

    @Override
    public String toString() {
        return nomProgramme;
    }
}
