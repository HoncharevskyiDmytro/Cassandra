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

public class AddDocumentsToUser {

	private JFrame addDocumentsFrame;
	private JComboBox documentField;
	private JLabel documentLabel;
	int counter = 0;
	private JButton returnButton;
	private JLabel whatToDoLabel;
	private JButton addAllButton;
	boolean ready = true;
	String commit_ready = "";
//	String max_doc_id = "";
	
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
					AddDocumentsToUser window = new AddDocumentsToUser();
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
	public AddDocumentsToUser() {
//		conn = Javaconnect.ConnectionDb();
		initialize();
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
		
		documentField = new JComboBox();
		documentField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		documentField.setEditable(false);
		documentField.setBounds(162, 50, 132, 28);
		addDocumentsFrame.getContentPane().add(documentField);
		
		//To do
		
		try {
			
			int local_document_counter = 0;
			int[] indices_not_to_add = new int[20];
			String login = Authorization.getLogin();
			
			for(int some_counter = 1; some_counter < 21; some_counter++){
				indices_not_to_add[some_counter - 1] = some_counter;
			}
			
			String cqlStatement = "SELECT document_id FROM \"Users_by_Documents\" WHERE user_login = '" + login + "';";		
			
			for (Row row : session.execute(cqlStatement)) {
				
				local_document_counter += 1;
				
	        }
			
			String[] user_document_indices = new String[local_document_counter];
			String document_index_not_trimmed;
			String[] document_index = new String[2];
			int i = 0;
			
			for (Row row : session.execute(cqlStatement)) {
				
				document_index_not_trimmed = row.toString().substring(4);
				document_index = document_index_not_trimmed.split("]");
				user_document_indices[i] = document_index[0];
				System.out.println(user_document_indices[i]);
				i++;
	        }
			
			local_document_counter = 0;
			
			cqlStatement = "SELECT document_id FROM \"Documents_by_Users\";";		
			
			for (Row row : session.execute(cqlStatement)) {
				
				local_document_counter += 1;
				
	        }
			
			String[] document_user_indices = new String[local_document_counter];
			
			i = 0;
			
			for (Row row : session.execute(cqlStatement)) {
				
				document_index_not_trimmed = row.toString().substring(4);
				document_index = document_index_not_trimmed.split("]");
				document_user_indices[i] = document_index[0];
				System.out.println(document_user_indices[i]);
				i++;
				
	        }
			
			cqlStatement = "SELECT MAX(document_id) FROM \"Documents_by_Users\";";
			
			for (Row row : session.execute(cqlStatement)) {

				document_index_not_trimmed = row.toString().substring(4);
				document_index = document_index_not_trimmed.split("]");
	        }

			Integer max_doc_id = Integer.parseInt(document_index[0]);
			
			for (int j = 0; j < user_document_indices.length; j++){
				for (int k = 0; k < document_user_indices.length; k++){
					if(user_document_indices[j].equals(document_user_indices[k])) {
						Integer doc = Integer.parseInt(user_document_indices[j]);
						indices_not_to_add[doc-1] = 0;
					}
				}
			}
			
			for(int some_counter = 1; some_counter < 21; some_counter++){
				if ((some_counter <= max_doc_id) && (indices_not_to_add[some_counter-1] != 0)){
					documentField.addItem(indices_not_to_add[some_counter-1]);
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserWindow.main(null);
            	addDocumentsFrame.setVisible(false);
			}
		});
		returnButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		returnButton.setBounds(309, 89, 123, 28);
		addDocumentsFrame.getContentPane().add(returnButton);
		
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
							"INSERT INTO \"Documents_by_Users\" (document_id, user_login)" +
							"VALUES (" + documentField.getSelectedItem() + ", '" + Authorization.getLogin() + "');" + 
							"INSERT INTO \"Users_by_Documents\" (document_id, user_login)" +
							"VALUES (" + documentField.getSelectedItem() + ", '" + Authorization.getLogin() + "');" +
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
		addAllButton.setBounds(10, 89, 287, 28);
		addDocumentsFrame.getContentPane().add(addAllButton);
		addDocumentsFrame.setBounds(100, 100, 450, 157);
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
