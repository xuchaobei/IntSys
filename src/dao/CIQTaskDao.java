package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import servlet.FoodDBPool;
import servlet.HZPDBPool;

public class CIQTaskDao {
    public static List<String> getDeclNoForCIQDao(String url) throws SQLException{
    	List<String> declNoList=new ArrayList<String>();
    	Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		ResultSet rs = null;
		String wCall = null;
		wCall = "{call Pro_GetDeclNoForCIQProcessDate()}";
		try {
			proc = conn.prepareCall(wCall);
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				declNoList.add(rs.getString("DeclNo")); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	return declNoList;
    } 
    
    public static void updateCIQDeclProcessDate(String url,String declNo,Timestamp leaveTime,int processID) throws SQLException{
    	Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		ResultSet rs = null;
		String wCall = null;
		wCall = "{call Pro_UpdateCIQDeclProcessDate(?,?,?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1,declNo);
			proc.setTimestamp(2,leaveTime);
			proc.setInt(3, processID);
			proc.execute();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
