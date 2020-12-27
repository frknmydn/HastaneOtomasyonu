package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Bashekim;
import Model.Clinic;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {
	
	
	static Bashekim bashekim = new Bashekim();
	Clinic clinic = new Clinic();
	//Statik bir Ba�hekim nesnesi �rettik ve bunu gui'nin i�inde parametre olarak kullanaca��z (Giri� i�lemi i�in)
	private JPanel w_pane;
	private JTextField txt_doktorAd;
	private JTextField txt_doktorTC;
	private JTextField txt_doktorSifre;
	private JTextField txt_doktorId;
	//private JTable table_doctor;
	private DefaultTableModel doctorModel=null; //Bir tabloya veri eklemek i�in table modellerden yararlan�rl�
	private Object[] doctorData=null; //doktorlar� i�ine ataca��m�z bir list
	private JTable table_doctor;
	private JTable table_clinic;
	private JTextField txt_clinicName;
	private JPopupMenu clinicMenu;
	
	private DefaultTableModel clinicModel=null;
	private Object[] clinicData = null;
	private JTable table_worker;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public BashekimGUI(Bashekim bashekim) throws SQLException {
		
		
		// table i�ine doktorlar� dizmek i�in bir arraylist oolu�turduk
		
		//Doktor model
		doctorModel = new DefaultTableModel();//initilation
		Object[] colDoctorName=new Object[4];  //Ba�l�klar�n yaz�l� oldu�u array, 4 tane index al�caz.
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Ad Soyad";  // table'�n �st�nde b�lme olu�turma i�lemeri. 
		colDoctorName[2] = "TC no";
		colDoctorName[3] = "�ifre";
		doctorModel.setColumnIdentifiers(colDoctorName);//Doktor model i�ine colomnlar� at�yoruz.
		// doctordata doktorlar�n verilerini tuttu�umuz bir arraydir.
		doctorData=new Object[4];
		
		
		
		for(int i=0; i<bashekim.getDoctorList().size();i++) {
			doctorData[0]=bashekim.getDoctorList().get(i).getId();
			doctorData[1]=bashekim.getDoctorList().get(i).getName();
			doctorData[2]=bashekim.getDoctorList().get(i).getTcno();
			doctorData[3]=bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);//Her defas�nda modelin i�ine att�k
		}
		
		
		
		
		
		//Klinik Model  --- clinicModel ---- clinicModell
		clinicModel = new DefaultTableModel();
		Object[] colClinic=new Object[2]; //Clinik i�indeki colomnlar
		colClinic[0]= "ID";
		colClinic[1]= "Poliklinik Ad�"; //Clinic_table �st�ndeki b�lmelerin neler i�erece�ini tan�mlad�k
		clinicModel.setColumnIdentifiers(colClinic); //yukar�da tan�mlad���m�z� clinicModel i�ine att�k
		clinicData= new Object[2];
		for(int i=0;i<clinic.getList().size();i++) {
			clinicData[0]=clinic.getList().get(i).getId();  //0. b�lmeye id'yi
			clinicData[1]=clinic.getList().get(i).getName(); //1. b�lmeye name'i atad�k
			clinicModel.addRow(clinicData); //olu�turdu�umuz datalar� clinicModel table'�n�n i�ine att�k
		}
		
		
		
		//Worker Model
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2]; // 2 boyutlu bir object olu�turduk
		colWorker[0] = "ID";
		colWorker[1] = "Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData= new Object[2];
		
		
		
		
		
		

				
		setTitle("Hastane Y\u00F6netim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ho�geldiniz Say�n " +bashekim.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 232, 31);
		w_pane.add(lblNewLabel);
		
		JButton btn_cikis = new JButton("\u00C7\u0131k\u0131\u015F Yap");
		btn_cikis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btn_cikis.setBounds(497, 16, 124, 23);
		w_pane.add(btn_cikis);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 78, 729, 386);
		w_pane.add(tabbedPane);
		
		JPanel w_doctor = new JPanel();
		w_doctor.setBackground(Color.WHITE);
		tabbedPane.addTab("Doktor Y�netimi", null, w_doctor, null);
		w_doctor.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setBounds(542, 38, 69, 15);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		w_doctor.add(lblNewLabel_1);
		
		txt_doktorAd = new JTextField();
		txt_doktorAd.setBounds(542, 52, 147, 29);
		w_doctor.add(txt_doktorAd);
		txt_doktorAd.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("T.C no");
		lblNewLabel_1_1.setBounds(542, 92, 69, 15);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		w_doctor.add(lblNewLabel_1_1);
		
		txt_doktorTC = new JTextField();
		txt_doktorTC.setBounds(542, 108, 147, 29);
		txt_doktorTC.setColumns(10);
		w_doctor.add(txt_doktorTC);
		
		JLabel lblNewLabel_1_2 = new JLabel("\u015Eifre");
		lblNewLabel_1_2.setBounds(542, 149, 69, 15);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		w_doctor.add(lblNewLabel_1_2);
		
		txt_doktorSifre = new JTextField();
		txt_doktorSifre.setBounds(542, 162, 147, 29);
		txt_doktorSifre.setColumns(10);
		w_doctor.add(txt_doktorSifre);
		
		JButton btn_doktorEkle = new JButton("Ekle");
		btn_doktorEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txt_doktorAd.getText().length()==0|| txt_doktorSifre.getText().length()==0
						||txt_doktorTC.getText().length()==0) {
					
					Helper.showMsg("fiil");
					
				}
				else {
				boolean control = bashekim.addDoctor(txt_doktorTC.getText(), txt_doktorSifre.getText(), txt_doktorAd.getText());
				//Yukar�da ba�hekimde doktor ekleme metodunun constructoru ile yukar�daki
				//- yukar�daki fieldlar� kulanarak doktor olu�turuyoruz.
				if(control) {
					//e�er i�lem ba�ar�l� ise mesaj g�stericez.
					Helper.showMsg("success");
					
					txt_doktorTC.setText(null);
					txt_doktorAd.setText(null); //en son i�ini bo�alt�yoruz.
					txt_doktorSifre.setText(null);
					
					try {
						updateDoctorModel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				}
				
				
				
			}
		});
		btn_doktorEkle.setBounds(571, 202, 89, 23);
		w_doctor.add(btn_doktorEkle);
		
		JLabel lblNewLabel_1_3 = new JLabel("Kullan\u0131c\u0131 id");
		lblNewLabel_1_3.setBounds(542, 258, 69, 15);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		w_doctor.add(lblNewLabel_1_3);
		
		txt_doktorId = new JTextField();
		txt_doktorId.setBounds(542, 271, 147, 29);
		txt_doktorId.setColumns(10);
		w_doctor.add(txt_doktorId);
		
		
		JButton txt_doktorSil = new JButton("Sil");
		txt_doktorSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txt_doktorId.getText().length()==0) {
					Helper.showMsg("Silmek istedi�iniz doktoru se�iniz");
					
				}
				else {
					if(Helper.comfirm("sure")) {
						int selectedId = Integer.parseInt(txt_doktorId.getText());
						boolean control = bashekim.deleteDoctor(selectedId);
						if(control) {
							Helper.showMsg("success");
							txt_doktorId.setText(null);
							try {
								updateDoctorModel();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
					}
				}
			}
		});
		txt_doktorSil.setBounds(571, 311, 89, 23);
		w_doctor.add(txt_doktorSil);
		
		JScrollPane scrollDoctor = new JScrollPane();
		scrollDoctor.setBounds(10, 11, 494, 336);
		w_doctor.add(scrollDoctor);
		
		table_doctor = new JTable(doctorModel);
		scrollDoctor.setViewportView(table_doctor);
		
		
		//buras� �al��m�yor neden �al��m�yor hi�bir fikrim yok. Bast���n row id i�ine gelmiyor!!!!
		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			//table'da bir rrow'a bast���m�zda bast���m�z rowu getirmek i�in
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
					//txt_doktorId.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					txt_doktorId.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					
					//yukar�da se�ilmi� rowun, 0. sat�r�n� getir diyoruz. ��nk� id 0. sat�rda.
					//Table i�ine ekleme i�in
					table_doctor = new JTable(doctorModel);//table i�inde konstructura olu�turulan doktormodeli ekliyoruz.
					scrollDoctor.setViewportView(table_doctor);
				
				
				
			}
		});
		
		//update i�in tablonun de�i�ip de�i�medi�ini kontrol edicez.
		
		
		table_doctor.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType()==TableModelEvent.UPDATE) {//tablodaki modelin de�i�ikli�i update Mi?
					int selectedID = Integer.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectedName=table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectedTcno=table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectedPass=table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();
					
					boolean control = bashekim.updateDoctor(selectedID, selectedTcno, selectedPass, selectedName);
					if(control) {
						Helper.showMsg("success");
					}//Table i�ine ekleme i�in
					
				}
				
			}
		});
		
		table_doctor = new JTable(doctorModel);//table i�inde konstructura olu�turulan doktormodeli ekliyoruz.
		scrollDoctor.setViewportView(table_doctor);
		
		JPanel w_clinic = new JPanel();
		tabbedPane.addTab("Klinik Y�netimi", null, w_clinic, null);
		w_clinic.setLayout(null);
		
		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 11, 257, 336);
		w_clinic.add(w_scrollClinic);
		
		
		
		
		
		
		
		
		
		
		
		//___________----------___________------------______________
		//popup men� olu�turuyoruz.
		//bu popup men� sa� klik at�l�nca a��lacak olan men�
		
		
		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("G�ncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);
		
		
		
		
		//yapt���m�z updateMenuye updateleme listenar� verip tekrar updateClinic ile g�ncellenmesini sa�l�yoruz.
		updateMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(),0).toString());
				Clinic selecClinic = clinic.getFetch(selectedID);
				UpdateClinicGUI updateGui = new UpdateClinicGUI(selecClinic);
				updateGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGui.setVisible(true);
				updateGui.addWindowListener(new WindowAdapter() {
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						updateClinicModel();
						super.windowClosed(e);
					}
				});
			}
			
			
		});
		
		//yapt���m�z deleteMenu'ye listener veriyoruz
		deleteMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Helper.comfirm("sure")) {
					int selectedID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(),0).toString());
					if(clinic.deleteClinic(selectedID)) {
						Helper.showMsg("success");
						updateClinicModel();
					}
					else
						Helper.showMsg("error");
						
				}
				
			}
		});
		
		

		
		//__________-----------_____________-----------____________-----------______
		
		

		
		
		table_clinic = new JTable(clinicModel); //olu�turulan ve doldurulan clinicModeli table i�ine att�k
		table_clinic.setComponentPopupMenu(clinicMenu);//table clinic'e t�kland���nda popupmen�y� ��kar�yoruz.
		w_scrollClinic.setViewportView(table_clinic);
		
		
		//A�a��da table clinic i�ine t�klanma listener� koyuyoruz.
		table_clinic.addMouseListener(new MouseAdapter() {//t�klanan yerdeki verinin gelmesini sa�lamak i�in
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				Point point = e.getPoint();//point bir koordinat tutucudur.
				int selectedRow = table_clinic.rowAtPoint(point);//t�klad��� yerdeki koordinaolar� al�yor
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
				
				
			}
		});
		
		
		JLabel txt_ClinicName = new JLabel("Yeni Klinik Ad�");
		txt_ClinicName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_ClinicName.setBounds(277, 38, 101, 15);
		w_clinic.add(txt_ClinicName);
		
		txt_clinicName = new JTextField();
		txt_clinicName.setColumns(10);
		txt_clinicName.setBounds(277, 52, 159, 23);
		w_clinic.add(txt_clinicName);
		
		JButton btn_clinicAdd = new JButton("Ekle");
		btn_clinicAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txt_clinicName.getText().length()==0) {
					Helper.showMsg("fill");
				}
				else {
					if(clinic.addClinic(txt_clinicName.getText())) {
						
						Helper.showMsg("success");
						txt_clinicName.setText("");
						updateClinicModel();
						
					}
				}
				
				
				
				
			}
		});
		btn_clinicAdd.setBounds(309, 81, 89, 23);
		w_clinic.add(btn_clinicAdd);
		
		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(457, 11, 243, 336);
		w_clinic.add(w_scrollWorker);
		
		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);
		
		
		
		// Doktoru klini�e ekleme i�lemleri i�in dropdown olu�turduk. Ard�ndan t�klanan doktorun id'si ve name'ini gelmesini istedik
		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(277, 275, 170, 30);//dropdownlist i�ine ekleme yapmam�z i�in a�a��da yaz�yoruz
		for(int i=0; i<bashekim.getDoctorList().size();i++) {
			
			//item ad�nda bir class olu�turuyoruz. Bunun amac� ismi ile birlikte key olarak id'sinin de tutulmas�n� istiyoruz.
			select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(),bashekim.getDoctorList().get(i).getName()));//d�ng�deki ininci eleman�n doktorlist i�inden getir
		}
		
		//t�klanan doktorun o doktor olarak gelmesini yani item value'ya t�kland���nda
		//item keyinin de al�nmas�n� istiyoruz.
		
		select_doctor.addActionListener(e -> {//lambda
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " " + item.getValue());
		});
		w_clinic.add(select_doctor);
		
		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//ilk olarak tablodan bir de�er se�mek laz�m.
				int selRow = table_clinic.getSelectedRow();
				if(selRow>=0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();// se�ti�imiz rowun ilk eleman�n� getir.
					int selClinicID = Integer.parseInt(selClinic);
					Item doktorItem = (Item) select_doctor.getSelectedItem();
					
					boolean control = bashekim.addWorker(doktorItem.getKey(), selClinicID);
					
					if(control) {//e�er veri eklendiyse
						Helper.showMsg("success");
						//Sa� taraf�n g�ncellenmesi i�in tekrar yaz�yoruz
						DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
						clearModel.setRowCount(0);
						try {
							for(int i =0; i<bashekim.getClinicDoctorList(selClinicID).size();i++) {
								workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
								workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
								workerModel.addRow(workerData);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						table_worker.setModel(workerModel);
					}
					else {
						Helper.showMsg("error");
					}
				}
				else {
					Helper.showMsg("L�tfen bir poliklinik se�iniz.");
				}
			}
		});
		btn_addWorker.setBounds(309, 316, 89, 23);
		w_clinic.add(btn_addWorker);
		
		JLabel txt_ClinicName_1 = new JLabel("Poliklinik Ad\u0131");
		txt_ClinicName_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_ClinicName_1.setBounds(277, 144, 101, 15);
		w_clinic.add(txt_ClinicName_1);
		
		JButton btn_workerSelect = new JButton("Se\u00E7");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Se�ilen rowu almak zorunday�z.
				int selRow = table_clinic.getSelectedRow();
				if(selRow>=0) {
					//se�ilen klini�i ald�k 
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();// se�ti�imiz rowun ilk eleman�n� getir.
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);
					
					//Art�k de�erleri atabilirz.
					
					try {
						for(int i =0; i<bashekim.getClinicDoctorList(selClinicID).size();i++) {
							workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
							workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
							workerModel.addRow(workerData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);
					
				}
				else {
					Helper.showMsg("L�tfen bir poliklinik se�iniz.");
				}
			}
		});
		btn_workerSelect.setBounds(276, 159, 89, 23);
		w_clinic.add(btn_workerSelect);
		
		
	}
	
	//doktor eklendikten sonra tablonun tekrar g�ncellenmesini istiyoruz.
	//bu y�zden bu metodu tablonun g�ncellenmesini istedi�imiz gerekli yerlerde kullanaca��z.
	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel= (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0); //B�t�n rowlar� sildik
		
		for(int i=0; i<bashekim.getDoctorList().size();i++) {
			doctorData[0]=bashekim.getDoctorList().get(i).getId();
			doctorData[1]=bashekim.getDoctorList().get(i).getName();
			doctorData[2]=bashekim.getDoctorList().get(i).getTcno();
			doctorData[3]=bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);//Her defas�nda modelin i�ine att�k
		}

		
	}
	
	
	//ayn� �ekilde clinicmodel i�inde bir update i�lemi yapt���m�zda dinamik olarak tablonun
	//tekrar g�ncellenmesini istiyoruz bu y�zden gerekli yerlerde bu metodu �a��rabilriz.
	public void updateClinicModel() {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		
		for(int i=0;i<clinic.getList().size();i++) {
			clinicData[0]=clinic.getList().get(i).getId();
			clinicData[1]=clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		
	}
}
