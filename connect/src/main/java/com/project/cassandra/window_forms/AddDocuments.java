package com.project.cassandra.window_forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.project.cassandra.window_forms.AskingExit;
import com.project.cassandra.connect.CassandraInitialConnection;
import com.project.cassandra.connect.JTextFieldLimit;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.project.cassandra.connect.Program_Beginning;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class AddDocuments {

	private JFrame addDocumentsFrame;
	private JTextField documentField;
	private JLabel documentLabel;
	private JLabel titleLabel;
	private JTextArea titleTextField;
	private JLabel contentLabel;
	private JTextArea contentTextField;
	int counter = 0;
	private JButton clearButton;
	private JButton returnButton;
	private JLabel whatToDoLabel;
	private JButton addAllButton;
	boolean ready = true;
	String commit_ready = "";
	String doc_id = "";
	
	Cluster cluster = CassandraInitialConnection.getClusterConnection(Program_Beginning.SERVER_IP);
	Session session = CassandraInitialConnection.getKeyspaceConnection(cluster, Program_Beginning.PROGRAM_KEYSPACE);
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
//	Session session = Program_Beginning.getSession();
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	JFrame frame = new JFrame("JOptionPane showMessageDialog example");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddDocuments window = new AddDocuments();
					window.addDocumentsFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddDocuments() {
//		conn = Javaconnect.ConnectionDb();
		initialize();
		((AbstractDocument) contentTextField.getDocument()).setDocumentFilter(new MyDocumentFilter(contentTextField, 7));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addDocumentsFrame = new JFrame();
		addDocumentsFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AskingExit.main(null);
			}
		});
		addDocumentsFrame.setResizable(false);
		addDocumentsFrame.setTitle("Add Documents - User's Documents Application - v.0");
		addDocumentsFrame.getContentPane().setBackground(SystemColor.control);
		addDocumentsFrame.getContentPane().setLayout(null);
		
		documentLabel = new JLabel("Document ID:");
		documentLabel.setForeground(new Color(0, 0, 0));
		documentLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		documentLabel.setBounds(12, 50, 152, 28);
		addDocumentsFrame.getContentPane().add(documentLabel);
		
		documentField = new JTextField();
		documentField.setHorizontalAlignment(SwingConstants.CENTER);
		documentField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		documentField.setEditable(false);
		documentField.setBounds(162, 50, 132, 28);
		addDocumentsFrame.getContentPane().add(documentField);
		documentField.setColumns(10);
		
		//To do
		
		try {
			
			String cqlStatement = "SELECT MAX(document_id) FROM \"Documents_by_Users\";";
			String document_index_not_trimmed;
			String[] document_index = new String[2];
			
			for (Row row : session.execute(cqlStatement)) {

				document_index_not_trimmed = row.toString().substring(4);
				document_index = document_index_not_trimmed.split("]");
	        }

			Integer doc = Integer.parseInt(document_index[0])  + 1;
			doc_id = doc.toString();
			documentField.setText(doc_id);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}	
		
		titleLabel = new JLabel("Document title:");
		titleLabel.setForeground(new Color(0, 0, 0));
		titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		titleLabel.setBounds(12, 81, 152, 28);
		addDocumentsFrame.getContentPane().add(titleLabel);
		
		titleTextField = new JTextArea();
		titleTextField.setColumns(2);
		titleTextField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		titleTextField.setBounds(12, 108, 420, 56);
		
		addDocumentsFrame.getContentPane().add(titleTextField);
		
		contentLabel = new JLabel("Document content:");
		contentLabel.setForeground(Color.BLACK);
		contentLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		contentLabel.setBounds(12, 167, 196, 28);
		addDocumentsFrame.getContentPane().add(contentLabel);
		
		contentTextField = new JTextArea();
		contentTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode()==KeyEvent.VK_UP)
		        {
					titleTextField.requestFocusInWindow();
		        }
			}
		});
		contentTextField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		contentTextField.setBounds(12, 200, 420, 330);
		contentTextField.setDocument(new JTextFieldLimit(500));
		addDocumentsFrame.getContentPane().add(contentTextField);
		contentTextField.setColumns(4);
		
		returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				documentField.setText("");
				UserWindow.main(null);
            	addDocumentsFrame.setVisible(false);
			}
		});
		returnButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		returnButton.setBounds(264, 580, 168, 28);
		addDocumentsFrame.getContentPane().add(returnButton);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentTextField.setText("");
				titleTextField.setText("");	
			}
		});
		clearButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		clearButton.setBounds(12, 580, 168, 28);
		addDocumentsFrame.getContentPane().add(clearButton);
		
		whatToDoLabel = new JLabel("<html>Adding documents here:</html>");
		whatToDoLabel.setForeground(new Color(0, 0, 0));
		whatToDoLabel.setFont(new Font("Times New Roman", Font.ITALIC, 24));
		whatToDoLabel.setBounds(12, 11, 420, 28);
		addDocumentsFrame.getContentPane().add(whatToDoLabel);
		
		addAllButton = new JButton("Add documents and return");
		addAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String cqlStatement = "BEGIN BATCH " + 
							"INSERT INTO \"Documents_by_Users\" (document_id, document_title, document_content, user_login)" +
							"VALUES (" + doc_id + ", '" + titleTextField.getText() + "','" + contentTextField.getText() +
							"','" + Authorization.getLogin() + "');" + 
							"INSERT INTO \"Users_by_Documents\" (document_id, document_title, document_content, user_login)" +
							"VALUES (" + doc_id + ", '" + titleTextField.getText() + "','" + contentTextField.getText() +
							"','" + Authorization.getLogin() + "');" +
					        "APPLY BATCH";
							
			        session.execute(cqlStatement);
			        			
					} catch (Exception arg0) {
						JOptionPane.showMessageDialog(null, arg0);
					}
				UserWindow.main(null);
            	addDocumentsFrame.setVisible(false);
			}
		});
		addAllButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addAllButton.setBounds(12, 541, 420, 28);
		addDocumentsFrame.getContentPane().add(addAllButton);
		addDocumentsFrame.setBounds(100, 100, 450, 646);
		addDocumentsFrame.setLocationRelativeTo(null);
		addDocumentsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	public void addItemToComboBox(JComboBox<String> arg0, String s) {
	}
	public void getFocusOnTextFieldAfterPressingEnter(JTextField arg1, KeyEvent arg0) {
		if (arg0.getKeyCode()==KeyEvent.VK_ENTER)
        {
			arg1.requestFocusInWindow();
        }
	}
	public class MyDocumentFilter extends DocumentFilter {

	    private JTextArea area;
	    private int max;

	    public MyDocumentFilter(JTextArea area, int max) {
	        this.area = area;
	        this.max = max;
	    }

	    @Override
	    public void replace(FilterBypass fb, int offset, int length,
	            String text, AttributeSet attrs) throws BadLocationException {
	        super.replace(fb, offset, length, text, attrs);
	        int lines = area.getLineCount();
	        if (lines > max) {
	            int linesToRemove = lines - max -1;
	            int lengthToRemove = area.getLineStartOffset(linesToRemove);
	            remove(fb, 0, lengthToRemove);
	        }
	    }
	}
}
