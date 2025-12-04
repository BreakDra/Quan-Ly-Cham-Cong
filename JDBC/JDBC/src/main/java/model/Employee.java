package model;

public class Employee {

    private int accountID;
    private int employeeID;
    private String fullname;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private double salary;
    private String imagePath;
    private String departmentID;
    private String positionID;
    private java.sql.Date birthdate;
    private java.sql.Date hireDate;

    public Employee() {}

    public java.sql.Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(java.sql.Date birthdate) {
        this.birthdate = birthdate;
    }

    public java.sql.Date getHireDate() {
        return hireDate;
    }
    public void setHireDate(java.sql.Date hireDate) {
        this.hireDate = hireDate;
    }

    public int getAccountID() {
        return accountID;
    }
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getPositionID() {
        return positionID;
    }
    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }
}
