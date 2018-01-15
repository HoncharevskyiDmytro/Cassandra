package com.project.cassandra.window_forms;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AskingExit {

	
	private JFrame askingExitFrame;
	final JDialog askingExitDialog = new JDialog(askingExitFrame, null, true);
	private JButton noButton;
	private JButton yesButton;
	private JLabel messageLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AskingExit window = new AskingExit();
					window.askingExitDialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AskingExit() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		askingExitDialog.setTitle("User's Documents Application - v.0");
		askingExitDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		askingExitDialog.setBounds(100, 100, 415, 300);
		askingExitDialog.setLocationRelativeTo(null);
		askingExitDialog.getContentPane().setLayout(null);
		
		noButton = new JButton("No");
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				askingExitDialog.setVisible(false);
			}
		});
		noButton.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		noButton.setBounds(230, 167, 132, 50);
		askingExitDialog.getRootPane().setDefaultButton(noButton);
		noButton.requestFocus();
		askingExitDialog.getContentPane().add(noButton);
		
		yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		yesButton.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		yesButton.setBounds(33, 167, 132, 50);
		askingExitDialog.getContentPane().add(yesButton);
		
		messageLabel = new JLabel("Would you like to exit program?");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		messageLabel.setBounds(12, 72, 373, 50);
		askingExitDialog.getContentPane().add(messageLabel);
	}
}
