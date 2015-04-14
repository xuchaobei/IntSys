

package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dao.CIQTaskDao;

public class CIQTask extends TimerTask{
	private static boolean isRunning = false;
	private static Log log = LogFactory.getLog(CIQTask.class);
	private static String classforname = "oracle.jdbc.driver.OracleDriver";
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if (!isRunning) {
				isRunning = true;
				getDeclNoForCIQProcessDate();
				isRunning = false;
			} else {
				// System.out.println("task2上一次认为还未结束！");
				log.info("CIQTask上一次认为还未结束！");
			}
		} catch (Exception e) {
			// System.out.println("Task2失败！");
			log.error("CIQTask失败！");
			isRunning = false;
		}
	}
	public void getDeclNoForCIQProcessDate(){
		try{
			log.info("从CIQ读取流程");			
			List<String> declNoList=CIQTaskDao.getDeclNoForCIQDao("food");		
			performCIQProcess(declNoList,"food");				
			declNoList=CIQTaskDao.getDeclNoForCIQDao("hzp");
			performCIQProcess(declNoList,"hzp");	
		}catch(Exception e){			
			log.error("错误",e);		
		}		
	}
	
	public void performCIQProcess(List<String> declNoList,String url) throws SQLException{		
		Connection conn=null;	
		PreparedStatement stmt = null;
		ResultSet rs= null;
		Configuration cfg=new Configuration();
		try{
			Class.forName(classforname);	
			String wurl=cfg.getCIQUrl();
			String user=cfg.getCIQUser();
			String psw=cfg.getCIQPSW();
			conn=DriverManager.getConnection(wurl,user,psw);	
			log.info("CIQ2000连接成功");
			for(int i=0;i<declNoList.size();i++){
				try{
					String Sqlstmt ="Select LEAVE_TIME from CIQUSR.v_xc_proc where decl_no like ? and Flow_Node='0403'";				
					stmt = conn.prepareStatement(Sqlstmt);
					stmt.setString(1,declNoList.get(i));
					rs=stmt.executeQuery();
					while(rs.next()){
						Timestamp leaveTime=rs.getTimestamp("LEAVE_TIME");
						CIQTaskDao.updateCIQDeclProcessDate(url, declNoList.get(i), leaveTime, 1);
					}
				}catch(SQLException e){					
					log.error("错误",e);
				}finally{
					try{
						if(rs!=null)
							rs.close();
						if(stmt!=null)
							stmt.close();						
					}catch(SQLException e){
						log.error(e);
					}								
				}					
				try{
					String Sqlstmt ="Select LEAVE_TIME from CIQUSR.v_xc_proc where decl_no like ? and Flow_Node='0506'";
					stmt = conn.prepareStatement(Sqlstmt);
					stmt.setString(1,declNoList.get(i));
					rs=stmt.executeQuery();
					while(rs.next()){
						Timestamp leaveTime=rs.getTimestamp("LEAVE_TIME");
						CIQTaskDao.updateCIQDeclProcessDate(url, declNoList.get(i), leaveTime, 2);
					}
				}catch(SQLException e){
					log.error("错误",e);
				}finally{
					try{
						if(rs!=null)
							rs.close();
						if(stmt!=null)
							stmt.close();						
					}catch(SQLException e){
						log.error(e);
					}								
				}				
			}
		}catch(ClassNotFoundException e){
			//e.printStackTrace();
			log.error(e);			
		}catch(SQLException e){
			//e.printStackTrace();
			e.printStackTrace();
			log.error("CIQ2000连接失败！  "+e);			
		}finally{
			try{				
				if(conn!=null)
					conn.close();
			}catch(SQLException e){
				log.error(e);
			}								
		}	
	}
}
