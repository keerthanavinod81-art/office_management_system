package software_employee_office_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    private static final long serialVersionUID = -1066404725758743369L;
    JTextField tfusername, tfpassword;

    public Login(){
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername= new JLabel("Username");
        lblusername.setBounds(40, 20, 100, 30);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150,20, 150, 30);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 70, 100, 30);
        add(lblpassword);

        tfpassword = new JPasswordField(); // JPasswordField masks the input
        tfpassword.setBounds(150, 70, 150, 30);
        add(tfpassword);

        JButton login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        setSize(600, 300);
        setLocation(450, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String username = tfusername.getText();
        String password = tfpassword.getText();
        
        // Matches your app_users table columns in MySQL
        String sql = "SELECT * FROM app_users WHERE username = ? AND password_hash = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.dispose();
                    Home home = new Home();
                    home.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}