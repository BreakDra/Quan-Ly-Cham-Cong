package model;

import java.util.Date;

public class Attendance {

    private int attendanceID;
    private int employeeID;
    private int shiftID;
    private Date inTime;
    private Date outTime;
    private double workHours;
    private int overtimeMinutes;
    private int status;
    private Integer approvedBy;

    public Attendance() {}

    public int getAttendanceID() {
        return attendanceID;
    }
    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getShiftID() {
        return shiftID;
    }
    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public Date getInTime() {
        return inTime;
    }
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }
    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public double getWorkHours() {
        return workHours;
    }
    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public int getOvertimeMinutes() {
        return overtimeMinutes;
    }
    public void setOvertimeMinutes(int overtimeMinutes) {
        this.overtimeMinutes = overtimeMinutes;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }
    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }
}
