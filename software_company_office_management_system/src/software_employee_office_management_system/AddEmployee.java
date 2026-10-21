package software_employee_office_management_system;
import java.sql.Connection;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import javax.swing.*;

public class AddEmployee extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3073781592087629461L;
	JTextField tfname, tfdepartment, tfdesignation, tfsalary;
    JButton add, back;

    AddEmployee()  {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(250, 30, 400, 50);
        heading.setFont(new Font("SansSerif", Font.BOLD, 25));
        add(heading);

        String[] names = {"Name", "Department", "Designation", "Salary"};
        JTextField[] fields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            JLabel l = new JLabel(names[i]);
            l.setBounds(150, 120 + i * 60, 150, 30);
            l.setFont(new Font("serif", Font.PLAIN, 20));
            add(l);
            fields[i] = new JTextField();
            fields[i].setBounds(320, 120 + i * 60, 250, 30);
            add(fields[i]);
        }
        tfname = fields[0];
        tfdepartment = fields[1];
        tfdesignation = fields[2];
        tfsalary = fields[3];

        add = new JButton("Add Details");
        add.setBounds(200, 420, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);

        back = new JButton("Back");
        back.setBounds(400, 420, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(800, 550);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            try {
                double salary = Double.parseDouble(tfsalary.getText());
                java.sql.Connection conn = DBConnection.getConnection();

                PreparedStatement ps = conn.prepareStatement(
                    "insert into employee (name, department, designation, salary) values (?, ?, ?, ?)"
                );
                ps.setString(1, tfname.getText());
                ps.setString(2, tfdepartment.getText());
                ps.setString(3, tfdesignation.getText());
                ps.setDouble(4, salary);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Details added successfully");
                setVisible(false);
                new Home();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Salary must be a number");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}
