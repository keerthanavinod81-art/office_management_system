package software_employee_office_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {

    private JButton add, view, update, remove;
    private JButton addAttendance, viewAttendance, logout;

    public Home() {
        setTitle("Employee Management System");
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Heading
        JLabel heading = new JLabel("EMPLOYEE MANAGEMENT SYSTEM", JLabel.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(new Color(30, 45, 75));
        heading.setBounds(100, 30, 650, 35);
        add(heading);

        // Row 1: Add Employee & View Employees
        add = new JButton("Add Employee");
        add.setBounds(180, 100, 200, 40);
        add.setFont(new Font("Tahoma", Font.BOLD, 13));
        add.setBackground(new Color(40, 40, 40));
        add.setForeground(Color.WHITE);
        add.setFocusPainted(false);
        add.addActionListener(this);
        add(add);

        view = new JButton("View Employees");
        view.setBounds(460, 100, 200, 40);
        view.setFont(new Font("Tahoma", Font.BOLD, 13));
        view.setBackground(new Color(40, 40, 40));
        view.setForeground(Color.WHITE);
        view.setFocusPainted(false);
        view.addActionListener(this);
        add(view);

        // Row 2: Update Employee & Remove Employee
        update = new JButton("Update Employee");
        update.setBounds(180, 165, 200, 40);
        update.setFont(new Font("Tahoma", Font.BOLD, 13));
        update.setBackground(new Color(40, 40, 40));
        update.setForeground(Color.WHITE);
        update.setFocusPainted(false);
        update.addActionListener(this);
        add(update);

        remove = new JButton("Remove Employee");
        remove.setBounds(460, 165, 200, 40);
        remove.setFont(new Font("Tahoma", Font.BOLD, 13));
        remove.setBackground(new Color(40, 40, 40));
        remove.setForeground(Color.WHITE);
        remove.setFocusPainted(false);
        remove.addActionListener(this);
        add(remove);

        // Row 3: Add Attendance & View Attendance
        addAttendance = new JButton("Add Attendance");
        addAttendance.setBounds(180, 230, 200, 40);
        addAttendance.setFont(new Font("Tahoma", Font.BOLD, 13));
        addAttendance.setBackground(new Color(40, 40, 40));
        addAttendance.setForeground(Color.WHITE);
        addAttendance.setFocusPainted(false);
        addAttendance.addActionListener(this);
        add(addAttendance);

        viewAttendance = new JButton("View Attendance");
        viewAttendance.setBounds(460, 230, 200, 40);
        viewAttendance.setFont(new Font("Tahoma", Font.BOLD, 13));
        viewAttendance.setBackground(new Color(40, 40, 40));
        viewAttendance.setForeground(Color.WHITE);
        viewAttendance.setFocusPainted(false);
        viewAttendance.addActionListener(this);
        add(viewAttendance);

        // Row 4: Logout
        logout = new JButton("Logout");
        logout.setBounds(320, 310, 200, 40);
        logout.setFont(new Font("Tahoma", Font.BOLD, 13));
        logout.setBackground(new Color(205, 50, 45));
        logout.setForeground(Color.WHITE);
        logout.setFocusPainted(false);
        logout.addActionListener(this);
        add(logout);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            new AddEmployee().setVisible(true);
        } else if (e.getSource() == view) {
            new ViewEmployee().setVisible(true);
        } else if (e.getSource() == update) {
            new UpdateEmployee().setVisible(true);
        } else if (e.getSource() == remove) {
            new RemoveEmployee().setVisible(true);
        } else if (e.getSource() == addAttendance) {
            new AddAttendance().setVisible(true);
        } else if (e.getSource() == viewAttendance) {
            new ViewAttendance().setVisible(true);
        } else if (e.getSource() == logout) {
            setVisible(false);
            dispose();
            new Login().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home().setVisible(true));
    }
}