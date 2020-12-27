package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Model.Appointment;
import Model.Doctor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;

public class DoctorGUI extends JFrame {

	private static Doctor doctor = new Doctor(); // statik olarak olujþturmamýzýn sebebi gelen doktor nesnesinin dinamik olmamasý için.

	private JPanel contentPane;
	private JTable table_wHour;
	private DefaultTableModel whorModel;
	private Object[] whourData = null;
	
	private JTable table_randevu;
	private DefaultTableModel randevuModel;
	private Object[] randevuData=null;
	
	Appointment appointment = new Appointment();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);//buranýn içine doctoru eklememiz lazým
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
	public DoctorGUI(Doctor doctor) {//bunun içine de doctor classýný atmamýz lazým
		
		
		
		//Randevularýn listelenmesi için
		randevuModel = new DefaultTableModel();
		Object[] colRandevu = new Object[2];
		colRandevu[0] = "Hasta Adý";
		colRandevu[1] = "Tarih";
		randevuModel.setColumnIdentifiers(colRandevu);
		randevuData= new Object[2];
		
		for(int i=0; i<appointment.getDoctorList(doctor.getId()).size();i++) {
			randevuData[0] = appointment.getDoctorList(doctor.getId()).get(i).getHastaName();
			randevuData[1] = appointment.getDoctorList(doctor.getId()).get(i).getAppDate();
			randevuModel.addRow(randevuData);
		}
		
		
		
		
		
		
		
		
		whorModel = new DefaultTableModel();
		Object[] colWhour= new Object[2];
		colWhour[0] = "ID";    //table içinde 2 tane sütun oluþturuyoruz ve bunlarýn baþlýklarýný id ve tarih olarak atýyoruz.
		colWhour[1] = "Tarih";
		whorModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		
		try {
			for(int i=0; i<doctor.getWhourList(doctor.getId()).size();i++) {
				whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();  
				whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
				//object arrayi olarak oluþturduðumuz whourData'nýn 1. elemanýna doctor'un id'sini alýayoruz
				//o id'ye göre birinci döngünün ilk workhour id'sini 
				//ayný þekilde 1. döngünün ilk workhour tarihini atýyoruz.
				
				whorModel.addRow(whourData);//Oluþturulan DefTableModel içine yukarýda çektiðimiz
				//datalarý gönderiyoruz.
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750,500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoþgeldiniz Sayýn " + doctor.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 232, 31);
		contentPane.add(lblNewLabel);
		
		JButton btn_cikis = new JButton("\u00C7\u0131k\u0131\u015F Yap");
		btn_cikis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btn_cikis.setBounds(498, 16, 124, 23);
		contentPane.add(btn_cikis);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 74, 729, 386);
		contentPane.add(w_tab);
		
		JPanel w_wHour = new JPanel();
		w_wHour.setBackground(Color.WHITE);
		w_tab.addTab("Çalýþma Saatleri", null, w_wHour, null);
		w_wHour.setLayout(null);
		
		JDateChooser select_date = new JDateChooser();
		select_date.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		select_date.setBounds(10, 11, 131, 28);
		w_wHour.add(select_date);
		
		JComboBox select_time = new JComboBox();
		select_time.setFont(new Font("Tahoma", Font.BOLD, 11));
		select_time.setModel(new DefaultComboBoxModel(new String[] {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:30", "14:00", "14:30", "15:00", "15:30"}));
		select_time.setBounds(151, 11, 72, 28);
		w_wHour.add(select_time);
		
		
		
		
		//Ekle butonuna basýldýðýnda eklenecek tarihi ve saati simpleDateFormat olarak almaya çalýþýcaz.
		JButton btn_add_wHour = new JButton("Ekle");
		btn_add_wHour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date ="";
				try {
					date = sdf.format(select_date.getDate()); //Gui'de alýnan tarihi sdf'e çevirdik
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
					if(date.length()==0) {
					Helper.showMsg("Lütfen bir tarih seçiniz");
				}
					else {
						
						String time = " " + select_time.getSelectedItem().toString()+":00";
						String selectDate = date+time;
						
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if(control) {
							Helper.showMsg("success");
							try {
								updateWhourModel(doctor); //Tekrar güncellenmesini saðlýyoruz.
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
							
						}
						else {
							Helper.showMsg("error");
						}
					}
				
				
				
				
				
			}
		});
		
		
		
		btn_add_wHour.setBounds(233, 11, 72, 28);
		w_wHour.add(btn_add_wHour);
		
		JScrollPane w_scroll_wHour = new JScrollPane();
		w_scroll_wHour.setBounds(10, 50, 704, 298);
		w_wHour.add(w_scroll_wHour);
		
		table_wHour = new JTable(whorModel); // table'ýn constructoruna model atanýr.
		w_scroll_wHour.setViewportView(table_wHour);
		
		
		
		
		//Seçileni table'dan silmek için 
		
		JButton btn_deleteWhour = new JButton("Sil");
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_wHour.getSelectedRow(); //Seçilen row'u bir int deðer içine atýyoruz.
				if(selRow>=0) {
				
					String selectRow = table_wHour.getModel().getValueAt(selRow, 0).toString();
					//seçilen rowdaki 1. index olan id'yi selectRow içine gönderiyoruz.
					int selID = Integer.parseInt(selectRow);
					//seçilen id'yi int'e çeviriyoruz.
					boolean control = doctor.deleteWhour(selID);
					//Dönen int deðerini id olduðu için o id deðerine göre
					// silme iþlemini yapýyoruz.
					if(control) {
						Helper.showMsg("success");
						try {
							updateWhourModel(doctor);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						Helper.showMsg("error");
					}
					
				}
				
				else {
					Helper.showMsg("Lütfen bir Tarih seçiniz");
				}
				}
		});
		btn_deleteWhour.setBounds(329, 11, 72, 28);
		w_wHour.add(btn_deleteWhour);
		
		JPanel panel = new JPanel();
		w_tab.addTab("Randevular", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 499, 336);
		panel.add(scrollPane);
		
		table_randevu = new JTable(randevuModel);
		scrollPane.setViewportView(table_randevu);
	}
	
	
	//Her ekleme ve Silme iþleminden sonra table'ýn güncellenmesi için bu metodu yazýyoruz 
	//ve güncellenecek zamanlarýnda gerekli yerlere bu metodu çaðýrýyoruz.
	
	public void updateWhourModel(Doctor doctor) throws SQLException {//bu doktor nesnesini globalden alMAMAlý. Burada kendimiz oluþturuyorzu.
		//Yani içerideki doctor nesnesini parametre olarak atmak için burada yeni bir parametre ile tanýmlýyoruz.
		DefaultTableModel clearModel= (DefaultTableModel) table_wHour.getModel();
		clearModel.setRowCount(0); //Bütün rowlarý sildik
		
		for(int i=0; i<doctor.getWhourList(doctor.getId()).size();i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whorModel.addRow(whourData);

		
	}
	
	}
}
