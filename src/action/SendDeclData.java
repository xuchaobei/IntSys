package action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.LabApplyBean;
import model.SampleBean;
import model.SampleItemBean;
import model.SamplingBean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import servlet.ConPool;

public class SendDeclData {/*
	private String declNo;
	private String result;
	private String applyID;
	private String applyNo;
	private String status; // 发送样品信息时使用
	private String typeCode;

	public String getDeclNo() {
		return declNo;
	}

	public void setDeclNo(String declNo) {
		this.declNo = declNo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLabApply() {
		Connection conn = ConPool.getConnection();
		CallableStatement proc = null;
		ResultSet rs = null;
		LabApplyBean labApplyBean;
		List<LabApplyBean> labApplyList = new ArrayList<LabApplyBean>();
		try {
			proc = conn.prepareCall("{call Pro_GetLabApplyInfoByDeclNo(?,?)}");
			proc.setString(1, declNo);
			proc.registerOutParameter(2, java.sql.Types.INTEGER);
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				labApplyBean = new LabApplyBean(rs);
				labApplyList.add(labApplyBean);
			}
			int returnCode = proc.getInt(2);
			if (returnCode == 0) {
				this.creatLabApplyXml(labApplyList);
				this.result = Integer.toString(returnCode);
			} else {
				if (returnCode == 1)
					this.result = Integer.toString(returnCode); // 1：没有报验数据
				if (returnCode == 2)
					this.result = Integer.toString(returnCode); // 2：报验数据已发送
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
				e.printStackTrace();
			}
		}
		return "success";
	}

	public void creatLabApplyXml(List<LabApplyBean> labApplyList) {
		Element dataRoot;
		Element root;
		Document doc;
		Element BizID, BizNo, BizTypeID, BizTypeName, BizTypeCode, BizOriginalNo, SampleTypeID, ProductName, ProductNameEN, ProductCount, ProductCountUnit, DeliveryDeptID, DeliveryUserID, DeliveryTelephone, DeliveryMobile, ProductCondition, ProductPreservation, ProductDisposition, ReportVersion, ReportDelivery, ReportUse, TestRemark, BizRemark, DueDate, Retest, RetestBizNo, RetestSN, AcceptDeptID, DraftDeptID, CIQNo, SampleTypeName, AcceptUserID, AcceptUserName, AcceptDate, DomesticOrigin, ExportCountry, biz_Fee, biz_CustomerList, BizTypeNo;

		for (int i = 0; i < labApplyList.size(); i++) {
			dataRoot = new Element("DataRoot");
			root = new Element("biz_Main");
			doc = new Document(dataRoot);

			BizID = new Element("BizID"); //

			BizNo = new Element("BizNo"); //

			BizTypeID = new Element("BizTypeID");
			BizTypeID.setText(labApplyList.get(i).getApplyKindID());
			// BizTypeID.setText("32");

			BizTypeName = new Element("BizTypeName");
			BizTypeName.setText(labApplyList.get(i).getApplyKind());

			BizTypeCode = new Element("BizTypeCode");
			BizTypeCode.setText(labApplyList.get(i).getApplyKindCode());
			// BizTypeCode.setText("1023");

			BizTypeNo = new Element("BizTypeNo"); // 数据库表中没有
			BizTypeNo.setText(labApplyList.get(i).getApplyKindNo());
			// BizTypeNo.setText("23"); // 新增

			BizOriginalNo = new Element("BizOriginalNo");
			BizOriginalNo.setText(Integer.toString(labApplyList.get(i)
					.getLabApplyID())); // ?原始编号

			SampleTypeID = new Element("SampleTypeID");
			SampleTypeID.setText(labApplyList.get(i).getSampleKindID());

			ProductName = new Element("ProductName");
			ProductName.setText(labApplyList.get(i).getSampleName());

			ProductNameEN = new Element("ProductNameEN");//

			ProductCount = new Element("ProductCount");
			ProductCount.setText(Float.toString(labApplyList.get(i)
					.getSampleCount()));

			ProductCountUnit = new Element("ProductCountUnit");
			ProductCountUnit.setText(labApplyList.get(i).getCountUnit());

			DeliveryDeptID = new Element("DeliveryDeptID");
			DeliveryDeptID.setText(labApplyList.get(i).getApplyDeptID());

			DeliveryUserID = new Element("DeliveryUserID");
			DeliveryUserID.setText(labApplyList.get(i).getAppliantID());

			DeliveryTelephone = new Element("DeliveryTelephone");//

			DeliveryMobile = new Element("DeliveryMobile");//

			ProductCondition = new Element("ProductCondition");
			ProductCondition.setText(labApplyList.get(i).getSampleState());

			ProductPreservation = new Element("ProductPreservation");
			ProductPreservation.setText(labApplyList.get(i)
					.getSamplePreservation());

			ProductDisposition = new Element("ProductDisposition");
			ProductDisposition.setText(labApplyList.get(i).getSampleDisposal());

			ReportVersion = new Element("ReportVersion");//

			ReportDelivery = new Element("ReportDelivery");//

			ReportUse = new Element("ReportUse");//

			TestRemark = new Element("TestRemark");
			// TestRemark.setText(labApplyList.get(i).getRemarks());//?检测备注与业务备注区别,备注不管

			BizRemark = new Element("BizRemark");//
			// BizRemark.setText(labApplyList.get(i).getRemarks());

			DueDate = new Element("DueDate");
			// DueDate.setText(labApplyList.get(i).getApplyDate());//?

			Retest = new Element("Retest");//

			RetestBizNo = new Element("RetestBizNo");//

			RetestSN = new Element("RetestSN");//

			AcceptDeptID = new Element("AcceptDeptID");
			AcceptDeptID.setText("0");

			DraftDeptID = new Element("DraftDeptID");//

			CIQNo = new Element("CIQNo");
			CIQNo.setText(labApplyList.get(i).getDeclNo());

			SampleTypeName = new Element("SampleTypeName");
			SampleTypeName.setText(labApplyList.get(i).getSampleKind());

			AcceptUserID = new Element("AcceptUserID");// 送检员id和受理员id区别,受理人员都不用管
			AcceptUserID.setText("0"); // 高斯

			AcceptUserName = new Element("AcceptUserName");// group？？？
			// AcceptUserName.setText(labApplyList.get(i).get)
			AcceptUserName.setText("0"); // 高斯

			AcceptDate = new Element("AcceptDate");
			// AcceptDate.setText(labApplyList.get(i).getApplyDate());
			// //送检日期？？受理日期

			DomesticOrigin = new Element("DomesticOrigin");
			DomesticOrigin.setText(labApplyList.get(i).getDomRegion());

			ExportCountry = new Element("ExportCountry");
			ExportCountry.setText(labApplyList.get(i).getCountryName());

			biz_Fee = new Element("biz_Fee");
			Element Biz_ID, TotalItemFee, TotalCIQFee, TotalOtherFee, TotalFee, FinalFee, ChargeFee, InvoiceTitle;
			Biz_ID = new Element("BizID");
			TotalItemFee = new Element("TotalItemFee");
			TotalCIQFee = new Element("TotalCIQFee");
			TotalOtherFee = new Element("TotalOtherFee");
			TotalFee = new Element("TotalFee");
			TotalFee.setText(Float.toString(labApplyList.get(i).getFee()));
			FinalFee = new Element("FinalFee");
			ChargeFee = new Element("ChargeFee");
			InvoiceTitle = new Element("InvoiceTitle");
			biz_Fee.addContent(Biz_ID);
			biz_Fee.addContent(TotalItemFee);
			biz_Fee.addContent(TotalCIQFee);
			biz_Fee.addContent(TotalOtherFee);
			biz_Fee.addContent(TotalFee);
			biz_Fee.addContent(FinalFee);
			biz_Fee.addContent(ChargeFee);
			biz_Fee.addContent(InvoiceTitle);

			biz_CustomerList = new Element("biz_CustomerList");
			Element biz_Customer, BizCustomerType, BizCustomerTypeName, Invoice_Title, SortOrder, CustomerID, CustomerName, CustomerNameEN, CustomerAddress, ZipCode, ContactName, ContactTelphone, ContactMobile, ContactFax;
			biz_Customer = new Element("biz_Customer");
			BizCustomerType = new Element("BizCustomerType");
			BizCustomerType.setText("1"); // 高斯要求
			BizCustomerTypeName = new Element("BizCustomerTypeName");
			Invoice_Title = new Element("InvoiceTitle");
			SortOrder = new Element("SortOrder");
			CustomerID = new Element("CustomerID");
			CustomerName = new Element("CustomerName");
			CustomerName.setText(labApplyList.get(i).getEntName());
			CustomerNameEN = new Element("CustomerNameEN");
			CustomerAddress = new Element("CustomerAddress");
			ZipCode = new Element("ZipCode");
			ContactName = new Element("ContactName");
			ContactTelphone = new Element("ContactTelphone");
			ContactMobile = new Element("ContactMobile");
			ContactFax = new Element("ContactFax");
			biz_Customer.addContent(BizCustomerType);
			biz_Customer.addContent(BizCustomerTypeName);
			biz_Customer.addContent(Invoice_Title);
			biz_Customer.addContent(SortOrder);
			biz_Customer.addContent(CustomerID);
			biz_Customer.addContent(CustomerName);
			biz_Customer.addContent(CustomerNameEN);
			biz_Customer.addContent(CustomerAddress);
			biz_Customer.addContent(ZipCode);
			biz_Customer.addContent(ContactName);
			biz_Customer.addContent(ContactTelphone);
			biz_Customer.addContent(ContactMobile);
			biz_Customer.addContent(ContactFax);
			biz_CustomerList.addContent(biz_Customer);

			root.addContent(BizID);
			root.addContent(BizNo);
			root.addContent(BizTypeID);
			root.addContent(BizTypeName);
			root.addContent(BizTypeCode);
			root.addContent(BizTypeNo); // new
			root.addContent(BizOriginalNo);
			root.addContent(SampleTypeID);// new
			root.addContent(ProductName);
			root.addContent(ProductNameEN);
			root.addContent(ProductCount);
			root.addContent(ProductCountUnit);
			root.addContent(DeliveryDeptID);
			root.addContent(DeliveryUserID);
			root.addContent(DeliveryTelephone);
			root.addContent(DeliveryMobile);
			root.addContent(ProductCondition);
			root.addContent(ProductPreservation);
			root.addContent(ProductDisposition);
			root.addContent(ReportVersion);
			root.addContent(ReportDelivery);
			root.addContent(ReportUse);
			root.addContent(TestRemark);
			root.addContent(BizRemark);
			root.addContent(DueDate);
			root.addContent(Retest);
			root.addContent(RetestBizNo);
			root.addContent(RetestSN);
			root.addContent(AcceptDeptID);
			root.addContent(DraftDeptID);
			root.addContent(CIQNo);
			root.addContent(SampleTypeName);
			root.addContent(AcceptUserID);
			root.addContent(AcceptUserName);
			root.addContent(AcceptDate);
			root.addContent(DomesticOrigin);
			root.addContent(ExportCountry);
			root.addContent(biz_Fee);
			root.addContent(biz_CustomerList);
			dataRoot.addContent(root);

			org.jdom.output.Format format = org.jdom.output.Format
					.getPrettyFormat();
			format.setEncoding("utf-8");// 设置编码格式
			XMLOutputter xmlout = new XMLOutputter(format);
			// xmlout.output(Doc, new PrintWriter(System.out));
			String xmlStr = xmlout.outputString(doc);
			System.out.println(xmlStr);

			this.sendLabApply(xmlStr, labApplyList.get(i));
		}

	}

	public void sendLabApply(String xmlStr, LabApplyBean labApplyBean) {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			StringEntity stringEntity = new StringEntity(xmlStr, "text/xml",
					"utf-8"); // 第二三个参数合并为一个参数："text/plain;charset=\"UTF-8\"";
			stringEntity.setChunked(true);
			HttpPost httpPost = new HttpPost(
					ConPool.getConfig().getLrpUrl()
							+ "/BizAccept/AcceptMain?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
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
				ExportXml.export(result, "LRP返回报验数据");
				this.saveLabApplyNo(result, labApplyBean);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			httpClient.getConnectionManager().shutdown();
		}

	}

	// 保存报检号
	public void saveLabApplyNo(String result, LabApplyBean labApplyBean) {
		try {
			Reader rr = new StringReader(result);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder domBuilder = builderFactory.newDocumentBuilder();
			org.w3c.dom.Document document = (org.w3c.dom.Document) domBuilder
					.parse(new InputSource(rr));
			org.w3c.dom.Element root = document.getDocumentElement();
			Node bizID = root.getElementsByTagName("BizID").item(0);
			Node bizNo = root.getElementsByTagName("BizNo").item(0);
			Node bizTypeCode = root.getElementsByTagName("BizTypeCode").item(0);
			Node bizStatus = root.getElementsByTagName("BizStatus").item(0); // 仅在发送样品信息时使用
			if (bizID.getChildNodes().getLength() != 0)
				applyID = bizID.getFirstChild().getNodeValue();
			if (bizNo.getChildNodes().getLength() != 0)
				applyNo = bizNo.getFirstChild().getNodeValue();
			if (bizTypeCode.getChildNodes().getLength() != 0)
				typeCode = bizTypeCode.getFirstChild().getNodeValue();
			if (bizStatus.getChildNodes().getLength() != 0)
				status = bizStatus.getFirstChild().getNodeValue();
			// System.out.println("----报验号----" + applyNo + "---报验ID---" +
			// applyID);
			Connection conn = ConPool.getConnection();
			CallableStatement proc = null;
			try {
				proc = conn.prepareCall("{call Pro_UpdateLabApplyNo(?,?,?,?)}");
				proc.setInt(1, labApplyBean.getLabApplyID());
				proc.setString(2, applyNo);
				proc.setString(3, applyID);
				proc.registerOutParameter(4, java.sql.Types.INTEGER);
				proc.execute();
				int returnCode = proc.getInt(4);
				if (returnCode != 0) {
					System.out.println("保存报检号成功"); // 非0表示成功
					this.getLabSample(labApplyBean);
				}
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
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getLabSample(LabApplyBean labApplyBean) {
		Connection conn = ConPool.getConnection();
		CallableStatement proc = null;
		ResultSet rs = null;
		SampleBean sampleBean;
		SampleItemBean sampleItemBean;
		SamplingBean samplingBean;
		List<SampleBean> sampleList = new ArrayList<SampleBean>();

		ArrayList<Element> elements = new ArrayList<Element>();
		try {
			proc = conn.prepareCall("{call Pro_GetLabSampleByLabApplyID(?)}");
			proc.setInt(1, labApplyBean.getLabApplyID());
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				sampleBean = new SampleBean(rs);
				sampleList.add(sampleBean);
			}

			// 得到项目集合和检验集合
			for (int i = 0; i < sampleList.size(); i++) {
				List<SampleItemBean> sampleItemList = new ArrayList<SampleItemBean>();
				List<SamplingBean> samplingList = new ArrayList<SamplingBean>();
				proc = conn
						.prepareCall("{call Pro_GetLabSampleItemBySampleID(?)}");
				proc.setInt(1, sampleList.get(i).getSampleID());
				proc.execute();
				rs = proc.getResultSet();
				while (rs.next()) {
					sampleItemBean = new SampleItemBean(rs);
					sampleItemList.add(sampleItemBean);
				}
				if (proc.getMoreResults())
					rs = proc.getResultSet();
				while (rs.next()) {
					samplingBean = new SamplingBean(rs);
					samplingList.add(samplingBean);
				}
				elements.add(this.createSampleXml(sampleList.get(i),
						sampleItemList, samplingList)); // 生成biz_Sample子节点,样品子节点
			}
			this.createTotalXml(elements);
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
				e.printStackTrace();
			}
		}
	}

	public void createTotalXml(ArrayList<Element> biz_Samples) {
		Element dataRoot;
		Element root;
		Document doc;
		Element BizID, BizTypeCode, BizStatus, biz_SampleList;

		dataRoot = new Element("DataRoot");
		root = new Element("biz_Main");
		doc = new Document(dataRoot);
		BizID = new Element("BizID");
		BizID.setText(applyID);
		BizTypeCode = new Element("BizTypeCode");
		BizTypeCode.setText(typeCode);
		BizStatus = new Element("BizStatus");
		BizStatus.setText(status);
		biz_SampleList = new Element("biz_SampleList");

		for (int i = 0; i < biz_Samples.size(); i++) {
			biz_SampleList.addContent(biz_Samples.get(i));
		}
		root.addContent(BizID);
		root.addContent(BizTypeCode);
		root.addContent(BizStatus);
		root.addContent(biz_SampleList);
		dataRoot.addContent(root);

		org.jdom.output.Format format = org.jdom.output.Format
				.getPrettyFormat();
		format.setEncoding("utf-8");// 设置编码格式
		XMLOutputter xmlout = new XMLOutputter(format);
		// xmlout.output(Doc, new PrintWriter(System.out));
		String xmlStr = xmlout.outputString(doc);
		System.out.println(xmlStr);
		// ExportXml.export(xmlStr, "发送到LRP的样品检样数据");
		this.sendLabSample(xmlStr);
	}

	public Element createSampleXml(SampleBean sampleBean,
			List<SampleItemBean> sampleItemList, List<SamplingBean> samplingList) {

		Element biz_Sample, SampleNo, SampleNumber, SampleName, SampleMark, SampleSpec, SampleQuantity, DeliveryNo, SampleDueDate, SampleRemark;
		Element biz_SampleItemList, biz_SampleItem, TestItemID, TestItemCode, ItemID, ItemCode, ItemName, DeptID;
		Element biz_TestSampleList, biz_TestSample, Dept_ID, Dept_Name; // 注意最后两个节点名字和生成节点的名字不同

		biz_Sample = new Element("biz_Sample");

		SampleNo = new Element("SampleNo");

		SampleNumber = new Element("SampleNumber");

		SampleName = new Element("SampleName");
		SampleName.setText(sampleBean.getSampleName());

		SampleMark = new Element("SampleMark");
		SampleMark.setText(sampleBean.getSampleMarked());

		SampleSpec = new Element("SampleSpec");

		SampleQuantity = new Element("SampleQuantity");
		SampleQuantity.setText(sampleBean.getSampleCount());

		DeliveryNo = new Element("DeliveryNo");
		DeliveryNo.setText(Integer.toString(sampleBean.getSampleID()));

		SampleDueDate = new Element("SampleDueDate");

		SampleRemark = new Element("SampleRemark");
		SampleRemark.setText(sampleBean.getSampleRemarks());

		biz_SampleItemList = new Element("biz_SampleItemList");

		for (int j = 0; j < sampleItemList.size(); j++) {
			biz_SampleItem = new Element("biz_SampleItem");
			TestItemID = new Element("TestItemID"); // lrp生成
			TestItemID.setText(sampleItemList.get(j).getTestItemID());

			TestItemCode = new Element("TestItemCode");
			TestItemCode.setText(sampleItemList.get(j).getItemCode());

			ItemID = new Element("ItemID");
			ItemID.setText(sampleItemList.get(j).getItemID());

			ItemCode = new Element("ItemCode");
			ItemCode.setText(sampleItemList.get(j).getItemCode());

			ItemName = new Element("ItemName");
			ItemName.setText(sampleItemList.get(j).getItemName());

			DeptID = new Element("DeptID");
			DeptID.setText(sampleItemList.get(j).getDeptID()); // t-labSampleItem
																// 增加此字段

			biz_SampleItem.addContent(TestItemID);
			biz_SampleItem.addContent(TestItemCode);
			biz_SampleItem.addContent(ItemID);
			biz_SampleItem.addContent(ItemCode);
			biz_SampleItem.addContent(ItemName);
			biz_SampleItem.addContent(DeptID);
			biz_SampleItemList.addContent(biz_SampleItem);
		}
		biz_TestSampleList = new Element("biz_TestSampleList");
		for (int k = 0; k < samplingList.size(); k++) {
			biz_TestSample = new Element("biz_TestSample");
			Dept_ID = new Element("DeptID");
			Dept_ID.setText(samplingList.get(k).getLabDeptID());
			Dept_Name = new Element("DeptName");
			Dept_Name.setText(samplingList.get(k).getLabDeptName());
			biz_TestSample.addContent(Dept_ID);
			biz_TestSample.addContent(Dept_Name);
			biz_TestSampleList.addContent(biz_TestSample);
		}

		biz_Sample.addContent(SampleNo);
		biz_Sample.addContent(SampleNumber);
		biz_Sample.addContent(SampleName);
		biz_Sample.addContent(SampleMark);
		biz_Sample.addContent(SampleSpec);
		biz_Sample.addContent(SampleQuantity);
		biz_Sample.addContent(DeliveryNo);
		biz_Sample.addContent(SampleDueDate);
		biz_Sample.addContent(SampleRemark);
		biz_Sample.addContent(biz_SampleItemList);
		biz_Sample.addContent(biz_TestSampleList);
		return biz_Sample;
	}

	public void sendLabSample(String xmlStr) {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			StringEntity stringEntity = new StringEntity(xmlStr, "text/xml",
					"utf-8"); // 第二三个参数合并为一个参数："text/plain;charset=\"UTF-8\"";
			stringEntity.setChunked(true);
			HttpPost httpPost = new HttpPost(
					ConPool.getConfig().getLrpUrl()
							+ "/BizAccept/AcceptSample?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&uid=1&uname=admin&utype=2");
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
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
				// ExportXml.export(result, "LRP返回样品检样数据");
				this.saveSampleNoAndSamplingNo(result);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			httpClient.getConnectionManager().shutdown();
		}

	}

	// 保存样品号和检样号
	public void saveSampleNoAndSamplingNo(String result) {
		try {
			Reader rr = new StringReader(result);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder domBuilder = builderFactory.newDocumentBuilder();
			org.w3c.dom.Document document = (org.w3c.dom.Document) domBuilder
					.parse(new InputSource(rr));
			org.w3c.dom.Element root = document.getDocumentElement();

			NodeList biz_Sample = root.getElementsByTagName("biz_Sample");
			System.out.println(biz_Sample.getLength());
			if (biz_Sample != null) {
				for (int i = 0; i < biz_Sample.getLength(); i++) {
					Node sample = biz_Sample.item(i);
					String sampleID = "";
					String sampleNo = "";
					String labDeptID = "";
					String samplingNo = "";
					String sampleBarcode = "";
					String samplingBarcode = "";
					for (Node node = sample.getFirstChild(); node != null; node = node
							.getNextSibling()) {
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							if (node.getNodeName().equals("SampleNo")) {
								if (node.getChildNodes().getLength() != 0)
									sampleNo = node.getFirstChild()
											.getNodeValue();
							}
							if (node.getNodeName().equals("DeliveryNo")) {
								if (node.getChildNodes().getLength() != 0)
									sampleID = node.getFirstChild()
											.getNodeValue();
							}
							if (node.getNodeName().equals("SampleBarcode")) {
								if (node.getChildNodes().getLength() != 0)
									sampleBarcode = node.getFirstChild()
											.getNodeValue();
							}

							if (node.getNodeName().equals("biz_TestSampleList")) { // 获得检样号
								org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
								NodeList biz_TestSample = nodeElement
										.getElementsByTagName("biz_TestSample");
								for (int k = 0; k < biz_TestSample.getLength(); k++) {
									Node biz_sampling = biz_TestSample.item(k);
									for (Node nodeOfTestSample = biz_sampling
											.getFirstChild(); nodeOfTestSample != null; nodeOfTestSample = nodeOfTestSample
											.getNextSibling()) {

										if (nodeOfTestSample.getNodeName()
												.equals("TestSampleID")) {
											if (nodeOfTestSample
													.getChildNodes()
													.getLength() != 0)
												samplingNo = nodeOfTestSample
														.getFirstChild()
														.getNodeValue();
										}
										if (nodeOfTestSample.getNodeName()
												.equals("DeptID")) {
											if (nodeOfTestSample
													.getChildNodes()
													.getLength() != 0)
												labDeptID = nodeOfTestSample
														.getFirstChild()
														.getNodeValue();
										}
										if (nodeOfTestSample.getNodeName()
												.equals("TestSampleBarcode")) {
											if (nodeOfTestSample
													.getChildNodes()
													.getLength() != 0)
												samplingBarcode = nodeOfTestSample
														.getFirstChild()
														.getNodeValue();
										}

									}
									Connection conn = ConPool.getConnection();
									CallableStatement proc = null;
									try {
										proc = conn
												.prepareCall("{call Pro_UpdateSamplingNo(?,?,?,?,?)}");
										proc.setInt(1,
												Integer.parseInt(sampleID));
										proc.setString(2, samplingNo);
										proc.setString(3, labDeptID);
										proc.setString(4, samplingBarcode);
										proc.registerOutParameter(5,
												java.sql.Types.INTEGER);
										proc.execute();
										int returnCode1 = proc.getInt(5);
										if (returnCode1 != 0)
											System.out.println("保存检样号成功");
										proc.close();
										conn.close();
									} catch (NumberFormatException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							}
						}
					}
					// System.out.println("---样品号---" + sampleNo);
					Connection conn = ConPool.getConnection();
					CallableStatement proc = null;
					try {
						proc = conn
								.prepareCall("{call Pro_UpdateSampleNo(?,?,?,?,?)}");
						proc.setInt(1, Integer.parseInt(sampleID));
						proc.setString(2, sampleNo);
						proc.setString(3, applyNo);
						proc.setString(4, sampleBarcode);
						proc.registerOutParameter(5, java.sql.Types.INTEGER);
						proc.execute();
						int returnCode = proc.getInt(5);
						if (returnCode != 0) {
							System.out.println("保存样品号成功"); // 非0表示成功
						}

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
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/}
