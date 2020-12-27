package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import Helper.Helper;

public class Hasta extends User {

	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.getConnection();
	PreparedStatement preparedStatement = null;

	// user extend edildikten sonra superClasstan constructor extend edilir.

	public Hasta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Hasta(int id, String tcno, String password, String name, String type) {
		super(id, tcno, password, name, type);
		// TODO Auto-generated constructor stub
	}

	public boolean register(String tcno, String password, String name) {

		int key = 0; // Bu keyin amacý veri eklendi mi eklenmedi mi kontrolü için
		int count = 0; // ayný user ayný saate eklenemsini count ile engellicez.

		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES " + "(?,?,?,?)";

		try {

			st = con.createStatement();

			rs = st.executeQuery("SELECT * FROM user WHERE tcno = '" + tcno + "'");// ayný tc'ye sahip kullanýocý var mý
																					// diye.

			while (rs.next()) {

				count++; // eðer resultset ile deðer bulursa count'ý 1 arttýr.
				Helper.showMsg("Bu T.C. no'ya sahip daha önce kayýt vardýr");
				break;

			}

			if (count == 0) {

				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, tcno);
				preparedStatement.setString(2, password); // Db içinden gelenleri metottan gelenlere update ettik
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, "hasta"); // type'ý statik olarak hasta yapýyoruz. Çünkü Register kýsmý
															// sadece hastalar için olacak

				preparedStatement.executeUpdate(); // çalýþtýrdýk.
				key = 1;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key == 1) {
			return true;
		}

		else {
			return false;
		}

	}

	// Hastanýn appointment eklemesi için
	public boolean addAppointment(int doctor_id, String doctor_name, int hasta_id, String hasta_name, String appDate) {

		int key = 0; // Bu keyin amacý veri eklendi mi eklenmedi mi kontrolü için

		String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES "
				+ "(?,?,?,?,?)";

		try {

			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, doctor_id);
			preparedStatement.setString(2, doctor_name);
			preparedStatement.setInt(3, hasta_id);
			preparedStatement.setString(4, hasta_name);
			preparedStatement.setString(5, appDate);

			preparedStatement.executeUpdate(); // çalýþtýrdýk.
			key = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key == 1) {
			return true;
		}

		else {
			return false;
		}

	}

	// Tarihin seçildiðinde statüsünü deðiþtirmek laýzm.
	public boolean updateWhourStatus(int doctor_id, String wdate) {

		int key = 0; // Bu keyin amacý veri eklendi mi eklenmedi mi kontrolü için

		String query = "UPDATE whour SET satatus = ? WHERE doctor_id = ? AND wdate = ?";

		try {

			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "p");
			preparedStatement.setInt(2, doctor_id);
			preparedStatement.setString(3, wdate);
			preparedStatement.executeUpdate(); // çalýþtýrdýk.
			key = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key == 1) {
			return true;
		}

		else {
			return false;
		}

	}
	
	public boolean deleteAppointment(int id) {
		
		
		String query = "DELETE FROM appointment WHERE id = ?";
		boolean key = false;
		
		try {
			st=con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			key = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(key) 
			return true;
		
		else
			return false;
			
		
		
		
		
	}

}
