package Proje;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class VeriBaglanti {
	
	static Connection myConn;
	static Statement myStat;
	
	static ResultSet baglan()
	{
		ResultSet myRs = null;
		
		try {
			
			myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/veri","root","1813");
			myStat = (Statement) myConn.createStatement();
			myRs = myStat.executeQuery("select * from fisveri");

			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return myRs;
	}
	
	static void ekle(String sql_sorgu)
	{
		try {
			myStat.executeUpdate(sql_sorgu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static ResultSet araIsletmeAdi(String bilgi){
		
		ResultSet myRs2=null;
		
		try {
			
			myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/veri","root","1813");
			myStat = (Statement) myConn.createStatement();
			myRs2 = myStat.executeQuery("select * from fisveri where isletmeAdi = '" + bilgi + "';");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return myRs2;
	}
	
static ResultSet araTarih(String bilgi){
		
		ResultSet myRs2=null;
		
		try {
			
			myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/veri","root","1813");
			myStat = (Statement) myConn.createStatement();
			myRs2 = myStat.executeQuery("select * from fisveri where tarih = '" + bilgi + "';");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return myRs2;
	}
	
	

}
