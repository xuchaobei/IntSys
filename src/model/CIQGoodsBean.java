package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CIQGoodsBean {
    private String GOODS_NO;
    private String GOODS_CNAME;
    private String GOODS_ENAME;
    private String GOODS_MODEL;
    private String WEIGHT;
    private String WEIGHT_UNIT_CODE;
    private String QTY;
    private String QTY_UNIT_CODE;
    private String CCY;
    private String GOODS_VALUES;
    private String VALUES_USD;
    private String VALUES_RMB;
    private String PACK_NUMBER;
    private String PACK_TYPE_CODE;
    private String ORIGIN_PLACE_CODE;
    private String ORIGIN_COUNTRY_CODE;
    private String HS_CODE;            //2014-6-21 add
    
	public String getVALUES_RMB() {
		return VALUES_RMB;
	}
	public void setVALUES_RMB(String vALUES_RMB) {
		VALUES_RMB = vALUES_RMB;
	}
	public String getGOODS_NO() {
		return GOODS_NO;
	}
	public void setGOODS_NO(String gOODS_NO) {
		GOODS_NO = gOODS_NO;
	}
	public String getGOODS_CNAME() {
		return GOODS_CNAME;
	}
	public void setGOODS_CNAME(String gOODS_CNAME) {
		GOODS_CNAME = gOODS_CNAME;
	}
	public String getGOODS_ENAME() {
		return GOODS_ENAME;
	}
	public void setGOODS_ENAME(String gOODS_ENAME) {
		GOODS_ENAME = gOODS_ENAME;
	}
	public String getGOODS_MODEL() {
		return GOODS_MODEL;
	}
	public void setGOODS_MODEL(String gOODS_MODEL) {
		GOODS_MODEL = gOODS_MODEL;
	}
	public String getWEIGHT() {
		return WEIGHT;
	}
	public void setWEIGHT(String wEIGHT) {
		WEIGHT = wEIGHT;
	}
	public String getWEIGHT_UNIT_CODE() {
		return WEIGHT_UNIT_CODE;
	}
	public void setWEIGHT_UNIT_CODE(String wEIGHT_UNIT_CODE) {
		WEIGHT_UNIT_CODE = wEIGHT_UNIT_CODE;
	}
	public String getQTY() {
		return QTY;
	}
	public void setQTY(String qTY) {
		QTY = qTY;
	}
	public String getQTY_UNIT_CODE() {
		return QTY_UNIT_CODE;
	}
	public void setQTY_UNIT_CODE(String qTY_UNIT_CODE) {
		QTY_UNIT_CODE = qTY_UNIT_CODE;
	}
	public String getCCY() {
		return CCY;
	}
	public void setCCY(String cCY) {
		CCY = cCY;
	}
	public String getGOODS_VALUES() {
		return GOODS_VALUES;
	}
	public void setGOODS_VALUES(String gOODS_VALUES) {
		GOODS_VALUES = gOODS_VALUES;
	}
	public String getVALUES_USD() {
		return VALUES_USD;
	}
	public void setVALUES_USD(String vALUES_USD) {
		VALUES_USD = vALUES_USD;
	}
	public String getPACK_NUMBER() {
		return PACK_NUMBER;
	}
	public void setPACK_NUMBER(String pACK_NUMBER) {
		PACK_NUMBER = pACK_NUMBER;
	}
	public String getPACK_TYPE_CODE() {
		return PACK_TYPE_CODE;
	}
	public void setPACK_TYPE_CODE(String pACK_TYPE_CODE) {
		PACK_TYPE_CODE = pACK_TYPE_CODE;
	}
	public String getORIGIN_PLACE_CODE() {
		return ORIGIN_PLACE_CODE;
	}
	public void setORIGIN_PLACE_CODE(String oRIGIN_PLACE_CODE) {
		ORIGIN_PLACE_CODE = oRIGIN_PLACE_CODE;
	}
	public String getORIGIN_COUNTRY_CODE() {
		return ORIGIN_COUNTRY_CODE;
	}
	public void setORIGIN_COUNTRY_CODE(String oRIGIN_COUNTRY_CODE) {
		ORIGIN_COUNTRY_CODE = oRIGIN_COUNTRY_CODE;
	}
	
	public String getHS_CODE() {
        return HS_CODE;
    }
    public void setHS_CODE(String hS_CODE) {
        HS_CODE = hS_CODE;
    }
    public CIQGoodsBean(ResultSet rs){
		try{
			this.setCCY(rs.getString("cCY"));
			this.setGOODS_CNAME(rs.getString("gOODS_CNAME"));
			this.setGOODS_ENAME(rs.getString("gOODS_ENAME"));
			this.setGOODS_MODEL(rs.getString("gOODS_MODEL"));
			this.setGOODS_NO(rs.getString("gOODS_NO"));
			this.setGOODS_VALUES(rs.getString("gOODS_VALUES"));			
			this.setORIGIN_COUNTRY_CODE(rs.getString("oRIGIN_COUNTRY_CODE"));
			this.setORIGIN_PLACE_CODE(rs.getString("oRIGIN_PLACE_CODE"));
			this.setPACK_NUMBER(rs.getString("pACK_NUMBER"));
			this.setPACK_TYPE_CODE(rs.getString("pACK_TYPE_CODE"));
			this.setQTY(rs.getString("qTY"));
			this.setQTY_UNIT_CODE(rs.getString("qTY_UNIT_CODE"));
			this.setVALUES_USD(rs.getString("vALUES_USD"));
			this.setVALUES_RMB(rs.getString("vALUES_RMB"));
			this.setWEIGHT(rs.getString("wEIGHT"));
			this.setWEIGHT_UNIT_CODE(rs.getString("wEIGHT_UNIT_CODE"));
			this.setHS_CODE(rs.getString("hS_CODE"));
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
