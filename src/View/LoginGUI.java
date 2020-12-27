package View;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField txt_hastaTc;
	private JTextField txt_doktorTc;
	private JPasswordField txt_doktorPassword;
	private DBCollection conn = new DBCollection();
	private JPasswordField txt_hastaPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Otomasyonu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("hastaneLogo2.png")));
		lbl_logo.setBounds(200, 25, 51, 33);
		w_pane.add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Y\u00F6netim Sistemine Ho\u015F Geldiniz");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblNewLabel.setBounds(118, 69, 318, 14);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_personelLogin = new JTabbedPane(JTabbedPane.TOP);
		w_personelLogin.setBounds(10, 115, 474, 245);
		w_pane.add(w_personelLogin);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		w_personelLogin.addTab("Hasta Giri�i", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblTcNumaranzGiriniz = new JLabel("T.C numaran\u0131z\u0131 Giriniz");
		lblTcNumaranzGiriniz.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblTcNumaranzGiriniz.setBounds(10, 34, 132, 15);
		panel.add(lblTcNumaranzGiriniz);
		
		JLabel lblifreniziGiriniz = new JLabel("\u015Eifrenizi Giriniz");
		lblifreniziGiriniz.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblifreniziGiriniz.setBounds(10, 77, 132, 15);
		panel.add(lblifreniziGiriniz);
		
		txt_hastaTc = new JTextField();
		txt_hastaTc.setBounds(152, 32, 173, 20);
		panel.add(txt_hastaTc);
		txt_hastaTc.setColumns(10);
		
		
		
		//Kay�t olma sayfas�n� a�aca��z.
		JButton btn_kayit = new JButton("Kay\u0131t Ol");
		btn_kayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rgui= new RegisterGUI();
				rgui.setVisible(true);  // kay�t olma sayfas�n�n visibilty a��l�r
				dispose(); // login sayfas� �ld�r�l�r.
				
			}
		});
		
		
		
		btn_kayit.setBounds(53, 137, 111, 37);
		panel.add(btn_kayit);
		
		JButton btn_giris = new JButton("Giri\u015F Yap");
		btn_giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] input = txt_hastaPassword.getPassword(); // passwordu bir char arrayine atmam�z gerekiyor
				String pass = new String(input); // ard�ndan char arrayini String'e d�n��t�rmek gerekiyor ki giri� i�in equals komutunu kullanal�m.
				if(txt_hastaTc.getText().length()==0|| txt_hastaPassword.getPassword().length==0) {
					
					Helper.showMsg("fill");
				}
				else {
					boolean key=true;
					try {
						Connection con =conn.getConnection();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("Select * From user");
						System.out.println(input);
						
						
						while(rs.next()) {// user tablosunda d�ner
							if(txt_hastaTc.getText().equals(rs.getString("tcno")) && pass.equals(rs.getString("password")) ) {
							if(rs.getString("type").equals("hasta")) {
								Hasta hasta = new Hasta();
								hasta.setId(rs.getInt("id"));   // olu�turulacak hasta  nesnesinin gerekli contributes'lar� tan�mlan�r.
								hasta.setPassword("password");  //
								hasta.setTcno(rs.getString("tcno"));
								hasta.setName(rs.getString("name"));
								hasta.setType(rs.getString("type"));
								System.out.println(rs.getString("name"));
								HastaGUI hGUI = new HastaGUI(hasta); //BhekimGui i�erisine burada al�nan ba�hekimi g�nderiyoz.
								
								hGUI.setVisible(true); // ba�hekim gui'si a��l�r
								
								dispose();//Login jframe'i �ld�r�l�r.
								key=false;
							}
							
						
							
							
							
							
							}
							
							
							
						}
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("Kullan�c� bilgilerini kontrol ediniz veya kay�t olunuz");
					}
					
					
				}
				
			}
		});
		btn_giris.setBounds(213, 137, 112, 37);
		panel.add(btn_giris);
		
		txt_hastaPassword = new JPasswordField();
		txt_hastaPassword.setBounds(152, 75, 173, 20);
		panel.add(txt_hastaPassword);
		
		JPanel w_hastaLogin = new JPanel();
		w_personelLogin.addTab("Doktor Giri�i", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 0, 469, 217);
		w_hastaLogin.add(panel_1);
		
		JLabel lblTcNumaranzGiriniz_1 = new JLabel("T.C numaran\u0131z\u0131 Giriniz");
		lblTcNumaranzGiriniz_1.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblTcNumaranzGiriniz_1.setBounds(10, 34, 132, 15);
		panel_1.add(lblTcNumaranzGiriniz_1);
		
		JLabel lblifreniziGiriniz_1 = new JLabel("\u015Eifrenizi Giriniz");
		lblifreniziGiriniz_1.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblifreniziGiriniz_1.setBounds(10, 77, 132, 15);
		panel_1.add(lblifreniziGiriniz_1);
		
		txt_doktorTc = new JTextField();
		txt_doktorTc.setColumns(10);
		txt_doktorTc.setBounds(152, 32, 173, 20);
		panel_1.add(txt_doktorTc);
		
		txt_doktorPassword = new JPasswordField();
		txt_doktorPassword.setBounds(152, 75, 173, 20);
		panel_1.add(txt_doktorPassword);
		
		JButton btn_doktorLogin = new JButton("Giri\u015F Yap");
		btn_doktorLogin.addActionListener(new ActionListener() {
			char[] input = txt_doktorPassword.getPassword();
			String pass2 = new String(input);
			
			public void actionPerformed(ActionEvent e) {
				if(txt_doktorTc.getText().length()==0 || txt_doktorPassword.getPassword().length==0) {

					Helper.showMsg("fiil");
					
					
				}
				else {
					
					try {
						Connection con =conn.getConnection();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("Select * From user");
						
						
						while(rs.next()) {//Yukar�da yazd���m�z field olan txtdoktor k�sm�n� ve password k�sm�n�
							//DB'deki userlar aras�nda dola��p buluyoruz. Ald���m�z bilgilerle uyu�an DB kullan�c�s�n�
							//Bhekim nesnesi olu�turarak at�yoruz.
							if(txt_doktorTc.getText().equals(rs.getString("tcno")) && pass2.equals(rs.getString("password")) ) {
							if(rs.getString("type").equals("bashekim")) {
								Bashekim bhekim = new Bashekim();
								bhekim.setId(rs.getInt("id"));
								bhekim.setPassword("password");
								bhekim.setTcno(rs.getString("tcno"));
								bhekim.setName(rs.getString("name"));
								bhekim.setType(rs.getString("type"));
								System.out.println(rs.getString("name"));
								BashekimGUI bGUI = new BashekimGUI(bhekim); //BhekimGui i�erisine burada al�nan ba�hekimi g�nderiyoz.
								
								bGUI.setVisible(true); // ba�hekim gui'si a��l�r
								
								dispose();//Login jframe'i �ld�r�l�r.
							}
							
							
							// a�a��da bir adet doktor nesnesi �reticez ve gerekli value'lar�n� vericez
							//Ard�ndan doktorGUi a��lacak ve a��lan gui ile beraber
							//DB'den �ekti�imiz doktorun verilerini statik olarak
							//doctorGUI'sinde bir doctor nesnesi olu�turarak tutucaz.
							//Ard�ndan GUI i�erisinde DoctorGUI nin olu�turuldu�u yere
							// parametre olarak DoctorGUI i�inde olu�turdu�umuz
							//statik doctor nesnelerini at�caz.
							//B�ylelikle burada olu�turulan doctorun field'lar�
							//Olu�an gui ile statik olarak tutulmu� ve fieldlar�na
							//ula�m�� olaca��z.(Login mant���)
							
							if(rs.getString("type").equals("doktor")) {
								Doctor doctor = new Doctor();
								
								doctor.setId(rs.getInt("id"));
								doctor.setPassword("password");
								doctor.setTcno(rs.getString("tcno"));
								doctor.setName(rs.getString("name"));
								doctor.setType(rs.getString("type"));
								System.out.println(rs.getString("name"));
								DoctorGUI dGUI = new DoctorGUI(doctor);
								dGUI.setVisible(true);
								dispose();
								
							}
							
							
							}
							
							
							
						}
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
						
				}
				
				
				
			}
		});
		
		btn_doktorLogin.setBounds(184, 137, 111, 37);
		panel_1.add(btn_doktorLogin);
	}
}
