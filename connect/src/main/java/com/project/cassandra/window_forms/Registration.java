package com.project.cassandra.window_forms;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.ComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.project.cassandra.window_forms.AskingExit;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.project.cassandra.connect.CassandraInitialConnection;
import com.project.cassandra.connect.JTextFieldLimit;
import com.project.cassandra.connect.Program_Beginning;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

public class Registration {

	public static final String WARNING_HOLLOW_FIELD = "** - field cannot be hollow";
	public static int counter = 1;
	private JFrame registrationWindow;
	private JLabel nameLabel;
	private JLabel surnameLabel;
	private JLabel lblDateOfBirth;
	private JLabel phoneLabel;
	private ComboBoxModel String;
	private JComboBox monthOfBirthComboBox;
	private JComboBox emailDomainComboBox;
	private JTextField nameField;
	private JLabel loginLabel;
	private JLabel passwordLabel;
	private JLabel passwordConfirmationLabel;
	private JTextField surnameField;
	private JTextField loginField;
	private JPasswordField passwordField;
	private JPasswordField passwordConfirmationField;
	private JSpinner dayOfBirthField;
	private JSpinner yearOfBirthField;
	private JTextField emailAddressTextField;
	private JLabel inputMessageLabel;
	private JButton registerButton;
	private JLabel nameWarningLabel;
	private JLabel surnameWarningLabel;
	private JLabel loginWarningLabel;
	private JLabel passwordWarningLabel;
	private JLabel passwordConfirmationWarningLabel;
	boolean arg1 = false, arg2 = false, arg3 = false, arg4 = false, arg5 = false, fontchanger = true;
	private JLabel messageLabel;
	private JButton returnButton;
	private JButton clearButton;
	int month = 0;
	String temporary_town = "", temporary_district_certificate = "", temporary_university = "", temporary_faculty = "", temporary_course = "";
	
