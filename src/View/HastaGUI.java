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

	// klinikleri combobox içine listelemek için
	private Clinic clinic = new Clinic();

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private JTable table_doctor;
	// table_doctor oluþturulduktan sonra modeli verilir.
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;

	// çalýþma saatlerini eklemek için
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	
	//Randevularý eklemek için
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
		Object[] colDoctor = new Object[2]; // Baþlýklarýn yazýlý olduðu array, 2 tane index alýcaz.
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad"; // table'ýn üstünde bölme oluþturma iþlemeri.

		doctorModel.setColumnIdentifiers(colDoctor);// Doktor model içine colomnlarý atýyoruz.
		// doctordata doktorlarýn verilerini tuttuðumuz bir arraydir.
		doctorData = new Object[2];

		whourModel = new DefaultTableModel();// initilation
		Object[] colWhour = new Object[2]; // Baþlýklarýn yazýlý olduðu array, 2 tane index alýcaz.
		colWhour[0] = "ID";
		colWhour[1] = "Tarih"; // table'ýn üstünde bölme oluþturma iþlemeri.

		whourModel.setColumnIdentifiers(colWhour);

		whourData = new Object[2];
		
		
		
		//Randevularý listelicez
		appointmentModel = new DefaultTableModel();// initilation
		Object[] colAppoint = new Object[3]; // Baþlýklarýn yazýlý olduðu array, 2 tane index alýcaz.
		colAppoint[0] = "ID";
		colAppoint[1] = "Doktor"; // table'ýn üstünde bölme oluþturma iþlemeri.
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

		JLabel lblNewLabel = new JLabel("Hoþgeldiniz Sayýn " + hasta.getName());
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
		select_clinic.addItem("--Poliklinik seç--");// default deðerini bu þekilde belirliyoruz.
		for (int i = 0; i < clinic.getList().size(); i++) {
			// Item içine klinikteki nesneleri ekliyoruz.
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		// Bu alaný dinlememeiz gerekiyor.
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					// 0. eleman default olduðu için o seçilemez.

					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					// System.out.println(item.getKey() + "- " + item.getValue());
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0); // deðer varsa sýfýrlýyoruz.

					try {
						// seçilen klinikte çalýþan doktorlarý listelemek için yazýldý.
						// Comboox'ta seçilen itemin keyi klinik id olduðu için o klinik id'ye göre
						// Clinic.getDoctorlist içine o id atamasýný yapýnca doktorlarý görebileceðiz
						for (int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {// poliklinik seç basýldýðýnda
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0); // deðer varsa sýfýrlýyoruz.
				}

			}
		});

		w_appointment.add(select_clinic);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 42, 279, 301);
		w_appointment.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);// Table içinde yapýlandýrýlan dokcot_model atýlýr
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
					clearModel.setRowCount(0); // deðer varsa sýfýrlýyoruz.

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
					
					//Daha sonra appointment için kullanmak için global deðiþkene atama yapýcaz.
					
					selectDoctorID = id;
					selectDoctorName= table_doctor.getModel().getValueAt(row,1 ).toString();//1. kolonda isim olduðu için.
					//System.out.println(selectDoctorName);
				} else {
					Helper.showMsg("Lütfen bir doktor seçiniz!");
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
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);// Id nin daha az yer kaplamasý için

		JLabel Saatler = new JLabel("Bo\u015F Randevu");
		Saatler.setFont(new Font("Tahoma", Font.BOLD, 13));
		Saatler.setBounds(489, 17, 226, 16);
		w_appointment.add(Saatler);

		JLabel txt_ClinicName_1_1 = new JLabel("Randevu Se\u00E7");
		txt_ClinicName_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		txt_ClinicName_1_1.setBounds(328, 232, 101, 15);
		w_appointment.add(txt_ClinicName_1_1);

		
		//Randevu tarihi seçme iþlemi için. Bunun öncesinde Appointment classýný yazdýk
		//Ardýndan hasta.java classý içinde de gerekli SQL querysini oluþturduk ve metodunu yazdýk.
		JButton btn_addAppointmen = new JButton("Se\u00E7");
		btn_addAppointmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if(selRow>=0) {
					String date = (String) table_whour.getModel().getValueAt(selRow, 1); //1. kolonda tarih yazýyor
					//þöyle bir sýkýntý var seçilen doktorun id'sini bir yerde tutmamýz gerekiyor ki
					//addappointment metodunda gerekli parametre olarak kullanabilelim
					//Global olarak tanýmlama yapalým o zaman doctor_id için.
					boolean control = hasta.addAppointment(selectDoctorID, selectDoctorName, hasta.getId(), hasta.getName(), date);
					if(control) {
						
						//data sonra DB'deki whour içindeki seçilen tarihin statusunu pasife çekmemiz lazým.
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
					Helper.showMsg("Lütfen geçerli bir randevu tarihi seçiniz");
				}
			
				

				
			}
		});
		btn_addAppointmen.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btn_addAppointmen.setBounds(328, 249, 89, 23);
		w_appointment.add(btn_addAppointmen);
		
		JPanel w_randevular = new JPanel();
		w_tab.addTab("Randevularým", null, w_randevular, null);
		w_randevular.setLayout(null);
		
		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 475, 332);
		w_randevular.add(w_scrollAppoint);
		
		table_appoint = new JTable(appointmentModel);//Atamasýný yapmamýz laýzm parametre olarak.
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
	
	
	//Bir randevu seçildikten sonra seçilen randevu pasife döneceði için table içinde gözükmesini
	//istemiyoruz. O yüzden table içini tekrar temizleyip baþtan yazýyoruz.
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
	
	//Yeni randevu alýndýðýnda güncellenmesi için
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
