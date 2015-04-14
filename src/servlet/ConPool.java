package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConPool {
	private static String DriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static Configuration config=new Configuration();
	private static Log log=LogFactory.getLog(Configuration.class);
	//private static String UserName="sa";
	//private static String Password="up";
	
	public static Connection getConnection(String url,String user,String psw){
		Connection conn=null;
		try{
			Class.forName(DriverName);
			conn=DriverManager.getConnection(url,user,psw);		
		}catch(ClassNotFoundException e){
			//e.printStackTrace();
			log.error(e);
			return null;
		}catch(SQLException e){
			//e.printStackTrace();
			log.error(e);
			return null;
		}
		return conn;
	}
		
    public static Configuration getConfig() {
		return config;
	}
}
