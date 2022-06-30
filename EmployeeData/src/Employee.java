import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;

public class Employee {

	private JFrame frame;
	private JTextField jtxtEmployeeID;
	private JTable table;
	private JTextField jtxtNINumber;
	private JTextField jtxtFirstname;
	private JTextField jtxtSurname;
	private JTextField jtxtGender;
	private JTextField jtxtDOB;
	private JTextField jtxtAge;
	private JTextField jtxtSalary;

	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	DefaultTableModel model = new DefaultTableModel();
	private String sql;
	
	
	
	/**
	 * Launch the application.
	 */
	
	public void updateTable()
	{
		conn = EmployeeData.ConnectDB();
		
		if(conn !=null) 
		{
			String sql = "Select EmpID,NINumber,Firstname,Surname,Gender,DOB,Age,Salary";
	    }
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			Object[] columnData = new Object[8];
			
			while(rs.next()) {
			
				columnData [0] = rs.getString("EmpID");
				columnData [1] = rs.getString("NINumber");
				columnData [2] = rs.getString("Firstname");
				columnData [3] = rs.getString("Surname");
				columnData [4] = rs.getString("Gender");
				columnData [5] = rs.getString("DOB");
				columnData [6] = rs.getString("Age");
				columnData [7] = rs.getString("Salary");
				
				model.addRow(columnData);
				
			}
		}
		
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, e);
		}
	
}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee window = new Employee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Employee() {
		initialize();
		
		conn = EmployeeData.ConnectDB();
		Object col[] = {"EmpID","NINumber","Firstname","Surname","Gender","DOB","Age","Salary"};
		model.setColumnIdentifiers(col);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0,0, 1450, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("EmployeeID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 27, 89, 35);
		frame.getContentPane().add(lblNewLabel);
		
		jtxtEmployeeID = new JTextField();
		jtxtEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 14));
		jtxtEmployeeID.setBounds(133, 31, 199, 27);
		frame.getContentPane().add(jtxtEmployeeID);
		jtxtEmployeeID.setColumns(10);
		
		JButton btnNewButton = new JButton(" Add New");                                        // ADD BOX
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "INSERT INTO employee(EmpID,NINumber,Firstname,Surname,Gender,DOB,Age,Salary)VALES(?,?,?,?,?,?,?,?)";
				
				try 
				{
					pst = conn.prepareStatement(sql);
					  pst.setString(1, jtxtEmployeeID.getText());
					  pst.setString(2, jtxtNINumber.getText());
					  pst.setString(3, jtxtFirstname.getText());
					  pst.setString(4, jtxtSurname.getText());
					  pst.setString(5, jtxtGender.getText());
					  pst.setString(6, jtxtDOB.getText());
					  pst.setString(7, jtxtAge.getText());
					  pst.setString(8, jtxtSalary.getText());
					  
					  
					  pst.execute();
					  
					  rs.close();
					  pst.close();
				}
				catch (Exception ev)
				{
					JOptionPane.showMessageDialog(null, "System Update Completed");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model .addRow(new Object[] {
						

						   jtxtEmployeeID.getText(),
						   jtxtNINumber.getText(),
						   jtxtFirstname.getText(),
						   jtxtSurname.getText(),
						   jtxtGender.getText(),
						   jtxtDOB.getText(),
						   jtxtAge.getText(),
						   jtxtSalary.getText(),
				});
			
				    if(table.getSelectedRow() == -1) {
					if(table.getRowCount() == 0) {
						JOptionPane.showConfirmDialog(null,"Membersship Update Confirmed","Employee Database Management System",
								JOptionPane.OK_OPTION);
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(416, 418, 119, 35);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(406, 44, 900, 335);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmpID", "NINumber", "Firstname", "Surname", "Gender", "DOB", "Age", "Salary"
			}
		));
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		jtxtNINumber = new JTextField();
		jtxtNINumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		jtxtNINumber.setColumns(10);
		jtxtNINumber.setBounds(133, 69, 199, 27);
		frame.getContentPane().add(jtxtNINumber);
		
		JButton btnNewButton_1 = new JButton("Print");                                 // PRINT BOX
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MessageFormat header = new MessageFormat("Printing in Progress");
				MessageFormat footer = new MessageFormat("Page  {0, number, integer}");
				
				try 
				{
					table.print();
				}
				
				catch(java.awt.print.PrinterException ev) {
					System.err.format("No Printer found", ev.getMessage());
				}
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setBounds(648, 418, 89, 35);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Restart");                              // RESTART BOX
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				jtxtEmployeeID.setText(null);
				jtxtNINumber.setText(null);
				jtxtFirstname.setText(null);
				jtxtSurname.setText(null);
				jtxtGender.setText(null);
				jtxtDOB.setText(null);
				jtxtAge.setText(null);
				jtxtSalary.setText(null);
				
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_1.setBounds(851, 417, 89, 37);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("Exit");                                // EXIT BOX
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame =new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(frame,"Confirm if you want to exit","Employee Database Management System",
					    JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION)
				{
				  System.exit(0);	
				
				}
			}
		});
		btnNewButton_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_2.setBounds(1040, 416, 89, 37);
		frame.getContentPane().add(btnNewButton_1_2);
		
		JLabel lblNinumber = new JLabel("NI Number");
		lblNinumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNinumber.setBounds(10, 69, 89, 35);
		frame.getContentPane().add(lblNinumber);
		
		jtxtFirstname = new JTextField();
		jtxtFirstname.setFont(new Font("Tahoma", Font.BOLD, 14));
		jtxtFirstname.setColumns(10);
		jtxtFirstname.setBounds(133, 107, 199, 27);
		frame.getContentPane().add(jtxtFirstname);
		
		jtxtSurname = new JTextField();
		jtxtSurname.setFont(new Font("Tahoma", Font.BOLD, 14));
		jtxtSurname.setColumns(10);
		jtxtSurname.setBounds(133, 145, 199, 27);
		frame.getContentPane().add(jtxtSurname);
		
		jtxtGender = new JTextField();
		jtxtGender.setFont(new Font("Tahoma", Font.BOLD, 14));
		jtxtGender.setColumns(10);
		jtxtGender.setBounds(133, 183, 199, 27);
		frame.getContentPane().add(jtxtGender);
		
		jtxtDOB = new JTextField();
		jtxtDOB.setFont(new Font("Tahoma", Font.BOLD, 14));
		jtxtDOB.setColumns(10);
		jtxtDOB.setBounds(133, 221, 199, 27);
		frame.getContentPane().add(jtxtDOB);
		
		jtxtAge = new JTextField();
		jtxtAge.setFont(new Font("Tahoma", Font.BOLD, 14));
		jtxtAge.setColumns(10);
		jtxtAge.setBounds(133, 259, 199, 27);
		frame.getContentPane().add(jtxtAge);
		
		jtxtSalary = new JTextField();
		jtxtSalary.setFont(new Font("Tahoma", Font.BOLD, 14));
		jtxtSalary.setColumns(10);
		jtxtSalary.setBounds(133, 297, 199, 27);
		frame.getContentPane().add(jtxtSalary);
		
		JLabel lblNewLabel_1_1 = new JLabel("Firstname");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 107, 89, 35);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Surname");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 145, 89, 35);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Gender");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(10, 183, 89, 35);
		frame.getContentPane().add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("DOB");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_4.setBounds(10, 221, 89, 35);
		frame.getContentPane().add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Age");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_5.setBounds(10, 259, 89, 35);
		frame.getContentPane().add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("Salary");
		lblNewLabel_1_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_6.setBounds(10, 297, 89, 35);
		frame.getContentPane().add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1 = new JLabel("Employee Database Management System");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_1.setBounds(356, 0, 808, 27);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
