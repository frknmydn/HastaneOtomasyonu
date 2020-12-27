package Model;

//import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Statement;

import java.sql.*;

public class Bashekim extends User{
	
	Connection con = conn.getConnection();
	Statement st = null;
	ResultSet rs = null;
	
	PreparedStatement preparedStatement = null;
	
	public Bashekim(int id, String tcno, String password, String name, String type) {
		super(id, tcno, password, name, type);
		// TODO Auto-generated constructor stub
	}
	
	public Bashekim() {
		
	}
	
	public ArrayList<User> getDoctorList() throws SQLException{
		ArrayList<User> list = new ArrayList<>();
		 //Buradaki metot DB içindeki bütün doktorlarý toplamamýzý saðlar.
		
		
		User obj;
		try {
			
			st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM user");
			
			//st=(Statement) con.createStatement();
			
			while(rs.next()) {
				obj = new User(rs.getInt("id"),rs.getString("tcno"),rs.getString("password"),rs.getString("name"),
						rs.getString("type"));
				
				if(rs.getString("type").equals("doktor")) {//sadece doktorlarý getirmek için
				
				
				list.add(obj); //yukarýda Userdan bir nesne ürettiðinde listenin içerisine tek tek atacak.
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			try {
				st.close();
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
			
		}
		
		
		return list;
		
		
	}
	
	
	
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException{
		ArrayList<User> list = new ArrayList<>();
		
		
		
		User obj;
		try {
			
			st=con.createStatement();
			//worker'ý user ile birleþtir.
			rs=st.executeQuery("SELECT u.*,w.* FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id =  "+ clinic_id);
			
			//st=(Statement) con.createStatement();
			
			while(rs.next()) {
				obj = new User(rs.getInt("u.id"),rs.getString("u.tcno"),rs.getString("u.password"),rs.getString("u.name"),
						rs.getString("u.type"));
				
				if(rs.getString("type").equals("doktor")) {//sadece doktorlarý getirmek için
				
				
				list.add(obj); //yukarýda Userdan bir nesne ürettiðinde listenin içerisine tek tek atacak.
				}
				else
					System.out.println("Bir sýkýntý var");
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catckardeh block
			e.printStackTrace();
		}
		finally {
			
			try {
				st.close();
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
			
		}
		
		
		return list;
		
		
	}
	
	
	
	
	
	
	
	
	public boolean addDoctor(String tcno, String password, String name) {
		
		String query = "INSERT INTO user " + "(tcno,password,name,type) VALUES"+
		"(?,?,?,?)";
		
		boolean key =false;
		
		try {
			st=con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno); //1. soru iþareti içine tcno'yu ata
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, "doktor"); //Type'ý elemizle girdik çünkü sadece doktor eklicez
			preparedStatement.executeUpdate();//Sorguyu çalýþtýr
			
			key=true; // eðer iþlemler yapýlýrsa key trueya döner
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		if(key) 
			return true; 
		
		
		else
		return false;
	}
	
	
	
public boolean deleteDoctor(int id) {
		
		//String query = "DELETE FROM USER (id) + VALUES (?)";
		String query = "DELETE FROM user WHERE id = ?";
		
		
		boolean key =false;
		
		try {
		
			
			
			
			st=con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id); //soru iþareti yerine id'yi getir
			
			preparedStatement.executeUpdate();//Sorguyu çalýþtýr
			
			key=true; // eðer iþlemler yapýlýrsa key trueya döner
			//yani yukarýdaki fonksiyonu if içinde yazabiliriz.
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		if(key) 
			return true; 
		
		
		else
		return false;
	}



public boolean updateDoctor(int id,String tcno, String password, String name) {
	
	
	String query = "UPDATE user set name =?,tcno=?,password=? WHERE id=?";
	
	
	boolean key =false;
	
	try {
	
		
		
		
		st=con.createStatement();
		
		preparedStatement = con.prepareStatement(query);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, tcno);
		preparedStatement.setString(3, password);
		preparedStatement.setInt(4, id);
		
		
		
		preparedStatement.executeUpdate();//Sorguyu çalýþtýr
		
		key=true; // eðer iþlemler yapýlýrsa key trueya döner
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	if(key) 
		return true; 
	
	
	else
	return false;
}


public boolean addWorker(int user_id, int clinic_id) {
	
	String query = "INSERT INTO worker " + "(user_id, clinic_id) VALUES"+
	"(?,?)";
	int count=0; 
	
	
	boolean key =false;
	
	try {
		st=con.createStatement();
		//Bir doktoru sadece bir klinikte çalýþtýrmak için kullanýyoruz.
		//Eðer resultset Doktoru bulursa count 1 olacaðýndan ekleme iþlemi çalýþmayacak
		
		rs= st.executeQuery("SELECT * FROM worker WHERE user_id = "+ user_id);
		
		
		
		while(rs.next()) {
			//"SELECT * FROM clinic WHERE id = "+ id
			count++;
			// Yani eklenecek deðerden bir tane varsa bir tane daha eklenmesini engelliyoruz.
		}
		if(count==0) { //ama eðer rs db içinde o id'ye ait veri bulamazsa count 0 olacaðýndan veri ekleme iþlemine geçiþ yapýlabilir.
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, user_id); //1. soru iþareti içine user_id'yi ata
			preparedStatement.setInt(2, clinic_id); //2. soru iþareti içine clinic_id'yi ata
			
			preparedStatement.executeUpdate();//Sorguyu çalýþtýr
			
		}
		
		key=true; // eðer iþlemler yapýlýrsa key trueya döner
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	if(key) 
		return true; 
	
	
	else
	return false;
}
/*


public boolean addWorker(int user_id, int clinic_id) {
	
	String query = "INSERT INTO worker " + "(user_id, clinic_id) VALUES"+
	"(?,?)";
	
	
	boolean key =false;
	
	try {
		st=con.createStatement();
		
		
		
		 // Yani eklenecek deðerden bir tane varsa bir tane daha eklenmesini engelliyoruz.
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, user_id); //1. soru iþareti içine tcno'yu ata
			preparedStatement.setInt(2, clinic_id);
			
			preparedStatement.executeUpdate();//Sorguyu çalýþtýr
			
		
		
		key=true; // eðer iþlemler yapýlýrsa key trueya döner
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	if(key) 
		return true; 
	
	
	else
	return false;
}
*/



	
	
	

}
