package software_employee_office_management_system;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeDAO {

    public static void addEmployee() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter employee name: ");
        String name = sc.nextLine();

        System.out.print("Enter department: ");
        String department = sc.nextLine();

        System.out.print("Enter designation: ");
        String designation = sc.nextLine();

        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();

        String sql = "INSERT INTO employee "
                + "(name, department, designation, salary) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, designation);
            ps.setDouble(4, salary);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding employee.");
            e.printStackTrace();
        }
    }
    
public static void viewEmployees() {
    String sql = "SELECT * FROM employee";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        System.out.println("\n--- Employee Details ---");

        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Department: " + rs.getString("department"));
            System.out.println("Designation: " + rs.getString("designation"));
            System.out.println("Salary: " + rs.getDouble("salary"));
            System.out.println("-------------------------");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving employees.");
        e.printStackTrace();
    }
}

public static void viewDepartments() {
    String sql = "SELECT * FROM departments";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        System.out.println("\n--- Department Details ---");

        while (rs.next()) {
            System.out.println(
                "ID: " + rs.getInt("department_id"));
            System.out.println(
                "Name: " + rs.getString("department_name"));
           
            System.out.println("-------------------------");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving departments.");
        e.printStackTrace();
    }
}

public static void viewAttendance() {
    String sql = "SELECT * FROM attendance";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        System.out.println("\n--- Attendance Details ---");

        while (rs.next()) {
            System.out.println(
                "Attendance ID: " + rs.getInt("attendance_id"));
            System.out.println(
                "Employee ID: " + rs.getInt("employee_id"));
            System.out.println(
                "Date: " + rs.getDate("attendance_date"));
            System.out.println(
                "Status: " + rs.getString("attendance_status"));
            System.out.println("-------------------------");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving attendance.");
        e.printStackTrace();
    }
}

public static void viewLeaveRequests() {
    String sql = "SELECT * FROM leave_requests";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        System.out.println("\n--- Leave Requests ---");

        while (rs.next()) {
            System.out.println(
                "Leave ID: " + rs.getInt("leave_id"));
            System.out.println(
                "Employee ID: " + rs.getInt("employee_id"));
            System.out.println(
                "Start Date: " + rs.getDate("start_date"));
            System.out.println(
                "End Date: " + rs.getDate("end_date"));
            System.out.println(
                "Leave Type: " + rs.getString("leave_type"));
            System.out.println(
                "Reason: " + rs.getString("reason"));
            System.out.println(
                "Status: " + rs.getString("approval_status"));
            System.out.println("-------------------------");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving leave requests.");
        e.printStackTrace();
    }
}

public static void viewTasks() {
    String sql = "SELECT * FROM tasks";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        System.out.println("\n--- Task Details ---");

        while (rs.next()) {
            System.out.println(
                "Task ID: " + rs.getInt("task_id"));
            System.out.println(
                "Title: " + rs.getString("title"));
            System.out.println(
                "Description: " + rs.getString("description"));
            System.out.println(
                "Created By (Employee ID): " + rs.getInt("created_by"));
            System.out.println(
                "Due Date: " + rs.getDate("due_date"));
            System.out.println(
                "Status: " + rs.getString("task_status"));
            System.out.println("-------------------------");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving tasks.");
        e.printStackTrace();
    }
}

public static void viewTaskAssignments() {
    String sql = "SELECT * FROM task_assignments";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        System.out.println("\n--- Task Assignments ---");

        while (rs.next()) {
            System.out.println(
                "Task ID: " + rs.getInt("task_id"));
            System.out.println(
                "Employee ID: " + rs.getInt("employee_id"));
            System.out.println(
                "Assigned On: " + rs.getDate("assigned_on"));
            System.out.println("-------------------------");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving task assignments.");
        e.printStackTrace();
    }
}


    
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int choice;

    do {
        System.out.println("\n=== Employee Management System ===");
        System.out.println("1. Add Employee");
        System.out.println("2. View Employees");
        System.out.println("3. View Departments");
        System.out.println("4. View Attendance");
        System.out.println("5. View Leave Requests");
        System.out.println("6. View Tasks");
        System.out.println("7. View Task Assignments");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");

        choice = sc.nextInt();

        switch (choice) {
            case 1:
                addEmployee();
                break;

            case 2:
                viewEmployees();
                break;

            case 3:
                viewDepartments();
                break;

            case 4:
                viewAttendance();
                break;

            case 5:
                viewLeaveRequests();
                break;

            case 6:
                viewTasks();
                break;

            case 7:
                viewTaskAssignments();
                break;

            case 8:
                System.out.println("Exiting program...");
                break;

            default:
                System.out.println("Invalid choice. Try again.");
        }

    } while (choice != 8);

    sc.close();
}
}