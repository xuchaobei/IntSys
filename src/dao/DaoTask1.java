package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import servlet.FoodDBPool;
import servlet.HZPDBPool;

public class DaoTask1 {
	
	public static void SaveLabAppliantDao(String url,
			String userName,String loginName,String userType,String userID,String sex,String userStatus,String telePhone,String fax,String mobile,String email) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = "{call Pro_SaveLabAppliant(?,?,?,?,?,?,?,?,?,?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, userName);
			proc.setString(2, loginName);
			proc.setString(3, userType);
			proc.setString(4, userID);
			proc.setString(5, sex);
			proc.setString(6, userStatus);
			proc.setString(7, telePhone);
			proc.setString(8, fax);
			proc.setString(9, mobile);
			proc.setString(10, email);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public static void SaveLabApplyDeptDao(String url,
			String deptID,String deptName,String deptSimpleName,String deptCode,String deptPath,String deptNo,String deptCIQCode,String deptProperty,
			String deptDesc,String parentDeptID,String sortOrder) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = "{call Pro_SaveLabApplyDept(?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, deptID);
			proc.setString(2, deptName);
			proc.setString(3, deptSimpleName);
			proc.setString(4, deptCode);
			proc.setString(5, deptPath);
			proc.setString(6, deptNo);
			proc.setString(7, deptCIQCode);
			proc.setString(8, deptProperty);
			proc.setString(9, deptDesc);
			proc.setString(10, parentDeptID);
			proc.setString(11, sortOrder);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public static void SaveLabApplyKindDao(String url,
			String applyKindID,String applyKindCode,String applyKind,String applyKindDesc,String parentApplyKindID,String applyKindNo,String sordOrder) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = "{call Pro_SaveLabApplyKind(?,?,?,?,?,?,?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, applyKindID);
			proc.setString(2, applyKindCode);
			proc.setString(3, applyKind);
			proc.setString(4, applyKindDesc);
			proc.setString(5, parentApplyKindID);
			proc.setString(6, applyKindNo);
			proc.setString(7, sordOrder);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public static void SaveLabDeptDao(String url,String deptID,String deptName,String deptSimpleName,String deptCode,String deptPath,String deptNo,String deptCIQCode,String deptProperty,
			String deptDesc,String parentDeptID,String sortOrder) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = "{call Pro_SaveLabDept(?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, deptID);
			proc.setString(2, deptName);
			proc.setString(3, deptSimpleName);
			proc.setString(4, deptCode);
			proc.setString(5, deptPath);
			proc.setString(6, deptNo);
			proc.setString(7, deptCIQCode);
			proc.setString(8, deptProperty);
			proc.setString(9, deptDesc);
			proc.setString(10, parentDeptID);
			proc.setString(11, sortOrder);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public static void SaveLabItemDao(String url,
			String itemCode,String itemName,String testItemID,String testItemCode,String itemID,String standardNo,
			String standardName,String productCategoryCode,String productCategoryName,String detectionLimit,String resultUnit,String limit,String testPeriod,String testFee,String deptID,String testGroupID,String testGroupName,String defaultUserID,String defaultUserName) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = null;
		wCall = "{call Pro_SaveLabItem(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";        //只能保存一条数据，是不是itemCode相同自动替换而不是添加？
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, itemCode);
			proc.setString(2, itemName);
			proc.setString(3, testItemID);
			proc.setString(4, testItemCode);
			proc.setString(5, itemID);     
			proc.setString(6, standardNo);                
			proc.setString(7, standardName);     
			proc.setString(8,productCategoryCode);    //样品类别已改名为适用范围
			proc.setString(9,productCategoryName);
			proc.setString(10,detectionLimit);
			proc.setString(11,resultUnit);
			proc.setString(12,limit);
			proc.setString(13,testPeriod);
			proc.setString(14,testFee);
			proc.setString(15,deptID);
			proc.setString(16, testGroupID);
			proc.setString(17, testGroupName);
			proc.setString(18, defaultUserID);
			proc.setString(19,defaultUserName);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public static void SaveLabSampleDisposal(String url,String CategoryName) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = null;
		wCall = "{call Pro_SaveLabSampleDisposal(?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, CategoryName);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public static void SaveLabSampleKindDao(String url,String CategoryID,String CategoryName,String SampleTypeID,String SampleTypeNo) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = null;
		wCall = "{call Pro_SaveLabSampleKind(?,?,?,?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, CategoryID);
			proc.setString(2, CategoryName);
			proc.setString(3,SampleTypeID);
			proc.setString(4,SampleTypeNo);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public static void SaveLabSamplePhysicalStateDao(String url,String CategoryName) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = null;
		wCall = "{call Pro_SaveLabSamplePhysicalState(?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, CategoryName);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public static void SaveLabSamplePreservationDao(String url,String CategoryName) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = null;
		wCall = "{call Pro_SaveLabSamplePreservation(?)}";
		try {
			proc = conn.prepareCall(wCall);
			proc.setString(1, CategoryName);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public static void DelLabOutdatedParamDao(String url) throws SQLException{
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
		CallableStatement proc = null;
		String wCall = null;
		wCall = "{call Pro_DelLabOutdatedParam()}";
		try {
			proc = conn.prepareCall(wCall);
			proc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(proc!=null)
					proc.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
}
