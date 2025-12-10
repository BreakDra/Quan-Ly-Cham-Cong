DROP DATABASE IF EXISTS quan_ly_nhan_su;
CREATE DATABASE quan_ly_nhan_su;
USE quan_ly_nhan_su;

-- Bảng ca làm việc
CREATE TABLE shift (
    shiftID INT PRIMARY KEY AUTO_INCREMENT,
    shiftName VARCHAR(255) NOT NULL UNIQUE,
    timeStart TIME NOT NULL,
    timeEnd TIME NOT NULL,
    breakMinutes INT DEFAULT 0,
    overtimeRate DECIMAL(5,2) DEFAULT 1.0
);

INSERT INTO shift(shiftName, timeStart, timeEnd, breakMinutes, overtimeRate) 
VALUES 
('Ca sáng', '06:45:00', '12:10:00', 15, 3.0),
('Ca chiều', '13:00:00', '18:20:00', 15, 3.0);

-- Bảng chức vụ
CREATE TABLE positions (
    positionID CHAR(25) PRIMARY KEY,
    positionName VARCHAR(255) NOT NULL,
    note VARCHAR(255)
);

INSERT INTO positions(positionID, positionName) VALUES('P001','Developer');

-- Bảng phòng ban
CREATE TABLE department (
    departmentID CHAR(25) PRIMARY KEY,
    departmentName VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50),
    note VARCHAR(255)
);

INSERT INTO department(departmentID, departmentName) VALUES('D001','IT');

-- Bảng tài khoản
CREATE TABLE account (
    accountID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','EMPLOYEE') NOT NULL DEFAULT 'EMPLOYEE',
    active BOOLEAN DEFAULT FALSE   -- mặc định chưa duyệt
);

INSERT INTO account(username, password, role, active) VALUES 
('tuongan', '123', 'ADMIN', TRUE),
('son', '123', 'EMPLOYEE', TRUE);

-- Bảng nhân viên
CREATE TABLE employee (
    employeeID INT PRIMARY KEY AUTO_INCREMENT,
    accountID INT UNIQUE,
    fullname VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    birthdate DATE,
    phone VARCHAR(50),
    email VARCHAR(255),
    address VARCHAR(255),
    hireDate DATE,
    salary DECIMAL(15,2),
    imagePath VARCHAR(300),
    departmentID CHAR(25),
    positionID CHAR(25),
    CONSTRAINT fk_employee_account FOREIGN KEY (accountID) REFERENCES account(accountID),
    CONSTRAINT fk_employee_department FOREIGN KEY (departmentID) REFERENCES department(departmentID),
    CONSTRAINT fk_employee_position FOREIGN KEY (positionID) REFERENCES positions(positionID)
);

INSERT INTO employee(accountID, fullname, gender, birthdate, phone, email, address, hireDate, salary, imagePath, departmentID, positionID) 
VALUES 
(1, 'Nguyễn Tường An', 'Male', '2006-11-15', '0911111117', 'oii@gmail.com', 'Hà Nội', '2025-11-15', 999.00, 'facebook.com', 'D001', 'P001'),
(2, 'Admin User', 'Male', '1990-01-01', '0123456789', 'admin@gmail.com', 'Hà Nội', '2020-01-01', 50.00, 'facebook.com', 'D001', 'P001');

-- Bảng chấm công
CREATE TABLE attendance (
    attendanceID INT PRIMARY KEY AUTO_INCREMENT,
    employeeID INT NOT NULL,
    shiftID INT,
    inTime DATETIME,
    outTime DATETIME,
    workHours DECIMAL(5,2),
    overtimeMinutes INT,
    status INT DEFAULT 0,
    approvedBy INT,
    CONSTRAINT fk_attendance_employee FOREIGN KEY (employeeID) REFERENCES employee(employeeID),
    CONSTRAINT fk_attendance_shift FOREIGN KEY (shiftID) REFERENCES shift(shiftID),
    CONSTRAINT fk_attendance_account FOREIGN KEY (approvedBy) REFERENCES account(accountID)
);

CREATE TABLE update_request (
    requestID INT PRIMARY KEY AUTO_INCREMENT,
    employeeID INT NOT NULL,
    fullname VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    address VARCHAR(255),
    gender VARCHAR(10),
    username VARCHAR(255),
    password VARCHAR(255),
    status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
    requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approvedBy INT,
    CONSTRAINT fk_update_employee FOREIGN KEY (employeeID) REFERENCES employee(employeeID),
    CONSTRAINT fk_update_account FOREIGN KEY (approvedBy) REFERENCES account(accountID)
);


