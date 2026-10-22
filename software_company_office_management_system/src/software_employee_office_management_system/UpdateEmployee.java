package software_employee_office_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JButton btnUpdate, btnDelete, btnBack;

    public UpdateEmployee() {
        setTitle("Update / Delete Employee");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("MANAGE EMPLOYEE", JLabel.CENTER);
        heading.setBounds(70, 20, 300, 30);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        heading.setForeground(new Color(25, 25, 112));
        add(heading);

        btnUpdate = new JButton("Update Employee");
        btnUpdate.setBounds(120, 70, 200, 35);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnUpdate.addActionListener(this);
        add(btnUpdate);

        btnDelete = new JButton("Delete Employee");
        btnDelete.setBounds(120, 125, 200, 35);
        btnDelete.setBackground(new Color(178, 34, 34));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.addActionListener(this);
        add(btnDelete);

        btnBack = new JButton("Back to Dashboard");
        btnBack.setBounds(120, 180, 200, 35);
        btnBack.setBackground(Color.DARK_GRAY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBack.addActionListener(this);
        add(btnBack);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUpdate) {
            updateEmployee();
        } else if (e.getSource() == btnDelete) {
            deleteEmployee();
        } else if (e.getSource() == btnBack) {
            setVisible(false);
            dispose();
            new Home().setVisible(true);
        }
    }

    public void updateEmployee() {
        String id = JOptionPane.showInputDialog(this, "Enter Employee ID to update:");

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        String sql = "SELECT * FROM employee WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Employee not found!");
                    return;
                }

                String name = JOptionPane.showInputDialog(this, "Enter Name:", rs.getString("name"));
                if (name == null) return;

                String department = JOptionPane.showInputDialog(this, "Enter Department:", rs.getString("department"));
                if (department == null) return;

                String designation = JOptionPane.showInputDialog(this, "Enter Designation:", rs.getString("designation"));
                if (designation == null) return;

                String salary = JOptionPane.showInputDialog(this, "Enter Salary:", rs.getString("salary"));
                if (salary == null) return;

                double sal;
                try {
                    sal = Double.parseDouble(salary.trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Enter a valid salary!");
                    return;
                }

                String update = "UPDATE employee SET name=?, department=?, designation=?, salary=? WHERE id=?";

                try (PreparedStatement ps2 = con.prepareStatement(update)) {
                    ps2.setString(1, name);
                    ps2.setString(2, department);
                    ps2.setString(3, designation);
                    ps2.setDouble(4, sal);
                    ps2.setString(5, id.trim());

                    ps2.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Employee updated successfully!");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void deleteEmployee() {
        String id = JOptionPane.showInputDialog(this,"Enter Employee ID to delete:");

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            this, "Delete employee ID " + id + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM employee WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id.trim());
            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee();
    }
}