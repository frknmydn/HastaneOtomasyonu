package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBCollection;

public class Appointment {
	DBCollection conn = new DBCollection();
	Connection con = conn.getConnection();
	Statement st=null;
	ResultSet rs=null;
	PreparedStatement preparedStatement;
	
	
	private int id, doctorId,hastaId;
	private String doctorName,hastaName,appDate;
	
	public Appointment(int id, int doctorId, int hastaId, String doctorName, String hastaName, String appDate) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.hastaId = hastaId;
		this.doctorName = doctorName;
		this.hastaName = hastaName;
		this.appDate = appDate;
	}
	
	public Appointment(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getHastaId() {
		return hastaId;
	}

	public void setHastaId(int hastaId) {
		this.hastaId = hastaId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getHastaName() {
		return hastaName;
	}

	public void setHastaName(String hastaName) {
		this.hastaName = hastaName;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	
	//Hastalara ait randevularý listelemek için
	public ArrayList<Appointment> getHastaList(int hasta_id){
		ArrayList<Appointment> list = new ArrayList<>();
		
		Appointment obj;
		//con=conn.getConnection();
		
		
		
		try {
			
			st= con.createStatement();
			rs=st.executeQuery("SELECT * FROM appointment where hasta_id =" + hasta_id);
			
			while(rs.next()){//iterator
				obj=new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorId(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaId(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setAppDate(rs.getString("app_date"));
				
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
	
	//Doktorlara ait randevularý listelemek için.
	public ArrayList<Appointment> getDoctorList(int doctor_id){
		ArrayList<Appointment> list = new ArrayList<>();
		
		Appointment obj;
		con=conn.getConnection();
		
		
		
		try {
			
			st= con.createStatement();
			rs=st.executeQuery("SELECT * FROM appointment where doctor_id= " + doctor_id);
			
			while(rs.next()){//iterator
				obj=new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorId(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaId(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setAppDate(rs.getString("app_date"));
				
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
	
	
	
	
	
	
	
	
	

}
