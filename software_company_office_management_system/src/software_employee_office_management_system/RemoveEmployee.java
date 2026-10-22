package software_employee_office_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveEmployee extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    Choice cEmpId;
    JButton checkBtn, deleteBtn, backBtn;
    JLabel lblNameVal, lblDeptVal, lblDesigVal;

    public RemoveEmployee() {
        setTitle("Remove Employee");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("REMOVE EMPLOYEE");
        heading.setBounds(160, 20, 250, 30);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        heading.setForeground(new Color(25, 25, 112));
        add(heading);

        JLabel lblId = new JLabel("Select Employee ID:");
        lblId.setBounds(50, 70, 150, 25);
        lblId.setFont(new Font("SansSerif", Font.BOLD, 13));
        add(lblId);

        cEmpId = new Choice();
        cEmpId.setBounds(210, 70, 150, 25);
        add(cEmpId);

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id FROM employee")) {
            while (rs.next()) {
                cEmpId.add(String.valueOf(rs.getInt("id")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading IDs: " + e.getMessage());
        }

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(50, 120, 120, 25);
        add(lblName);

        lblNameVal = new JLabel();
        lblNameVal.setBounds(210, 120, 200, 25);
        add(lblNameVal);

        JLabel lblDept = new JLabel("Department:");
        lblDept.setBounds(50, 160, 120, 25);
        add(lblDept);

        lblDeptVal = new JLabel();
        lblDeptVal.setBounds(210, 160, 200, 25);
        add(lblDeptVal);

        JLabel lblDesig = new JLabel("Designation:");
        lblDesig.setBounds(50, 200, 120, 25);
        add(lblDesig);

        lblDesigVal = new JLabel();
        lblDesigVal.setBounds(210, 200, 200, 25);
        add(lblDesigVal);

        checkBtn = new JButton("Fetch Details");
        checkBtn.setBounds(370, 70, 120, 25);
        checkBtn.setBackground(Color.DARK_GRAY);
        checkBtn.setForeground(Color.WHITE);
        checkBtn.addActionListener(this);
        add(checkBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(100, 260, 120, 35);
        deleteBtn.setBackground(new Color(178, 34, 34));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(this);
        add(deleteBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(260, 260, 120, 35);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(540, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkBtn) {
            String selectedId = cEmpId.getSelectedItem();
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "No employee selected.");
                return;
            }

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT name, department, designation FROM employee WHERE id = ?")) {
                ps.setString(1, selectedId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        lblNameVal.setText(rs.getString("name"));
                        lblDeptVal.setText(rs.getString("department"));
                        lblDesigVal.setText(rs.getString("designation"));
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Fetch failed: " + ex.getMessage());
            }

        } else if (e.getSource() == deleteBtn) {
            String selectedId = cEmpId.getSelectedItem();
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "No employee selected.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete employee ID " + selectedId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String sql = "DELETE FROM employee WHERE id = ?";

            try (Connection con = DBConnection.getConnection();
                    Statement stmt = con.createStatement()) {

                   stmt.execute("SET FOREIGN_KEY_CHECKS= 0;");
                   int rows = stmt.executeUpdate("DELETE FROM employee WHERE id = " + selectedId.trim());
                   stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
                    cEmpId.remove(selectedId);
                    lblNameVal.setText("");
                    lblDeptVal.setText("");
                    lblDesigVal.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Delete failed: " + ex.getMessage());
            }

        } else if (e.getSource() == backBtn) {
            setVisible(false);
            dispose();
            new Home().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}