package software_employee_office_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Date;
import java.sql.Time;
import java.time.*;
import java.time.format.*;

public class AddAttendance extends JFrame implements ActionListener {

    private JTextField tfEmpId, tfDate, tfCheckIn, tfCheckOut, tfHours;
    private JButton btnCalculate, btnSave, btnBack;

    public AddAttendance() {
        setTitle("Record Employee Attendance");
        setSize(480, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Header Title
        JLabel heading = new JLabel("Log Employee Attendance");
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        heading.setBounds(110, 20, 300, 30);
        add(heading);

        // Employee ID
        JLabel lblEmpId = new JLabel("Employee ID:");
        lblEmpId.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEmpId.setBounds(40, 75, 140, 25);
        add(lblEmpId);

        tfEmpId = new JTextField();
        tfEmpId.setBounds(190, 75, 220, 25);
        add(tfEmpId);

        // Date (YYYY-MM-DD)
        JLabel lblDate = new JLabel("Date (YYYY-MM-DD):");
        lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDate.setBounds(40, 115, 150, 25);
        add(lblDate);

        tfDate = new JTextField();
        tfDate.setBounds(190, 115, 220, 25);
        add(tfDate);

        // Check-in Time (HH:MM:SS)
        JLabel lblCheckIn = new JLabel("Check-In :");
        lblCheckIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCheckIn.setBounds(40, 155, 150, 25);
        add(lblCheckIn);

        tfCheckIn = new JTextField();
        tfCheckIn.setBounds(190, 155, 220, 25);
        add(tfCheckIn);

        // Check-out Time (HH:MM:SS)
        JLabel lblCheckOut = new JLabel("Check-Out :");
        lblCheckOut.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCheckOut.setBounds(40, 195, 150, 25);
        add(lblCheckOut);

        tfCheckOut = new JTextField();
        tfCheckOut.setBounds(190, 195, 220, 25);
        add(tfCheckOut);

        // Total Hours
        JLabel lblHours = new JLabel("Total Hours:");
        lblHours.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblHours.setBounds(40, 235, 140, 25);
        add(lblHours);

        tfHours = new JTextField();
        tfHours.setBounds(190, 235, 110, 25);
        tfHours.setEditable(false);
        tfHours.setBackground(new Color(240, 240, 240));
        add(tfHours);

        // Calculate Button
        btnCalculate = new JButton("Calculate");
        btnCalculate.setBounds(310, 235, 100, 25);
        btnCalculate.addActionListener(this);
        add(btnCalculate);

        // Save & Back Buttons
        btnSave = new JButton("Save Record");
        btnSave.setBackground(new Color(30, 144, 255));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBounds(100, 300, 130, 35);
        btnSave.addActionListener(this);
        add(btnSave);

        btnBack = new JButton("Close");
        btnBack.setBackground(Color.DARK_GRAY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBounds(250, 300, 110, 35);
        btnBack.addActionListener(this);
        add(btnBack);
    }

    private LocalTime parseTimeFlexible(String input, boolean isCheckOut, LocalTime checkInTime) {
        String s = input.trim().toUpperCase();

        // 1. Handle AM / PM strings if typed (e.g., "5 PM", "5:30PM")
        boolean isPM = s.endsWith("PM");
        boolean isAM = s.endsWith("AM");
        s = s.replace("AM", "").replace("PM", "").trim();

        int hour = 0, minute = 0, second = 0;
        String[] parts = s.split(":");

        hour = Integer.parseInt(parts[0]);
        if (parts.length > 1) minute = Integer.parseInt(parts[1]);
        if (parts.length > 2) second = Integer.parseInt(parts[2]);

        // Adjust for AM/PM if specified
        if (isPM && hour < 12) hour += 12;
        if (isAM && hour == 12) hour = 0;

        // Auto-detect PM for standard office hours if AM/PM was omitted:
        // E.g., if checkout is entered as "5" or "5:00" and check-in is "09:00", turn 5 into 17 (5 PM)
        if (!isPM && !isAM && isCheckOut && checkInTime != null) {
            if (hour < checkInTime.getHour() && hour <= 12) {
                hour += 12;
            }
        }

        return LocalTime.of(hour, minute, second);
    }

    private Double calculateTotalHours() {
        String inStr = tfCheckIn.getText().trim();
        String outStr = tfCheckOut.getText().trim();

        if (inStr.isEmpty() || outStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Check-In and Check-Out times first.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            // Helper to parse shorthand like "9", "9:00", "09:00:00"
            LocalTime inTime = parseSimpleTime(inStr);
            LocalTime outTime = parseSimpleTime(outStr);

            // If check-out is entered as 1 to 11 and is earlier than check-in, treat it as PM (add 12 hours)
            if (outTime.isBefore(inTime) && outTime.getHour() <= 12) {
                outTime = outTime.plusHours(12);
            }

            if (outTime.isBefore(inTime)) {
                JOptionPane.showMessageDialog(this, "Check-out time cannot be earlier than check-in time!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Put clean HH:MM:SS format back into the input boxes
            tfCheckIn.setText(String.format("%02d:%02d:%02d", inTime.getHour(), inTime.getMinute(), inTime.getSecond()));
            tfCheckOut.setText(String.format("%02d:%02d:%02d", outTime.getHour(), outTime.getMinute(), outTime.getSecond()));

            long totalMinutes = Duration.between(inTime, outTime).toMinutes();
            double hours = Math.round((totalMinutes / 60.0) * 100.0) / 100.0;
            tfHours.setText(String.valueOf(hours));
            return hours;

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please enter a valid time (e.g. 09:00:00 and 17:00:00, or 9 and 5).", "Format Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private LocalTime parseSimpleTime(String s) {
        s = s.toUpperCase().replace("AM", "").replace("PM", "").trim();
        String[] parts = s.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = (parts.length > 1) ? Integer.parseInt(parts[1]) : 0;
        int sec = (parts.length > 2) ? Integer.parseInt(parts[2]) : 0;
        return LocalTime.of(h, m, sec);
    }

    private void saveRecord() {
        String empId = tfEmpId.getText().trim();
        String dateStr = tfDate.getText().trim();
        String checkInStr = tfCheckIn.getText().trim();
        String checkOutStr = tfCheckOut.getText().trim();

        if (empId.isEmpty() || dateStr.isEmpty() || checkInStr.isEmpty() || checkOutStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the details.", "Missing Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Double totalHours = calculateTotalHours();
        if (totalHours == null) {
            return;
        }

        String sql = "INSERT INTO attendance (employee_id, attendance_date, check_in_time, check_out_time, total_hours) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empId);
            ps.setDate(2, Date.valueOf(dateStr));
            ps.setTime(3, Time.valueOf(checkInStr));
            ps.setTime(4, Time.valueOf(checkOutStr));
            ps.setDouble(5, totalHours);

            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Attendance recorded successfully!");
                tfEmpId.setText("");
                tfDate.setText("");
                tfCheckIn.setText("");
                tfCheckOut.setText("");
                tfHours.setText("");
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "Attendance for employee " + empId + " on date " + dateStr + " already exists!", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Please enter the date in YYYY-MM-DD format (e.g., 2026-10-22).", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCalculate) {
            calculateTotalHours();
        } else if (e.getSource() == btnSave) {
            saveRecord();
        } else if (e.getSource() == btnBack) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddAttendance().setVisible(true);
            }
        });
    }
}