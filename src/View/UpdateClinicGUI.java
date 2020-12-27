package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import Helper.Helper;
import Model.Clinic;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateClinicGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txt_clinicNewName;
	//içine klinik objesini atmam lazým
	private static Clinic clinic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
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
	public UpdateClinicGUI(Clinic clinic) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 244, 130);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel txt_ClinicName = new JLabel("Poliklinik Ad\u0131");
		txt_ClinicName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_ClinicName.setBounds(34, 11, 69, 15);
		contentPane.add(txt_ClinicName);
		
		txt_clinicNewName = new JTextField();
		txt_clinicNewName.setColumns(10);
		txt_clinicNewName.setBounds(34, 24, 147, 29);
		txt_clinicNewName.setText(clinic.getName());//içine açtýðýmýz kliniðin adýnýn gelmesi lazým
		contentPane.add(txt_clinicNewName);
		
		JButton updateClinic = new JButton("D\u00FCzenle");
		updateClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.comfirm("sure")) {
					clinic.updateClinic(clinic.getId(), txt_clinicNewName.getText());
					Helper.showMsg("success");
					dispose();//bu frame'i kapatýyorz.
					
				}
			}
		});
		updateClinic.setBounds(70, 57, 89, 23);
		contentPane.add(updateClinic);
	}
}
