package com.project.cassandra.window_forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.project.cassandra.window_forms.AskingExit;
import com.project.cassandra.window_forms.Authorization;
import com.project.cassandra.connect.Program_Beginning;
import com.project.cassandra.connect.CassandraInitialConnection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.awt.SystemColor;

public class UserWindow {

	private JFrame studentFrame;
	private JTable studentsProfileTable;
	private JScrollPane scrollProfilePane;
	private static Boolean update = false;
	boolean ready = true;
	private JLabel messageLabel;
	private JLabel warningLabel;
	private JButton returnButton;
	private JButton deleteProfileButton;
	private JButton changeProfileButton;
	private JButton addDocumentsToProfileButton;
	
	Cluster cluster = CassandraInitialConnection.getClusterConnection(Program_Beginning.SERVER_IP);
	Session session = CassandraInitialConnection.getKeyspaceConnection(cluster, Program_Beginning.PROGRAM_KEYSPACE);
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
//	Session session = Program_Beginning.getSession();
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	int updateTableButtonFunction = 0;
	JFrame frame = new JFrame("JOptionPane showMessageDialog example");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserWindow window = new UserWindow();
					window.studentFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserWindow() {
		setUpdate(Registration.getRegister());
		setUpdate(false);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		studentFrame = new JFrame();
		studentFrame.setResizable(false);
		studentFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		studentFrame.getContentPane().setBackground(SystemColor.control);
		studentFrame.getContentPane().setLayout(null);
		
		scrollProfilePane = new JScrollPane();
		scrollProfilePane.setBounds(22, 162, 758, 301);
		studentFrame.getContentPane().add(scrollProfilePane);
		studentsProfileTable = new JTable();
		scrollProfilePane.setViewportView(studentsProfileTable);
		studentsProfileTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		studentsProfileTable.setColumnSelectionAllowed(true);
		studentsProfileTable.setCellSelectionEnabled(true);
		studentsProfileTable.setRowHeight(84);
		studentsProfileTable.setFillsViewportHeight(true);
		studentsProfileTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"User Login", "User Password", "User Full Name", "User Date of Birth", "User Email", "User Document ID", "User Document Title", "User Document Content", "User Document Creation Date", "User Document Author"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, true, true, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		studentsProfileTable.getColumnModel().getColumn(0).setPreferredWidth(160);
		studentsProfileTable.getColumnModel().getColumn(0).setMinWidth(40);
		studentsProfileTable.getColumnModel().getColumn(1).setPreferredWidth(166);
		studentsProfileTable.getColumnModel().getColumn(1).setMinWidth(60);
		studentsProfileTable.getColumnModel().getColumn(2).setPreferredWidth(188);
		studentsProfileTable.getColumnModel().getColumn(3).setPreferredWidth(118);
		studentsProfileTable.getColumnModel().getColumn(4).setPreferredWidth(118);
		studentsProfileTable.getColumnModel().getColumn(5).setPreferredWidth(103);
		studentsProfileTable.getColumnModel().getColumn(6).setPreferredWidth(306);
		studentsProfileTable.getColumnModel().getColumn(7).setPreferredWidth(297);
		studentsProfileTable.getColumnModel().getColumn(8).setPreferredWidth(227);
		studentsProfileTable.getColumnModel().getColumn(9).setPreferredWidth(175);
		studentsProfileTable.setForeground(new Color(0, 0, 0));
		studentsProfileTable.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		try {
			DefaultTableModel model = (DefaultTableModel) studentsProfileTable.getModel();
			String cqlStatement = "SELECT * FROM \"Users_by_Documents\" WHERE user_login ='" + Authorization.getLogin() + "' ALLOW FILTERING";
			for (Row row : session.execute(cqlStatement)) {

				System.out.println(row.toString());
				String[] splittedString = row.toString().split(",");
				String[] modelArray = new String[splittedString.length];
				String[] tempString;
				modelArray[0] = splittedString[0].substring(4);
				for (int i = 1; i < splittedString.length; i++) {
					modelArray[i] = splittedString[i].substring(1);
				}
				tempString = modelArray[4].split("'");
				modelArray[4] = tempString[1];
				tempString = modelArray[5].split("'");
				modelArray[5] = tempString[1];
				tempString = modelArray[10].split("]");
				modelArray[10] = tempString[0];
				model.addRow(new Object[] {modelArray[0],modelArray[6],modelArray[4] + " " + modelArray[5],modelArray[2],modelArray[3],
						modelArray[1],modelArray[7],modelArray[8],modelArray[9],modelArray[10]});
				
	        }
		
    	} catch (Exception arg0) {
    		JOptionPane.showMessageDialog(null, arg0);
    	}
		
		
		messageLabel = new JLabel("<html>Hello user! Here you could add some documents and change your profile.</html>");
		messageLabel.setForeground(new Color(0, 0, 0));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		messageLabel.setBounds(12, 10, 768, 35);
		studentFrame.getContentPane().add(messageLabel);
		
		warningLabel = new JLabel();
		warningLabel.setText("Profile Data");
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		warningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		warningLabel.setForeground(Color.BLACK);
		warningLabel.setBounds(12, 123, 768, 28);
		studentFrame.getContentPane().add(warningLabel);
		
		returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Authorization.main(null);
				studentFrame.setVisible(false);
			}
		});
		returnButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		returnButton.setBounds(556, 473, 224, 56);
		studentFrame.getContentPane().add(returnButton);
		
		deleteProfileButton = new JButton("Delete Profile");
		deleteProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int local_document_counter = 0;
					String login = Authorization.getLogin();
					
					/*
					 * DELETE for previous DB structure
					String cqlStatement = "CREATE INDEX IF NOT EXISTS user_search ON \"Documents_by_Users\" (FULL(user_login));";
					session.execute(cqlStatement);
							
					cqlStatement = "SELECT document_id FROM \"Documents_by_Users\" WHERE user_login CONTAINS '" + login + "' ALLOW FILTERING;";
					
					for (Row row : session.execute(cqlStatement)) {

						document_index = row.toString().substring(5, 6);
		
			        }

					cqlStatement = "BEGIN BATCH " + 
					"DELETE FROM \"Users_by_Documents\" where user_login = {'" + login + "'};" + 
					"DELETE FROM \"Documents_by_Users\" where document_id = {" + document_index + "} AND user_login = {'" + login + "'};" + 
	                "APPLY BATCH";
					*/
					
					//NOT TO DO DELETE with query builder without batch
					
					System.out.println(login);
							
					String cqlStatement = "SELECT document_id FROM \"Documents_by_Users\" WHERE user_login = '" + login + "' ALLOW FILTERING;";		
					
					for (Row row : session.execute(cqlStatement)) {
						
						local_document_counter += 1;
						
			        }
					
					String[] document_indices = new String[local_document_counter];
					String document_index_not_trimmed;
					String[] document_index = new String[2];
					int i = 0;
					
					for (Row row : session.execute(cqlStatement)) {
						
						document_index_not_trimmed = row.toString().substring(4);
						document_index = document_index_not_trimmed.split("]");
						document_indices[i] = document_index[0];
						i++;
			        }
					
					String deleteQuery = "";
					
					for (int j = 0; j < local_document_counter; j++) {
						deleteQuery += "DELETE FROM \"Documents_by_Users\" where document_id = " + document_indices[j] + " AND user_login = '" + login + "';";
					}

					cqlStatement = "BEGIN BATCH " + 
					"DELETE FROM \"Users_by_Documents\" where user_login = '" + login + "';" + 
					deleteQuery + 
	                "APPLY BATCH";
					
					session.execute(cqlStatement);
										
					JOptionPane.showMessageDialog(frame,"User Successfully Deleted!");
					Authorization.main(null);
					studentFrame.setVisible(false);
		    	} catch (Exception arg0) {
		    		JOptionPane.showMessageDialog(null, arg0);
		    	}
			}
		});
		deleteProfileButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		deleteProfileButton.setBounds(294, 473, 224, 56);
		studentFrame.getContentPane().add(deleteProfileButton);
		
		changeProfileButton = new JButton("Change Profile Data");
		changeProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setUpdate(true);
				btnRegisterActionPerformed(e);
				studentFrame.setVisible(false);
			}
		});
		changeProfileButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		changeProfileButton.setBounds(22, 473, 224, 56);
		studentFrame.getContentPane().add(changeProfileButton);
		
		addDocumentsToProfileButton = new JButton("Add Doc to Profile");
		addDocumentsToProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddDocumentsToUser.main(null);
				studentFrame.setVisible(false);
			}
		});
		addDocumentsToProfileButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addDocumentsToProfileButton.setBounds(556, 56, 224, 56);
		studentFrame.getContentPane().add(addDocumentsToProfileButton);
		
		JButton addDocumentButton = new JButton("Add Document");
		addDocumentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddDocuments.main(null);
				studentFrame.setVisible(false);
			}
		});
		addDocumentButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addDocumentButton.setBounds(22, 56, 224, 56);
		studentFrame.getContentPane().add(addDocumentButton);
		
		JButton seeAllDocumentsButton = new JButton("See All Documents");
		seeAllDocumentsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SeeAllDocumentsWindow.main(null);
				studentFrame.setVisible(false);
			}
		});
		seeAllDocumentsButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		seeAllDocumentsButton.setBounds(294, 56, 224, 56);
		studentFrame.getContentPane().add(seeAllDocumentsButton);
		studentFrame.setTitle("User window - User's Documents Application - v.0");
		studentFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				AskingExit.main(null);
			}
		});
		studentFrame.setBounds(100, 100, 810, 574);
		studentFrame.setLocationRelativeTo(null);
	}
	public void setUpdate(Boolean update) {
	    this.update = update;
	}
	public static Boolean getUpdate() {
		return update;
	}
	private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {
		Registration registrationForm = new Registration();
		registrationForm.main(null);
    }
}
