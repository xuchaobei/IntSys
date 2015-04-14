package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import action.ExportXml;
import dao.DaoTask1;



// ���ڶ�ȡLRP��������
public class Task1 extends TimerTask {
	private static boolean isRunning = false;
    private String result;  
    Boolean appliant=false;	            
    Boolean labApplyDept=false;
    Boolean labApplyKind=false;
    Boolean labDept=false;
    Boolean labItem=false;
    Boolean labSampleDisposal=false;
    Boolean labSampleKind=false;
    Boolean labSamplePhysicalState=false;
    Boolean labSamplePreservation=false;
    private static Log log=LogFactory.getLog(Task1.class);
    
	public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			if (!isRunning) {							
				SaveLabAppliant(); // �ɹ�					
				SaveLabApplyDept(); // �ɹ�				
				SaveLabApplyKind(); // �ɹ�				
				SaveLabDept(); // �ɹ�				
				SaveLabItem(); 			
				SaveLabSampleDisposal(); // �ɹ�
				SaveLabSampleKind(); // �ɹ�
				SaveLabSamplePhysicalState(); // �ɹ�
			    SaveLabSamplePreservation();// �ɹ�
			    if(appliant==true&&labApplyDept==true&&labApplyKind==true&&labDept==true&&labItem==true
			    		&&labSampleDisposal==true&&labSampleKind==true&&labSamplePhysicalState==true&&labSamplePreservation==true)
				    DelLabOutdatedParam();
				isRunning = false;
			} else {
				log.info("task1��һ����Ϊ��δ������");
				//System.out.println("task1��һ����Ϊ��δ������");
			}
		}catch(Exception e){
			log.error("Task1ʧ�ܣ�");
			isRunning = false;
			//System.out.println("Task1ʧ�ܣ�");
		}	
	}

	public String readManually() {
		this.result = "0";
		try{
			SaveLabAppliant(); // �ɹ�				
			SaveLabApplyDept(); // �ɹ�		
			SaveLabApplyKind(); // �ɹ�
			SaveLabDept(); // �ɹ�
			SaveLabItem(); 
			SaveLabSampleDisposal(); // �ɹ�
			SaveLabSampleKind(); // �ɹ�
			SaveLabSamplePhysicalState(); // �ɹ�
			SaveLabSamplePreservation();// �ɹ�
			DelLabOutdatedParam();
			this.result = "1";
		}catch(Exception e){
			log.error(e);
		}	
		return "success";
	}
	public String SaveLabAppliant() {
		appliant=false;	
		HttpClient httpclient = new DefaultHttpClient();
		try {
			StringEntity stringEntity= new StringEntity(this.getHeader("LabAppliant"), "text/xml", "utf-8");
			stringEntity.setChunked(true);			
			HttpPost httpPost=new HttpPost(ConPool.getConfig().getLrpUrl()+"/User/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2"); 
			httpPost.setEntity(stringEntity);			
			//HttpGet httpPost=new HttpGet("http://localhost:8000/IntSysHttp/test.xml");
			HttpResponse response = httpclient.execute(httpPost);


			// Examine the response status
			log.info(response.getStatusLine());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
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
				//log.info(result);
				//ExportXml.export(result, "�ͼ���");                  //���������xml
				//System.out.println(result);
				Reader rr = new StringReader(result);				
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));
				Element root = document.getDocumentElement();
				NodeList appliants = root.getElementsByTagName("org_User");
				if (appliants != null) {
					for (int i = 0; i < appliants.getLength(); i++) {
						Node app = appliants.item(i);
						String userName = null;
						String loginName = null;
						String userType = null;
						String userID = null;
						String sex = null;
						String userStatus = null;
						String telePhone = null;
						String fax = null;
						String mobile = null;
						String email = null;
						for (Node node = app.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("UserName")) {
									userName = this.getChildValue(node);
									//System.out.println(userName);
								} else if (node.getNodeName().equals(
										"LoginName")) {
									loginName = this.getChildValue(node);
									//System.out.println(loginName);
								} else if (node.getNodeName()
										.equals("UserType")) {
									userType = this.getChildValue(node);
									//System.out.println(userType);
								} else if (node.getNodeName().equals("UserID")) {
									userID = this.getChildValue(node);
									//System.out.println(userID);
								} else if (node.getNodeName().equals("Sex")) {
									sex = this.getChildValue(node);
									//System.out.println(sex);
								} else if (node.getNodeName().equals(
										"UserStatus")) {
									userStatus = this.getChildValue(node);
									//System.out.println(userStatus);
								} else if (node.getNodeName().equals(
										"Telephone")) {
									telePhone = this.getChildValue(node);
									//System.out.println(telePhone);
								} else if (node.getNodeName().equals("Fax")) {
									fax = this.getChildValue(node);
									//System.out.println(fax);
								} else if (node.getNodeName().equals("Mobile")) {
									mobile = this.getChildValue(node);
									//System.out.println(mobile);
								} else if (node.getNodeName().equals("Email")) {
									email = this.getChildValue(node);
									//System.out.println(email);
								}
							}
						}
						
						DaoTask1.SaveLabAppliantDao("food", userName, loginName, userType, userID, sex, userStatus, telePhone, fax, mobile, email);
						DaoTask1.SaveLabAppliantDao("hzp", userName, loginName, userType, userID, sex, userStatus, telePhone, fax, mobile, email);
					}
				}

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			log.error("����",e);
			//e.printStackTrace();			
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		appliant=true;	
		return "success";
	}

	public String SaveLabApplyDept() {
		labApplyDept=false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			StringEntity stringEntity= new StringEntity(this.getHeader("LabApplyDept"), "text/xml", "utf-8");
			stringEntity.setChunked(true);			
			HttpPost httpPost=new HttpPost(ConPool.getConfig().getLrpUrl()+"/Department/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2"); 
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);

			// Examine the response status
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
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
				//log.info(result);
		//		ExportXml.export(result,"��ⲿ��");       //���������xml�ļ�
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));
				Element root = document.getDocumentElement();
				NodeList org_Departments = root
						.getElementsByTagName("org_Department");
				if (org_Departments != null) {
					for (int i = 0; i < org_Departments.getLength(); i++) {
						String deptID = null;
						String deptName = null;
						String deptSimpleName = null;
						String deptCode = null;
						String deptPath = null;
						String deptNo = null;
						String deptCIQCode = null;
						String deptProperty = null;
						String deptDesc = null;
						String parentDeptID = null;
						String sortOrder = null;
						Node department = org_Departments.item(i);
						for (Node node = department.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("DeptID")) {
									deptID = this.getChildValue(node);
									//System.out.println(deptID);
								}
								if (node.getNodeName().equals("DeptName")) {
									deptName = this.getChildValue(node);
									//System.out.println(deptName);
								}
								if (node.getNodeName().equals("DeptSimpleName")) {
									deptSimpleName = this.getChildValue(node);
									//System.out.println(deptSimpleName);
								}
								if (node.getNodeName().equals("DeptCode")) {
									deptCode = this.getChildValue(node);
									//System.out.println(deptCode);
								}
								if (node.getNodeName().equals("DeptPath")) {
									deptPath = this.getChildValue(node);
									//System.out.println(deptPath);
								}
								if (node.getNodeName().equals("DeptNo")) {
									deptNo = this.getChildValue(node);
									//System.out.println(deptNo);
								}
								if (node.getNodeName().equals("CIQCode")) {
									deptCIQCode = this.getChildValue(node);
									//System.out.println(deptCIQCode);
								}
								if (node.getNodeName().equals("DeptProperty")) {
									deptProperty = this.getChildValue(node);
									//System.out.println(deptProperty);
								}
								if (node.getNodeName().equals("DeptDescr")) {
									deptDesc = this.getChildValue(node);
									//System.out.println(deptDesc);
								}
								if (node.getNodeName().equals("ParentDeptID")) {
									parentDeptID = this.getChildValue(node);
									//System.out.println(parentDeptID);
								}
								if (node.getNodeName().equals("SortOrder")) {
									sortOrder = this.getChildValue(node);
									//System.out.println(sortOrder);
								}
							}
						}						
					    DaoTask1.SaveLabApplyDeptDao("food", deptID, deptName, deptSimpleName, deptCode, deptPath, deptNo, deptCIQCode, deptProperty, deptDesc, parentDeptID, sortOrder);
					    DaoTask1.SaveLabApplyDeptDao("hzp", deptID, deptName, deptSimpleName, deptCode, deptPath, deptNo, deptCIQCode, deptProperty, deptDesc, parentDeptID, sortOrder);
					}
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (Exception e) {
			log.error("����",e);
			//e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		labApplyDept=true;
		return "success";
	}

	public String SaveLabApplyKind() {
		labApplyKind=false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			StringEntity stringEntity= new StringEntity(this.getHeader("LabApplyKind"), "text/xml", "utf-8");
			stringEntity.setChunked(true);			
			HttpPost httpPost=new HttpPost(ConPool.getConfig().getLrpUrl()+"/BizType/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2"); 
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);

			// Examine the response status
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
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
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));
				Element root = document.getDocumentElement();
				NodeList applyKinds = root.getElementsByTagName("cfg_BizType");
				if (applyKinds != null) {
					for (int i = 0; i < applyKinds.getLength(); i++) {
						String applyKindID = null;
						String applyKindCode = null;
						String applyKind = null;
						String applyKindDesc = null;
						String parentApplyKindID = null;
						String applyKindNo = null;
						String sordOrder = null;
						Node department = applyKinds.item(i);
						for (Node node = department.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("BizTypeID")) {
									applyKindID = this.getChildValue(node);
									//System.out.println(applyKindID);
								} else if (node.getNodeName().equals(
										"BizTypeCode")) {
									applyKindCode = this.getChildValue(node);
									//System.out.println(applyKindCode);
								} else if (node.getNodeName().equals(
										"BizTypeName")) {
									applyKind = this.getChildValue(node);
									//System.out.println(applyKind);
								} else if (node.getNodeName().equals(
										"BizTypeDescr")) {
									applyKindDesc = this.getChildValue(node);
									//System.out.println(applyKindDesc);
								} else if (node.getNodeName().equals(
										"ParentBizTypeID")) {
									parentApplyKindID = this
											.getChildValue(node);
									//System.out.println(parentApplyKindID);
								} else if (node.getNodeName().equals(
										"BizTypeNo")) {
									applyKindNo = this.getChildValue(node);
									//System.out.println(applyKindNo);
								} else if (node.getNodeName().equals(
										"SortOrder")) {
									sordOrder = this.getChildValue(node);
									//System.out.println(sordOrder);
								}

							}
							DaoTask1.SaveLabApplyKindDao("food", applyKindID, applyKindCode, applyKind, applyKindDesc, parentApplyKindID, applyKindNo, sordOrder);
							DaoTask1.SaveLabApplyKindDao("hzp", applyKindID, applyKindCode, applyKind, applyKindDesc, parentApplyKindID, applyKindNo, sordOrder);
						}
					}
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("����",e);			
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		labApplyKind=true;
		return "success";
	}

	public String SaveLabDept() {
		labDept=false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			StringEntity stringEntity= new StringEntity(this.getHeader("LabDept"), "text/xml", "utf-8");
			stringEntity.setChunked(true);
			// Prepare a request object
			//HttpGet httpget = new HttpGet("http://192.168.1.15:81/Department/GetMultiple2?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
			HttpPost httpPost=new HttpPost(ConPool.getConfig().getLrpUrl()+"/Department/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2"); 
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);

			// Examine the response status
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
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
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));
				Element root = document.getDocumentElement();
				NodeList org_Departments = root
						.getElementsByTagName("org_Department");
				if (org_Departments != null) {
					for (int i = 0; i < org_Departments.getLength(); i++) {
						String deptID = null;
						String deptName = null;
						String deptSimpleName = null;
						String deptCode = null;
						String deptPath = null;
						String deptNo = null;
						String deptCIQCode = null;
						String deptProperty = null;
						String deptDesc = null;
						String parentDeptID = null;
						String sortOrder = null;
						Node department = org_Departments.item(i);
						for (Node node = department.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("DeptID")) {
									deptID = this.getChildValue(node);
									//System.out.println(deptID);
								}
								else if (node.getNodeName().equals("DeptName")) {
									deptName = this.getChildValue(node);
									//System.out.println(deptName);
								}
								else if (node.getNodeName().equals("DeptSimpleName")) {
									deptSimpleName = this.getChildValue(node);
									//System.out.println(deptSimpleName);
								}
								else if (node.getNodeName().equals("DeptCode")) {
									deptCode = this.getChildValue(node);
									//System.out.println(deptCode);
								}
								else if (node.getNodeName().equals("DeptPath")) {
									deptPath = this.getChildValue(node);
									//System.out.println(deptPath);
								}
								else if (node.getNodeName().equals("DeptNo")) {
									deptNo = this.getChildValue(node);
									//System.out.println(deptNo);
								}
								else if (node.getNodeName().equals("CIQCode")) {
									deptCIQCode = this.getChildValue(node);
									//System.out.println(deptCIQCode);
								}
								else if (node.getNodeName().equals("DeptProperty")) {
									deptProperty = this.getChildValue(node);
									//System.out.println(deptProperty);
								}
								else if (node.getNodeName().equals("DeptDescr")) {
									deptDesc = this.getChildValue(node);
									//System.out.println(deptDesc);
								}
								else if (node.getNodeName().equals("ParentDeptID")) {
									parentDeptID = this.getChildValue(node);
									//System.out.println(parentDeptID);
								}
								else if (node.getNodeName().equals("SortOrder")) {
									sortOrder = this.getChildValue(node);
									//System.out.println(sortOrder);
								}
							}
						}
						DaoTask1.SaveLabDeptDao("food", deptID, deptName, deptSimpleName, deptCode, deptPath, deptNo, deptCIQCode, deptProperty, deptDesc, parentDeptID, sortOrder);
						DaoTask1.SaveLabDeptDao("hzp", deptID, deptName, deptSimpleName, deptCode, deptPath, deptNo, deptCIQCode, deptProperty, deptDesc, parentDeptID, sortOrder);
					}
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("����",e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		labDept=true;
		return "success";
	}

	public String SaveLabItem() { 
		labItem=false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpPost;
			HttpResponse response;
			HttpEntity entity;
			BufferedReader rd;
			StringBuffer temp;
			String line;
			String result;
			Reader rr;
			DocumentBuilderFactory builderFactory;
			DocumentBuilder domBuilder;
			Document document;
			Element gsp;

			StringEntity stringEntity = new StringEntity(
					this.getHeader("LabItem"), "text/xml", "utf-8");
			stringEntity.setChunked(true);
			httpPost = new HttpPost(
					ConPool.getConfig().getLrpUrl()+"/TestItem/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
			httpPost.setEntity(stringEntity);
			response = httpclient.execute(httpPost);
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());
			entity = response.getEntity();

			if (entity != null) {
				InputStream stream = entity.getContent();			
				rd = new BufferedReader(new InputStreamReader(stream, "utf-8"));
			
				temp = new StringBuffer();
				line = rd.readLine();
				while (line != null) {
					temp.append(line).append("\r\n");
					line = rd.readLine();
				}
				rd.close();
				stream.close();
				result = temp.toString();
				ExportXml.export(result,"�����Ŀ");       //���������xml�ļ�
				//System.out.println(result);
				rr = new StringReader(result);
				builderFactory = DocumentBuilderFactory.newInstance();
				domBuilder = builderFactory.newDocumentBuilder();
				document = domBuilder.parse(new InputSource(rr));
				gsp = document.getDocumentElement();
				NodeList itemList = gsp.getElementsByTagName("abi_TestItem");
				if (itemList != null) {
					for (int i = 0; i < itemList.getLength(); i++) {
						Node item = itemList.item(i);
						String itemCode = null;
						String itemName = null;						
						String testItemID=null;
						String testItemCode=null;
						String itemID=null;
						String standardNo=null;
						String standardName=null;					
						String productCategoryCode = null;
						String productCategoryName=null;
						String detectionLimit = null;
						String resultUnit= null;
						String limit = null;
						String testPeriod= null;
						String testFee= null;
						String deptID= null;
						String testGroupID=null;
						String testGroupName=null;
						String defaultUserID=null;
						String defaultUserName=null;
						for (Node node = item.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("ItemCode")) {
									if (node.getChildNodes().getLength() != 0) {
										itemCode = node.getFirstChild()
												.getNodeValue();
										//System.out.println(itemCode);
									}
								} else if (node.getNodeName()
										.equals("ItemName")) {
									if (node.getChildNodes().getLength() != 0) {
										itemName = node.getFirstChild()
												.getNodeValue();
										//System.out.println(itemName);
									}
								} else if (node.getNodeName().equals(
										"TestItemID")) {
									if (node.getChildNodes().getLength() != 0) {
										testItemID = node.getFirstChild()
												.getNodeValue();
										//System.out.println(testItemID);
									}
								} else if (node.getNodeName().equals(
										"TestItemCode")) {
									if (node.getChildNodes().getLength() != 0) {
										testItemCode = node.getFirstChild()
												.getNodeValue();
										//System.out.println(testItemCode);
									}
								} else if (node.getNodeName().equals(
										"ItemID")) {
									if (node.getChildNodes().getLength() != 0) {
										itemID = node.getFirstChild()
												.getNodeValue();
										//System.out.println(itemID);
									}
								} else if (node.getNodeName().equals(
										"StandardName")) {
									if (node.getChildNodes().getLength() != 0) {
										standardName = node.getFirstChild()
												.getNodeValue();
										//System.out.println(standardName);
									}
								} else if (node.getNodeName().equals(
										"StandardNo")) {
									if (node.getChildNodes().getLength() != 0) {
										standardNo = node.getFirstChild()
												.getNodeValue();
										//System.out.println(standardNo);
									}
								} else if (node.getNodeName().equals(
										"ProductCategoryCode")) {
									if (node.getChildNodes().getLength() != 0) {
										productCategoryCode = node
												.getFirstChild().getNodeValue();
										//System.out.println(productCategoryCode);
									}
								} else if (node.getNodeName().equals(
										"ProductCategoryName")) {
									if (node.getChildNodes().getLength() != 0) {
										productCategoryName = node
												.getFirstChild().getNodeValue();
										//System.out.println(productCategoryName);
									}
								} else if (node.getNodeName().equals(
										"DetectionLimit")) {
									if (node.getChildNodes().getLength() != 0) {
										detectionLimit = node.getFirstChild()
												.getNodeValue();
										//System.out.println(detectionLimit);
									}
								} else if (node.getNodeName().equals(
										"ResultUnit")) {
									if (node.getChildNodes().getLength() != 0) {
										resultUnit = node.getFirstChild()
												.getNodeValue();
										//System.out.println(resultUnit);
									}
								} else if (node.getNodeName().equals("Limit")) {
									if (node.getChildNodes().getLength() != 0) {
										limit = node.getFirstChild()
												.getNodeValue();
										//System.out.println(limit);
									}
								} else if (node.getNodeName().equals(
										"TestPeriod")) {
									if (node.getChildNodes().getLength() != 0) {
										testPeriod = node.getFirstChild()
												.getNodeValue();
										//System.out.println(testPeriod);
									}
								} else if (node.getNodeName().equals("TestFee")) {
									if (node.getChildNodes().getLength() != 0) {
										testFee = node.getFirstChild()
												.getNodeValue();
										//System.out.println(testFee);
									}
								} else if (node.getNodeName().equals("DeptID")) {
									if (node.getChildNodes().getLength() != 0) {
										deptID = node.getFirstChild()
												.getNodeValue();
										//System.out.println(deptID);
									}
								} else if (node.getNodeName().equals("TestGroupID")) {
									if (node.getChildNodes().getLength() != 0) {
										testGroupID = node.getFirstChild()
												.getNodeValue();
										//System.out.println(deptID);
									}
								} else if (node.getNodeName().equals("TestGroupName")) {
									if (node.getChildNodes().getLength() != 0) {
										testGroupName = node.getFirstChild()
												.getNodeValue();
										//System.out.println(deptID);
									}
								} else if (node.getNodeName().equals("DefaultUserID")) {
									if (node.getChildNodes().getLength() != 0) {
										defaultUserID = node.getFirstChild()
												.getNodeValue();
										//System.out.println(deptID);
									}
								} else if (node.getNodeName().equals("DefaultUserName")) {
									if (node.getChildNodes().getLength() != 0) {
										defaultUserName = node.getFirstChild()
												.getNodeValue();
										//System.out.println(deptID);
									}
								}
							}
						}
    					if("����ù����A".equals(itemName) || "������������".equals(itemName) ){
    					    log.info("��ȡ�������Ŀ�� "+itemCode+"--"+itemName);
                        }
    					DaoTask1.SaveLabItemDao("food", itemCode, itemName, testItemID, testItemCode, itemID, standardNo, standardName, productCategoryCode, productCategoryName, detectionLimit, resultUnit, limit, testPeriod, testFee, deptID,testGroupID,testGroupName,defaultUserID,defaultUserName);
    					DaoTask1.SaveLabItemDao("hzp", itemCode, itemName, testItemID, testItemCode, itemID, standardNo, standardName, productCategoryCode, productCategoryName, detectionLimit, resultUnit, limit, testPeriod, testFee, deptID,testGroupID,testGroupName,defaultUserID,defaultUserName);
    				    if("����ù����A".equals(itemName) || "������������".equals(itemName) ){
                            log.info("�����Ŀ������ɣ� "+itemCode+"--"+itemName);
                        }
					    
					}						
				}
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ParserConfigurationException e) {
			//e.printStackTrace();
			log.error(e);
		} catch (SAXException e) {
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("����",e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		labItem=true;
		return "success";
	}
	
	public String SaveLabItem2(File file) { 
        labItem=false;
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httpPost;
            HttpResponse response;
            HttpEntity entity;
            BufferedReader rd;
            StringBuffer temp;
            String line;
            String result;
            Reader rr;
            DocumentBuilderFactory builderFactory;
            DocumentBuilder domBuilder;
            Document document;
            Element gsp;

       /*     StringEntity stringEntity = new StringEntity(
                    this.getHeader("LabItem"), "text/xml", "utf-8");
            stringEntity.setChunked(true);
            httpPost = new HttpPost(
                    ConPool.getConfig().getLrpUrl()+"/TestItem/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
            httpPost.setEntity(stringEntity);
            response = httpclient.execute(httpPost);
            //System.out.println(response.getStatusLine());
            log.info(response.getStatusLine());
            entity = response.getEntity();*/

            if (file != null) {
                //InputStream stream = entity.getContent();           
                
                //rd = new BufferedReader(new InputStreamReader(stream, "utf-8"));
                rd = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
                
            
                temp = new StringBuffer();
                line = rd.readLine();
                while (line != null) {
                    temp.append(line).append("\r\n");
                    line = rd.readLine();
                }
                rd.close();
                //stream.close();
                result = temp.toString();
                ExportXml.export(result,"�����Ŀ");       //���������xml�ļ�
                //System.out.println(result);
                rr = new StringReader(result);
                builderFactory = DocumentBuilderFactory.newInstance();
                domBuilder = builderFactory.newDocumentBuilder();
                document = domBuilder.parse(new InputSource(rr));
                gsp = document.getDocumentElement();
                NodeList itemList = gsp.getElementsByTagName("abi_TestItem");
                if (itemList != null) {
                    for (int i = 0; i < itemList.getLength(); i++) {
                        System.out.println("������" + itemList.getLength());
                        Node item = itemList.item(i);
                        String itemCode = null;
                        String itemName = null;                     
                        String testItemID=null;
                        String testItemCode=null;
                        String itemID=null;
                        String standardNo=null;
                        String standardName=null;                   
                        String productCategoryCode = null;
                        String productCategoryName=null;
                        String detectionLimit = null;
                        String resultUnit= null;
                        String limit = null;
                        String testPeriod= null;
                        String testFee= null;
                        String deptID= null;
                        String testGroupID=null;
                        String testGroupName=null;
                        String defaultUserID=null;
                        String defaultUserName=null;
                        for (Node node = item.getFirstChild(); node != null; node = node
                                .getNextSibling()) {
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                if (node.getNodeName().equals("ItemCode")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        itemCode = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(itemCode);
                                    }
                                } else if (node.getNodeName()
                                        .equals("ItemName")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        itemName = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(itemName);
                                    }
                                } else if (node.getNodeName().equals(
                                        "TestItemID")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        testItemID = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(testItemID);
                                    }
                                } else if (node.getNodeName().equals(
                                        "TestItemCode")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        testItemCode = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(testItemCode);
                                    }
                                } else if (node.getNodeName().equals(
                                        "ItemID")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        itemID = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(itemID);
                                    }
                                } else if (node.getNodeName().equals(
                                        "StandardName")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        standardName = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(standardName);
                                    }
                                } else if (node.getNodeName().equals(
                                        "StandardNo")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        standardNo = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(standardNo);
                                    }
                                } else if (node.getNodeName().equals(
                                        "ProductCategoryCode")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        productCategoryCode = node
                                                .getFirstChild().getNodeValue();
                                        //System.out.println(productCategoryCode);
                                    }
                                } else if (node.getNodeName().equals(
                                        "ProductCategoryName")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        productCategoryName = node
                                                .getFirstChild().getNodeValue();
                                        //System.out.println(productCategoryName);
                                    }
                                } else if (node.getNodeName().equals(
                                        "DetectionLimit")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        detectionLimit = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(detectionLimit);
                                    }
                                } else if (node.getNodeName().equals(
                                        "ResultUnit")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        resultUnit = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(resultUnit);
                                    }
                                } else if (node.getNodeName().equals("Limit")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        limit = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(limit);
                                    }
                                } else if (node.getNodeName().equals(
                                        "TestPeriod")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        testPeriod = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(testPeriod);
                                    }
                                } else if (node.getNodeName().equals("TestFee")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        testFee = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(testFee);
                                    }
                                } else if (node.getNodeName().equals("DeptID")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        deptID = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(deptID);
                                    }
                                } else if (node.getNodeName().equals("TestGroupID")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        testGroupID = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(deptID);
                                    }
                                } else if (node.getNodeName().equals("TestGroupName")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        testGroupName = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(deptID);
                                    }
                                } else if (node.getNodeName().equals("DefaultUserID")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        defaultUserID = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(deptID);
                                    }
                                } else if (node.getNodeName().equals("DefaultUserName")) {
                                    if (node.getChildNodes().getLength() != 0) {
                                        defaultUserName = node.getFirstChild()
                                                .getNodeValue();
                                        //System.out.println(deptID);
                                    }
                                }
                            }
                        }
                        if("����ù����A".equals(itemName) || "������������".equals(itemName) ){
                            System.out.println(itemCode+"--"+itemName);
                        }
                        System.out.println("save item--"+ "itemCode: "+ itemCode + " itemName: "+itemName + " testItemID: "+testItemID + " testItemCode: "+testItemCode+" itemID: "+itemID
                                +" standardNo: "+standardNo);
                        SaveLabItemDao("food", itemCode, itemName, testItemID, testItemCode, itemID, standardNo, standardName, productCategoryCode, productCategoryName, detectionLimit, resultUnit, limit, testPeriod, testFee, deptID,testGroupID,testGroupName,defaultUserID,defaultUserName);
                        //DaoTask1.SaveLabItemDao("hzp", itemCode, itemName, testItemID, testItemCode, itemID, standardNo, standardName, productCategoryCode, productCategoryName, detectionLimit, resultUnit, limit, testPeriod, testFee, deptID,testGroupID,testGroupName,defaultUserID,defaultUserName);
                        }                       
                    }
                }
            
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error(e);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error(e);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error(e);
        } catch (ParserConfigurationException e) {
            //e.printStackTrace();
            log.error(e);
        } catch (SAXException e) {
            //e.printStackTrace();
            log.error(e);
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("����",e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
       // labItem=true;
        return "success";
    }

	
	static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String URL = "jdbc:sqlserver://172.16.36.225:1433;databaseName=test";
    static final String USER = "aaa";
    static final String PWD = "aa";
    
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER,PWD);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }
    
    public static void SaveLabItemDao(String url,
            String itemCode,String itemName,String testItemID,String testItemCode,String itemID,String standardNo,
            String standardName,String productCategoryCode,String productCategoryName,String detectionLimit,String resultUnit,String limit,String testPeriod,String testFee,String deptID,String testGroupID,String testGroupName,String defaultUserID,String defaultUserName) throws SQLException{
        //Connection conn = ConPool.getConnection(url,user,psw);
        Connection conn=getConnection();
        CallableStatement proc = null;
        String wCall = null;
        wCall = "{call Pro_SaveLabItem(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";        //ֻ�ܱ���һ�����ݣ��ǲ���itemCode��ͬ�Զ��滻���������ӣ�
        try {
            proc = conn.prepareCall(wCall);
            proc.setString(1, itemCode);
            proc.setString(2, itemName);
            proc.setString(3, testItemID);
            proc.setString(4, testItemCode);
            proc.setString(5, itemID);     
            proc.setString(6, standardNo);                
            proc.setString(7, standardName);     
            proc.setString(8,productCategoryCode);    //��Ʒ����Ѹ���Ϊ���÷�Χ
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
    
    public static void DelLabOutdatedParamDao(String url) throws SQLException{
        //Connection conn = ConPool.getConnection(url,user,psw);
        Connection conn=getConnection();
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
    
    
	public static void main(String[] args) throws SQLException {	    
	 
        File file = new File("D://22.xml");  
        new Task1().SaveLabItem2(file);
        DelLabOutdatedParamDao("food");
        
    }
	
	public String SaveLabSampleDisposal() {
		labSampleDisposal=true;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			StringEntity stringEntity= new StringEntity(this.getHeader("LabSampleDisposal"), "text/xml", "utf-8");
			stringEntity.setChunked(true);			
			HttpPost httpPost=new HttpPost(ConPool.getConfig().getLrpUrl()+"/OptionItem/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2"); 
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);

			// Examine the response status
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
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
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));

				Element gsp = document.getDocumentElement();
				NodeList listOfProductDisposal = gsp
						.getElementsByTagName("cfg_OptionItem");
				if(listOfProductDisposal!=null){
					for (int i = 0; i < listOfProductDisposal .getLength(); i++) {
						Node ItemOfgsp = listOfProductDisposal .item(i);
						String CategoryName = null;
						for (Node node = ItemOfgsp.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("OptionItemName")) {
									CategoryName = node.getFirstChild()
											.getNodeValue();
									//System.out.println(CategoryName);
								}
							}
						}
						DaoTask1.SaveLabSampleDisposal("food", CategoryName);
						DaoTask1.SaveLabSampleDisposal("hzp", CategoryName);
					}
				}			
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("����",e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		labSampleDisposal=true;
		return "success";
	}

	public String SaveLabSampleKind() {
		labSampleKind=false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			StringEntity stringEntity = new StringEntity(
					this.getHeader("LabSampleKind"), "text/xml", "utf-8");
			stringEntity.setChunked(true);
			HttpPost httpPost = new HttpPost(
					ConPool.getConfig().getLrpUrl()+"/SampleType/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);
			// Examine the response status
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
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
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));

				Element gsp = document.getDocumentElement();
				NodeList sampleKinds = gsp
						.getElementsByTagName("cfg_SampleType");
				if (sampleKinds != null) {
					for (int i = 0; i < sampleKinds.getLength(); i++) {
						Node sampleKind = sampleKinds.item(i);
						String CategoryID = null;
						String CategoryName = null;
						String SampleTypeID=null;
						String SampleTypeNo=null;
						for (Node node = sampleKind.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("SampleTypeCode")) {
									CategoryID = this.getChildValue(node);
									//System.out.println(CategoryID);
								} else if (node.getNodeName().equals(
										"SampleTypeName")) {
									CategoryName =this.getChildValue(node);
									//System.out.println(CategoryName);
								}else if (node.getNodeName().equals(
										"SampleTypeID")) {
									SampleTypeID = this.getChildValue(node);
									//System.out.println(SampleTypeID);
								}else if (node.getNodeName().equals(
										"SampleTypeNo")) {
									SampleTypeNo = this.getChildValue(node);
									//System.out.println(SampleTypeNo);
								}
							}
						}
						DaoTask1.SaveLabSampleKindDao("food", CategoryID, CategoryName, SampleTypeID, SampleTypeNo);
						DaoTask1.SaveLabSampleKindDao( "hzp", CategoryID, CategoryName, SampleTypeID, SampleTypeNo);
					}
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("����",e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		labSampleKind=true;
		return "success";
	}

	public String SaveLabSamplePhysicalState() {
		labSamplePhysicalState=false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			StringEntity stringEntity= new StringEntity(this.getHeader("LabSamplePhysicalState"), "text/xml", "utf-8");
			stringEntity.setChunked(true);			
			HttpPost httpPost=new HttpPost(ConPool.getConfig().getLrpUrl()+"/OptionItem/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2"); 
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);


			// Examine the response status
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
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
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));

				Element gsp = document.getDocumentElement();
				NodeList listOfSamplePhysicalState = gsp
						.getElementsByTagName("cfg_OptionItem");
				if(listOfSamplePhysicalState!=null){
					for (int i = 0; i < listOfSamplePhysicalState.getLength(); i++) {
						Node ItemOfgsp = listOfSamplePhysicalState.item(i);
						String CategoryName = null;
						for (Node node = ItemOfgsp.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("OptionItemName")) {
									//CategoryName = node.getFirstChild().getNodeValue();
									CategoryName = this.getChildValue(node);
									//System.out.println(CategoryName);
								}
							}
						}
					DaoTask1.SaveLabSamplePhysicalStateDao("food", CategoryName);
					DaoTask1.SaveLabSamplePhysicalStateDao("hzp", CategoryName);
					}
				}				
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("����",e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		labSamplePhysicalState=true;
		return "success";
	}

	public String SaveLabSamplePreservation() {
		labSamplePreservation=false;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			StringEntity stringEntity = new StringEntity(
					this.getHeader("LabSamplePreservation"), "text/xml",
					"utf-8");
			stringEntity.setChunked(true);
			HttpPost httpPost = new HttpPost(
					ConPool.getConfig().getLrpUrl()+"/OptionItem/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);

			// Examine the response status
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
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
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));

				Element gsp = document.getDocumentElement();

				NodeList listOfSamplePreservation = gsp
						.getElementsByTagName("cfg_OptionItem");
				if (listOfSamplePreservation != null) {
					for (int i = 0; i < listOfSamplePreservation.getLength(); i++) {
						Node ItemOfgsp = listOfSamplePreservation.item(i);
						String CategoryName = null;
						for (Node node = ItemOfgsp.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("OptionItemName")) {
									//CategoryName = node.getFirstChild().getNodeValue();
									CategoryName = this.getChildValue(node);
								//	System.out.println(CategoryName);
								}
							}
						}
						DaoTask1.SaveLabSamplePreservationDao("food", CategoryName);
						DaoTask1.SaveLabSamplePreservationDao("hzp", CategoryName);
					}
				}

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("����",e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		labSamplePreservation=true;
		return "success";
	}

	public String DelLabOutdatedParam() {
		try {
		    DaoTask1.DelLabOutdatedParamDao("food");
		    DaoTask1.DelLabOutdatedParamDao("hzp");
		} catch (Exception e) {
			log.error("����",e);
			//e.printStackTrace();
		}
		return "success";
	}

	public String getChildValue(Node node) {
		if (node.getFirstChild() != null) {
			return node.getFirstChild().getNodeValue();
		} else
			return "";
	}
	
    public String getHeader(String judge){
		String xmlStr;
		org.jdom.Element root = new org.jdom.Element("DataRoot");
		org.jdom.Document doc = new org.jdom.Document(root);
		if (judge.equals("LabDept")) { // ���첿��
			org.jdom.Element flag = new org.jdom.Element("TestDeptFlag");
			flag.setText("1");
			root.addContent(flag);
		} else if (judge.equals("LabApplyDept")) { // �ͼ첿��
			org.jdom.Element flag = new org.jdom.Element("DeliveryDeptFlag");
			flag.setText("1");
			root.addContent(flag);
		} else if (judge.equals("LabApplyKind")) { // ҵ�����
			
		} else if (judge.equals("LabSampleKind")) {    // ��Ʒ���
			
		}else if(judge.equals("LabAppliant")){    //�ͼ���
			//org.jdom.Element flag = new org.jdom.Element("DeptCode");
			//flag.setText("1");
			//root.addContent(flag);
		}else if(judge.equals("LabSampleDisposal")){  //��Ʒ���÷�ʽ
			org.jdom.Element flag = new org.jdom.Element("OptionCode");
			flag.setText("100015");
			root.addContent(flag);
		}else if(judge.equals("LabSamplePhysicalState")){  //��Ʒ״̬
			org.jdom.Element flag = new org.jdom.Element("OptionCode");
			flag.setText("100005");
			root.addContent(flag);
		}else if(judge.equals("LabSamplePreservation")){   //��Ʒ���淽ʽ 
			org.jdom.Element flag = new org.jdom.Element("OptionCode");
			flag.setText("100010");
			root.addContent(flag);
		}else if(judge.equals("LabItem")){   //��Ʒ���淽ʽ 
			org.jdom.Element pageSize = new org.jdom.Element("PageSize");
			pageSize.setText("10000");
			org.jdom.Element pageIndex = new org.jdom.Element("PageIndex");
			pageIndex.setText("0");
			org.jdom.Element sql= new org.jdom.Element("Sql");
			org.jdom.Element itemProperty = new org.jdom.Element("itemProperty");
			itemProperty.setText("1,2");
			root.addContent(pageSize);
			root.addContent(pageIndex);
			root.addContent(sql);
			root.addContent(itemProperty);
		}
		org.jdom.output.Format format = org.jdom.output.Format
				.getPrettyFormat();
		format.setEncoding("utf-8");// ���ñ����ʽ
		XMLOutputter xmlout = new XMLOutputter(format);
		// xmlout.output(Doc, new PrintWriter(System.out));
		xmlStr = xmlout.outputString(doc);
		//System.out.println(xmlStr);
		return xmlStr;
	}
}