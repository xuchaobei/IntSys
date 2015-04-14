package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CIQDeclBean {
    private String CONSIGNOR_CNAME;
    private String CONSIGNEE_CNAME;
    private String TRANS_TYPE_CODE;
    private String TRANS_MEANS_CODE;
    private String ENTRY_PORT_CODE;
    private String DESP_PORT_CODE;
    private String CONTRACT_NO;
    private String CARRIER_NOTE_NO;
    private String GOODS_PLACE;
    private String ARRI_DATE;
    private String UNLOAD_DATE;
    private String DECL_NO;
    private String MARK_NO;
    private String INSP_ORG_CODE;
    private String INSP_DEPT_1;
    private String DESP_DATE;
    private String ARRI_PORT_CODE;
    private String DECL_REG_NO;
    private String DECL_DATE;
    private String CONSIGNOR_CODE;
    private String PROD_REG_NO;
    private String CONSIGNEE_CODE;
    private String TRADE_COUNTRY_CODE;
    private String VIA_PORT_CODE;   
    private String CONSIGNEE_ENAME;
	public String getCONSIGNOR_CNAME() {
		return CONSIGNOR_CNAME;
	}
	public void setCONSIGNOR_CNAME(String cONSIGNOR_CNAME) {
		CONSIGNOR_CNAME = cONSIGNOR_CNAME;
	}
	public String getCONSIGNEE_CNAME() {
		return CONSIGNEE_CNAME;
	}
	public void setCONSIGNEE_CNAME(String cONSIGNEE_CNAME) {
		CONSIGNEE_CNAME = cONSIGNEE_CNAME;
	}
	public String getTRANS_TYPE_CODE() {
		return TRANS_TYPE_CODE;
	}
	public void setTRANS_TYPE_CODE(String tRANS_TYPE_CODE) {
		TRANS_TYPE_CODE = tRANS_TYPE_CODE;
	}
	public String getTRANS_MEANS_CODE() {
		return TRANS_MEANS_CODE;
	}
	public void setTRANS_MEANS_CODE(String tRANS_MEANS_CODE) {
		TRANS_MEANS_CODE = tRANS_MEANS_CODE;
	}
	public String getENTRY_PORT_CODE() {
		return ENTRY_PORT_CODE;
	}
	public void setENTRY_PORT_CODE(String eNTRY_PORT_CODE) {
		ENTRY_PORT_CODE = eNTRY_PORT_CODE;
	}
	public String getDESP_PORT_CODE() {
		return DESP_PORT_CODE;
	}
	public void setDESP_PORT_CODE(String dESP_PORT_CODE) {
		DESP_PORT_CODE = dESP_PORT_CODE;
	}
	public String getCONTRACT_NO() {
		return CONTRACT_NO;
	}
	public void setCONTRACT_NO(String cONTRACT_NO) {
		CONTRACT_NO = cONTRACT_NO;
	}
	public String getCARRIER_NOTE_NO() {
		return CARRIER_NOTE_NO;
	}
	public void setCARRIER_NOTE_NO(String cARRIER_NOTE_NO) {
		CARRIER_NOTE_NO = cARRIER_NOTE_NO;
	}
	public String getGOODS_PLACE() {
		return GOODS_PLACE;
	}
	public void setGOODS_PLACE(String gOODS_PLACE) {
		GOODS_PLACE = gOODS_PLACE;
	}
	public String getARRI_DATE() {
		return ARRI_DATE;
	}
	public void setARRI_DATE(String aRRI_DATE) {
		ARRI_DATE = aRRI_DATE;
	}
	public String getUNLOAD_DATE() {
		return UNLOAD_DATE;
	}
	public void setUNLOAD_DATE(String uNLOAD_DATE) {
		UNLOAD_DATE = uNLOAD_DATE;
	}
	public String getDECL_NO() {
		return DECL_NO;
	}
	public void setDECL_NO(String dECL_NO) {
		DECL_NO = dECL_NO;
	}
	public String getMARK_NO() {
		return MARK_NO;
	}
	public void setMARK_NO(String mARK_NO) {
		MARK_NO = mARK_NO;
	}
	public String getINSP_ORG_CODE() {
		return INSP_ORG_CODE;
	}
	public void setINSP_ORG_CODE(String iNSP_ORG_CODE) {
		INSP_ORG_CODE = iNSP_ORG_CODE;
	}
	public String getINSP_DEPT_1() {
		return INSP_DEPT_1;
	}
	public void setINSP_DEPT_1(String iNSP_DEPT_1) {
		INSP_DEPT_1 = iNSP_DEPT_1;
	}
	public String getDESP_DATE() {
		return DESP_DATE;
	}
	public void setDESP_DATE(String dESP_DATE) {
		DESP_DATE = dESP_DATE;
	}
	public String getARRI_PORT_CODE() {
		return ARRI_PORT_CODE;
	}
	public void setARRI_PORT_CODE(String aRRI_PORT_CODE) {
		ARRI_PORT_CODE = aRRI_PORT_CODE;
	}
	public String getDECL_REG_NO() {
		return DECL_REG_NO;
	}
	public void setDECL_REG_NO(String dECL_REG_NO) {
		DECL_REG_NO = dECL_REG_NO;
	}
	public String getDECL_DATE() {
		return DECL_DATE;
	}
	public void setDECL_DATE(String dECL_DATE) {
		DECL_DATE = dECL_DATE;
	}
	public String getCONSIGNOR_CODE() {
		return CONSIGNOR_CODE;
	}
	public void setCONSIGNOR_CODE(String cONSIGNOR_CODE) {
		CONSIGNOR_CODE = cONSIGNOR_CODE;
	}
	public String getPROD_REG_NO() {
		return PROD_REG_NO;
	}
	public void setPROD_REG_NO(String pROD_REG_NO) {
		PROD_REG_NO = pROD_REG_NO;
	}
	public String getCONSIGNEE_CODE() {
		return CONSIGNEE_CODE;
	}
	public void setCONSIGNEE_CODE(String cONSIGNEE_CODE) {
		CONSIGNEE_CODE = cONSIGNEE_CODE;
	}
	public String getTRADE_COUNTRY_CODE() {
		return TRADE_COUNTRY_CODE;
	}
	public void setTRADE_COUNTRY_CODE(String tRADE_COUNTRY_CODE) {
		TRADE_COUNTRY_CODE = tRADE_COUNTRY_CODE;
	}
	public String getVIA_PORT_CODE() {
		return VIA_PORT_CODE;
	}
	public void setVIA_PORT_CODE(String vIA_PORT_CODE) {
		VIA_PORT_CODE = vIA_PORT_CODE;
	}
	public String getCONSIGNEE_ENAME() {
		return CONSIGNEE_ENAME;
	}
	public void setCONSIGNEE_ENAME(String cONSIGNEE_ENAME) {
		CONSIGNEE_ENAME = cONSIGNEE_ENAME;
	}    
    
    public CIQDeclBean(ResultSet rs){
    	try{
    		this.setARRI_DATE(rs.getString("aRRI_DATE"));
    		this.setARRI_PORT_CODE(rs.getString("aRRI_PORT_CODE"));
    		this.setCARRIER_NOTE_NO(rs.getString("cARRIER_NOTE_NO"));
    		this.setCONSIGNEE_CNAME(rs.getString("cONSIGNEE_CNAME"));
    		this.setCONSIGNEE_CODE(rs.getString("cONSIGNEE_CODE"));
    		this.setCONSIGNEE_ENAME(rs.getString("cONSIGNEE_ENAME"));
    		this.setCONSIGNOR_CNAME(rs.getString("cONSIGNOR_CNAME"));
    		this.setCONSIGNOR_CODE(rs.getString("cONSIGNOR_CODE"));
    		this.setCONTRACT_NO(rs.getString("cONTRACT_NO"));
    		this.setDECL_DATE(rs.getString("dECL_DATE"));
    		this.setDECL_NO(rs.getString("dECL_NO"));
    		this.setDECL_REG_NO(rs.getString("dECL_REG_NO"));
    		this.setDESP_DATE(rs.getString("dESP_DATE"));
    		this.setDESP_PORT_CODE(rs.getString("dESP_PORT_CODE"));
    		this.setENTRY_PORT_CODE(rs.getString("eNTRY_PORT_CODE"));
    		this.setGOODS_PLACE(rs.getString("gOODS_PLACE"));
    		this.setINSP_DEPT_1(rs.getString("iNSP_DEPT_1"));
    		this.setINSP_ORG_CODE(rs.getString("iNSP_ORG_CODE"));
    		this.setMARK_NO(rs.getString("mARK_NO"));
    		this.setPROD_REG_NO(rs.getString("pROD_REG_NO"));
    		this.setTRADE_COUNTRY_CODE(rs.getString("tRADE_COUNTRY_CODE"));
    		this.setTRANS_MEANS_CODE(rs.getString("tRANS_MEANS_CODE"));
    		this.setTRANS_TYPE_CODE(rs.getString("tRANS_TYPE_CODE"));
    		this.setUNLOAD_DATE(rs.getString("uNLOAD_DATE"));
    		this.setVIA_PORT_CODE(rs.getString("vIA_PORT_CODE"));
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}    	
    }
}
