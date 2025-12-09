package model;

public class Position {

    private String positionID;
    private String positionName;
    private String note;

    public Position() {}

    public String getPositionID() {
        return positionID;
    }
    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }

    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
