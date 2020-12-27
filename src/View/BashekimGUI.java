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
	//Statik bir Baþhekim nesnesi ürettik ve bunu gui'nin içinde parametre olarak kullanacaðýz (Giriþ iþlemi için)
	private JPanel w_pane;
	private JTextField txt_doktorAd;
	private JTextField txt_doktorTC;
	private JTextField txt_doktorSifre;
	private JTextField txt_doktorId;
	//private JTable table_doctor;
	private DefaultTableModel doctorModel=null; //Bir tabloya veri eklemek için table modellerden yararlanýrlý
	private Object[] doctorData=null; //doktorlarý içine atacaðýmýz bir list
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
		
		
		// table içine doktorlarý dizmek için bir arraylist ooluþturduk
		
		//Doktor model
		doctorModel = new DefaultTableModel();//initilation
		Object[] colDoctorName=new Object[4];  //Baþlýklarýn yazýlý olduðu array, 4 tane index alýcaz.
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Ad Soyad";  // table'ýn üstünde bölme oluþturma iþlemeri. 
		colDoctorName[2] = "TC no";
		colDoctorName[3] = "Þifre";
		doctorModel.setColumnIdentifiers(colDoctorName);//Doktor model içine colomnlarý atýyoruz.
		// doctordata doktorlarýn verilerini tuttuðumuz bir arraydir.
		doctorData=new Object[4];
		
		
		
		for(int i=0; i<bashekim.getDoctorList().size();i++) {
			doctorData[0]=bashekim.getDoctorList().get(i).getId();
			doctorData[1]=bashekim.getDoctorList().get(i).getName();
			doctorData[2]=bashekim.getDoctorList().get(i).getTcno();
			doctorData[3]=bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);//Her defasýnda modelin içine attýk
		}
		
		
		
		
		
		//Klinik Model  --- clinicModel ---- clinicModell
		clinicModel = new DefaultTableModel();
		Object[] colClinic=new Object[2]; //Clinik içindeki colomnlar
		colClinic[0]= "ID";
		colClinic[1]= "Poliklinik Adý"; //Clinic_table üstündeki bölmelerin neler içereceðini tanýmladýk
		clinicModel.setColumnIdentifiers(colClinic); //yukarýda tanýmladýðýmýzý clinicModel içine attýk
		clinicData= new Object[2];
		for(int i=0;i<clinic.getList().size();i++) {
			clinicData[0]=clinic.getList().get(i).getId();  //0. bölmeye id'yi
			clinicData[1]=clinic.getList().get(i).getName(); //1. bölmeye name'i atadýk
			clinicModel.addRow(clinicData); //oluþturduðumuz datalarý clinicModel table'ýnýn içine attýk
		}
		
		
		
		//Worker Model
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2]; // 2 boyutlu bir object oluþturduk
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
		
		JLabel lblNewLabel = new JLabel("Hoþgeldiniz Sayýn " +bashekim.getName());
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
		tabbedPane.addTab("Doktor Yönetimi", null, w_doctor, null);
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
				//Yukarýda baþhekimde doktor ekleme metodunun constructoru ile yukarýdaki
				//- yukarýdaki fieldlarý kulanarak doktor oluþturuyoruz.
				if(control) {
					//eðer iþlem baþarýlý ise mesaj göstericez.
					Helper.showMsg("success");
					
					txt_doktorTC.setText(null);
					txt_doktorAd.setText(null); //en son içini boþaltýyoruz.
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
					Helper.showMsg("Silmek istediðiniz doktoru seçiniz");
					
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
		
		
		//burasý çalýþmýyor neden çalýþmýyor hiçbir fikrim yok. Bastýðýn row id içine gelmiyor!!!!
		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			//table'da bir rrow'a bastýðýmýzda bastýðýmýz rowu getirmek için
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
					//txt_doktorId.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					txt_doktorId.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					
					//yukarýda seçilmiþ rowun, 0. satýrýný getir diyoruz. Çünkü id 0. satýrda.
					//Table içine ekleme için
					table_doctor = new JTable(doctorModel);//table içinde konstructura oluþturulan doktormodeli ekliyoruz.
					scrollDoctor.setViewportView(table_doctor);
				
				
				
			}
		});
		
		//update için tablonun deðiþip deðiþmediðini kontrol edicez.
		
		
		table_doctor.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType()==TableModelEvent.UPDATE) {//tablodaki modelin deðiþikliði update Mi?
					int selectedID = Integer.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectedName=table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectedTcno=table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectedPass=table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();
					
					boolean control = bashekim.updateDoctor(selectedID, selectedTcno, selectedPass, selectedName);
					if(control) {
						Helper.showMsg("success");
					}//Table içine ekleme için
					
				}
				
			}
		});
		
		table_doctor = new JTable(doctorModel);//table içinde konstructura oluþturulan doktormodeli ekliyoruz.
		scrollDoctor.setViewportView(table_doctor);
		
		JPanel w_clinic = new JPanel();
		tabbedPane.addTab("Klinik Yönetimi", null, w_clinic, null);
		w_clinic.setLayout(null);
		
		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 11, 257, 336);
		w_clinic.add(w_scrollClinic);
		
		
		
		
		
		
		
		
		
		
		
		//___________----------___________------------______________
		//popup menü oluþturuyoruz.
		//bu popup menü sað klik atýlýnca açýlacak olan menü
		
		
		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);
		
		
		
		
		//yaptýðýmýz updateMenuye updateleme listenarý verip tekrar updateClinic ile güncellenmesini saðlýyoruz.
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
		
		//yaptýðýmýz deleteMenu'ye listener veriyoruz
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
		
		

		
		
		table_clinic = new JTable(clinicModel); //oluþturulan ve doldurulan clinicModeli table içine attýk
		table_clinic.setComponentPopupMenu(clinicMenu);//table clinic'e týklandýðýnda popupmenüyü çýkarýyoruz.
		w_scrollClinic.setViewportView(table_clinic);
		
		
		//Aþaðýda table clinic içine týklanma listenerý koyuyoruz.
		table_clinic.addMouseListener(new MouseAdapter() {//týklanan yerdeki verinin gelmesini saðlamak için
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				Point point = e.getPoint();//point bir koordinat tutucudur.
				int selectedRow = table_clinic.rowAtPoint(point);//týkladýðý yerdeki koordinaolarý alýyor
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
				
				
			}
		});
		
		
		JLabel txt_ClinicName = new JLabel("Yeni Klinik Adý");
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
		
		
		
		// Doktoru kliniðe ekleme iþlemleri için dropdown oluþturduk. Ardýndan týklanan doktorun id'si ve name'ini gelmesini istedik
		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(277, 275, 170, 30);//dropdownlist içine ekleme yapmamýz için aþaðýda yazýyoruz
		for(int i=0; i<bashekim.getDoctorList().size();i++) {
			
			//item adýnda bir class oluþturuyoruz. Bunun amacý ismi ile birlikte key olarak id'sinin de tutulmasýný istiyoruz.
			select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(),bashekim.getDoctorList().get(i).getName()));//döngüdeki ininci elemanýn doktorlist içinden getir
		}
		
		//týklanan doktorun o doktor olarak gelmesini yani item value'ya týklandýðýnda
		//item keyinin de alýnmasýný istiyoruz.
		
		select_doctor.addActionListener(e -> {//lambda
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " " + item.getValue());
		});
		w_clinic.add(select_doctor);
		
		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//ilk olarak tablodan bir deðer seçmek lazým.
				int selRow = table_clinic.getSelectedRow();
				if(selRow>=0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();// seçtiðimiz rowun ilk elemanýný getir.
					int selClinicID = Integer.parseInt(selClinic);
					Item doktorItem = (Item) select_doctor.getSelectedItem();
					
					boolean control = bashekim.addWorker(doktorItem.getKey(), selClinicID);
					
					if(control) {//eðer veri eklendiyse
						Helper.showMsg("success");
						//Sað tarafýn güncellenmesi için tekrar yazýyoruz
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
					Helper.showMsg("Lütfen bir poliklinik seçiniz.");
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
				//Seçilen rowu almak zorundayýz.
				int selRow = table_clinic.getSelectedRow();
				if(selRow>=0) {
					//seçilen kliniði aldýk 
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();// seçtiðimiz rowun ilk elemanýný getir.
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);
					
					//Artýk deðerleri atabilirz.
					
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
					Helper.showMsg("Lütfen bir poliklinik seçiniz.");
				}
			}
		});
		btn_workerSelect.setBounds(276, 159, 89, 23);
		w_clinic.add(btn_workerSelect);
		
		
	}
	
	//doktor eklendikten sonra tablonun tekrar güncellenmesini istiyoruz.
	//bu yüzden bu metodu tablonun güncellenmesini istediðimiz gerekli yerlerde kullanacaðýz.
	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel= (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0); //Bütün rowlarý sildik
		
		for(int i=0; i<bashekim.getDoctorList().size();i++) {
			doctorData[0]=bashekim.getDoctorList().get(i).getId();
			doctorData[1]=bashekim.getDoctorList().get(i).getName();
			doctorData[2]=bashekim.getDoctorList().get(i).getTcno();
			doctorData[3]=bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);//Her defasýnda modelin içine attýk
		}

		
	}
	
	
	//ayný þekilde clinicmodel içinde bir update iþlemi yaptýðýmýzda dinamik olarak tablonun
	//tekrar güncellenmesini istiyoruz bu yüzden gerekli yerlerde bu metodu çaðýrabilriz.
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
