package software_employee_office_management_system;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table = new JTable();
    Choice cid = new Choice();
    JButton search, showAll, back;

    ViewEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lbl = new JLabel("Search by Employee Id");
        lbl.setBounds(20, 20, 150, 20);
        add(lbl);

        cid.setBounds(180, 20, 150, 20);
        add(cid);

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select id from employee")) {
            while (rs.next()) {
                cid.add(rs.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadTable("select * from employee");

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 500);
        add(jsp);

        search = new JButton("Search");
        search.setBounds(20, 70, 90, 20);
        search.addActionListener(this);
        add(search);

        showAll = new JButton("Show All");
        showAll.setBounds(130, 70, 90, 20);
        showAll.addActionListener(this);
        add(showAll);

        back = new JButton("Back");
        back.setBounds(240, 70, 90, 20);
        back.addActionListener(this);
        add(back);

        setSize(900, 650);
        setLocation(300, 100);
        setVisible(true);
    }

    void loadTable(String query) {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            DefaultTableModel model = new DefaultTableModel();
            for (int i = 1; i <= cols; i++) {
                model.addColumn(md.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] row = new Object[cols];
                for (int i = 1; i <= cols; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            if (cid.getItemCount() > 0) {
                loadTable("select * from employee where id = " + cid.getSelectedItem());
            }
        } else if (ae.getSource() == showAll) {
            loadTable("select * from employee");
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}