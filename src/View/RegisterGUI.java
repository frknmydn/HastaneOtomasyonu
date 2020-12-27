package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Helper.*;
import Model.Hasta;
import Model.User;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField txt_name;
	private JTextField txt_TC;
	private JLabel lblNewLabel_1_2;
	private JPasswordField passwordField;
	private Hasta hasta = new Hasta();//Kayýt olmak için kullanýcaz.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 11, 69, 15);
		w_pane.add(lblNewLabel_1);
		
		txt_name = new JTextField();
		txt_name.setColumns(10);
		txt_name.setBounds(10, 25, 207, 25);
		w_pane.add(txt_name);
		
		JLabel lblNewLabel_1_1 = new JLabel("T.C. numaras\u0131");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(10, 70, 98, 15);
		w_pane.add(lblNewLabel_1_1);
		
		txt_TC = new JTextField();
		txt_TC.setColumns(10);
		txt_TC.setBounds(10, 84, 207, 25);
		w_pane.add(txt_TC);
		
		lblNewLabel_1_2 = new JLabel("\u015Eifre");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(10, 129, 98, 15);
		w_pane.add(lblNewLabel_1_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 155, 207, 25);
		w_pane.add(passwordField);
		
		JButton btn_register = new JButton("Kay\u0131t Ol");
		btn_register.addActionListener(new ActionListener() {
			
			//Kayýt olma butonu 
			
			public void actionPerformed(ActionEvent e) {
				
				if(txt_TC.getText().length()==0 || passwordField.getText().length()==0||txt_name.getText().length()==0 ) {
					Helper.showMsg("fill");
				}
				else {
					boolean control = hasta.register(txt_TC.getText(), passwordField.getText(), txt_name.getText());
					if(control) {
						Helper.showMsg("success");
						LoginGUI login = new LoginGUI();
						login.setVisible(true);
						dispose();
					}
					else {
						Helper.showMsg("error");
					}
				}
			}
		});
		btn_register.setBounds(10, 205, 89, 23);
		w_pane.add(btn_register);
		
		
		//Geri dön butonu
		JButton btn_backTo = new JButton("Geri D\u00F6n");
		btn_backTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
				
			}
		});
		btn_backTo.setBounds(10, 239, 89, 23);
		w_pane.add(btn_backTo);
	}
}
