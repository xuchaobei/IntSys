package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import servlet.FoodDBPool;
import servlet.HZPDBPool;

public class DelTaskDao {
    public static List<String> getLabApplyNo(String url) throws SQLException{
    	List<String> labApplyIDList=new ArrayList<String>();
    	Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		ResultSet rs = null;
		String wCall = null;
		wCall = "{call Pro_GetLabApplyNoForDelSample()}";
		try {
			proc = conn.prepareCall(wCall);
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				labApplyIDList.add(rs.getString("ApplyID")); // 需要高斯确认是根据报验号读取检测结果，还是根据样品号读取检测结果
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
    	return labApplyIDList;
    } 
    
    public static void updateDelSampleFlg(String labApplyID,String url) throws SQLException{
    	
    	Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;		
		String wCall = null;
		wCall = "{call Pro_UpdateDelSampleFlg(?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1,labApplyID);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}    	
    } 
}
