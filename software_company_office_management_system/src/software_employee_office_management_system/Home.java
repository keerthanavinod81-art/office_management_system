package software_employee_office_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JButton viewBtn, addBtn, updateBtn, removeBtn, logoutBtn;

    public Home() {
        setTitle("Employee Management System");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        heading.setBounds(300, 30, 500, 40);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setForeground(new Color(25, 25, 112));
        add(heading);

        addBtn = new JButton("Add Employee");
        addBtn.setBounds(300, 120, 180, 40);
        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(this);
        add(addBtn);

        viewBtn = new JButton("View Employees");
        viewBtn.setBounds(520, 120, 180, 40);
        viewBtn.setBackground(Color.BLACK);
        viewBtn.setForeground(Color.WHITE);
        viewBtn.addActionListener(this);
        add(viewBtn);

        updateBtn = new JButton("Update Employee");
        updateBtn.setBounds(300, 200, 180, 40);
        updateBtn.setBackground(Color.BLACK);
        updateBtn.setForeground(Color.WHITE);
        updateBtn.addActionListener(this);
        add(updateBtn);

        removeBtn = new JButton("Remove Employee");
        removeBtn.setBounds(520, 200, 180, 40);
        removeBtn.setBackground(Color.BLACK);
        removeBtn.setForeground(Color.WHITE);
        removeBtn.addActionListener(this);
        add(removeBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(420, 280, 160, 35);
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.addActionListener(this);
        add(logoutBtn);

        setSize(1000, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            setVisible(false);
            new AddEmployee().setVisible(true);
        } else if (ae.getSource() == viewBtn) {
            setVisible(false);
            new ViewEmployee().setVisible(true);
        } else if (ae.getSource() == updateBtn) {
            setVisible(false);
            new UpdateEmployee().setVisible(true);
        } else if (ae.getSource() == removeBtn) {
            setVisible(false);
            new RemoveEmployee().setVisible(true);
        } else if (ae.getSource() == logoutBtn) {
            setVisible(false);
            dispose();
            new Login().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}