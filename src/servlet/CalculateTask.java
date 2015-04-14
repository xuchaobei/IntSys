package servlet;

import java.sql.SQLException;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dao.CalculateDao;

public class CalculateTask extends TimerTask {
	private static boolean isRunning = false;
	private static Log log = LogFactory.getLog(CalculateTask.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if (!isRunning) {
				isRunning = true;
				calculateDailyInspInfo();
				calculateMonthlyInspInfo();
				isRunning = false;
			} else {				
				log.info("CIQTask��һ����Ϊ��δ������");
			}
		} catch (Exception e) {			
			log.error("CIQTaskʧ�ܣ�");
			isRunning = false;
		}
	}
	
	public void calculateDailyInspInfo(){
		try{
			CalculateDao.calculateDailyInspInfo("food");
			CalculateDao.calculateDailyInspInfo("hzp");
		}catch(SQLException e){
			log.error("����",e);
		}
	}
	
	public void calculateMonthlyInspInfo(){
		try{
			CalculateDao.calculateMonthlyInspInfo("food");
			CalculateDao.calculateMonthlyInspInfo("hzp");
		}catch(SQLException e){
			log.error("����",e);
		}
	}
}
