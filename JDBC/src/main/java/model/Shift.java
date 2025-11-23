package model;

public class Shift {

    private int shiftID;
    private String shiftName;
    private String timeStart;
    private String timeEnd;
    private int breakMinutes;
    private double overtimeRate;

    public Shift() {}

    public int getShiftID() {
        return shiftID;
    }
    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftName() {
        return shiftName;
    }
    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getBreakMinutes() {
        return breakMinutes;
    }
    public void setBreakMinutes(int breakMinutes) {
        this.breakMinutes = breakMinutes;
    }

    public double getOvertimeRate() {
        return overtimeRate;
    }
    public void setOvertimeRate(double overtimeRate) {
        this.overtimeRate = overtimeRate;
    }
}
