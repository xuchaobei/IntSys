package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import model.CIQDeclBean;
import model.CIQFeeBean;
import model.CIQGoodsBean;
import model.LabApplyBean;
import servlet.FoodDBPool;
import servlet.HZPDBPool;
import servlet.Task3;

public class DaoTask3 {
    private static Log log=LogFactory.getLog(DaoTask3.class);
    
    public static List<LabApplyBean> getLabApplyDao(String url) throws SQLException{
    	//Connection conn = ConPool.getConnection(url,user,psw);
    	Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
    	CallableStatement proc = null;
		ResultSet rs = null;
		LabApplyBean labApplyBean;
		List<LabApplyBean> labApplyList = new ArrayList<LabApplyBean>();
		try {
			proc = conn.prepareCall("{call Pro_GetLabApplySendingLRP()}");
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				labApplyBean = new LabApplyBean(rs);
				labApplyList.add(labApplyBean);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("getLabApplyDao", e);
		} catch(Exception e) {
		    log.error("getLabApplyDao", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("getLabApplyDao", e);
			} catch (Exception e){
			    log.error("getLabApplyDao", e);
			}
		}
		return labApplyList;
    }    
    
    public static CIQDeclBean getCIQDeclDao(String url,String declNo) throws SQLException{
    	//Connection conn = ConPool.getConnection(url,user,psw);
    	Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
    	CallableStatement proc = null;
		ResultSet rs = null;
		CIQDeclBean wCIQDeclBean=null;
		
		try {
			proc = conn.prepareCall("{call Pro_GetDeclInfoFromCIQ(?)}");
			proc.setString(1,declNo);
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				wCIQDeclBean = new CIQDeclBean(rs);		//only one item		
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("getCIQDeclDao", e);
		} catch (Exception e) {
		    log.error("getCIQDeclDao", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("getCIQDeclDao", e);
			} catch (Exception e) {
			    log.error("getCIQDeclDao", e);
			}
		}
		return wCIQDeclBean;
    }

    public static CIQGoodsBean getCIQGoodsDao(String url,String declNo) throws SQLException{
    	//Connection conn = ConPool.getConnection(url,user,psw);
    	Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
    	CallableStatement proc = null;
		ResultSet rs = null;
		CIQGoodsBean wCIQGoodsBean=null;
		
		try {
			proc = conn.prepareCall("{call Pro_GetDeclGoodsInfoFromCIQ(?,?)}");
			proc.setString(1,declNo);
			proc.setInt(2,1);
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				wCIQGoodsBean = new CIQGoodsBean(rs);		//only one item		
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("getCIQGoodsDao", e);
		} catch (Exception e) {
		    log.error("getCIQGoodsDao", e);

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			    log.error("getCIQGoodsDao", e);
			} catch (Exception e){
			    log.error("getCIQGoodsDao", e);
			}
		}
		return wCIQGoodsBean;
    }
    
    //2014-6-21 add
    public static List<String> getCountryName(String url, String originCountryCode, String tradeCountryCode) throws SQLException{
        //Connection conn = ConPool.getConnection(url,user,psw);
        List<String> list = new ArrayList<String>();
        Connection conn=null;
        if(url.equals("food"))
            conn = FoodDBPool.ds.getConnection();
        else if(url.equals("hzp"))
            conn=HZPDBPool.ds.getConnection();
        CallableStatement proc = null;
        String originCountryName = null;
        String tradeCountryName = null;
        try {
            proc = conn.prepareCall("{call Pro_GetCountryName(?,?,?,?)}");
            proc.setString(1, originCountryCode);
            proc.setString(2, tradeCountryCode);
            proc.registerOutParameter(3, java.sql.Types.VARCHAR);
            proc.registerOutParameter(4, java.sql.Types.VARCHAR);
            proc.execute();
            originCountryName = proc.getString(3);
            tradeCountryName = proc.getString(4);
            
            list.add(originCountryName);
            list.add(tradeCountryName);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("getCountryName", e);
        } catch (Exception e) {
            log.error("getCountryName", e);
        }finally {
            try {
                if (proc != null)
                    proc.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("getCountryName", e);
            } catch (Exception e) {
                log.error("getCountryName", e);
            }
        }
        return list;
    }
    
    public static List<CIQFeeBean> getCIQFeeDao(String url,String declNo) throws SQLException{
    	//Connection conn = ConPool.getConnection(url,user,psw);
    	Connection conn=null;
		if(url.equals("food"))
		    conn = FoodDBPool.ds.getConnection();
		else if(url.equals("hzp"))
			conn=HZPDBPool.ds.getConnection();
    	CallableStatement proc = null;
		ResultSet rs = null;
		CIQFeeBean wCIQFeeBean=null;
		List<CIQFeeBean> wCIQFeeList=new ArrayList<CIQFeeBean>(); 
		try {
			proc = conn.prepareCall("{call Pro_GetDeclFee(?)}");
			proc.setString(1,declNo);			
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				wCIQFeeBean = new CIQFeeBean(rs);	
				wCIQFeeList.add(wCIQFeeBean);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("getCIQFeeDao", e);
		} catch (Exception e) {
		    log.error("getCIQFeeDao", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("getCIQFeeDao", e);
			} catch (Exception e){
			    log.error("getCIQFeeDao", e);
			}
		}
		return wCIQFeeList;
    }
}
