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

public class SeeAllDocumentsWindow {

	private JFrame studentFrame;
	private JTable studentsProfileTable;
	private JScrollPane scrollProfilePane;
	private static Boolean update = false;
	boolean ready = true;
	private JLabel warningLabel;
	private JButton returnButton;
	
	Cluster cluster = CassandraInitialConnection.getClusterConnection(Program_Beginning.SERVER_IP);
	Session session = CassandraInitialConnection.getKeyspaceConnection(cluster, Program_Beginning.PROGRAM_KEYSPACE);
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
//	Session session = Program_Beginning.getSession();
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	int updateTableButtonFunction = 0;
	JFrame frame = new JFrame("JOptionPane showMessageDialog example");
	private JTable statisticsTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeeAllDocumentsWindow window = new SeeAllDocumentsWindow();
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
	public SeeAllDocumentsWindow() {
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
		scrollProfilePane.setBounds(22, 50, 517, 301);
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
				"User Document ID", "User Documents Title", "User Document Content", "User Documents Creation Date", "User Document Author", "User Login", "User Password", "User Full Name", "User Date of Birth", "User Email"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true, false, false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		studentsProfileTable.getColumnModel().getColumn(0).setPreferredWidth(103);
		studentsProfileTable.getColumnModel().getColumn(1).setPreferredWidth(306);
		studentsProfileTable.getColumnModel().getColumn(2).setPreferredWidth(297);
		studentsProfileTable.getColumnModel().getColumn(3).setPreferredWidth(227);
		studentsProfileTable.getColumnModel().getColumn(4).setPreferredWidth(150);
		studentsProfileTable.getColumnModel().getColumn(5).setPreferredWidth(160);
		studentsProfileTable.getColumnModel().getColumn(5).setMinWidth(40);
		studentsProfileTable.getColumnModel().getColumn(6).setPreferredWidth(166);
		studentsProfileTable.getColumnModel().getColumn(6).setMinWidth(60);
		studentsProfileTable.getColumnModel().getColumn(7).setPreferredWidth(188);
		studentsProfileTable.getColumnModel().getColumn(8).setPreferredWidth(118);
		studentsProfileTable.getColumnModel().getColumn(9).setPreferredWidth(118);
		studentsProfileTable.setForeground(new Color(0, 0, 0));
		studentsProfileTable.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		try {
			DefaultTableModel model = (DefaultTableModel) studentsProfileTable.getModel();
			String cqlStatement = "SELECT * FROM \"Documents_by_Users\";";
			for (Row row : session.execute(cqlStatement)) {

				System.out.println(row.toString());
				String[] splittedString = row.toString().split(",");
				String[] modelArray = new String[splittedString.length];
				String[] tempString;
				modelArray[0] = splittedString[0].substring(4);
				if (splittedString.length == 11) {
					for (int i = 1; i < splittedString.length; i++) {
						modelArray[i] = splittedString[i].substring(1);
					}
					if (modelArray[8].contains("'")) {
						tempString = modelArray[8].split("'");
						modelArray[8] = tempString[1];
					}
					if (modelArray[9].contains("'")) {
						tempString = modelArray[9].split("'");
						modelArray[9] = tempString[1];
					}
					if (modelArray[10].contains("]")) {
						tempString = modelArray[10].split("]");
						modelArray[10] = tempString[0];
					}
					model.addRow(new Object[] {modelArray[0],modelArray[5],modelArray[3],modelArray[2],modelArray[4],modelArray[1],
							modelArray[10],modelArray[8] + " " + modelArray[9],modelArray[6],modelArray[7]});
				} else {
					for (int i = 1; i < splittedString.length; i++) {
						modelArray[i] = splittedString[i].substring(1);
					}
					if (modelArray[9].contains("]")) {
						tempString = modelArray[9].split("]");
						modelArray[9] = tempString[0];
					}
					model.addRow(new Object[] {modelArray[0],modelArray[5],modelArray[3],modelArray[2],modelArray[4],modelArray[1],
							modelArray[9],modelArray[8],modelArray[6],modelArray[7]});
				}
								
	        }
		
    	} catch (Exception arg0) {
    		JOptionPane.showMessageDialog(null, arg0);
    	}
		
		warningLabel = new JLabel();
		warningLabel.setText("Document Data");
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		warningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		warningLabel.setForeground(Color.BLACK);
		warningLabel.setBounds(10, 11, 768, 28);
		studentFrame.getContentPane().add(warningLabel);
		
		returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserWindow.main(null);
				studentFrame.setVisible(false);
			}
		});
		returnButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		returnButton.setBounds(297, 362, 224, 56);
		studentFrame.getContentPane().add(returnButton);
		
		JScrollPane scrollStatisticsPane = new JScrollPane();
		scrollStatisticsPane.setBounds(585, 50, 193, 301);
		studentFrame.getContentPane().add(scrollStatisticsPane);
		
		statisticsTable = new JTable();
		statisticsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Document", "Number of Users"
			}
		));
		statisticsTable.getColumnModel().getColumn(0).setPreferredWidth(68);
		statisticsTable.getColumnModel().getColumn(1).setPreferredWidth(125);
		scrollStatisticsPane.setViewportView(statisticsTable);
		statisticsTable.setRowHeight(84);
		statisticsTable.setForeground(Color.BLACK);
		statisticsTable.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		statisticsTable.setFillsViewportHeight(true);
		statisticsTable.setColumnSelectionAllowed(true);
		statisticsTable.setCellSelectionEnabled(true);
		statisticsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		// AGGREGATE
		
		try {
			DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();
			String cqlStatement = "CREATE OR REPLACE FUNCTION state_group_and_count(state map<int, int>, type int) " + 
					"CALLED ON NULL INPUT " +
					"RETURNS map<int, int> " +
					"LANGUAGE java AS ' " + 
					"Integer count = (Integer) state.get(type);"
					+ " if (count == null) count = 1;"
					+ " else count++;"
					+ " state.put(type, count);"
					+ " return state; ';";
					
			session.execute(cqlStatement);
					
			cqlStatement = "CREATE OR REPLACE AGGREGATE group_and_count(int) "
					+ "SFUNC state_group_and_count "
					+ "STYPE map<int, int> "
					+ "INITCOND {};";
				 
			session.execute(cqlStatement);
					
			cqlStatement = "SELECT group_and_count(document_id) FROM \"Users_by_Documents\";";
			
			String resultString = "";
					
			for (Row row : session.execute(cqlStatement)) {
						
				resultString += row.toString();
						
			}
			
			System.out.println(resultString);
			
			String[] splittedString = resultString.toString().split(",");
			String[] modelArray = new String[splittedString.length];
			String[] tempString;
			modelArray[0] = splittedString[0].substring(5);
			for (int i = 1; i < splittedString.length; i++) {
				modelArray[i] = splittedString[i].substring(1);
			}
			tempString = modelArray[splittedString.length - 1].split("}");
			modelArray[splittedString.length - 1] = tempString[0];
			
			String[] resultSet = new String[2];
			
			for (int i = 0; i < splittedString.length; i++) {
				System.out.println(modelArray[i]);
				resultSet = modelArray[i].split("=");
				model.addRow(new Object[] {resultSet[0] , resultSet[1]});
			}
				
		} catch (Exception arg0) {
		    	JOptionPane.showMessageDialog(null, arg0);
		}
		
		studentFrame.setTitle("All Documents window - User's Documents Application - v.0");
		studentFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				AskingExit.main(null);
			}
		});
		studentFrame.setBounds(100, 100, 810, 462);
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
