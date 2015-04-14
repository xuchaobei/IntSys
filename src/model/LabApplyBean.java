package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LabApplyBean {
    private int labApplyID;
    private String declNo;
    private String applyNo;
    private String applyKind;
    private String applyKindCode;
    private String applyKindNo;
    private String applyKindID;
    private String sampleKind;
    private String sampleKindID;
    private String entName;
    private String applyOrg;
    private String applyDept;
    private String applyDeptID;
    private String appliant;
    private String appliantID;
    private String countryName;
    private String domRegion;
    private String labOrg;
    private String labDept;
    private String labDeptID;
    private String labGroup;
    private String sampleState;
    private String sampleDisposal;
    private String samplePreservation;
    private String remarks;
    private byte submitFlg;
    private byte ifUrgent;
    private String sampleName;
    private float sampleCount;
    private String countUnit;
    private String applyDate;
    private String fee;                    //13.1.16 float类型修改成String
    private byte sendFlg;

	public String getApplyKindNo() {
		return applyKindNo;
	}
	public void setApplyKindNo(String applyKindNo) {
		this.applyKindNo = applyKindNo;
	}
	public int getLabApplyID() {
		return labApplyID;
	}
	public String getDeclNo() {
		return declNo;
	}
	public void setDeclNo(String declNo) {
		this.declNo = declNo;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getApplyKind() {
		return applyKind;
	}
	public void setApplyKind(String applyKind) {
		this.applyKind = applyKind;
	}
	public String getApplyKindCode() {
		return applyKindCode;
	}
	public void setApplyKindCode(String applyKindCode) {
		this.applyKindCode = applyKindCode;
	}
	public String getApplyKindID() {
		return applyKindID;
	}
	public void setApplyKindID(String applyKindID) {
		this.applyKindID = applyKindID;
	}
	public String getSampleKind() {
		return sampleKind;
	}
	public void setSampleKind(String sampleKind) {
		this.sampleKind = sampleKind;
	}
	public String getSampleKindID() {
		return sampleKindID;
	}
	public void setSampleKindID(String sampleKindID) {
		this.sampleKindID = sampleKindID;
	}
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	public String getApplyOrg() {
		return applyOrg;
	}
	public void setApplyOrg(String applyOrg) {
		this.applyOrg = applyOrg;
	}
	public String getApplyDept() {
		return applyDept;
	}
	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}
	public String getApplyDeptID() {
		return applyDeptID;
	}
	public void setApplyDeptID(String applyDeptID) {
		this.applyDeptID = applyDeptID;
	}
	public String getAppliant() {
		return appliant;
	}
	public void setAppliant(String appliant) {
		this.appliant = appliant;
	}
	public String getAppliantID() {
		return appliantID;
	}
	public void setAppliantID(String appliantID) {
		this.appliantID = appliantID;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getDomRegion() {
		return domRegion;
	}
	public void setDomRegion(String domRegion) {
		this.domRegion = domRegion;
	}
	public String getLabOrg() {
		return labOrg;
	}
	public void setLabOrg(String labOrg) {
		this.labOrg = labOrg;
	}
	public String getLabDept() {
		return labDept;
	}
	public void setLabDept(String labDept) {
		this.labDept = labDept;
	}
	public String getLabDeptID() {
		return labDeptID;
	}
	public void setLabDeptID(String labDeptID) {
		this.labDeptID = labDeptID;
	}
	public String getLabGroup() {
		return labGroup;
	}
	public void setLabGroup(String labGroup) {
		this.labGroup = labGroup;
	}
	public String getSampleState() {
		return sampleState;
	}
	public void setSampleState(String sampleState) {
		this.sampleState = sampleState;
	}
	public String getSampleDisposal() {
		return sampleDisposal;
	}
	public void setSampleDisposal(String sampleDisposal) {
		this.sampleDisposal = sampleDisposal;
	}
	public String getSamplePreservation() {
		return samplePreservation;
	}
	public void setSamplePreservation(String samplePreservation) {
		this.samplePreservation = samplePreservation;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public byte getSubmitFlg() {
		return submitFlg;
	}
	public void setSubmitFlg(byte submitFlg) {
		this.submitFlg = submitFlg;
	}
	public byte getIfUrgent() {
		return ifUrgent;
	}
	public void setIfUrgent(byte ifUrgent) {
		this.ifUrgent = ifUrgent;
	}
	public float getSampleCount() {
		return sampleCount;
	}
	public void setSampleCount(float sampleCount) {
		this.sampleCount = sampleCount;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public byte getSendFlg() {
		return sendFlg;
	}
	public void setSendFlg(byte sendFlg) {
		this.sendFlg = sendFlg;
	}
	public void setLabApplyID(int labApplyID) {
		this.labApplyID = labApplyID;
	}
	public String getSampleName() {
		return sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getCountUnit() {
		return countUnit;
	}
	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

    public LabApplyBean(ResultSet rs){
    	try {
			this.setAppliant(rs.getString("Appliant"));
			this.setAppliantID(rs.getString("AppliantID"));
			this.setApplyDate(rs.getString("ApplyDate"));
			this.setApplyDept(rs.getString("ApplyDept"));
			this.setApplyDeptID(rs.getString("ApplyDeptID"));
			this.setApplyKind(rs.getString("ApplyKind"));
			this.setApplyKindCode(rs.getString("ApplyKindCode"));
			this.setApplyKindNo(rs.getString("ApplyKindNo"));
			this.setApplyKindID(rs.getString("ApplyKindID"));
			this.setApplyNo(rs.getString("ApplyNo"));
			this.setApplyOrg(rs.getString("ApplyOrg"));
			this.setCountryName(rs.getString("countryName"));
			this.setCountUnit(rs.getString("countUnit"));
			this.setDeclNo(rs.getString("DeclNo"));
			this.setDomRegion(rs.getString("domRegion"));
			this.setEntName(rs.getString("entName"));
			this.setFee(rs.getString("fee"));                                          //13.1.16修改
			this.setIfUrgent(rs.getByte("ifUrgent"));
			this.setLabApplyID(rs.getInt("labApplyID"));
			this.setLabDept(rs.getString("labDept"));
			this.setLabDeptID(rs.getString("labDeptID"));
			this.setLabGroup(rs.getString("labGroup"));
			this.setLabOrg(rs.getString("labOrg"));
			this.setRemarks(rs.getString("remarks"));
			this.setSampleCount(rs.getFloat("sampleCount"));
			this.setSampleDisposal(rs.getString("sampleDisposal"));
			this.setSampleKind(rs.getString("sampleKind"));
			this.setSampleKindID(rs.getString("SampleKindID"));
			this.setSampleName(rs.getString("sampleName"));
			this.setSamplePreservation(rs.getString("samplePreservation"));
			this.setSampleState(rs.getString("SampleState"));
			this.setSendFlg(rs.getByte("SendFlg"));
			this.setSubmitFlg(rs.getByte("submitFlg"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
