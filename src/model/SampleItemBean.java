package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SampleItemBean {
    private int sampleItemID;
    private int sampleID;
    private String testItemID;
    private String testItemCode;  
    private String itemID;
    private String itemCode;
    private String itemName;
   // private String LRPItemNo;
  //  private String LRPItemName;
    private String labData;
    private String labDataUnit;
    private String deptID;   
    private String deptName;
    //以下为新增
    private String standardNo;
    private String standardName;
    private String productCategoryCode;
    private String productCategoryName;
    private String detectionLimit;
    private String resultUnit;
    private String limit;
    private String testPeriod;
    private String testFee;
    private String testGroupID;
    private String testGroupName;
    private String defaultUserID;
    private String defaultUserName;
    
    
    
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTestGroupID() {
		return testGroupID;
	}
	public void setTestGroupID(String testGroupID) {
		this.testGroupID = testGroupID;
	}
	public String getTestGroupName() {
		return testGroupName;
	}
	public void setTestGroupName(String testGroupName) {
		this.testGroupName = testGroupName;
	}
	public String getDefaultUserID() {
		return defaultUserID;
	}
	public void setDefaultUserID(String defaultUserID) {
		this.defaultUserID = defaultUserID;
	}
	public String getDefaultUserName() {
		return defaultUserName;
	}
	public void setDefaultUserName(String defaultUserName) {
		this.defaultUserName = defaultUserName;
	}
	public String getStandardNo() {
		return standardNo;
	}
	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}
	public String getStandardName() {
		return standardName;
	}
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	public String getProductCategoryCode() {
		return productCategoryCode;
	}
	public void setProductCategoryCode(String productCategoryCode) {
		this.productCategoryCode = productCategoryCode;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public String getDetectionLimit() {
		return detectionLimit;
	}
	public void setDetectionLimit(String detectionLimit) {
		this.detectionLimit = detectionLimit;
	}
	public String getResultUnit() {
		return resultUnit;
	}
	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getTestPeriod() {
		return testPeriod;
	}
	public void setTestPeriod(String testPeriod) {
		this.testPeriod = testPeriod;
	}
	public String getTestFee() {
		return testFee;
	}
	public void setTestFee(String testFee) {
		this.testFee = testFee;
	}
	public String getDeptID() {
		return deptID;
	}
	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}
	public String getTestItemID() {
		return testItemID;
	}
	public void setTestItemID(String testItemID) {
		this.testItemID = testItemID;
	}
	public String getTestItemCode() {
		return testItemCode;
	}
	public void setTestItemCode(String testItemCode) {
		this.testItemCode = testItemCode;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getSampleItemID() {
		return sampleItemID;
	}
	public void setSampleItemID(int sampleItemID) {
		this.sampleItemID = sampleItemID;
	}
	public int getSampleID() {
		return sampleID;
	}
	public void setSampleID(int sampleID) {
		this.sampleID = sampleID;
	}

	public String getLabData() {
		return labData;
	}
	public void setLabData(String labData) {
		this.labData = labData;
	}
	public String getLabDataUnit() {
		return labDataUnit;
	}
	public void setLabDataUnit(String labDataUnit) {
		this.labDataUnit = labDataUnit;
	}
    public SampleItemBean(ResultSet rs){
    	try {
			this.setSampleItemID(rs.getInt("sampleItemID"));
			this.setSampleID(rs.getInt("SampleID"));
			this.setLabData(rs.getString("labData"));
			this.setLabDataUnit(rs.getString("labDataUnit"));
		//	this.setLRPItemName(rs.getString("LRPItemName"));  //数据库表中没有这个字段
			
			this.setTestItemID(rs.getString("TestItemID"));
			this.setTestItemCode(rs.getString("TestItemCode"));
			this.setItemID(rs.getString("ItemID"));
			this.setItemCode(rs.getString("ItemCode"));
			this.setItemName(rs.getString("ItemName"));
			this.setDeptID(rs.getString("DeptID"));
			this.setDeptName(rs.getString("LRPDepartment"));
			this.setStandardNo(rs.getString("StandardNo"));
			this.setStandardName(rs.getString("StandardName"));
			this.setProductCategoryCode(rs.getString("productCategoryCode"));
			this.setProductCategoryName(rs.getString("productCategoryName"));
			this.setDetectionLimit(rs.getString("detectionLimit"));
			this.setResultUnit(rs.getString("resultUnit"));
			this.setLimit(rs.getString("limit"));
			this.setTestPeriod(rs.getString("testPeriod"));
			this.setTestFee(rs.getString("testFee"));
			this.setTestGroupID(rs.getString("testGroupID"));
			this.setTestGroupName(rs.getString("testGroupName"));
			this.setDefaultUserID(rs.getString("defaultUserID"));
			this.setDefaultUserName(rs.getString("defaultUserName"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
