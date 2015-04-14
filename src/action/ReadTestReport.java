package action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

import servlet.ConPool;

public class ReadTestReport {/*
	private String labApplyNo;
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLabApplyNo() {
		return labApplyNo;
	}

	public void setLabApplyNo(String labApplyNo) {
		this.labApplyNo = labApplyNo;
	}

	public String updateItemLabData() {
		this.result = "0";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost;
		HttpResponse response;
		HttpEntity entity;
		Connection conn = ConPool.getConnection();
		CallableStatement proc = null;
		String wCall = null;
		try {
			StringEntity stringEntity = new StringEntity(this.getHeader(
					"TestResult", labApplyNo), "text/xml", "utf-8");
			stringEntity.setChunked(true);
			httpPost = new HttpPost(
					ConPool.getConfig().getLrpUrl()
							+ "/ResultQuery/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
			httpPost.setEntity(stringEntity);
			response = httpclient.execute(httpPost);

			// Examine the response status
			System.out.println(response.getStatusLine());

			// Get hold of the response entity
			entity = response.getEntity();

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
				// ExportXml.export(result, "检测结果");
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));
				Element gsp = document.getDocumentElement();
				Node sampleItemList = gsp.getElementsByTagName(
						"biz_SampleItemList").item(0);
				int testFlag = this.judgeReport(labApplyNo);// 保存检测报告
				if (testFlag == 1) {
					if (sampleItemList.getChildNodes().getLength() != 0) {
						NodeList listOfItem = gsp
								.getElementsByTagName("biz_SampleItem");
						for (int i = 0; i < listOfItem.getLength(); i++) {
							conn = ConPool.getConnection();
							Node ItemOfgsp = listOfItem.item(i);
							String DpTestItemID = null; // 实验室系统项目号不确定
							String ResultData = null;
							String ResultUnit = null;
							String SampleID = null; // 食品系统ID,从何而来？？？
							for (Node node = ItemOfgsp.getFirstChild(); node != null; node = node
									.getNextSibling()) {
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									if (node.getNodeName().equals("TestItemID")) {
										DpTestItemID = node.getFirstChild()
												.getNodeValue();
										System.out.println(DpTestItemID);
									} else if (node.getNodeName().equals(
											"DeliveryNo")) {
										if (node.getChildNodes().getLength() != 0) {
											SampleID = node.getFirstChild()
													.getNodeValue();
											System.out.println(SampleID);
										} else
											SampleID = null;
									} else if (node.getNodeName().equals(
											"ResultData")) {
										if (node.getChildNodes().getLength() != 0) {
											ResultData = node.getFirstChild()
													.getNodeValue();
											System.out.println(ResultData);
										} else
											ResultData = "";
									} else if (node.getNodeName()
											.equals("Unit")) {
										if (node.getChildNodes().getLength() != 0) {
											ResultUnit = node.getFirstChild()
													.getNodeValue();
											System.out.println(ResultUnit);
										} else
											ResultUnit = "";

									}
								}
							}
							if (SampleID != null) { // 判断什么作用？
								int ReturnCode = 0;
								wCall = "{call Pro_UpdateItemLabData(?,?,?,?,?)}";
								proc = conn.prepareCall(wCall);
								proc.setString(1, SampleID);
								proc.setString(2, DpTestItemID);
								proc.setString(3, ResultData);
								proc.setString(4, ResultUnit);
								proc.registerOutParameter(5,
										java.sql.Types.INTEGER);
								proc.execute();
								ReturnCode = proc.getInt(5);
								proc.close();

								wCall = "{call Pro_UpdateReadTestDataFlg(?)}";
								proc = conn.prepareCall(wCall);
								proc.setString(1, labApplyNo);
								proc.execute();
								proc.close();
								conn.close();
								if (ReturnCode != 0)
									System.out.println("读取结果成功");
							}
						}

					} else
						System.out.println(labApplyNo + "没有检测结果");
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return "success";
	}

	public int judgeReport(String labApplyNo) {
		int flag = 0; // 用于是否读取检测结果
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost;
		HttpResponse response;
		HttpEntity entity;
		try {
			StringEntity stringEntity = new StringEntity(this.getHeader(
					"ifReport", labApplyNo), "text/xml", "utf-8");
			System.out.println(labApplyNo);
			stringEntity.setChunked(true);
			httpPost = new HttpPost(
					ConPool.getConfig().getLrpUrl()
							+ "/BizReport_List/GetMultiple?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
			httpPost.setEntity(stringEntity);
			response = httpclient.execute(httpPost);

			// Examine the response status
			System.out.println(response.getStatusLine());

			// Get hold of the response entity
			entity = response.getEntity();

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
				// ExportXml.export(result, "查询检测报告");
				Reader rr = new StringReader(result);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));
				Element root = document.getDocumentElement();
				Node itemAmount = root.getElementsByTagName("ItemAmount").item(
						0);
				String reportCount = itemAmount.getFirstChild().getNodeValue();
				if (reportCount.equals("0")) {
					System.out.println("没有检测报告！");
				} else {
					Node reportFileID = root.getElementsByTagName(
							"ReportFileID").item(0);
					String fileID = reportFileID.getFirstChild().getNodeValue();
					saveTestReport(fileID, labApplyNo);
					flag = 1;
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return flag;
	}

	public void saveTestReport(String fileID, String labApplyNo) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet;
		HttpResponse response;
		HttpEntity entity;
		FileOutputStream fos = null;
		try {
			httpGet = new HttpGet(
					ConPool.getConfig().getLrpUrl()
							+ "/File/Download?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2&FileID="
							+ fileID);

			// Execute the request
			response = httpclient.execute(httpGet);

			// Examine the response status
			System.out.println(response.getStatusLine());

			// Get hold of the response entity
			entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity != null) {
				InputStream instream = entity.getContent();

				try {
					String fileName = labApplyNo;
					// String filePath = System.getProperty("user.home")+
					// File.separator + "TestReport" + File.separator+ fileName
					// + ".doc";
					String filePath = ConPool.getConfig().getSaveUrl()
							+ fileName + ".doc";
					File dstFile = new File(filePath);
					if (dstFile.exists())
						dstFile.delete();
					else {
						if (!dstFile.getParentFile().exists())
							dstFile.getParentFile().mkdirs();
						if (!dstFile.exists()) {
							try {
								dstFile.createNewFile();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					fos = new FileOutputStream(dstFile);
					int i = 0;
					while ((i = instream.read()) != -1) {
						fos.write(i);
					}
					System.out.println("检测报告保存成功");
					this.saveReportFileName(labApplyNo, fileName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						fos.close();
						instream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public void saveReportFileName(String labApplyNo, String fileName) {
		Connection conn = ConPool.getConnection();
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{call Pro_SaveLabReportFilename(?,?)}");
			proc.setString(1, labApplyNo);
			proc.setString(2, fileName);
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
	}

	public String getHeader(String judge, String labApplyNo) {
		String xmlStr;
		org.jdom.Element root = new org.jdom.Element("DataRoot");
		org.jdom.Document doc = new org.jdom.Document(root);
		if (judge.equals("TestResult")) {
			org.jdom.Element sql = new org.jdom.Element("Sql");
			sql.setText(" AND m.BizNo='" + labApplyNo + "'");
			root.addContent(sql);
		} else if (judge.equals("ifReport")) {
			org.jdom.Element sql = new org.jdom.Element("Sql");
			// sql.setText( "AND m.BizID=301");
			sql.setText(" And BizNo='"
					+ labApplyNo
					+ "' AND BizStatus IN (10180, 10190) AND ReportStatus = 50560");
			
			 * org.jdom.Element optionCode = new org.jdom.Element("OptionCode");
			 * optionCode.setText("2"); org.jdom.Element formCode = new
			 * org.jdom.Element("FormCode"); formCode.setText("65");
			 
			org.jdom.Element pageSize = new org.jdom.Element("PageSize");
			pageSize.setText("1");
			org.jdom.Element pageIndex = new org.jdom.Element("PageIndex");
			pageIndex.setText("0");
			root.addContent(sql);
			// root.addContent(optionCode);
			// root.addContent(formCode);
			root.addContent(pageSize);
			root.addContent(pageIndex);
		}
		org.jdom.output.Format format = org.jdom.output.Format
				.getPrettyFormat();
		format.setEncoding("utf-8");// 设置编码格式
		XMLOutputter xmlout = new XMLOutputter(format);
		// xmlout.output(Doc, new PrintWriter(System.out));
		xmlStr = xmlout.outputString(doc);
		//System.out.println(xmlStr);
		return xmlStr;
	}
*/}
