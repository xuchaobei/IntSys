package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LRPServlet  extends HttpServlet{	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer=null;
	private static Log log=LogFactory.getLog(LRPServlet.class);

	public LRPServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
    public void destroy() {   
        super.destroy();    
        if(timer!=null){   
            timer.cancel();   
           // System.out.println("��ʱ������");
            log.info("��ʱ������");
        }        
    }   
    public void doGet(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {   
           
    }   
  
       
    public void doPost(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {   
       // doGet(request, response);          
    }   

	public void init() throws ServletException{		        
		//��õ��������
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		Date date = new Date();
		Date date1=new Date();
		//�����ȡ�������ݿ�ʼʱ���ַ���
		String timeStr = "1:00:00"; 
		String timeStr1="23:00:00";
		timeStr = sdf.format(date)+timeStr;
		timeStr1=sdf.format(date1)+timeStr1;
		//��õ����ָ��ʱ���date����
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {  
		date = sdf.parse(timeStr);
		date1=sdf.parse(timeStr1);
        } catch (ParseException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
        	log.error(e);
        }		
		//�жϽ����ִ��ʱ���Ƿ��Ѿ���ȥ�������ȥ���Ϊ����
		if(date.getTime()<System.currentTimeMillis()){
		 date = new Date(date.getTime()+24*60*60*1000);
		}
		if(date1.getTime()<System.currentTimeMillis()){
			 date1 = new Date(date1.getTime()+24*60*60*1000);
		}
		
        String startTask = getInitParameter("startTask");             
        // ������ʱ��   
        if(startTask.equals("true")){          	
            timer = new Timer(true);   //true��ʾtimerΪ��̨�߳�(daemon)            
            TimerTask task1=new Task1();
            TimerTask task2=new Task2();
            TimerTask task3=new Task3();
            TimerTask delTask=new DelTask();
            TimerTask CIQTask=new CIQTask();  
            TimerTask calculateTask=new CalculateTask();
            log.info("��ʱ������");
            try{
            	timer.schedule(task1, date, 24*60*60*1000);
            	timer.schedule(task3, 0, 3*60*1000);
           		timer.schedule(task2, 0, 5*60*1000);
           		timer.schedule(delTask, 0, 5*60*1000);
           		timer.schedule(CIQTask, date1, 24*60*60*1000);  
           		timer.schedule(calculateTask, date, 24*60*60*1000);
            }catch(Exception e){
            	//e.printStackTrace();
            	log.error(e);
            }
       		
        }   
    }  	

}
