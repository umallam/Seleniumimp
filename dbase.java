

import java.sql.*;
import java.util.Date;

public class dbase {

	    public static void main(String[] args) {
	          String url = "jdbc:mysql://localhost:3306/";
	          Date dt= new Date();
	          System.out.println(dt);
	          String dbName = "school_tirupati";
	          String driver = "com.mysql.jdbc.Driver";
	          String userName = "root";
	          String password = "pass";
	          try {
	          Class.forName(driver).newInstance();
	          Connection conn = DriverManager.getConnection(url+dbName,userName,password);
	          Statement st = conn.createStatement();
	          ResultSet res = st.executeQuery("SELECT * FROM  admission_register_high");
	          while (res.next()) {
	        	//res.getArray("First_Name");
	        	//res.getArray("Last_Name");
	          //int id = res.getInt("id");
	          //String msg = res.getString("msg");""
	        	  
	          System.out.println(res.getString("First_Name") + "\t" + res.getString("Last_Name"));
	          }
	          /*
	           int val = st.executeUpdate("select * from ");
	          if(val==1)
	              System.out.print("Successfully inserted value");
	          conn.close();
	          */
	          } catch (Exception e) {
	          e.printStackTrace();
	          }
	          }

}
