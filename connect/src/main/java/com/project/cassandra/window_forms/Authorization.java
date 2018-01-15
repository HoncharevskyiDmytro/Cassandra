package com.project.cassandra.window_forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
//import java.awt.HeadlessException;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.project.cassandra.window_forms.AskingExit;
import com.project.cassandra.connect.CassandraInitialConnection;
import com.project.cassandra.connect.JTextFieldLimit;
import com.project.cassandra.connect.Program_Beginning;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

//import javax.swing.UIManager;
import java.awt.SystemColor;

public class Authorization{

	private JFrame authorizationWindow;
	private JLabel loginLabel;
	private JLabel passwordLabel;
	private JTextField loginField;
	private JPasswordField passwordField;
	private static Boolean register = false;
	private static String login;
	private JButton btnRegister;
	private JButton btnSubmit;

	private List<JTextField> fieldList = new ArrayList<JTextField>();
	private JLabel messageLabel;
	
	Cluster cluster = CassandraInitialConnection.getClusterConnection(Program_Beginning.SERVER_IP);
	Session session = CassandraInitialConnection.getKeyspaceConnection(cluster, Program_Beginning.PROGRAM_KEYSPACE);
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
//	Session session = Program_Beginning.getSession();
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
	private void checkFieldsFull()
	  {
	    for (JTextField field : fieldList)
	    {
	      if (field.getText().trim().isEmpty())
	      {
	    	  btnSubmit.setEnabled(false);
	    	  return;
	      }
	    }
	    btnSubmit.setEnabled(true);
	  }
	private class MyDocListener implements DocumentListener
	  {
	    public void changedUpdate(DocumentEvent e)
	    {
	      checkFieldsFull();
	    }
	    public void insertUpdate(DocumentEvent e)
	    {
	      checkFieldsFull();
	    }
	    public void removeUpdate(DocumentEvent e)
	    {
	      checkFieldsFull();
	    }
	  }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Authorization window = new Authorization();
					window.authorizationWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Authorization() {
		setRegister(Registration.getRegister());
		setRegister(false);
		initialize();
		fieldList.add(passwordField);
		fieldList.add(loginField);
		messageLabel = new JLabel();
		messageLabel.setForeground(new Color(0, 0, 0));
		messageLabel.setText("<html>Authorization into User's Documents Application</html>");
		messageLabel.setFont(new Font("Times New Roman", Font.ITALIC, 24));
		messageLabel.setHorizontalAlignment(JLabel.CENTER);
		messageLabel.setBounds(12, 15, 503, 50);
		authorizationWindow.getContentPane().add(messageLabel);
	    DocumentListener myDocListener = new MyDocListener();
	    for (JTextField field : fieldList)
	    {
	      field.getDocument().addDocumentListener(myDocListener);
	    }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		authorizationWindow = new JFrame();
		authorizationWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				AskingExit.main(null);
			}
		});
		authorizationWindow.getContentPane().setForeground(new Color(0, 0, 0));
		authorizationWindow.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 20));
		authorizationWindow.setResizable(false);
		authorizationWindow.setTitle("User's Documents Application - v.0");
		authorizationWindow.getContentPane().setBackground(SystemColor.control);
		authorizationWindow.getContentPane().setLayout(null);
		
		loginLabel = new JLabel("Login:");
		loginLabel.setBackground(Color.WHITE);
		loginLabel.setForeground(new Color(0, 0, 0));
		loginLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		loginLabel.setBounds(12, 76, 75, 36);
		authorizationWindow.getContentPane().add(loginLabel);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(Color.BLACK);
		passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		passwordLabel.setBackground(new Color(240, 240, 240));
		passwordLabel.setBounds(12, 123, 118, 36);
		authorizationWindow.getContentPane().add(passwordLabel);
		
		loginField = new JTextField();
		loginField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				loginField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				loginField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
			}
		});
		loginField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				restrictSpace(arg0);
				char c = arg0.getKeyChar();
		        if (!(Character.isAlphabetic(c) || Character.isDigit(c)) )  
		        {
		        	arg0.consume();  
		        }
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_DOWN)
			    {
					passwordField.requestFocusInWindow();
			    }
			}
		});
		loginField.setForeground(new Color(0, 0, 0));
		loginField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		loginField.setBounds(140, 77, 375, 36);
		loginField.setDocument(new JTextFieldLimit(16));
		authorizationWindow.getContentPane().add(loginField);
		loginField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordField.setBorder(new LineBorder(new Color(0, 0, 128), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				passwordField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
			}
		});
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				restrictSpace(e);
				char c = e.getKeyChar();
		        if (!(Character.isAlphabetic(c) || Character.isDigit(c)) )  
		        {
		            e.consume();  
		        }
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_UP)
			    {
					loginField.requestFocusInWindow();
			    }
			}
		});
		passwordField.setForeground(new Color(0, 0, 0));
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		passwordField.setBounds(140, 124, 375, 36);
		passwordField.setDocument(new JTextFieldLimit(16));
		authorizationWindow.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setRegister(true);
				btnRegisterActionPerformed(arg0);
				authorizationWindow.setVisible(false);
			}
		});
		btnRegister.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		btnRegister.setBounds(279, 177, 235, 50);
		authorizationWindow.getContentPane().add(btnRegister);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBackground(new Color(240, 240, 240));
		btnSubmit.setEnabled(false);
		btnSubmit.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		btnSubmit.setBounds(12, 177, 140, 50);
		authorizationWindow.getContentPane().add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	boolean login_existance_flag = false;
            	
            	try {
            		
            		/*
            		 * SELECT for previous DB structure
            		String cqlStatement = "CREATE INDEX IF NOT EXISTS user_search ON \"Documents_by_Users\" (FULL(user_login));";
            	    session.execute(cqlStatement);	
            		
            	    cqlStatement = "CREATE INDEX IF NOT EXISTS password_search on \"Documents_by_Users\" (user_password);";
            	    session.execute(cqlStatement);
            				 
            		cqlStatement = "SELECT document_id FROM \"Documents_by_Users\" " + 
            				"WHERE user_login CONTAINS '" + loginField.getText() + "'" +
            				"AND user_password CONTAINS '" + passwordField.getText() + "' ALLOW FILTERING;";
            		*/
            				 
            		String cqlStatement = "SELECT user_password FROM \"Users_by_Documents\" " + 
            				"WHERE user_login = '" + loginField.getText() + "';";
            		        		
            		/*
            		// TO DO SELECT with query builder without batch
            		
           // 		Statement builderStatement = QueryBuilder.select("document_id").from(Program_Beginning.PROGRAM_KEYSPACE,"\"Documents_by_Users\"")
           // 				.where(QueryBuilder.eq("user_login[0]","{'" + loginField.getText() + "'}"))
           // 				.and(QueryBuilder.eq("user_password[0]","['" + passwordField.getText() + "']"));

           // 		ResultSet result = session.execute(builderStatement);
		   //         for (Row row : result) {
			//        	login_existance_flag = true;
			//        };         		
			        
			        // It should be working, but I don't have confidence in it 
            		
            		// UPD It is definitely not working, but I have done my best
			        
            		*/  
            		
            		for (Row row : session.execute(cqlStatement)) {
			            if(row.toString().equals("Row[" + passwordField.getText() + "]")) {
			            	login_existance_flag = true;
			            }
			        }
            		
            		/*
            		 * Was for previous DB structure
			        for (Row row : session.execute(cqlStatement)) {
			        	login_existance_flag = true;
			            if(!row.toString().equals("")) {

			            }
			        }
			        */
			        
			        if (login_existance_flag){
			        	
			        	setLogin(loginField.getText().toString());
	            		UserWindow.main(null);
	                	authorizationWindow.setVisible(false);
	                	
	            	} else {
	            		
        				JFrame frame = new JFrame("JOptionPane showMessageDialog example");
        				JOptionPane.showMessageDialog(frame, "Incorrect User of Password");
        				
        			}
			        
            	} catch (Exception arg0) {
            		JOptionPane.showMessageDialog(null, arg0);
            	}
            	
            }
        });

		authorizationWindow.getRootPane().setDefaultButton(btnSubmit);
		btnSubmit.requestFocus();
		authorizationWindow.setBounds(100, 100, 542, 265);
		authorizationWindow.setLocationRelativeTo(null);
		authorizationWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {
		Registration registrationForm = new Registration();
		registrationForm.main(null);
    }
	public void restrictSpace(KeyEvent arg0) {
		char c = arg0.getKeyChar();
        if (c==KeyEvent.VK_SPACE)
        {
        	arg0.consume();
        }
	}
	public void setRegister(Boolean register) {
	    this.register = register;
	}
	public static Boolean getRegister() {
		return register;
	}
	public void setLogin(String login) {
	    this.login = login;
	}
	public static String getLogin() {
		return login;
	}
}
