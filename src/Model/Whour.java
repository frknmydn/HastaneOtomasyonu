package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBCollection;

public class Whour {

	private int id;
	private int doctor_id;
	private String doctor_name, wdate, status;
	 
	DBCollection conn = new DBCollection();
	Connection con = conn.getConnection();
	Statement st = null;
	ResultSet rs = null;					//Database iþlemleri için gerekli referanslar tanýmlanýr
	
	PreparedStatement preparedStatement = null;

	public Whour(int id, int doctor_id, String doctor_name, String wdate, String status) {
		
		this.id = id;
		this.doctor_id = doctor_id;
		this.doctor_name = doctor_name;
		this.wdate = wdate;
		this.status = status;
	}
	
	public Whour() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	
	
	
	
}
