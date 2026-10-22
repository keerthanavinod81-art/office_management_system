package software_employee_office_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAttendance extends JFrame implements ActionListener {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbEmpId;
    private JButton btnSearch, btnReset, btnClose;

    public ViewAttendance() {
        setTitle("View Employee Attendance");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top Panel: Title & Filter Bar
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(Color.WHITE);

        JLabel heading = new JLabel("Attendance Records", JLabel.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        topPanel.add(heading, BorderLayout.NORTH);

        // Filter Controls
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        filterPanel.setBackground(Color.WHITE);

        JLabel lblSelect = new JLabel("Select Employee ID:");
        lblSelect.setFont(new Font("Tahoma", Font.BOLD, 14));
        filterPanel.add(lblSelect);

        cbEmpId = new JComboBox<>();
        cbEmpId.setPreferredSize(new Dimension(140, 28));
        filterPanel.add(cbEmpId);

        btnSearch = new JButton("Filter");
        btnSearch.setBackground(new Color(30, 144, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(this);
        filterPanel.add(btnSearch);

        btnReset = new JButton("Show All");
        btnReset.setBackground(new Color(70, 130, 180));
        btnReset.setForeground(Color.WHITE);
        btnReset.addActionListener(this);
        filterPanel.add(btnReset);

        topPanel.add(filterPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Table Setup
        String[] columns = {"Employee ID", "Date", "Check In", "Check Out", "Total Hours"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Close Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        btnClose = new JButton("Close");
        btnClose.setBackground(Color.DARK_GRAY);
        btnClose.setForeground(Color.WHITE);
        btnClose.addActionListener(this);
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);

        // Populate dropdown and initial table records
        loadEmployeeIds();
        loadData(null);
    }

    private void loadEmployeeIds() {
        cbEmpId.removeAllItems();
        cbEmpId.addItem("All Employees");

        String sql = "SELECT DISTINCT employee_id FROM attendance ORDER BY employee_id ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cbEmpId.addItem(rs.getString("employee_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData(String empId) {
        model.setRowCount(0);

        String sql;
        if (empId == null || empId.equals("All Employees")) {
            sql = "SELECT * FROM attendance ORDER BY attendance_date DESC, employee_id ASC";
        } else {
            sql = "SELECT * FROM attendance WHERE employee_id = ? ORDER BY attendance_date DESC";
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (empId != null && !empId.equals("All Employees")) {
                ps.setString(1, empId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("employee_id"),
                        rs.getDate("attendance_date"),
                        rs.getTime("check_in_time"),
                        rs.getTime("check_out_time"),
                        rs.getDouble("total_hours")
                    });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching attendance: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            String selectedId = (String) cbEmpId.getSelectedItem();
            loadData(selectedId);
        } else if (e.getSource() == btnReset) {
            cbEmpId.setSelectedIndex(0);
            loadData(null);
        } else if (e.getSource() == btnClose) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewAttendance().setVisible(true));
    }
}