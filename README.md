*Cấu trúc:
src/
 └── main/
     └── java/
         └── org/example/        → Chứa Main.java
         └── dao/                → Các lớp DAO xử lý database
         └── model/              → Các lớp Model (POJO)
         └── ui/                 → Các file giao diện Swing
 └── resources/
     └── db.sql                  → File tạo bảng MySQL
pom.xml                          → Quản lý thư viện Maven

 Công Nghệ Sử Dụng

Java 8+

Java Swing (UI)

MySQL 8

JDBC

Maven

MVC Architecture

 Tính Năng Chính
 1. Đăng Nhập (LoginUI)

Đăng nhập bằng username/password

Phân quyền Admin và Employee

Dẫn đến AdminDashboard hoặc EmployeeDashboard

 2. Quản lý Nhân viên (EmployeeManagementUI)

CRUD Nhân viên:

Tạo nhân viên

Sửa thông tin nhân viên

Xóa nhân viên

Xem danh sách nhân viên
Dùng các bảng:

employee

department

position

 3. Quản lý Phòng ban (DepartmentManagementUI)

Thêm phòng ban

Sửa phòng ban

Xóa phòng ban

Xem danh sách phòng ban
Bảng liên quan: department

 4. Quản lý Chức vụ (PositionManagementUI)

CRUD vị trí làm việc
Bảng liên quan: position

 5. Quản lý Ca làm (ShiftManagementUI)

Tạo ca làm

Sửa ca làm

Xóa ca làm
Bảng liên quan: shift

 6. Chấm công Start/Stop (AttendanceStartStopUI)

Dành cho nhân viên:

Bấm Start Work

Bấm Stop Work

Ghi thời gian vào bảng attendance

 7. Employee Dashboard

Xem thông tin cá nhân

Bắt đầu/kết thúc chấm công

Xem lịch sử chấm công

🛠️ 8. Admin Dashboard

Dành cho admin:

Truy cập tất cả UI quản lý:

Nhân viên

Phòng ban

Chức vụ

Ca làm

Quản lý tài khoản

Xem báo cáo chấm công

 Thiết Kế Database (MySQL)

Các bảng chính:

account(accountID, username, password, role)
employee(employeeID, name, email, departmentID, positionID, accountID)
department(departmentID, name)
position(positionID, name, description)
shift(shiftID, name, startTime, endTime)
attendance(attendanceID, employeeID, checkIn, checkOut, shiftID)
