package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import dao.DelTaskDao;

public class DelTask extends TimerTask {
	private static boolean isRunning = false;
	private static Log log = LogFactory.getLog(DelTask.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if (!isRunning) {
				isRunning = true;
				getLabApplyNoForDelSample();
				isRunning = false;
			} else {
				// System.out.println("task2上一次认为还未结束！");
				log.info("delTask上一次认为还未结束！");
			}
		} catch (Exception e) {
			// System.out.println("Task2失败！");
			log.error("delTask失败！");
			isRunning = false;
		}
	}

	public void getLabApplyNoForDelSample() {
	    Date now = new Date();
        DateFormat d=DateFormat.getDateTimeInstance();          
		try {
			log.info("检查样品是否可删除");
			List<String> labApplyIDList = DelTaskDao.getLabApplyNo("food");
			log.info("删除食品样品-----"+d.format(new Date()));
			checkDel(labApplyIDList, "food");
			List<String> labApplyIDList1 = DelTaskDao.getLabApplyNo("hzp");
			log.info("删除化妆品样品-----"+d.format(new Date()));
			checkDel(labApplyIDList1, "hzp");
		} catch (Exception e) {
			log.error("错误",e);
		}

	}

	public void checkDel(List<String> labApplyIDList, String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet;
		HttpResponse response;
		HttpEntity entity;
		try {
			for (int i = 0; i < labApplyIDList.size(); i++) {
				httpGet = new HttpGet(ConPool.getConfig().getLrpUrl()
						+ "/BizModify/CheckIsCanDelete?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2&BizID="
						+ labApplyIDList.get(i));

				response = httpclient.execute(httpGet);
				log.info(response.getStatusLine());
				entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(instream, "utf-8"));
					StringBuffer temp = new StringBuffer();
					String line = reader.readLine();
					while (line != null) {
						temp.append(line).append("\r\n");
						line = reader.readLine();
					}
					reader.close();
					instream.close();
					String result = temp.toString();
					log.info(result);
					Reader rr = new StringReader(result);
					DocumentBuilderFactory builderFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder domBuilder = builderFactory
							.newDocumentBuilder();
					Document document = domBuilder.parse(new InputSource(rr));
					Element root = document.getDocumentElement();
					Node isCanDel = root.getElementsByTagName("IsCanDelete")
							.item(0);
					if (isCanDel.getFirstChild().getNodeValue() != null) {
						String delFlag = isCanDel.getFirstChild()
								.getNodeValue();
						if (!delFlag.equalsIgnoreCase("True"))
							DelTaskDao.updateDelSampleFlg(labApplyIDList.get(i), url);
					}
				}
			}
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			log.error("错误",e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

}
