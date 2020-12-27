package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBCollection;

public class Clinic {
	DBCollection conn = new DBCollection();
	Connection con = conn.getConnection();
	Statement st = null;
	ResultSet rs = null;					//Database iþlemleri için gerekli referanslar tanýmlanýr
	
	PreparedStatement preparedStatement = null;
	
	private int id;
	private String name;
	
	public Clinic() {
		
	}
	public Clinic(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Clinic> getList(){
		ArrayList<Clinic> list = new ArrayList<>();
		
		Clinic obj;//Clinic tipinde bir obj belirliyoruz böylece clinic içindeki fieldlarý
		//(id,name) DB'den gelen id ve name'i arraylistimiz içine atabilirz.
		Connection con = conn.getConnection();
		
		try {
			
			st= con.createStatement();
			rs=st.executeQuery("SELECT * FROM clinic");
			
			while(rs.next()){//iterator
				obj=new Clinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				list.add(obj);
				
			}
				
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		finally {
			try {
				st.close();
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
		}
		
		return list;
		
		
	}
	
	
	
	
	
	public boolean addClinic(String name) {
		
		String query = "INSERT INTO clinic " + "(name) VALUES"+
				"(?)";
		
		boolean key =false;
		
		
		try {
			st=con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name); //1. soru iþareti içine dýþarýdan gelen name'i ata
			preparedStatement.executeUpdate();//yazýlan iþlemleri gerçekleþtir.
			
			key=true; // eðer iþlemler yapýlýrsa key trueya döner
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		if(key) 
			return true; 
		
		
		else
		return false;
	}


	public boolean deleteClinic(int id) {
		
		//String query = "DELETE FROM USER (id) + VALUES (?)";
		String query = "DELETE FROM clinic WHERE id = ?";
		
		
		boolean key =false;
		Connection con = conn.getConnection();
		
		try {
		
			
			
			
			st=con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id); //soru iþareti yerine id'yi getir
			
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


	public boolean updateClinic(int id,String name) {
		
		
		String query = "UPDATE clinic set name =? WHERE id=?";
		
		
		boolean key =false;
		
		
		try {
		
			
			
			Connection con = conn.getConnection();
			st=con.createStatement();
			
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, id);
			
			
			
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
	
	public Clinic getFetch(int id) {
		Clinic c= new Clinic();
		Connection con = conn.getConnection();
		try {
			
			st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM clinic WHERE id = "+ id);
			while(rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
		
		
	}
	
	//Klinik id'ye göre doktorlarý listelemek için.
	//Bu metot klinik id'yi yazýdðýnda o klinik id'de çalýþan doktorlarý listeler.
	
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
	



}
