package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CIQFeeBean {
    private String FEE_ITEM_NO;
    private String CALCU_FEE_MODE; 
    private String FEE;
    private String REPORT_FLAG;
	public String getFEE_ITEM_NO() {
		return FEE_ITEM_NO;
	}
	public void setFEE_ITEM_NO(String fEE_ITEM_NO) {
		FEE_ITEM_NO = fEE_ITEM_NO;
	}
	public String getCALCU_FEE_MODE() {
		return CALCU_FEE_MODE;
	}
	public void setCALCU_FEE_MODE(String cALCU_FEE_MODE) {
		CALCU_FEE_MODE = cALCU_FEE_MODE;
	}
	public String getFEE() {
		return FEE;
	}
	public void setFEE(String fEE) {
		FEE = fEE;
	}
	public String getREPORT_FLAG() {
		return REPORT_FLAG;
	}
	public void setREPORT_FLAG(String rEPORT_FLAG) {
		REPORT_FLAG = rEPORT_FLAG;
	}
    
    public CIQFeeBean(ResultSet rs){
    	try{
    		this.setCALCU_FEE_MODE(rs.getString("cALCU_FEE_MODE"));
    		this.setFEE(rs.getString("fEE"));
    		this.setFEE_ITEM_NO(rs.getString("fEE_ITEM_NO"));
    		this.setREPORT_FLAG(rs.getString("rEPORT_FLAG"));
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    }
}
