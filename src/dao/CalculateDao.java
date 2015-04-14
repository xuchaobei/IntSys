package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.LabApplyBean;
import servlet.FoodDBPool;
import servlet.HZPDBPool;

public class CalculateDao {
	public static void calculateDailyInspInfo(String url) throws SQLException{
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
    	CallableStatement proc = null;
		
		try {
			proc = conn.prepareCall("{call Pro_CalculateDailyInspInfo()}");
			proc.execute();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {				
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	};
	
	public static void calculateMonthlyInspInfo(String url) throws SQLException{
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
    	CallableStatement proc = null;
		
		try {
			proc = conn.prepareCall("{call Pro_CalculateMonthlyInspInfo()}");
			proc.execute();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {				
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	};
}
