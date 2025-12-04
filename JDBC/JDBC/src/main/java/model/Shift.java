package model;

import java.sql.Time;

public class Shift {

    private int shiftID;
    private String shiftName;
    private Time timeStart;
    private Time timeEnd;
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

    public Time getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(Time timeEnd) {
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
