package servlet;

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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.CIQDeclBean;
import model.CIQFeeBean;
import model.CIQGoodsBean;
import model.LabApplyBean;
import model.SampleBean;
import model.SampleItemBean;
import model.SamplingBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import dao.DaoTask3;


//定时发送报验样品等信息
public class Task3 extends TimerTask {
	private String applyNo;
	private String applyID;
	private String typeCode;
	private String status; // 发送样品信息时使用
	private static boolean isRunning = false;
	private static Log log=LogFactory.getLog(Task3.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			if (!isRunning) {
				isRunning = true;
				this.getLabApply();
				isRunning = false;
			} else {
				//System.out.println("task3上一次认为还未结束！");
				log.info("task3上一次认为还未结束！");
			}
		}catch(Exception e){
			//System.out.println("Task3失败!");
			log.info("Task3失败!");
			isRunning = false;
		}
		
	}

	public void getLabApply() {
		Date now = new Date();
        DateFormat d=DateFormat.getDateTimeInstance();	        
		//System.out.println("读取需要发送的报验数据----"+d.format(now));
        log.info("读取需要发送的报验数据----"+d.format(now));
        try{
        	List<LabApplyBean> labApplyList=DaoTask3.getLabApplyDao("food");
        	log.info("发送食品报验数据----"+d.format(new Date()));
    		this.creatLabApplyXml(labApplyList,"food");
    		List<LabApplyBean> labApplyList2=DaoTask3.getLabApplyDao("hzp");
    		log.info("发送化妆品报验数据----"+d.format(new Date()));
    		this.creatLabApplyXml(labApplyList2,"hzp");
        }catch(Exception e){
        	log.error("错误",e);
        }		
	}

	public void creatLabApplyXml(List<LabApplyBean> labApplyList,String url) throws Exception {
		Element dataRoot;
		Element root;
		Document doc;
		Element FuncCode,BizID, BizNo,BizSource, BizTypeID, BizTypeName, BizTypeCode, BizOriginalNo, SampleTypeID, ProductName, ProductNameEN, ProductCount, ProductCountUnit, DeliveryDeptID, DeliveryUserID, DeliveryTelephone, DeliveryMobile, ProductCondition, ProductPreservation, ProductDisposition, ReportVersion, ReportDelivery, ReportUse, TestRemark, BizRemark, DueDate, Retest, RetestBizNo, RetestSN, AcceptDeptID, DraftDeptID, CIQNo, SampleTypeName, AcceptUserID, AcceptUserName, AcceptDate, DomesticOrigin, ExportCountry, biz_Fee, biz_CustomerList, BizTypeNo;
	
		CIQDeclBean wCIQDeclBean;
		CIQGoodsBean wCIQGoodsBean;
		List<CIQFeeBean> wCIQFeeList;
		String originCountryCode;
		String originCountryName;
		String tradeCountryCode;
		String tradeCountryName;
		
		for (int i = 0; i < labApplyList.size(); i++) {
			wCIQDeclBean=DaoTask3.getCIQDeclDao(url,labApplyList.get(i).getDeclNo());
			tradeCountryCode = wCIQDeclBean.getTRADE_COUNTRY_CODE();                     //2014-6-21 add
			wCIQGoodsBean=DaoTask3.getCIQGoodsDao(url, labApplyList.get(i).getDeclNo());
			originCountryCode = wCIQGoodsBean.getORIGIN_COUNTRY_CODE();                  //2014-6-21 add
			wCIQFeeList=DaoTask3.getCIQFeeDao(url,labApplyList.get(i).getDeclNo());       //2014-6-21 add
			List<String> list  = DaoTask3.getCountryName(url, originCountryCode, tradeCountryCode); //2014-6-21 add
			originCountryName = list.get(0);//2014-6-21 add
			tradeCountryName = list.get(1);//2014-6-21 add
			dataRoot = new Element("DataRoot");
			root = new Element("biz_Main");
			doc = new Document(dataRoot);
			
            FuncCode=new Element("FuncCode");
            FuncCode.setText("1215");   //10、15新增
            
			BizID = new Element("BizID"); //

			BizNo = new Element("BizNo"); //
			
			BizSource=new Element("BizSource");
			BizSource.setText("2");

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
			BizRemark.setText(labApplyList.get(i).getAppliant()+","+labApplyList.get(i).getApplyDept());   //10、13新增

			DueDate = new Element("DueDate");
			// DueDate.setText(labApplyList.get(i).getApplyDate());//?

			Retest = new Element("Retest");//

			RetestBizNo = new Element("RetestBizNo");//

			RetestSN = new Element("RetestSN");//

			AcceptDeptID = new Element("AcceptDeptID");
			//AcceptDeptID.setText("0");
			AcceptDeptID.setText(labApplyList.get(i).getApplyDeptID());

			DraftDeptID = new Element("DraftDeptID");//

			CIQNo = new Element("CIQNo");
			CIQNo.setText(labApplyList.get(i).getDeclNo());

			SampleTypeName = new Element("SampleTypeName");
			SampleTypeName.setText(labApplyList.get(i).getSampleKind());

			AcceptUserID = new Element("AcceptUserID");// 送检员id和受理员id区别,受理人员都不用管
			//AcceptUserID.setText("0"); // 高斯
			AcceptUserID.setText(labApplyList.get(i).getAppliantID()); 
			
			AcceptUserName = new Element("AcceptUserName");	
			AcceptUserName.setText(labApplyList.get(i).getAppliant()); // 高斯

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
			//TotalItemFee.setText(Float.toString(labApplyList.get(i).getFee()));
			TotalItemFee.setText("0");
			TotalCIQFee = new Element("TotalCIQFee");
			TotalCIQFee.setText(labApplyList.get(i).getFee());                            //13.1.16改
			TotalOtherFee = new Element("TotalOtherFee");
			TotalOtherFee.setText("0");
			TotalFee = new Element("TotalFee");
			TotalFee.setText(labApplyList.get(i).getFee());                               //13.1.16改
			FinalFee = new Element("FinalFee");
			FinalFee.setText(labApplyList.get(i).getFee());                               //13.1.16改
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
			BizCustomerTypeName.setText("委托单位");
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
			
			Element CIQDetail,biz_FeeCIQList;
			CIQDetail = new Element("CIQDetail");
			biz_FeeCIQList=new Element("biz_FeeCIQList");
			if(wCIQDeclBean!=null&&wCIQGoodsBean!=null){
				Element CONSIGNOR_CNAME, CONSIGNEE_CNAME, TRANS_TYPE, TRANS_MEANS_CODE, ENTRY_PORT, DESP_Port, CONTRACT_NO, CARRIER_NOTE_NO, GOODS_PLACE, ARRI_DATE, UNLOAD_DATE, DECL_NO, HS_CODE, GOODS_CNAME, GOODS_ENAME, GOODS_MODEL, MARK_NO, WEIGHT, WEIGHT_UNIT_CODE, QTY, 
				QTY_UNIT_CODE, INSP_ORG_CODE, INSP_DEPT_1, CCY, DESP_DATE, GOODS_VALUES, VALUES_USD, ARRI_PORT_CODE, VALUES_RMB, DECL_REG_NO, DECL_DATE, PACK_NUMBER, PACK_TYPE_CODE, CIQ_CODE, CONSIGNOR_CODE, PROD_REG_NO, CONSIGNEE_CODE, TRADE_COUNTRY_CODE, ORIGIN_PLACE_CODE, ORIGIN_COUNTRY_CODE, 
				TRANS_TYPE_CODE, DESP_PORT_CODE, ENTRY_PORT_CODE,VIA_PORT_CODE, GOODS_NO, ENT_CNAME, Ent_EName, CONTACTOR, TELEPHONE, PROD_REG, ccy_name, WEIGHT_UNIT, QTY_UNIT, ORIGIN_PLACE, ORIGIN_COUNTRY, TRADE_COUNTRY, PACK_TYPE, ARRI_PORT;
				
				CONSIGNOR_CNAME = new Element("CONSIGNOR_CNAME");
				CONSIGNOR_CNAME.setText(wCIQDeclBean.getCONSIGNOR_CNAME());
				CONSIGNEE_CNAME = new Element("CONSIGNEE_CNAME");
				CONSIGNEE_CNAME.setText(wCIQDeclBean.getCONSIGNEE_CNAME());
				TRANS_TYPE = new Element("TRANS_TYPE");
				TRANS_TYPE.setText(wCIQDeclBean.getTRANS_TYPE_CODE());
				TRANS_MEANS_CODE = new Element("TRANS_MEANS_CODE");
				TRANS_MEANS_CODE.setText(wCIQDeclBean.getTRANS_MEANS_CODE());
				ENTRY_PORT = new Element("ENTRY_PORT");
				ENTRY_PORT.setText(wCIQDeclBean.getENTRY_PORT_CODE());
				DESP_Port = new Element("DESP_Port");
				DESP_Port.setText(wCIQDeclBean.getDESP_PORT_CODE());
				CONTRACT_NO = new Element("CONTRACT_NO");
				CONTRACT_NO.setText(wCIQDeclBean.getCONTRACT_NO());
				CARRIER_NOTE_NO = new Element("CARRIER_NOTE_NO");
				CARRIER_NOTE_NO.setText(wCIQDeclBean.getCARRIER_NOTE_NO());
				GOODS_PLACE = new Element("GOODS_PLACE");
				GOODS_PLACE.setText(wCIQDeclBean.getGOODS_PLACE());
				ARRI_DATE = new Element("ARRI_DATE");
				ARRI_DATE.setText(wCIQDeclBean.getARRI_DATE());
				UNLOAD_DATE = new Element("UNLOAD_DATE");
				UNLOAD_DATE.setText(wCIQDeclBean.getUNLOAD_DATE());
				DECL_NO = new Element("DECL_NO");
				DECL_NO.setText(wCIQDeclBean.getDECL_NO());
				HS_CODE = new Element("HS_CODE");
				//HS_CODE.setText(wCIQGoodsBean.getGOODS_NO());
				HS_CODE.setText(wCIQGoodsBean.getHS_CODE());
				GOODS_CNAME = new Element("GOODS_CNAME");
				GOODS_CNAME.setText(wCIQGoodsBean.getGOODS_CNAME());
				GOODS_ENAME = new Element("GOODS_ENAME");
				GOODS_ENAME.setText(wCIQGoodsBean.getGOODS_ENAME());
				GOODS_MODEL = new Element("GOODS_MODEL");
				GOODS_MODEL.setText(wCIQGoodsBean.getGOODS_MODEL());
				MARK_NO = new Element("MARK_NO");
				MARK_NO.setText(wCIQDeclBean.getMARK_NO());
				WEIGHT = new Element("WEIGHT");
				WEIGHT.setText(wCIQGoodsBean.getWEIGHT());
				WEIGHT_UNIT_CODE = new Element("WEIGHT_UNIT_CODE");
				WEIGHT_UNIT_CODE.setText(wCIQGoodsBean.getWEIGHT_UNIT_CODE());
				QTY = new Element("QTY");
				QTY.setText(wCIQGoodsBean.getQTY());
				QTY_UNIT_CODE = new Element("QTY_UNIT_CODE");
				QTY_UNIT_CODE.setText(wCIQGoodsBean.getQTY());
				INSP_ORG_CODE = new Element("INSP_ORG_CODE");
				INSP_ORG_CODE.setText(wCIQDeclBean.getINSP_ORG_CODE());
				INSP_DEPT_1 = new Element("INSP_DEPT_1");
				INSP_DEPT_1.setText(wCIQDeclBean.getINSP_DEPT_1());
				CCY = new Element("CCY");
				CCY.setText(wCIQGoodsBean.getCCY());
				DESP_DATE = new Element("DESP_DATE");
				DESP_DATE.setText(wCIQDeclBean.getDESP_DATE());
				GOODS_VALUES = new Element("GOODS_VALUES");
				GOODS_VALUES.setText(wCIQGoodsBean.getGOODS_VALUES());
				VALUES_USD = new Element("VALUES_USD");
				VALUES_USD.setText(wCIQGoodsBean.getVALUES_USD());
				ARRI_PORT_CODE = new Element("ARRI_PORT_CODE");
				ARRI_PORT_CODE.setText(wCIQDeclBean.getARRI_PORT_CODE());
				VALUES_RMB = new Element("VALUES_RMB");
				VALUES_RMB.setText(wCIQGoodsBean.getVALUES_RMB());
				DECL_REG_NO = new Element("DECL_REG_NO");
				DECL_REG_NO.setText(wCIQDeclBean.getDECL_REG_NO());
				DECL_DATE = new Element("DECL_DATE");
				DECL_DATE.setText(wCIQDeclBean.getDECL_DATE());
				PACK_NUMBER = new Element("PACK_NUMBER");
				PACK_NUMBER.setText(wCIQGoodsBean.getPACK_NUMBER());
				PACK_TYPE_CODE = new Element("PACK_TYPE_CODE");
				PACK_TYPE_CODE.setText(wCIQGoodsBean.getPACK_TYPE_CODE());
				CIQ_CODE = new Element("CIQ_CODE");
				CONSIGNOR_CODE = new Element("CONSIGNOR_CODE");
				CONSIGNOR_CODE.setText(wCIQDeclBean.getCONSIGNOR_CODE());
				PROD_REG_NO = new Element("PROD_REG_NO");
				PROD_REG_NO.setText(wCIQDeclBean.getPROD_REG_NO());
				CONSIGNEE_CODE = new Element("CONSIGNEE_CODE");
				CONSIGNEE_CODE.setText(wCIQDeclBean.getCONSIGNEE_CODE());
				TRADE_COUNTRY_CODE = new Element("TRADE_COUNTRY_CODE");
				TRADE_COUNTRY_CODE.setText(wCIQDeclBean.getTRADE_COUNTRY_CODE());
				ORIGIN_PLACE_CODE = new Element("ORIGIN_PLACE_CODE");
				ORIGIN_PLACE_CODE.setText(wCIQGoodsBean.getORIGIN_PLACE_CODE());
				ORIGIN_COUNTRY_CODE = new Element("ORIGIN_COUNTRY_CODE");
				ORIGIN_COUNTRY_CODE.setText(wCIQGoodsBean.getORIGIN_COUNTRY_CODE());
				TRANS_TYPE_CODE = new Element("TRANS_TYPE_CODE");
				TRANS_TYPE_CODE.setText(wCIQDeclBean.getTRANS_TYPE_CODE());
				DESP_PORT_CODE = new Element("DESP_PORT_CODE");
				DESP_PORT_CODE.setText(wCIQDeclBean.getDESP_PORT_CODE());
				ENTRY_PORT_CODE = new Element("ENTRY_PORT_CODE");
				ENTRY_PORT_CODE.setText(wCIQDeclBean.getENTRY_PORT_CODE());
				VIA_PORT_CODE = new Element("VIA_PORT_CODE");
				VIA_PORT_CODE.setText(wCIQDeclBean.getVIA_PORT_CODE());
				GOODS_NO = new Element("GOODS_NO");
				GOODS_NO.setText("1");
				ENT_CNAME = new Element("ENT_CNAME");
				ENT_CNAME.setText(wCIQDeclBean.getCONSIGNEE_CNAME());
				Ent_EName = new Element("Ent_EName");
				Ent_EName.setText(wCIQDeclBean.getCONSIGNEE_ENAME());
				CONTACTOR = new Element("CONTACTOR");
				TELEPHONE = new Element("TELEPHONE");
				PROD_REG = new Element("PROD_REG");
				ccy_name = new Element("ccy_name");
				ccy_name.setText(wCIQGoodsBean.getCCY());
				WEIGHT_UNIT = new Element("WEIGHT_UNIT");
				WEIGHT_UNIT.setText(wCIQGoodsBean.getWEIGHT_UNIT_CODE());
				QTY_UNIT = new Element("QTY_UNIT");
				QTY_UNIT.setText(wCIQGoodsBean.getQTY_UNIT_CODE());
				ORIGIN_PLACE = new Element("ORIGIN_PLACE");
				ORIGIN_PLACE.setText(wCIQGoodsBean.getORIGIN_PLACE_CODE());
				ORIGIN_COUNTRY = new Element("ORIGIN_COUNTRY");
				ORIGIN_COUNTRY.setText(originCountryName);               //2014-6-21 add
				TRADE_COUNTRY = new Element("TRADE_COUNTRY");     
				TRADE_COUNTRY.setText(tradeCountryName);                //2014-6-21 add
				PACK_TYPE = new Element("PACK_TYPE");
				PACK_TYPE.setText(wCIQGoodsBean.getPACK_TYPE_CODE());
				ARRI_PORT = new Element("ARRI_PORT");
				ARRI_PORT.setText(wCIQDeclBean.getARRI_PORT_CODE());	
				
				CIQDetail.addContent(CONSIGNOR_CNAME);
				CIQDetail.addContent(CONSIGNEE_CNAME);
				CIQDetail.addContent(TRANS_TYPE);
				CIQDetail.addContent(TRANS_MEANS_CODE);
				CIQDetail.addContent(ENTRY_PORT);
				CIQDetail.addContent(DESP_Port);
				CIQDetail.addContent(CONTRACT_NO);
				CIQDetail.addContent(CARRIER_NOTE_NO);
				CIQDetail.addContent(GOODS_PLACE);
				CIQDetail.addContent(ARRI_DATE);
				CIQDetail.addContent(UNLOAD_DATE);
				CIQDetail.addContent(DECL_NO);
				CIQDetail.addContent(HS_CODE);
				CIQDetail.addContent(GOODS_CNAME);
				CIQDetail.addContent(GOODS_ENAME);
				CIQDetail.addContent(GOODS_MODEL);
				CIQDetail.addContent(MARK_NO);
				CIQDetail.addContent(WEIGHT);
				CIQDetail.addContent(WEIGHT_UNIT_CODE);
				CIQDetail.addContent(QTY);
				CIQDetail.addContent(QTY_UNIT_CODE);			
				CIQDetail.addContent(INSP_ORG_CODE);
				CIQDetail.addContent(INSP_DEPT_1);
				CIQDetail.addContent(CCY);
				CIQDetail.addContent(DESP_DATE);
				CIQDetail.addContent(GOODS_VALUES);
				CIQDetail.addContent(VALUES_USD);
				CIQDetail.addContent(ARRI_PORT_CODE);
				CIQDetail.addContent(VALUES_RMB);
				CIQDetail.addContent(DECL_REG_NO);
				CIQDetail.addContent(DECL_DATE);
				CIQDetail.addContent(PACK_NUMBER);
				CIQDetail.addContent(PACK_TYPE_CODE);
				CIQDetail.addContent(CIQ_CODE);
				CIQDetail.addContent(CONSIGNOR_CODE);
				CIQDetail.addContent(PROD_REG_NO);
				CIQDetail.addContent(CONSIGNEE_CODE);
				CIQDetail.addContent(TRADE_COUNTRY_CODE);
				CIQDetail.addContent(ORIGIN_PLACE_CODE);
				CIQDetail.addContent(ORIGIN_COUNTRY_CODE);			
				CIQDetail.addContent(TRANS_TYPE_CODE);
				CIQDetail.addContent(DESP_PORT_CODE);
				CIQDetail.addContent(ENTRY_PORT_CODE);
				CIQDetail.addContent(VIA_PORT_CODE);
				CIQDetail.addContent(GOODS_NO);
				CIQDetail.addContent(ENT_CNAME);
				CIQDetail.addContent(Ent_EName);
				CIQDetail.addContent(CONTACTOR);
				CIQDetail.addContent(TELEPHONE);
				CIQDetail.addContent(PROD_REG);
				CIQDetail.addContent(ccy_name);
				CIQDetail.addContent(WEIGHT_UNIT);
				CIQDetail.addContent(QTY_UNIT);
				CIQDetail.addContent(ORIGIN_PLACE);
				CIQDetail.addContent(ORIGIN_COUNTRY);
				CIQDetail.addContent(TRADE_COUNTRY);
				CIQDetail.addContent(PACK_TYPE);
				CIQDetail.addContent(ARRI_PORT);
				if(wCIQFeeList!=null){
					Element biz_FeeCIQ,FeeName,FeeMethod,FeeValue,FeeFlag;
					
					for(int j=0;j<wCIQFeeList.size();j++){
						biz_FeeCIQ=new Element("biz_FeeCIQ");
						FeeName=new Element("FeeName");
						FeeName.setText(wCIQFeeList.get(j).getFEE_ITEM_NO());
						FeeMethod=new Element("FeeMethod");
						FeeMethod.setText(wCIQFeeList.get(j).getCALCU_FEE_MODE());
						FeeValue=new Element("FeeValue");
						FeeValue.setText(wCIQFeeList.get(j).getFEE());
						FeeFlag=new Element("FeeFlag");
						if(wCIQFeeList.get(j).getREPORT_FLAG().equals("是"))
						     FeeFlag.setText("1");
						if(wCIQFeeList.get(j).getREPORT_FLAG().equals("否"))
						     FeeFlag.setText("0");
						
						biz_FeeCIQ.addContent(FeeName);
						biz_FeeCIQ.addContent(FeeMethod);
						biz_FeeCIQ.addContent(FeeValue);
						biz_FeeCIQ.addContent(FeeFlag);
						biz_FeeCIQList.addContent(biz_FeeCIQ);
					}
				}				
			}
	
			
			root.addContent(FuncCode);
			root.addContent(BizID);
			root.addContent(BizNo);
			root.addContent(BizSource);
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
			root.addContent(CIQDetail);
			root.addContent(biz_FeeCIQList);			
			dataRoot.addContent(root);

			org.jdom.output.Format format = org.jdom.output.Format
					.getPrettyFormat();
			format.setEncoding("utf-8");// 设置编码格式
			XMLOutputter xmlout = new XMLOutputter(format);
			// xmlout.output(Doc, new PrintWriter(System.out));
			String xmlStr = xmlout.outputString(doc);
			//System.out.println(xmlStr);
            log.info("发送报验信息"+labApplyList.get(i).getDeclNo());
            log.info(xmlStr);
			this.sendLabApply(xmlStr, labApplyList.get(i),url);
		}
	}

	public void sendLabApply(String xmlStr, LabApplyBean labApplyBean,String url) throws Exception {
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
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());
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
				log.info(result);
				// ExportXml.export(result, "LRP返回报验数据");
				this.saveLabApplyNo(result, labApplyBean,url);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ClientProtocolException e) {
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
		} catch (Exception e){
            log.error(e);
        } finally {
			httpClient.getConnectionManager().shutdown();
		}

	}

	// 保存报检号
	public void saveLabApplyNo(String result, LabApplyBean labApplyBean,String url) throws Exception {
	    Connection conn=null;
	    CallableStatement proc = null;
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
			if (bizID.getChildNodes().getLength() != 0){
				applyID = bizID.getFirstChild().getNodeValue();
			    log.info(applyID);
			}				
			if (bizNo.getChildNodes().getLength() != 0){
				applyNo = bizNo.getFirstChild().getNodeValue();
			    log.info(applyNo);
			}				
			if (bizTypeCode.getChildNodes().getLength() != 0)
				typeCode = bizTypeCode.getFirstChild().getNodeValue();
			if (bizStatus.getChildNodes().getLength() != 0)
				status = bizStatus.getFirstChild().getNodeValue();
			//Connection conn = ConPool.getConnection(url,user,psw);
			
			if(url.equals("food"))
			    conn = FoodDBPool.ds.getConnection();
			else if(url.equals("hzp"))
				conn=HZPDBPool.ds.getConnection();
		
			proc = conn.prepareCall("{call Pro_UpdateLabApplyNo(?,?,?,?)}");
			proc.setInt(1, labApplyBean.getLabApplyID());
			proc.setString(2, applyNo);
			proc.setString(3, applyID);
			proc.registerOutParameter(4, java.sql.Types.INTEGER);
			proc.execute();
			int returnCode = proc.getInt(4);
			if (returnCode != 0) {
				//System.out.println("保存报检号成功"); // 非0表示成功
				log.info("保存报检号成功");
				this.getLabSample(labApplyBean,url);
			}
			 
		} catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error(e);
        } catch (DOMException e) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e){
		    log.error(e); 
		} finally {
            try {
                if (proc != null)
                    proc.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                log.error(e);
            }
        }
	}

	public void getLabSample(LabApplyBean labApplyBean,String url) throws Exception {
		//Connection conn = ConPool.getConnection(url,user,psw);
		Connection conn=null;
		CallableStatement proc = null;
		ResultSet rs = null;
		SampleBean sampleBean;
		SampleItemBean sampleItemBean;
		SamplingBean samplingBean;
		List<SampleBean> sampleList = new ArrayList<SampleBean>();

		ArrayList<Element> elements = new ArrayList<Element>();
		try {
		    if(url.equals("food"))
	            conn = FoodDBPool.ds.getConnection();
	        else if(url.equals("hzp"))
	            conn=HZPDBPool.ds.getConnection();
			proc = conn.prepareCall("{call Pro_GetLabSampleByLabApplyID(?)}");
			proc.setInt(1, labApplyBean.getLabApplyID());
			proc.execute();
			rs = proc.getResultSet();
			while (rs.next()) {
				sampleBean = new SampleBean(rs);
				sampleList.add(sampleBean);
			}
			rs.close();
            proc.close();
            conn.close();
			// 得到项目集合和检验集合
			for (int i = 0; i < sampleList.size(); i++) {
			    if(url.equals("food"))
		            conn = FoodDBPool.ds.getConnection();
		        else if(url.equals("hzp"))
		            conn=HZPDBPool.ds.getConnection();
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
				rs.close();
				proc.close();
				conn.close();
				elements.add(this.createSampleXml(sampleList.get(i),
						sampleItemList, samplingList)); // 生成biz_Sample子节点,样品子节点
				
			}
			this.createTotalXml(elements,url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.info(e);
		} catch (Exception e) {
		    log.info(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				log.error(e);
			}
		}
	}

	public void createTotalXml(ArrayList<Element> biz_Samples,String url) throws Exception {
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
		//System.out.println(xmlStr);
		log.info("发送样品检样数据");
		log.info(xmlStr);
		// ExportXml.export(xmlStr, "发送到LRP的样品检样数据");
		this.sendLabSample(xmlStr,url);
	}

	public Element createSampleXml(SampleBean sampleBean,
			List<SampleItemBean> sampleItemList, List<SamplingBean> samplingList) {

		Element biz_Sample, SampleNo, SampleNumber, SampleName, SampleMark, SampleSpec, SampleQuantity, DeliveryNo, SampleDueDate, SampleRemark;
		Element biz_SampleItemList, biz_SampleItem, TestItemID, TestItemCode, ItemID, ItemCode, ItemName, DeptID,DeptName,
		        StandardNo,StandardName,ProductCategoryCode,ProductCategoryName,DetectionLimit,ResultUnit,Limit,TestPeriod,TestFee,TestGroupID,TestGroupName,DefaultUserID,DefaultUserName;
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
			DeptName=new Element("DeptName");
			DeptName.setText(sampleItemList.get(j).getDeptName());
			
			StandardNo=new Element("StandardNo");
			StandardNo.setText(sampleItemList.get(j).getStandardNo());
			
			StandardName=new Element("StandardName");
			StandardName.setText(sampleItemList.get(j).getStandardName());
			
			ProductCategoryCode=new Element("ProductCategoryCode");
			ProductCategoryCode.setText(sampleItemList.get(j).getProductCategoryCode());
			
			ProductCategoryName=new Element("ProductCategoryName");
			ProductCategoryName.setText(sampleItemList.get(j).getProductCategoryName());

			DetectionLimit=new Element("DetectionLimit");
			DetectionLimit.setText(sampleItemList.get(j).getDetectionLimit());
			
			ResultUnit=new Element("ResultUnit");
			ResultUnit.setText(sampleItemList.get(j).getResultUnit());
			
			Limit=new Element("Limit");
			Limit.setText(sampleItemList.get(j).getLimit());
			
			TestPeriod=new Element("TestPeriod");
			TestPeriod.setText(sampleItemList.get(j).getTestPeriod());
			
			TestFee=new Element("TestFee");
			TestFee.setText(sampleItemList.get(j).getTestFee());
			
		    TestGroupID=new Element("TestGroupID");
		    TestGroupID.setText(sampleItemList.get(j).getTestGroupID());
		    
		    TestGroupName=new Element("TestGroupName");
		    TestGroupName.setText(sampleItemList.get(j).getTestGroupName());
		    
		    DefaultUserID=new Element("DefaultUserID");
		    DefaultUserID.setText(sampleItemList.get(j).getDefaultUserID());
		    
		    DefaultUserName=new Element("DefaultUserName");
		    DefaultUserName.setText(sampleItemList.get(j).getDefaultUserName());
			
			biz_SampleItem.addContent(TestItemID);
			biz_SampleItem.addContent(TestItemCode);
			biz_SampleItem.addContent(ItemID);
			biz_SampleItem.addContent(ItemCode);
			biz_SampleItem.addContent(ItemName);
			biz_SampleItem.addContent(DeptID);
			biz_SampleItem.addContent(DeptName);       //7、3新增检测部门
			biz_SampleItem.addContent(StandardNo);
			biz_SampleItem.addContent(StandardName);
			biz_SampleItem.addContent(ProductCategoryCode);
			biz_SampleItem.addContent(ProductCategoryName);
			biz_SampleItem.addContent(DetectionLimit);
			biz_SampleItem.addContent(ResultUnit);
			biz_SampleItem.addContent(Limit);
			biz_SampleItem.addContent(TestPeriod);
			biz_SampleItem.addContent(TestFee);
			biz_SampleItem.addContent(TestGroupID);
			biz_SampleItem.addContent(TestGroupName);
			biz_SampleItem.addContent(DefaultUserID);
			biz_SampleItem.addContent(DefaultUserName);
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

	public void sendLabSample(String xmlStr,String url) throws Exception {
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
			//System.out.println(response.getStatusLine());
			log.info(response.getStatusLine());
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
				log.info(result);
				//ExportXml.export(result, "LRP返回样品检样数据");
				this.saveSampleNoAndSamplingNo(result,url);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (ClientProtocolException e) {
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
		} catch (Exception e){
		    log.error(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

	}

	// 保存样品号和检样号
	public void saveSampleNoAndSamplingNo(String result,String url) throws Exception {
		try {
			Reader rr = new StringReader(result);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder domBuilder = builderFactory.newDocumentBuilder();
			org.w3c.dom.Document document = (org.w3c.dom.Document) domBuilder
					.parse(new InputSource(rr));
			org.w3c.dom.Element root = document.getDocumentElement();

			NodeList biz_Sample = root.getElementsByTagName("biz_Sample");
			//System.out.println(biz_Sample.getLength());
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
									//Connection conn = ConPool.getConnection(url,user,psw);
									Connection conn=null;
									if(url.equals("food"))
									    conn = FoodDBPool.ds.getConnection();
									else if(url.equals("hzp"))
										conn=HZPDBPool.ds.getConnection();
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
											//System.out.println("保存检样号成功");
										    log.info("保存检样号成功"+samplingNo);										
									} catch (NumberFormatException e) {
										// TODO Auto-generated catch block
										//e.printStackTrace();
										log.error(e);
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										//e.printStackTrace();
										log.error(e);
									} catch (Exception e){
							            log.error(e);
							        } finally {
										try {
											if (proc != null)
												proc.close();
											if (conn != null)
												conn.close();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											//e.printStackTrace();
											log.error(e);
										}
									}
								}

							}
						}
					}
					//System.out.println("---样品号---" + sampleNo);
					//Connection conn = ConPool.getConnection(url,user,psw);
					Connection conn=null;
					if(url.equals("food"))
					    conn = FoodDBPool.ds.getConnection();
					else if(url.equals("hzp"))
						conn=HZPDBPool.ds.getConnection();
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
							//System.out.println("保存样品号成功"); // 非0表示成功
							log.info("保存样品号成功"+sampleNo);
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						log.error(e);
					} catch (Exception e){
			            log.error(e);
			        } finally {
						try {
							if (proc != null)
								proc.close();
							if (conn != null)
								conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							log.error(e);
						}
					}
				}
			}
		} catch (DOMException e) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e);
		} catch (Exception e){
            log.error(e);
        }
	}

}
