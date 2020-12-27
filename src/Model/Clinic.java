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
	ResultSet rs = null;					//Database i�lemleri i�in gerekli referanslar tan�mlan�r
	
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
		
		Clinic obj;//Clinic tipinde bir obj belirliyoruz b�ylece clinic i�indeki fieldlar�
		//(id,name) DB'den gelen id ve name'i arraylistimiz i�ine atabilirz.
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
			preparedStatement.setString(1, name); //1. soru i�areti i�ine d��ar�dan gelen name'i ata
			preparedStatement.executeUpdate();//yaz�lan i�lemleri ger�ekle�tir.
			
			key=true; // e�er i�lemler yap�l�rsa key trueya d�ner
			
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
			preparedStatement.setInt(1, id); //soru i�areti yerine id'yi getir
			
			preparedStatement.executeUpdate();//Sorguyu �al��t�r
			
			key=true; // e�er i�lemler yap�l�rsa key trueya d�ner
			
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
			
			
			
			preparedStatement.executeUpdate();//Sorguyu �al��t�r
			
			key=true; // e�er i�lemler yap�l�rsa key trueya d�ner
			
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
	
	//Klinik id'ye g�re doktorlar� listelemek i�in.
	//Bu metot klinik id'yi yaz�d��nda o klinik id'de �al��an doktorlar� listeler.
	
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException{
		ArrayList<User> list = new ArrayList<>();
		
		
		
		User obj;
		try {
			
			st=con.createStatement();
			//worker'� user ile birle�tir.
			rs=st.executeQuery("SELECT u.*,w.* FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id =  "+ clinic_id);
			
			//st=(Statement) con.createStatement();
			
			while(rs.next()) {
				obj = new User(rs.getInt("u.id"),rs.getString("u.tcno"),rs.getString("u.password"),rs.getString("u.name"),
						rs.getString("u.type"));
				
				if(rs.getString("type").equals("doktor")) {//sadece doktorlar� getirmek i�in
				
				
				list.add(obj); //yukar�da Userdan bir nesne �retti�inde listenin i�erisine tek tek atacak.
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