	Cluster cluster = CassandraInitialConnection.getClusterConnection(Program_Beginning.SERVER_IP);
	Session session = CassandraInitialConnection.getKeyspaceConnection(cluster, Program_Beginning.PROGRAM_KEYSPACE);
		
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
//	Session session = Program_Beginning.getSession();
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	JFrame frame = new JFrame("JOptionPane showMessageDialog example");
	private static Boolean register;
	private String verification, editable;
	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registration window = new Registration();
					window.registrationWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Registration() {
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		System.out.println(Authorization.getRegister());
//		System.out.println(AdminWindow.getRegister());
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes" })
	private void initialize() {
		registrationWindow = new JFrame();
		registrationWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				AskingExit.main(null);
			}
		});
		registrationWindow.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 24));
		registrationWindow.setResizable(false);
		registrationWindow.getContentPane().setBackground(SystemColor.control);
		registrationWindow.getContentPane().setLayout(null);
		
		surnameLabel = new JLabel("*Last Name:");
		surnameLabel.setForeground(new Color(0, 0, 0));
		surnameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		surnameLabel.setBounds(33, 112, 130, 28);
		registrationWindow.getContentPane().add(surnameLabel);
		
		nameLabel = new JLabel("*First Name:");
		nameLabel.setForeground(new Color(0, 0, 0));
		nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		nameLabel.setBounds(33, 74, 130, 28);
		registrationWindow.getContentPane().add(nameLabel);
		
		lblDateOfBirth = new JLabel("*Date of Birth:");
		lblDateOfBirth.setForeground(new Color(0, 0, 0));
		lblDateOfBirth.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblDateOfBirth.setBounds(33, 264, 205, 28);
		registrationWindow.getContentPane().add(lblDateOfBirth);
		
		phoneLabel = new JLabel();
		phoneLabel.setForeground(new Color(0, 0, 0));
		phoneLabel.setText("Email:");
		phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		phoneLabel.setBounds(33, 303, 79, 28);
		registrationWindow.getContentPane().add(phoneLabel);
		
		nameField = new JTextField();
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				nameField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				nameField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
				if (nameField.getText().toString().equals("")) {
					nameWarningLabel.setText(WARNING_HOLLOW_FIELD);
					nameWarningLabel.setVisible(true); arg1 = false;}
				if ((!(nameField.getText().toString().equals(""))) && ((nameField.getText().toString().length()) < 2)) {
					nameWarningLabel.setText("** - first name should have > 1 letter");
					nameWarningLabel.setVisible(true); arg1 = false;}
				if ((!(nameField.getText().toString().equals(""))) && ((nameField.getText().toString().length()) >= 2)) {
					nameWarningLabel.setVisible(false); arg1 = true;}
				enableButton(arg1,arg2,arg3,arg4,arg5);
			}
		});
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (!(Character.isAlphabetic(c)))  
		        {
		        	arg0.consume();
		        }
			}
			@Override
			public void keyPressed(KeyEvent e) {
				restrictSpace(e);
				if (e.getKeyCode()==KeyEvent.VK_DOWN)
			    {
					surnameField.requestFocusInWindow();
			    }
				getFocusOnTextFieldAfterPressingEnter(surnameField, e);
			}
		});
		nameField.setToolTipText("First name consists of only letters");
		nameField.setForeground(Color.BLACK);
		nameField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		nameField.setColumns(10);
		nameField.setBounds(316, 74, 201, 28);
		nameField.setDocument(new JTextFieldLimit(16));
		nameField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		registrationWindow.getContentPane().add(nameField);
		
		loginLabel = new JLabel("*Login:");
		loginLabel.setForeground(Color.BLACK);
		loginLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		loginLabel.setBounds(33, 150, 90, 28);
		registrationWindow.getContentPane().add(loginLabel);
		
		passwordLabel = new JLabel("*Password:");
		passwordLabel.setForeground(Color.BLACK);
		passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		passwordLabel.setBounds(33, 188, 130, 28);
		registrationWindow.getContentPane().add(passwordLabel);
		
		passwordConfirmationLabel = new JLabel("*Password Confirmation:");
		passwordConfirmationLabel.setForeground(Color.BLACK);
		passwordConfirmationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		passwordConfirmationLabel.setBounds(33, 226, 258, 28);
		registrationWindow.getContentPane().add(passwordConfirmationLabel);
		
		surnameField = new JTextField();
		surnameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				restrictSpace(arg0);
				if (arg0.getKeyCode()==KeyEvent.VK_DOWN)
			    {
					loginField.requestFocusInWindow();
			    }
				if (arg0.getKeyCode()==KeyEvent.VK_UP)
			    {
					nameField.requestFocusInWindow();
			    }
				getFocusOnTextFieldAfterPressingEnter(loginField, arg0);
			}
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
		        if (!(Character.isAlphabetic(c)) & (c != '-'))
		        {
		        	e.consume();
		        }
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (Character.isAlphabetic(c))  
		        {
		        	Font font = new Font("Times New Roman", Font.PLAIN, 20);
		        	AffineTransform affinetransform = new AffineTransform();     
		        	FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		        	int textwidth = (int)(font.getStringBounds(surnameField.getText().toString(), frc).getWidth());
		        	if (textwidth > 201) {font = new Font("Times New Roman", Font.PLAIN, 18); surnameField.setFont(font);} else {font = new Font("Times New Roman", Font.PLAIN, 20); surnameField.setFont(font);}
		        }
			}
		});
		surnameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				surnameField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
				if (fontchanger == true) {
				surnameField.setFont(new Font("Times New Roman", Font.PLAIN, 20)); fontchanger = false;}
			}
			@Override
			public void focusLost(FocusEvent e) {
				surnameField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
				if (surnameField.getText().toString().equals("")) {
					surnameWarningLabel.setText(WARNING_HOLLOW_FIELD);
					surnameWarningLabel.setVisible(true); arg2 = false;}
				if ((!(surnameField.getText().toString().equals(""))) && ((surnameField.getText().toString().length()) < 3)) {
					surnameWarningLabel.setText("** - last name should have > 1 letter");
					surnameWarningLabel.setVisible(true); arg2 = false;}
				if ((!(surnameField.getText().toString().equals(""))) && ((surnameField.getText().toString().length()) >= 3)) {
					surnameWarningLabel.setVisible(false); arg2 = true;}
				enableButton(arg1,arg2,arg3,arg4,arg5);
			}
		});
		surnameField.setToolTipText("Last name consists of only letters");
		surnameField.setForeground(Color.BLACK);
		surnameField.setColumns(10);
		surnameField.setBounds(316, 112, 201, 28);
		surnameField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		surnameField.setDocument(new JTextFieldLimit(16));
		registrationWindow.getContentPane().add(surnameField);
		
		loginField = new JTextField();
		loginField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_UP)
			    {
					surnameField.requestFocusInWindow();
			    }
				if (e.getKeyCode()==KeyEvent.VK_DOWN)
			    {
					passwordField.requestFocusInWindow();
			    }
				getFocusOnTextFieldAfterPressingEnter(passwordField, e);
			}
			@Override
			public void keyTyped(KeyEvent e) {
				restrictSpace(e);
				char c = e.getKeyChar();
		        if (!(Character.isAlphabetic(c) || Character.isDigit(c)))  
		        {
		            e.consume();  
		        }
			}
		});
		loginField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				loginField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				loginField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
				if (loginField.getText().toString().equals("")) {
					loginWarningLabel.setText(WARNING_HOLLOW_FIELD);
					loginWarningLabel.setVisible(true); arg3 = false;}
				if ((!(loginField.getText().toString().equals(""))) && ((loginField.getText().toString().length()) < 5)) {
					loginWarningLabel.setText("** - login should have > 4 symbols");
					loginWarningLabel.setVisible(true); arg3 = false;}
				if ((!(loginField.getText().toString().equals(""))) && ((loginField.getText().toString().length()) >= 5)) {
					
					//find logins in Documents_by_Users cluster key table
					
					loginWarningLabel.setVisible(false); arg3 = true;				
					
					
	  /*          	try {
	            		String sql = "SELECT * FROM USERS where login=?";
	            		pst = (OraclePreparedStatement) conn.prepareStatement(sql);
	            		pst.setString(1, loginField.getText());
	            		rs = (OracleResultSet) pst.executeQuery();
	            		if (rs.next()) {
	            			if (rs.getString("login").equals("admin")) {
	            				loginWarningLabel.setText("** - ����� ���� ������������");
	        					loginWarningLabel.setVisible(true); arg3 = false;
	            			}  else {
	            				loginWarningLabel.setText("** - ����� ���� ����");
	        					loginWarningLabel.setVisible(true); arg3 = false;
	            			}
	            			} else {
	            				loginWarningLabel.setText("bla");
	            				loginWarningLabel.setVisible(false); arg3 = true;
	            			}		
	            		} catch (SQLException | HeadlessException arg0) {
	            			JOptionPane.showMessageDialog(null, arg0);
	            		}
	            		*/
			}
				enableButton(arg1,arg2,arg3,arg4,arg5);
			}
		});
		loginField.setToolTipText("");
		loginField.setForeground(Color.BLACK);
		loginField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		loginField.setColumns(10);
		loginField.setBounds(316, 150, 201, 28);
		loginField.setDocument(new JTextFieldLimit(16));
		loginField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		registrationWindow.getContentPane().add(loginField);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_UP)
			    {
					loginField.requestFocusInWindow();
			    }
				if (e.getKeyCode()==KeyEvent.VK_DOWN)
			    {
					passwordConfirmationField.requestFocusInWindow();
			    }
				getFocusOnTextFieldAfterPressingEnter(passwordConfirmationField, e);
			}
			@Override
			public void keyTyped(KeyEvent e) {
				restrictSpace(e);
				char c = e.getKeyChar();
		        if (!(Character.isAlphabetic(c) || Character.isDigit(c)) )  
		        {
		            e.consume();  
		        } 
			}
		});
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				passwordField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
				if (!(passwordConfirmationField.getText().toString().equals(passwordField.getText().toString()))) {
					passwordConfirmationWarningLabel.setText("** - passwords don't match");
					passwordConfirmationWarningLabel.setVisible(true); arg4 = false;
				}
				if ((!(passwordField.getText().toString().equals(""))) & ((passwordField.getText().toString().length()) < 7)) {
					passwordWarningLabel.setText("** - password should have > 6 symbols");
					passwordWarningLabel.setVisible(true); arg4 = false;}
				if ((!(passwordField.getText().toString().equals(""))) & ((passwordField.getText().toString().length()) >= 7)) {
					passwordWarningLabel.setVisible(false); arg4 = true;
					}
				if (passwordField.getText().toString().equals("")) {
					passwordWarningLabel.setText(WARNING_HOLLOW_FIELD);
					passwordWarningLabel.setVisible(true); arg4 = false;}
				enableButton(arg1,arg2,arg3,arg4,arg5);
			}
		});
		passwordField.setBounds(316, 188, 201, 28);
		passwordField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		passwordField.setDocument(new JTextFieldLimit(16));
		registrationWindow.getContentPane().add(passwordField);
		
		passwordConfirmationField = new JPasswordField();
		passwordConfirmationField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_UP)
			    {
					passwordField.requestFocusInWindow();
			    }
				if (e.getKeyCode()==KeyEvent.VK_DOWN)
			    {
					dayOfBirthField.requestFocusInWindow();
			    }
				if (e.getKeyCode()==KeyEvent.VK_ENTER)
			    {
					dayOfBirthField.requestFocusInWindow();
			    }
			}
			@Override
			public void keyTyped(KeyEvent e) {
				restrictSpace(e);
				char c = e.getKeyChar();
		        if (!(Character.isAlphabetic(c) || Character.isDigit(c)) )  
		        {
		            e.consume();  
		        }
			}
		});
		passwordConfirmationField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordConfirmationField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				passwordConfirmationField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
				if ((!(passwordField.getText().toString().equals(passwordConfirmationField.getText().toString()))) & (!(passwordConfirmationField.getText().toString().equals(""))) & ((passwordConfirmationField.getText().toString().length()) >= 7)) {
					passwordConfirmationWarningLabel.setText("** - passwords don't match");
					passwordConfirmationWarningLabel.setVisible(true); arg5 = false;
				}
				if ((!(passwordConfirmationField.getText().toString().equals(""))) & ((passwordConfirmationField.getText().toString().length()) < 7)) {
					passwordConfirmationWarningLabel.setText("** - password should have > 6 symbols");
					passwordConfirmationWarningLabel.setVisible(true); arg5 = false;}
				if (passwordConfirmationField.getText().toString().equals("")) {
					passwordConfirmationWarningLabel.setText(WARNING_HOLLOW_FIELD);
					passwordConfirmationWarningLabel.setVisible(true); arg5 = false;}
				if (passwordField.getText().toString().equals(passwordConfirmationField.getText().toString()) & (!(passwordConfirmationField.getText().toString().equals(""))) & ((passwordConfirmationField.getText().toString().length()) >= 7)) {
					passwordConfirmationWarningLabel.setVisible(false); arg5 = true;}
				enableButton(arg1,arg2,arg3,arg4,arg5);
			}
		});
		passwordConfirmationField.setBounds(316, 226, 201, 28);
		passwordConfirmationField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		passwordConfirmationField.setDocument(new JTextFieldLimit(16));
		registrationWindow.getContentPane().add(passwordConfirmationField);
		
		dayOfBirthField = new JSpinner();
		dayOfBirthField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				dayOfBirthField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				dayOfBirthField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
			}
		});
		dayOfBirthField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				getFocusOnComboBoxAfterPressingEnter(monthOfBirthComboBox, arg0);
				if (arg0.getKeyCode()==KeyEvent.VK_LEFT)
			    {
					passwordConfirmationField.requestFocusInWindow();
			    }
				if (arg0.getKeyCode()==KeyEvent.VK_RIGHT)
			    {
					monthOfBirthComboBox.requestFocusInWindow();
			    }
			}
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
		        if (!(Character.isDigit(c)))  
		        {
		        	e.consume();  
		        }
			}
		});
		dayOfBirthField.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		dayOfBirthField.setToolTipText("\u0414\u0435\u043D\u044C \u043D\u0430\u0440\u043E\u0434\u0436\u0435\u043D\u043D\u044F");
		dayOfBirthField.setForeground(Color.BLACK);
		dayOfBirthField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		dayOfBirthField.setBounds(316, 264, 43, 28);
		dayOfBirthField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		registrationWindow.getContentPane().add(dayOfBirthField);
		
		monthOfBirthComboBox = new JComboBox();
		monthOfBirthComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				restrictSpace(arg0);
					if (arg0.getKeyCode()==KeyEvent.VK_LEFT)
			        {
						dayOfBirthField.requestFocusInWindow();
			        }
					if (arg0.getKeyCode()==KeyEvent.VK_RIGHT)
			        {
						yearOfBirthField.requestFocusInWindow();
			        }
					if (arg0.getKeyCode()==KeyEvent.VK_ENTER)
			        {
						yearOfBirthField.requestFocusInWindow();
			        }
			}
		});
		monthOfBirthComboBox.setMaximumRowCount(5);
		monthOfBirthComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		monthOfBirthComboBox.setBounds(376, 264, 141, 28);
		monthOfBirthComboBox.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		addItemToComboBox(monthOfBirthComboBox, "January");
		addItemToComboBox(monthOfBirthComboBox, "February");
		addItemToComboBox(monthOfBirthComboBox, "March");
		addItemToComboBox(monthOfBirthComboBox, "April");
		addItemToComboBox(monthOfBirthComboBox, "May");
		addItemToComboBox(monthOfBirthComboBox, "June");
		addItemToComboBox(monthOfBirthComboBox, "July");
		addItemToComboBox(monthOfBirthComboBox, "August");
		addItemToComboBox(monthOfBirthComboBox, "September");
		addItemToComboBox(monthOfBirthComboBox, "October");
		addItemToComboBox(monthOfBirthComboBox, "November");
		addItemToComboBox(monthOfBirthComboBox, "December");
		registrationWindow.getContentPane().add(monthOfBirthComboBox);
		
		yearOfBirthField = new JSpinner();
		yearOfBirthField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				yearOfBirthField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				yearOfBirthField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
			}
		});
		yearOfBirthField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
		        if (!(Character.isDigit(c)))  
		        {
		        	e.consume();
		        }
			}
			@Override
			public void keyPressed(KeyEvent e) {
				getFocusOnTextFieldAfterPressingEnter(emailAddressTextField, e);
				if (e.getKeyCode()==KeyEvent.VK_LEFT)
			    {
					monthOfBirthComboBox.requestFocusInWindow();
			    }
				if (e.getKeyCode()==KeyEvent.VK_RIGHT)
			    {
					emailAddressTextField.requestFocusInWindow();
			    }
			}
		});
		yearOfBirthField.setModel(new SpinnerNumberModel(1996, 1940, 2002, 1));
		yearOfBirthField.setToolTipText("\u0420\u0456\u043A \u043D\u0430\u0440\u043E\u0434\u0436\u0435\u043D\u043D\u044F");
		yearOfBirthField.setForeground(Color.BLACK);
		yearOfBirthField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		yearOfBirthField.setBounds(534, 264, 63, 28);
		yearOfBirthField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		registrationWindow.getContentPane().add(yearOfBirthField);
		
		emailDomainComboBox = new JComboBox();
		emailDomainComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_LEFT)
		        {
					emailAddressTextField.requestFocusInWindow();
		        }
				if (e.getKeyCode()==KeyEvent.VK_RIGHT)
		        {
					registerButton.requestFocusInWindow();
		        }
				if (e.getKeyCode()==KeyEvent.VK_ENTER)
		        {
					registerButton.requestFocusInWindow();
		        }
			}
		});
		emailDomainComboBox.setMaximumRowCount(5);
		emailDomainComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		emailDomainComboBox.setBounds(534, 303, 165, 28);
		emailDomainComboBox.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		addItemToComboBox(emailDomainComboBox, "@gmail.com");
		addItemToComboBox(emailDomainComboBox, "@ukr.net");
		addItemToComboBox(emailDomainComboBox, "@i.ua");
		registrationWindow.getContentPane().add(emailDomainComboBox);
		
		emailAddressTextField = new JTextField();
		emailAddressTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				restrictSpace(e);
				if (e.getKeyCode()==KeyEvent.VK_UP)
			    {
					yearOfBirthField.requestFocusInWindow();
			    }
				if (e.getKeyCode()==KeyEvent.VK_DOWN)
			    {
					emailDomainComboBox.requestFocusInWindow();
			    }
				if (e.getKeyCode()==KeyEvent.VK_ENTER)
			    {
					emailDomainComboBox.requestFocusInWindow();
			    }
			}
		});
	
		emailAddressTextField.setForeground(Color.BLACK);
		emailAddressTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		emailAddressTextField.setColumns(10);
		emailAddressTextField.setBounds(316, 303, 201, 28);
		emailAddressTextField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		emailAddressTextField.setDocument(new JTextFieldLimit(15));
		registrationWindow.getContentPane().add(emailAddressTextField);
		
		inputMessageLabel = new JLabel();
		inputMessageLabel.setText("* - fields that have to be filled");
		inputMessageLabel.setForeground(Color.RED);
		inputMessageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		inputMessageLabel.setBounds(33, 339, 258, 28);
		registrationWindow.getContentPane().add(inputMessageLabel);
		
		registerButton = new JButton("Register");
		registerButton.setEnabled(false);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setRegister(false);
				if (Authorization.getRegister() == true) {
					verification = "1";
					editable = "2";
				}
				if (AdminWindow.getRegister() == true) {
					verification = "2";
					editable = "1";
				}
				if (monthOfBirthComboBox.getSelectedItem().toString().equals("January")) {month = 1;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("February")) {month = 2;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("March")) {month = 3;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("April")) {month = 4;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("May")) {month = 5;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("June")) {month = 6;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("July")) {month = 7;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("August")) {month = 8;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("September")) {month = 9;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("October")) {month = 10;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("November")) {month = 11;}
				else if (monthOfBirthComboBox.getSelectedItem().toString().equals("December")) {month = 12;}
				if (UserWindow.getUpdate() == true) {
					try {
						
						String document_index = "";
						String login = Authorization.getLogin();
						
						/*						
						String cqlStatement = "CREATE INDEX IF NOT EXISTS user_search ON \"Documents_by_Users\" (FULL(user_login));";
						session.execute(cqlStatement);
								
						cqlStatement = "SELECT document_id FROM \"Documents_by_Users\" WHERE user_login CONTAINS '" + login + "' ALLOW FILTERING;";
						
						for (Row row : session.execute(cqlStatement)) {

							document_index = row.toString().substring(5, 6);
			
				        }
						
						// UPDATE with cqlStatement with batch
						
						cqlStatement = "BEGIN BATCH " + 
								"UPDATE \"Users_by_Documents\" SET user_password = ['" +  passwordField.getText() + "']," +								
								"user_name = [{\"user_first_name\":'" + nameField.getText() + 
								"',\"user_last_name\":'" + surnameField.getText() + "'}]," + 
								"user_birthday = ['" + yearOfBirthField.getValue() + "-" + month + "-" + dayOfBirthField.getValue() + "']," + 
								"user_email = ['" + emailAddressTextField.getText() + emailDomainComboBox.getSelectedItem().toString() + "']" + 
				                "WHERE user_login = {'" + login + "'} AND document_id = {" + document_index + "};" + 
				                "UPDATE \"Documents_by_Users\" SET user_password = ['" +  passwordField.getText() + "']," +								
								"user_name = [{\"user_first_name\":'" + nameField.getText() + 
								"',\"user_last_name\":'" + surnameField.getText() + "'}]," + 
								"user_birthday = ['" + yearOfBirthField.getValue() + "-" + month + "-" + dayOfBirthField.getValue() + "']," + 
								"user_email = ['" + emailAddressTextField.getText() + emailDomainComboBox.getSelectedItem().toString() + "']" + 
				                "WHERE user_login = {'" + login + "'} AND document_id = {" + document_index + "};" + 
				                "APPLY BATCH";
				        */        
						
						System.out.println(login);
						
						String cqlStatement = "SELECT document_id FROM \"Documents_by_Users\" WHERE user_login = '" + login + "' ALLOW FILTERING;";
						
						for (Row row : session.execute(cqlStatement)) {

							document_index = row.toString().substring(4, 5);
			
				        }
						
						System.out.println(document_index);
						
						// UPDATE with cqlStatement with batch
						
						cqlStatement = "BEGIN BATCH " + 
								"UPDATE \"Users_by_Documents\" SET user_password = '" +  passwordField.getText() + "'," +								
								"user_name = {\"user_first_name\":'" + nameField.getText() + 
								"',\"user_last_name\":'" + surnameField.getText() + "'}," + 
								"user_birthday = '" + yearOfBirthField.getValue() + "-" + month + "-" + dayOfBirthField.getValue() + "'," + 
								"user_email = '" + emailAddressTextField.getText() + emailDomainComboBox.getSelectedItem().toString() + "'," + 
								"document_title = null, document_content = null, document_creation_date = null, document_author = null " + 
				                "WHERE user_login = '" + login + "' AND document_id = " + document_index + ";" +
				                "UPDATE \"Documents_by_Users\" SET user_password = '" +  passwordField.getText() + "'," +								
								"user_name = {\"user_first_name\":'" + nameField.getText() + 
								"',\"user_last_name\":'" + surnameField.getText() + "'}," + 
								"user_birthday = '" + yearOfBirthField.getValue() + "-" + month + "-" + dayOfBirthField.getValue() + "'," + 
								"user_email = '" + emailAddressTextField.getText() + emailDomainComboBox.getSelectedItem().toString() + "'," + 
								"document_title = null, document_content = null, document_creation_date = null, document_author = null " + 
				                "WHERE user_login = '" + login + "' AND document_id = " + document_index + ";" +  
				                "APPLY BATCH";
						
						session.execute(cqlStatement);
									
						// NOT TO DO UPDATE with query builder without batch
						
						if (UserWindow.getUpdate() == true) {
							JOptionPane.showMessageDialog(frame,"User updated successfully");
							UserWindow.main(null);
							registrationWindow.setVisible(false);
						}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e);
						}
				} else {
				try {
						

					/*
					 * INSERT for previous DB structure
					String cqlStatement = "BEGIN BATCH " + 
							"INSERT INTO \"Users_by_Documents\" (user_login, user_password, " +
							"user_name, user_birthday, user_email, document_id) " +
			                "VALUES ({'" + loginField.getText() + "'}, ['" + passwordField.getText() + "']," + 
			                "[{\"user_first_name\":'" + nameField.getText() + "',\"user_last_name\":'" + surnameField.getText() +
			                "'}],['" + yearOfBirthField.getValue() + "-" + month + "-" + dayOfBirthField.getValue() + "'],['" +
			                emailAddressTextField.getText() + emailDomainComboBox.getSelectedItem().toString() + "'],{" + counter +
			                "}" + ");" + 
			                "INSERT INTO \"Documents_by_Users\" (user_login, user_password, " +
							"user_name, user_birthday, user_email, document_id) " +
			                "VALUES ({'" + loginField.getText() + "'}, ['" + passwordField.getText() + "']," + 
			                "[{\"user_first_name\":'" + nameField.getText() + "',\"user_last_name\":'" + surnameField.getText() +
			                "'}],['" + yearOfBirthField.getValue() + "-" + month + "-" + dayOfBirthField.getValue() + "'],['" +
			                emailAddressTextField.getText() + emailDomainComboBox.getSelectedItem().toString() + "'],{" + counter +
			                "}" + ");" + 
			                "APPLY BATCH";
			        */
					
					String cqlStatement = "BEGIN BATCH " + 
							"INSERT INTO \"Users_by_Documents\" (user_login, user_password, " +
							"user_name, user_birthday, user_email, document_id) " +
			                "VALUES ('" + loginField.getText() + "', '" + passwordField.getText() + "'," + 
			                "{\"user_first_name\":'" + nameField.getText() + "',\"user_last_name\":'" + surnameField.getText() +
			                "'},'" + yearOfBirthField.getValue() + "-" + month + "-" + dayOfBirthField.getValue() + "','" +
			                emailAddressTextField.getText() + emailDomainComboBox.getSelectedItem().toString() + "'," + counter +
			                ");" + 
			                "INSERT INTO \"Documents_by_Users\" (user_login, user_password, " +
							"user_name, user_birthday, user_email, document_id) " +
							"VALUES ('" + loginField.getText() + "', '" + passwordField.getText() + "'," + 
			                "{\"user_first_name\":'" + nameField.getText() + "',\"user_last_name\":'" + surnameField.getText() +
			                "'},'" + yearOfBirthField.getValue() + "-" + month + "-" + dayOfBirthField.getValue() + "','" +
			                emailAddressTextField.getText() + emailDomainComboBox.getSelectedItem().toString() + "'," + counter +
			                ");" + 
			                "APPLY BATCH";
							
			        session.execute(cqlStatement);
			           
			     // NOT TO DO INSERT with query builder without batch 
			        			
					if (Authorization.getRegister() == true) {
						JOptionPane.showMessageDialog(frame,"New User Added Successfully!");
						Authorization.main(null);
						registrationWindow.setVisible(false);
					}
				//	if (AdminWindow.getRegister() == true) {
				//		JOptionPane.showMessageDialog(frame,"New User Added Successfully!");
				//		AdminWindow.main(null);
				//		registrationWindow.setVisible(false);
				//	}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					} 
				}
			}
		});
		registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		registerButton.setBounds(33, 377, 200, 50);
		registrationWindow.getContentPane().add(registerButton);
		
		nameWarningLabel = new JLabel();
		nameWarningLabel.setForeground(Color.RED);
		nameWarningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		nameWarningLabel.setBounds(534, 74, 279, 28);
		nameWarningLabel.setVisible(false);
		registrationWindow.getContentPane().add(nameWarningLabel);
		
		surnameWarningLabel = new JLabel();
		surnameWarningLabel.setText(WARNING_HOLLOW_FIELD);
		surnameWarningLabel.setForeground(Color.RED);
		surnameWarningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		surnameWarningLabel.setBounds(534, 112, 216, 28);
		surnameWarningLabel.setVisible(false);
		registrationWindow.getContentPane().add(surnameWarningLabel);
		
		loginWarningLabel = new JLabel();
		loginWarningLabel.setForeground(Color.RED);
		loginWarningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		loginWarningLabel.setBounds(534, 150, 279, 28);
		loginWarningLabel.setVisible(false);
		registrationWindow.getContentPane().add(loginWarningLabel);
		
		passwordWarningLabel = new JLabel();
		passwordWarningLabel.setForeground(Color.RED);
		passwordWarningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		passwordWarningLabel.setBounds(534, 188, 279, 28);
		passwordWarningLabel.setVisible(false);
		registrationWindow.getContentPane().add(passwordWarningLabel);
		
		passwordConfirmationWarningLabel = new JLabel();
		passwordConfirmationWarningLabel.setForeground(Color.RED);
		passwordConfirmationWarningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		passwordConfirmationWarningLabel.setBounds(534, 226, 279, 28);
		passwordConfirmationWarningLabel.setVisible(false);
		registrationWindow.getContentPane().add(passwordConfirmationWarningLabel);
		
		messageLabel = new JLabel("<html>New user registration in a system</html>");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(new Color(0, 0, 0));
		messageLabel.setFont(new Font("Times New Roman", Font.ITALIC, 24));
		messageLabel.setBounds(10, 22, 803, 37);
		registrationWindow.getContentPane().add(messageLabel);
		
		returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setRegister(false);
				if (Authorization.getRegister() == true) {
					Authorization.main(null);
					registrationWindow.setVisible(false);
				}
				if (AdminWindow.getRegister() == true) {
					AdminWindow.main(null);
					registrationWindow.setVisible(false);
				}
				if (UserWindow.getUpdate() == true) {
					UserWindow.main(null);
					registrationWindow.setVisible(false);
				}
			}
		});
		returnButton.setForeground(Color.BLACK);
		returnButton.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		returnButton.setEnabled(true);
		returnButton.setBounds(596, 377, 200, 50);
		registrationWindow.getContentPane().add(returnButton);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nameWarningLabel.setText("");
				nameField.setText("");
				surnameField.setText("");
				loginField.setText("");
				passwordField.setText("");
				passwordConfirmationField.setText("");
				emailAddressTextField.setText("");
				surnameWarningLabel.setText("");
				loginWarningLabel.setText("");
				passwordWarningLabel.setText("");
				passwordConfirmationWarningLabel.setText("");
				arg1 = false; arg2 = false; arg3 = false; arg4 = false; arg5 = false; fontchanger = true;
				registerButton.setEnabled(false);
			}
		});
		clearButton.setForeground(Color.BLACK);
		clearButton.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		clearButton.setEnabled(true);
		clearButton.setBounds(317, 377, 200, 50);
		registrationWindow.getContentPane().add(clearButton);
		if (UserWindow.getUpdate() == true) {
			loginField.setVisible(false); arg3 = true;
			System.out.println(Authorization.getLogin());
		} else {loginField.setVisible(true); arg3 = false;}
		registrationWindow.setTitle("Registration - User's Documents Application - v.0");
		registrationWindow.setBounds(100, 100, 837, 479);
		registrationWindow.setLocationRelativeTo(null);
		registrationWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	public void addItemToComboBox(JComboBox arg0, String s) {
		arg0.addItem(s);
	}
	public void getFocusOnComboBoxAfterPressingEnter(JComboBox arg1, KeyEvent arg0) {
		if (arg0.getKeyCode()==KeyEvent.VK_ENTER)
        {
			arg1.requestFocusInWindow();
        }
	}
	public void getFocusOnTextFieldAfterPressingEnter(JTextField arg1, KeyEvent arg0) {
		if (arg0.getKeyCode()==KeyEvent.VK_ENTER)
        {
			arg1.requestFocusInWindow();
        }
	}
	public void restrictSpace(KeyEvent arg0) {
		char c = arg0.getKeyChar();
        if (c==KeyEvent.VK_SPACE)
        {
        	arg0.consume();
        }
	}
	public void enableButton(boolean arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4) {
        if ((arg0 && arg2 && arg3 && arg4 && arg1) == true)
        {
        	registerButton.setEnabled(true);
        }
        if (!((arg0 && arg2 && arg3 && arg4 && arg1) == true))
        {
        	registerButton.setEnabled(false);
        }
	}
	public static String wrapString(String string, int charWrap) {
	    int lastBreak = 0;
	    int nextBreak = charWrap;
	    if (string.length() > charWrap) {
	        String setString = "";
	        do {
	            while (string.charAt(nextBreak) != ' ' && nextBreak > lastBreak) {
	                nextBreak--;
	            }
	            if (nextBreak == lastBreak) {
	                nextBreak = lastBreak + charWrap;
	            }
	            setString += string.substring(lastBreak, nextBreak).trim() + "\n";
	            lastBreak = nextBreak;
	            nextBreak += charWrap;

	        } while (nextBreak < string.length());
	        setString += string.substring(lastBreak).trim();
	        return setString;
	    } else {
	        return string;
	    }
	}
	public void setRegister(Boolean register) {
	    this.register = register;
	}
	public static Boolean getRegister() {
		return register;
	}
}