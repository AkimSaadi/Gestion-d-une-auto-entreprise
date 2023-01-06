package mod;

public class placePractice {
    private int idPlace;
    private String place;

    public placePractice(int idPlace, String place) {
        this.idPlace = idPlace;
        this.place = place;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return place;
    }
}
