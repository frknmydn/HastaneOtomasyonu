package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User{
	
	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.getConnection();
	PreparedStatement preparedStatement =null ;
	
	//userdan constructorlar oluþturulur.
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Doctor(int id, String tcno, String password, String name, String type) {
		super(id, tcno, password, name, type);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean addWhour(int doctor_id, String doctor_name, String wDate) {
		
		int key = 0; //Bu keyin amacý veri eklendi mi eklenmedi mi kontrolü için
		int count = 0; //ayný doktor ayný saate eklenemsini count ile engellicez.
		
		String query = "INSERT INTO whour" + "(doctor_id,doctor_name,wdate) VALUES "
		+ "(?,?,?)";
		
		try {
			
			st=con.createStatement();
			rs= st.executeQuery("SELECT * FROM whour WHERE satatus ='a' AND doctor_id=  " + doctor_id + 
					" AND wdate ='"+ "'" );
		
		while(rs.next()) {
			count++;  // eðer resultset ile deðer bulursa count'ý 1 arttýr.
			break;
			
			
		}
			
			
		
		if(count==0) {
			
			
				
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1,doctor_id);
				preparedStatement.setString(2,doctor_name);    //Db içinden gelenleri  metottan gelenlere update ettik
				preparedStatement.setString(3,wDate);
				preparedStatement.executeUpdate(); // çalýþtýrdýk.
				
				
		}
				
			key=1;	
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
			
			
			
			if(key==1) {
				return true;
			}
			
			else {
				return false;
			}
			

	}
	
	
	
	
	
	public ArrayList<Whour> getWhourList(int doctor_id) throws SQLException{
		
		ArrayList<Whour> list = new ArrayList<>();
		 //Buradaki metot DB içindeki bütün doktorlarý toplamamýzý saðlar.
		
		
		Whour obj;
		try {
			
			st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM whour WHERE satatus = 'a' AND doctor_id = " + doctor_id);
			
			//st=(Statement) con.createStatement();
			
			while(rs.next()) {
				obj= new Whour();
				
				
				
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt(doctor_id));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setStatus(rs.getString("satatus"));
				obj.setWdate(rs.getString("wdate"));
				list.add(obj); //yukarýda Whourdan bir nesne ürettiðinde listenin içerisine tek tek atacak.
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
			try {
				st.close();
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return list;
	
		
		
	}
	
	
public boolean deleteWhour(int id) {
		
		//String query = "DELETE FROM USER (id) + VALUES (?)";
		String query = "DELETE FROM whour WHERE id = ?";
		
		
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


	
	

}
