package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import servlet.FoodDBPool;
import servlet.HZPDBPool;

public class DaoTask2 {
	public static List<String> getTestDataDao(String url) throws SQLException {
		List<String> labApplyNoList = new ArrayList<String>();
		//Connection conn = ConPool.getConnection(url, user, psw);
		Connection conn=null;
		CallableStatement proc = null;
		ResultSet rs = null;
		String wCall = null;
		try {
			if(url.equals("food"))
			    conn = FoodDBPool.ds.getConnection();
			else if(url.equals("hzp"))
				conn=HZPDBPool.ds.getConnection();
			wCall = "{call Pro_GetLabApplyNoForTestData()}";
			proc = conn.prepareCall(wCall);
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				labApplyNoList.add(rs.getString("LabApplyNo")); // 需要高斯确认是根据报验号读取检测结果，还是根据样品号读取检测结果
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
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return labApplyNoList;
	}
}
