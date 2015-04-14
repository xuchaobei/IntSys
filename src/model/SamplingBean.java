package model;

import java.sql.ResultSet;
import java.sql.SQLException;

//¼ìÑùÊý¾Ý
public class SamplingBean {
    private int SamplingID;
    private int SampleID;
    private String LabDeptID;
    private String LabDeptName;
    private String SamplingNo;
	public int getSamplingID() {
		return SamplingID;
	}
	public void setSamplingID(int samplingID) {
		SamplingID = samplingID;
	}
	public int getSampleID() {
		return SampleID;
	}
	public void setSampleID(int sampleID) {
		SampleID = sampleID;
	}
	public String getLabDeptID() {
		return LabDeptID;
	}
	public void setLabDeptID(String labDeptID) {
		LabDeptID = labDeptID;
	}
	public String getLabDeptName() {
		return LabDeptName;
	}
	public void setLabDeptName(String labDeptName) {
		LabDeptName = labDeptName;
	}
	public String getSamplingNo() {
		return SamplingNo;
	}
	public void setSamplingNo(String samplingNo) {
		SamplingNo = samplingNo;
	}
    public SamplingBean(ResultSet rs){
    	try {
			this.setSamplingID(rs.getInt("samplingID"));
			this.setSampleID(rs.getInt("sampleID"));
			this.setLabDeptID(rs.getString("labDeptID"));
			this.setLabDeptName(rs.getString("labDeptName"));
			this.setSamplingNo(rs.getString("samplingNo"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
