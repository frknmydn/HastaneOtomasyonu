package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class HastaGUI extends JFrame {

	// klinikleri combobox i�ine listelemek i�in
	private Clinic clinic = new Clinic();

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private JTable table_doctor;
	// table_doctor olu�turulduktan sonra modeli verilir.
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;

	// �al��ma saatlerini eklemek i�in
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	
	//Randevular� eklemek i�in
	private Appointment appointment= new Appointment();
	private DefaultTableModel appointmentModel;
	private Object[] appointmentData = null;
	
	private int selectDoctorID= 0;
	private String selectDoctorName=null;
	private JTable table_appoint;
	private JTextField txt_silinecekID;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
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
	public HastaGUI(Hasta hasta) {

		doctorModel = new DefaultTableModel();// initilation
		Object[] colDoctor = new Object[2]; // Ba�l�klar�n yaz�l� oldu�u array, 2 tane index al�caz.
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad"; // table'�n �st�nde b�lme olu�turma i�lemeri.

		doctorModel.setColumnIdentifiers(colDoctor);// Doktor model i�ine colomnlar� at�yoruz.
		// doctordata doktorlar�n verilerini tuttu�umuz bir arraydir.
		doctorData = new Object[2];

		whourModel = new DefaultTableModel();// initilation
		Object[] colWhour = new Object[2]; // Ba�l�klar�n yaz�l� oldu�u array, 2 tane index al�caz.
		colWhour[0] = "ID";
		colWhour[1] = "Tarih"; // table'�n �st�nde b�lme olu�turma i�lemeri.

		whourModel.setColumnIdentifiers(colWhour);

		whourData = new Object[2];
		
		
		
		//Randevular� listelicez
		appointmentModel = new DefaultTableModel();// initilation
		Object[] colAppoint = new Object[3]; // Ba�l�klar�n yaz�l� oldu�u array, 2 tane index al�caz.
		colAppoint[0] = "ID";
		colAppoint[1] = "Doktor"; // table'�n �st�nde b�lme olu�turma i�lemeri.
		colAppoint[2] = "Tarih";
		appointmentModel.setColumnIdentifiers(colAppoint);
		appointmentData= new Object[3];
		
		for(int i=0;i<appointment.getHastaList(hasta.getId()).size();i++) {
			appointmentData[0] = appointment.getHastaList(hasta.getId()).get(i).getId();
			appointmentData[1] = appointment.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointmentData[2] = appointment.getHastaList(hasta.getId()).get(i).getAppDate();
			appointmentModel.addRow(appointmentData);
		}
		
		
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ho�geldiniz Say�n " + hasta.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 232, 31);
		w_pane.add(lblNewLabel);

		JButton btn_cikis = new JButton("\u00C7\u0131k\u0131\u015F Yap");
		btn_cikis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_cikis.setBounds(508, 16, 124, 23);
		w_pane.add(btn_cikis);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 78, 724, 382);
		w_pane.add(w_tab);

		JPanel w_appointment = new JPanel();
		w_tab.addTab("Randevu Alma", null, w_appointment, null);
		w_appointment.setLayout(null);

		JLabel lblDoktorListesi = new JLabel("Doktor Listesi");
		lblDoktorListesi.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDoktorListesi.setBounds(10, 15, 226, 16);
		w_appointment.add(lblDoktorListesi);

		JLabel lblPliklinikAd = new JLabel("Poliklinik Ad\u0131");
		lblPliklinikAd.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPliklinikAd.setBounds(313, 38, 151, 23);
		w_appointment.add(lblPliklinikAd);

		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(313, 61, 166, 23);
		select_clinic.addItem("--Poliklinik se�--");// default de�erini bu �ekilde belirliyoruz.
		for (int i = 0; i < clinic.getList().size(); i++) {
			// Item i�ine klinikteki nesneleri ekliyoruz.
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		// Bu alan� dinlememeiz gerekiyor.
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					// 0. eleman default oldu�u i�in o se�ilemez.

					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					// System.out.println(item.getKey() + "- " + item.getValue());
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0); // de�er varsa s�f�rl�yoruz.

					try {
						// se�ilen klinikte �al��an doktorlar� listelemek i�in yaz�ld�.
						// Comboox'ta se�ilen itemin keyi klinik id oldu�u i�in o klinik id'ye g�re
						// Clinic.getDoctorlist i�ine o id atamas�n� yap�nca doktorlar� g�rebilece�iz
						for (int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {// poliklinik se� bas�ld���nda
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0); // de�er varsa s�f�rl�yoruz.
				}

			}
		});

		w_appointment.add(select_clinic);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 42, 279, 301);
		w_appointment.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);// Table i�inde yap�land�r�lan dokcot_model at�l�r
		w_scrollDoctor.setViewportView(table_doctor);

		JLabel txt_ClinicName_1 = new JLabel("Doktor Se\u00E7");
		txt_ClinicName_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_ClinicName_1.setBounds(328, 159, 101, 15);
		w_appointment.add(txt_ClinicName_1);

		JButton btn_workerSelect = new JButton("Se\u00E7");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if (row >= 0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0); // de�er varsa s�f�rl�yoruz.

					try {
						for (int i = 0; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					table_whour.setModel(whourModel);
					
					//Daha sonra appointment i�in kullanmak i�in global de�i�kene atama yap�caz.
					
					selectDoctorID = id;
					selectDoctorName= table_doctor.getModel().getValueAt(row,1 ).toString();//1. kolonda isim oldu�u i�in.
					//System.out.println(selectDoctorName);
				} else {
					Helper.showMsg("L�tfen bir doktor se�iniz!");
				}

			}
		});
		btn_workerSelect.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btn_workerSelect.setBounds(328, 176, 89, 23);
		w_appointment.add(btn_workerSelect);

		JScrollPane w_scrollWhor = new JScrollPane();
		w_scrollWhor.setBounds(489, 42, 220, 301);
		w_appointment.add(w_scrollWhor);

		table_whour = new JTable(whourModel);
		w_scrollWhor.setViewportView(table_whour);
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);// Id nin daha az yer kaplamas� i�in

		JLabel Saatler = new JLabel("Bo\u015F Randevu");
		Saatler.setFont(new Font("Tahoma", Font.BOLD, 13));
		Saatler.setBounds(489, 17, 226, 16);
		w_appointment.add(Saatler);

		JLabel txt_ClinicName_1_1 = new JLabel("Randevu Se\u00E7");
		txt_ClinicName_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_ClinicName_1_1.setBounds(328, 232, 101, 15);
		w_appointment.add(txt_ClinicName_1_1);

		
		//Randevu tarihi se�me i�lemi i�in. Bunun �ncesinde Appointment class�n� yazd�k
		//Ard�ndan hasta.java class� i�inde de gerekli SQL querysini olu�turduk ve metodunu yazd�k.
		JButton btn_addAppointmen = new JButton("Se\u00E7");
		btn_addAppointmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if(selRow>=0) {
					String date = (String) table_whour.getModel().getValueAt(selRow, 1); //1. kolonda tarih yaz�yor
					//��yle bir s�k�nt� var se�ilen doktorun id'sini bir yerde tutmam�z gerekiyor ki
					//addappointment metodunda gerekli parametre olarak kullanabilelim
					//Global olarak tan�mlama yapal�m o zaman doctor_id i�in.
					boolean control = hasta.addAppointment(selectDoctorID, selectDoctorName, hasta.getId(), hasta.getName(), date);
					if(control) {
						
						//data sonra DB'deki whour i�indeki se�ilen tarihin statusunu pasife �ekmemiz laz�m.
						hasta.updateWhourStatus(selectDoctorID, date);
						updateWhourModel(selectDoctorID);
						Helper.showMsg("success");
						updateAppointModel(hasta.getId());
						
					}
					else {
						Helper.showMsg("error");
					}
				}
				else {
					Helper.showMsg("L�tfen ge�erli bir randevu tarihi se�iniz");
				}
			
				

				
			}
		});
		btn_addAppointmen.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btn_addAppointmen.setBounds(328, 249, 89, 23);
		w_appointment.add(btn_addAppointmen);
		
		JPanel w_randevular = new JPanel();
		w_tab.addTab("Randevular�m", null, w_randevular, null);
		w_randevular.setLayout(null);
		
		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 475, 332);
		w_randevular.add(w_scrollAppoint);
		
		table_appoint = new JTable(appointmentModel);//Atamas�n� yapmam�z la�zm parametre olarak.
		w_scrollAppoint.setViewportView(table_appoint);
		
		JLabel lblNewLabel_1 = new JLabel("L\u00FCtfen S\u0130LMEK istedi\u011Finiz randevunun\r\n ");
		lblNewLabel_1.setBounds(495, 40, 182, 42);
		w_randevular.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("id de\u011Ferini yaz\u0131n\u0131z.");
		lblNewLabel_1_1.setBounds(495, 59, 182, 42);
		w_randevular.add(lblNewLabel_1_1);
		
		txt_silinecekID = new JTextField();
		txt_silinecekID.setBounds(494, 93, 103, 20);
		w_randevular.add(txt_silinecekID);
		txt_silinecekID.setColumns(10);
		
		JButton btnNewButton = new JButton("Sil");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String silinecekDeger =  txt_silinecekID.getText().toString();
				int intDegeri = Integer.parseInt(silinecekDeger);
				System.out.println(intDegeri);
				boolean silme = hasta.deleteAppointment(intDegeri);
				updateAppointModel(hasta.getId());
				
				if(silme) {
					Helper.showMsg("success");
				}
				else
					Helper.showMsg("error");
			}
		});
		btnNewButton.setBounds(504, 124, 81, 20);
		w_randevular.add(btnNewButton);
	}
	
	
	//Bir randevu se�ildikten sonra se�ilen randevu pasife d�nece�i i�in table i�inde g�z�kmesini
	//istemiyoruz. O y�zden table i�ini tekrar temizleyip ba�tan yaz�yoruz.
	public void updateWhourModel(int doctor_id) {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		try {
			for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
				whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
				whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
				whourModel.addRow(whourData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Yeni randevu al�nd���nda g�ncellenmesi i�in
	public void updateAppointModel(int hasta_id) {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		for(int i=0;i<appointment.getHastaList(hasta_id).size();i++) {
			appointmentData[0] = appointment.getHastaList(hasta_id).get(i).getId();
			appointmentData[1] = appointment.getHastaList(hasta_id).get(i).getDoctorName();
			appointmentData[2] = appointment.getHastaList(hasta_id).get(i).getAppDate();
			appointmentModel.addRow(appointmentData);
		}
	}
}
