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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.project.cassandra.window_forms.AskingExit;
import com.datastax.driver.core.Session;
import com.project.cassandra.connect.Program_Beginning;
import java.awt.SystemColor;
//import main_classes.Javaconnect;
//import oracle.jdbc.*;

public class AdminWindow {

	private JFrame adminWindowFrame;
	private JTable studentsProfileTable;
	private JScrollPane scrollProfilePane;
	private static String name;
	private static String militaryDepartment;
	private static String university;
	private static Boolean register = false;
	private JLabel messageLabel;
	private JButton returnButton;
	private JButton deleteProfileButton;
	private JButton changeProfileButton;
	private JButton addProfileButton;
	private JButton addExamButton;
	private JButton changeExamButton;
	private JButton deleteExamButton;
	private JButton updateTableButton;
	private static String login;
	String current_student = "";
	int chooseButtonFunction = 0, updateTableButtonFunction = 0;
	boolean ready = true;
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
//	Session session = Program_Beginning.getSession();
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
//	Connection conn = null;
//	OraclePreparedStatement pst = null;
//	OracleResultSet rs = null;
	JFrame frame = new JFrame("JOptionPane showMessageDialog example");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminWindow window = new AdminWindow();
					window.adminWindowFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminWindow() {
//		conn = Javaconnect.ConnectionDb();
		setRegister(Registration.getRegister());
		setRegister(false);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		adminWindowFrame = new JFrame();
		adminWindowFrame.setResizable(false);
		adminWindowFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		adminWindowFrame.getContentPane().setBackground(SystemColor.control);
		adminWindowFrame.getContentPane().setLayout(null);
		
	/*	try {
    		String sql = "SELECT * FROM USER_MARKS";
    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
    		rs = (OracleResultSet) pst.executeQuery();
    		while (rs.next()) {
    			DefaultTableModel model = (DefaultTableModel) studentsMarksTable.getModel();
    			if (rs.getString("SHOW").equals("1")) {
    			model.addRow(new Object[] {
    				rs.getString("STUDENT"),
    				rs.getString("UNIVERSITY"),
    				rs.getString("NUMBER_COURSE"),
    				rs.getString("SEMESTER"),
    				rs.getString("SUBJECT"),
    				rs.getString("MARK"),
    				rs.getString("LOGIN")});
    			}
    		}
    		rs.close();
			pst.close();
    	} catch (SQLException | HeadlessException arg0) {
    		JOptionPane.showMessageDialog(null, arg0);
    	}*/
		
		scrollProfilePane = new JScrollPane();
		scrollProfilePane.setBounds(12, 106, 768, 353);
		adminWindowFrame.getContentPane().add(scrollProfilePane);
		studentsProfileTable = new JTable();
		scrollProfilePane.setViewportView(studentsProfileTable);
		studentsProfileTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		studentsProfileTable.setColumnSelectionAllowed(true);
		studentsProfileTable.setCellSelectionEnabled(true);
		studentsProfileTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) studentsProfileTable.getModel();
				if (!((studentsProfileTable.getSelectedRow() == -1) | (studentsProfileTable.getRowCount() == 0))) {
				setName(model.getValueAt(studentsProfileTable.getSelectedRow(), 0).toString());
				setUniversity(model.getValueAt(studentsProfileTable.getSelectedRow(), 1).toString());
				}
			}
		});
		studentsProfileTable.setRowHeight(24);
		studentsProfileTable.setFillsViewportHeight(true);
		studentsProfileTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Document ID", "Document Title", "Document Content", "Document Creation Date", "Document Author", "Users Login", "Users Full Names", "Users Dates of Birth", "Users Emails"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		studentsProfileTable.getColumnModel().getColumn(0).setPreferredWidth(84);
		studentsProfileTable.getColumnModel().getColumn(0).setMinWidth(40);
		studentsProfileTable.getColumnModel().getColumn(1).setPreferredWidth(228);
		studentsProfileTable.getColumnModel().getColumn(1).setMinWidth(24);
		studentsProfileTable.getColumnModel().getColumn(2).setPreferredWidth(343);
		studentsProfileTable.getColumnModel().getColumn(3).setPreferredWidth(174);
		studentsProfileTable.getColumnModel().getColumn(4).setPreferredWidth(207);
		studentsProfileTable.getColumnModel().getColumn(5).setPreferredWidth(475);
		studentsProfileTable.getColumnModel().getColumn(6).setPreferredWidth(428);
		studentsProfileTable.getColumnModel().getColumn(7).setPreferredWidth(311);
		studentsProfileTable.getColumnModel().getColumn(8).setPreferredWidth(331);
		studentsProfileTable.setForeground(new Color(0, 0, 0));
		studentsProfileTable.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		messageLabel = new JLabel("<html>Hello administrator! Here you could add some documents and change your profile.</html>");
		messageLabel.setForeground(new Color(0, 0, 0));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		messageLabel.setBounds(12, 10, 768, 24);
		adminWindowFrame.getContentPane().add(messageLabel);
		
		returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Authorization.main(null);
				adminWindowFrame.setVisible(false);
			}
		});
		returnButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		returnButton.setBounds(556, 537, 224, 56);
		adminWindowFrame.getContentPane().add(returnButton);
		
		deleteProfileButton = new JButton("Delete Profile");
		deleteProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) studentsProfileTable.getModel();
			/*	if  ((studentsProfileTable.getSelectedRow() == -1) | (studentsProfileTable.getRowCount() == 0)) {
					warningLabel.setText("�� �� ������� ��������");
				} else {
					try {
					String sql = "DELETE FROM USER_MEDICAL_COMMITEE WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
				    sql = "DELETE FROM USER_UNIVERSITY WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
					sql = "DELETE FROM USERS WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
					sql = "DELETE FROM USER_MEDICAL_EXAMINATION WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
				    sql = "DELETE FROM USER_MARKS WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
					sql = "DELETE FROM USER_EXAM WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
					sql = "DELETE FROM USER_MILITARY_DEPARTMENT WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
					sql = "DELETE FROM USER_MILITARY_DEPARTMENT_EXAM WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
					sql = "DELETE FROM USER_INFORMATION WHERE LOGIN='" + model.getValueAt(studentsProfileTable.getSelectedRow(), 2).toString() + "'";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
					rs.close();
					pst.close();
					JOptionPane.showMessageDialog(frame,"��������� ������� ������");
		    	} catch (SQLException | HeadlessException arg0) {
		    		JOptionPane.showMessageDialog(null, arg0);
		    	}
				}*/
			}
		});
		deleteProfileButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		deleteProfileButton.setBounds(556, 470, 224, 56);
		adminWindowFrame.getContentPane().add(deleteProfileButton);
		
		changeProfileButton = new JButton("Change Profile");
		changeProfileButton.setEnabled(false);
		changeProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	btnRegisterActionPerformed(e);
			//	adminWindowFrame.setVisible(false);
			}
		});
		changeProfileButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		changeProfileButton.setBounds(286, 470, 224, 56);
		adminWindowFrame.getContentPane().add(changeProfileButton);
		
		addProfileButton = new JButton("Create Profile");
		addProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRegister(true);
				btnRegisterActionPerformed(e);
				adminWindowFrame.setVisible(false);
			}
		});
		addProfileButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addProfileButton.setBounds(12, 470, 224, 56);
		adminWindowFrame.getContentPane().add(addProfileButton);
		
		addExamButton = new JButton("Add Document");
		addExamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if  ((studentsProfileTable.getSelectedRow() == -1) | (studentsProfileTable.getRowCount() == 0)) {
				} else {
					DefaultTableModel model = (DefaultTableModel) studentsProfileTable.getModel();
    				setLogin(model.getValueAt(studentsProfileTable.getSelectedRow(), 0).toString());
					AddDocuments.main(null);
					adminWindowFrame.setVisible(false);
				}
			}
		});
		addExamButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addExamButton.setBounds(12, 39, 230, 56);
		adminWindowFrame.getContentPane().add(addExamButton);
		
		changeExamButton = new JButton("Change Document");
		changeExamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if  ((studentsProfileTable.getSelectedRow() == -1) | (studentsProfileTable.getRowCount() == 0)) {
				} else {
					DefaultTableModel model = (DefaultTableModel) studentsProfileTable.getModel();
    				setLogin(model.getValueAt(studentsProfileTable.getSelectedRow(), 0).toString());
					AddDocuments.main(null);
					adminWindowFrame.setVisible(false);
				}
			}
		});
		changeExamButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		changeExamButton.setBounds(280, 39, 230, 56);
		adminWindowFrame.getContentPane().add(changeExamButton);
		
	/*	try {
    		String sql = "SELECT * FROM USER_INFORMATION";
    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
    		rs = (OracleResultSet) pst.executeQuery();
    		while (rs.next()) {
    			if (rs.getString("SHOW") != null) {
    				DefaultTableModel model = (DefaultTableModel) studentsProfileTable.getModel();
    				Statement pst1 = conn.createStatement();
    				ResultSet rs1 = pst1.executeQuery("SELECT * FROM USER_MEDICAL_COMMITEE WHERE LOGIN ='" + rs.getString("LOGIN") +"'");
    				while(rs1.next()){
    					Statement pst2 = conn.createStatement();
        				ResultSet rs2 = pst2.executeQuery("SELECT * FROM USER_UNIVERSITY WHERE LOGIN ='" + rs.getString("LOGIN") +"'");
        				while(rs2.next()){
        				if (rs.getString("VERIFICATION").equals("1")) {
    					model.addRow(new Object[] {
    							"<html><body bgcolor=\"RED\">" + rs.getString("NAME") + "</body></html>",
    							rs.getString("SURNAME"),
    							rs.getString("LOGIN"),
    							rs.getString("PASSWORD"),
    							rs.getString("DATE_OF_BIRTH"),
    							"<html>̳���: " + rs1.getString("TOWN") + ";<br>" +
    							"�����: " + rs1.getString("DISTRICT") + ";<br>" +
    							"����� ���������� ��������: " + rs1.getString("NUMBER_CERTIFICATE") + "</html>",
    							rs.getString("IDCODE"),
    							rs.getString("PASSPORT"),
    							"<html>̳���: " + rs2.getString("TOWN") + ";<br>" +
    							"����������: " + rs2.getString("UNIVERSITY") + ";<br>" +
    							"���������: " + rs2.getString("FACULTY") +
    							"; ��������: " + rs2.getString("COURSE") +
    							"; ����: " + rs2.getString("COURSE_NUMBER") + "</html>",
    							rs.getString("PHONE_NUMBER")});
        				} else {
        					model.addRow(new Object[] {
        							rs.getString("NAME"),
        							rs.getString("SURNAME"),
        							rs.getString("LOGIN"),
        							rs.getString("PASSWORD"),
        							rs.getString("DATE_OF_BIRTH"),
        							"<html>̳���: " + rs1.getString("TOWN") + ";<br>" +
        							"�����: " + rs1.getString("DISTRICT") + ";<br>" +
        							"����� ���������� ��������: " + rs1.getString("NUMBER_CERTIFICATE") + "</html>",
        							rs.getString("IDCODE"),
        							rs.getString("PASSPORT"),
        							"<html>̳���: " + rs2.getString("TOWN") + ";<br>" +
        							"����������: " + rs2.getString("UNIVERSITY") + ";<br>" +
        							"���������: " + rs2.getString("FACULTY") +
        							"; ��������: " + rs2.getString("COURSE") +
        							"; ����: " + rs2.getString("COURSE_NUMBER") + "</html>",
        							rs.getString("PHONE_NUMBER")});	
        				}
        				}
    					rs2.close();
        				pst2.close();
    				}
    				rs1.close();
    				pst1.close();
    				}
    		}
    		rs.close();
			pst.close();
    	} catch (SQLException | HeadlessException arg0) {
    		JOptionPane.showMessageDialog(null, arg0);
    	}
		
		try {
    		String sql = "SELECT * FROM USER_MEDICAL_EXAMINATION";
    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
    		rs = (OracleResultSet) pst.executeQuery();
    		while (rs.next()) {
    			DefaultTableModel model = (DefaultTableModel) studentsMedicalTable.getModel();
    			if (rs.getString("SHOW").equals("1")) {
    			model.addRow(new Object[] {
    				rs.getString("STUDENT"),
    				rs.getString("MILITARY_DISTRICT"),
    				rs.getString("DOCTOR"),
    				rs.getString("READY"),
    				rs.getString("REASON"),
    				rs.getString("LOGIN")});
    			}
    		}
    		rs.close();
			pst.close();
    	} catch (SQLException | HeadlessException arg0) {
    		JOptionPane.showMessageDialog(null, arg0);
    	}*/
		
		deleteExamButton = new JButton("Delete Document");
		deleteExamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			/*	if  ((studentsExamTable.getSelectedRow() == -1) | (studentsExamTable.getRowCount() == 0)) {
					warningLabel.setText("�� ������ ������� ��������");
				} else {
					DefaultTableModel model = (DefaultTableModel) studentsExamTable.getModel();
					try {
						String sql = "DELETE FROM USER_MILITARY_DEPARTMENT_EXAM WHERE LOGIN='" + model.getValueAt(studentsExamTable.getSelectedRow(), 5).toString() + "'";
			    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			    		rs = (OracleResultSet) pst.executeQuery();
						rs.close();
						pst.close();
						String sql1 = "SELECT * FROM USER_MILITARY_DEPARTMENT WHERE LOGIN='" + current_student + "'";
			    		pst = (OraclePreparedStatement) conn.prepareStatement(sql1);
			    		rs = (OracleResultSet) pst.executeQuery();
			    		while (rs.next()) {
			    			Statement pst1 = conn.createStatement();
			    			pst1.executeUpdate("INSERT INTO USER_MILITARY_DEPARTMENT_EXAM (STUDENT, MILITARY_DEPARTMENT, LOGIN, SUBJECT, READY, DESCRIPTION) VALUES ('"
			    			+ rs.getString("STUDENT") + "', '"
			    			+ rs.getString("MILITARY_DEPARTMENT") + "', '"
			    			+ current_student + "', '�������', '���', '����')");
			    			conn.commit();
			    			pst1.close();
			    			}
			    		JOptionPane.showMessageDialog(frame,"������ ��������");
			    		rs.close();
						pst.close();
			    	} catch (SQLException | HeadlessException e) {
			    		JOptionPane.showMessageDialog(null, e);
			    		try {
							conn.rollback();
						} catch (SQLException e1) {
							e1.printStackTrace();
							Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, e1);
						}
			    	}
				}*/
			}
		});
		deleteExamButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		deleteExamButton.setBounds(550, 39, 230, 56);
		adminWindowFrame.getContentPane().add(deleteExamButton);
		
		updateTableButton = new JButton("Refresh the Table");
		updateTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (updateTableButtonFunction == 0) {
				/*	DefaultTableModel model = (DefaultTableModel) studentsProfileTable.getModel();
					for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
				        model.removeRow(i);
				    }
					System.out.println("�������");
					try {
			    		String sql = "SELECT * FROM USER_INFORMATION";
			    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			    		rs = (OracleResultSet) pst.executeQuery();
			    		while (rs.next()) {
			    			if (rs.getString("SHOW") != null) {
			    				Statement pst1 = conn.createStatement();
			    				ResultSet rs1 = pst1.executeQuery("SELECT * FROM USER_MEDICAL_COMMITEE WHERE LOGIN ='" + rs.getString("LOGIN") +"'");
			    				while(rs1.next()){
			    					Statement pst2 = conn.createStatement();
			        				ResultSet rs2 = pst2.executeQuery("SELECT * FROM USER_UNIVERSITY WHERE LOGIN ='" + rs.getString("LOGIN") +"'");
			        				while(rs2.next()){
			        				if (rs.getString("VERIFICATION").equals("1")) {
			    					model.addRow(new Object[] {
			    							"<html><body bgcolor=\"RED\">" + rs.getString("NAME") + "</body></html>",
			    							rs.getString("SURNAME"),
			    							rs.getString("LOGIN"),
			    							rs.getString("PASSWORD"),
			    							rs.getString("DATE_OF_BIRTH"),
			    							"<html>̳���: " + rs1.getString("TOWN") + ";<br>" +
			    							"�����: " + rs1.getString("DISTRICT") + ";<br>" +
			    							"����� ���������� ��������: " + rs1.getString("NUMBER_CERTIFICATE") + "</html>",
			    							rs.getString("IDCODE"),
			    							rs.getString("PASSPORT"),
			    							"<html>̳���: " + rs2.getString("TOWN") + ";<br>" +
			    							"����������: " + rs2.getString("UNIVERSITY") + ";<br>" +
			    							"���������: " + rs2.getString("FACULTY") +
			    							"; ��������: " + rs2.getString("COURSE") +
			    							"; ����: " + rs2.getString("COURSE_NUMBER") + "</html>",
			    							rs.getString("PHONE_NUMBER")});
			        				} else {
			        					model.addRow(new Object[] {
			        							rs.getString("NAME"),
			        							rs.getString("SURNAME"),
			        							rs.getString("LOGIN"),
			        							rs.getString("PASSWORD"),
			        							rs.getString("DATE_OF_BIRTH"),
			        							"<html>̳���: " + rs1.getString("TOWN") + ";<br>" +
			        							"�����: " + rs1.getString("DISTRICT") + ";<br>" +
			        							"����� ���������� ��������: " + rs1.getString("NUMBER_CERTIFICATE") + "</html>",
			        							rs.getString("IDCODE"),
			        							rs.getString("PASSPORT"),
			        							"<html>̳���: " + rs2.getString("TOWN") + ";<br>" +
			        							"����������: " + rs2.getString("UNIVERSITY") + ";<br>" +
			        							"���������: " + rs2.getString("FACULTY") +
			        							"; ��������: " + rs2.getString("COURSE") +
			        							"; ����: " + rs2.getString("COURSE_NUMBER") + "</html>",
			        							rs.getString("PHONE_NUMBER")});	
			        				}
			        				}
			    					rs2.close();
			        				pst2.close();
			    				}
			    				rs1.close();
			    				pst1.close();
			    				}
			    		}
			    		rs.close();
						pst.close();
			    	} catch (SQLException | HeadlessException arg0) {
			    		JOptionPane.showMessageDialog(null, arg0);
			    	}*/
					}
				if (updateTableButtonFunction == 1) {
				/*	System.out.println("������");
					DefaultTableModel model = (DefaultTableModel) studentsMarksTable.getModel();
					for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
				        model.removeRow(i);
				    }
					try {
			    		String sql = "SELECT * FROM USER_MARKS";
			    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			    		rs = (OracleResultSet) pst.executeQuery();
			    		while (rs.next()) {
			    			if (rs.getString("SHOW").equals("1")) {
			    			model.addRow(new Object[] {
			    				rs.getString("STUDENT"),
			    				rs.getString("UNIVERSITY"),
			    				rs.getString("NUMBER_COURSE"),
			    				rs.getString("SEMESTER"),
			    				rs.getString("SUBJECT"),
			    				rs.getString("MARK"),
			    				rs.getString("LOGIN")});
			    			}
			    		}
			    		rs.close();
						pst.close();
			    	} catch (SQLException | HeadlessException arg0) {
			    		JOptionPane.showMessageDialog(null, arg0);
			    	}*/
					}
				if (updateTableButtonFunction == 2) {
				/*	System.out.println("������");
					DefaultTableModel model = (DefaultTableModel) studentsMedicalTable.getModel();
					for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
						model.removeRow(i);
					}
					try {
		    		String sql = "SELECT * FROM USER_MEDICAL_EXAMINATION";
		    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
		    		rs = (OracleResultSet) pst.executeQuery();
		    		while (rs.next()) {
		    			if (rs.getString("SHOW").equals("1")) {
		    			model.addRow(new Object[] {
		    				rs.getString("STUDENT"),
		    				rs.getString("MILITARY_DISTRICT"),
		    				rs.getString("DOCTOR"),
		    				rs.getString("READY"),
		    				rs.getString("REASON"),
		    				rs.getString("LOGIN")});
		    			}
		    		}
		    		rs.close();
					pst.close();
		    	} catch (SQLException | HeadlessException arg0) {
		    		JOptionPane.showMessageDialog(null, arg0);
		    	}*/
				}
				if (updateTableButtonFunction == 3) {
					/*
					System.out.println("�����");
					DefaultTableModel model = (DefaultTableModel) studentsExamTable.getModel();
					for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
						model.removeRow(i);
					}
					try {
						String sql = "SELECT * FROM USER_MILITARY_DEPARTMENT_EXAM";
			    		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
			    		rs = (OracleResultSet) pst.executeQuery();
			    		while (rs.next()) {
			    				if (rs.getString("READY").toString().equals("���")) {ready = true;} else {ready = false;}
			        			model.addRow(new Object[] {
			        				rs.getString("STUDENT"),
			        				rs.getString("MILITARY_DEPARTMENT"),
									rs.getString("SUBJECT"),
									ready,
									rs.getString("DESCRIPTION"),
									rs.getString("LOGIN")});
			    				}
			    		rs.close();
						pst.close();
			    	} catch (SQLException | HeadlessException arg0) {
			    		JOptionPane.showMessageDialog(null, arg0);
			    	}*/
				}
			}
		});
		updateTableButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		updateTableButton.setBounds(286, 537, 224, 56);
		adminWindowFrame.getContentPane().add(updateTableButton);
		adminWindowFrame.setTitle("Admin Window - User's Documents Application - v.0");
		adminWindowFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				AskingExit.main(null);
			}
		});
		adminWindowFrame.setBounds(100, 100, 810, 630);
		adminWindowFrame.setLocationRelativeTo(null);
	}
	public void setName(String name) {
	    this.name = name;
	}
	public static String getName() {
		return name;
	}
	public void setMilitaryDepartment(String militaryDepartment) {
	    this.militaryDepartment = militaryDepartment;
	}
	public static String getMilitaryDepartment() {
		return militaryDepartment;
	}
	public void setUniversity(String university) {
	    this.university = university;
	}
	public static String getUniversity() {
		return university;
	}
	public void setLogin(String login) {
	    this.login = login;
	}
	public static String getLogin() {
		return login;
	}
	public void setRegister(Boolean register) {
	    this.register = register;
	}
	public static Boolean getRegister() {
		return register;
	}
	private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {
		Registration registrationForm = new Registration();
		registrationForm.main(null);
    }
}
