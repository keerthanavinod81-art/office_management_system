package software_employee_office_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewEmployee extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JTable table;
    JButton backBtn;

    public ViewEmployee() {
        setTitle("View Employees");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("EMPLOYEE DETAILS", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(heading, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Department", "Designation", "Salary"});

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employee")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("designation"),
                    rs.getDouble("salary")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
        }

        JPanel bottomPanel = new JPanel();
        backBtn = new JButton("Back to Dashboard");
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(this);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            setVisible(false);
            dispose();
            new Home().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}