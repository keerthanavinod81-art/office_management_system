package software_employee_office_management_system;
import javax.swing.*;
import java.awt.Color;


public class splash extends JFrame  {
	splash() {
		
		getContentPane ().setBackground (Color.BLUE);
		
		
		setSize(1170,650);
		setLocation(200,50);
		setVisible(true);
	}
	public static void main(String[] args) {
		new splash();

	}

}