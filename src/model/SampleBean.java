package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SampleBean {
    private int sampleID;
    private String sampleName;
    private String sampleCount;
    private String countUnit;
    private String sampleMarked;
    private String sampleRemarks;
    private byte ifUrgent;
    private String labApplyID;
    private String applyNo;
    private String sampleNo;
    private String declNo;
    private int goodsNo;
	public int getSampleID() {
		return sampleID;
	}
	public void setSampleID(int sampleID) {
		this.sampleID = sampleID;
	}
	public String getSampleName() {
		return sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	public String getSampleCount() {
		return sampleCount;
	}
	public void setSampleCount(String sampleCount) {
		this.sampleCount = sampleCount;
	}
	public String getCountUnit() {
		return countUnit;
	}
	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}
	public String getSampleMarked() {
		return sampleMarked;
	}
	public void setSampleMarked(String sampleMarked) {
		this.sampleMarked = sampleMarked;
	}
	public String getSampleRemarks() {
		return sampleRemarks;
	}
	public void setSampleRemarks(String sampleRemarks) {
		this.sampleRemarks = sampleRemarks;
	}
	public byte getIfUrgent() {
		return ifUrgent;
	}
	public void setIfUrgent(byte ifUrgent) {
		this.ifUrgent = ifUrgent;
	}
	public String getLabApplyID() {
		return labApplyID;
	}
	public void setLabApplyID(String labApplyID) {
		this.labApplyID = labApplyID;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	public String getDeclNo() {
		return declNo;
	}
	public void setDeclNo(String declNo) {
		this.declNo = declNo;
	}
	public int getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(int goodsNo) {
		this.goodsNo = goodsNo;
	}
    
    public SampleBean(ResultSet rs){
    	try {
			this.setSampleID(rs.getInt("sampleID"));
			this.setGoodsNo(rs.getInt("GoodsNo"));
			this.setApplyNo(rs.getString("applyNo"));
			this.setCountUnit(rs.getString("countUnit"));
			this.setDeclNo(rs.getString("declNo"));
			this.setIfUrgent(rs.getByte("ifUrgent"));
			this.setLabApplyID(rs.getString("labApplyID"));
			this.setSampleCount(rs.getString("sampleCount"));
			this.setSampleMarked(rs.getString("sampleMarked"));
			this.setSampleName(rs.getString("sampleName"));
			this.setSampleNo(rs.getString("SampleNo"));
			this.setSampleRemarks(rs.getString("sampleRemarks"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
