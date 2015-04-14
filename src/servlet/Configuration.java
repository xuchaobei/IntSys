package servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private Properties properties;
    private InputStream inputStream;
    
    public Configuration(){
    	properties=new Properties();
    	try {
    		inputStream=Configuration.class.getResourceAsStream("/configuration.properties");			
			properties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public String getFrmUrl(){
    	return properties.getProperty("frmURL");
    }
    public String getFrmUser(){
    	return properties.getProperty("frmUSER");
    }
    public String getFrmPSW(){
    	return properties.getProperty("frmPSW");
    }
    public String getSjUrl(){
    	return properties.getProperty("sjURL");
    }
    public String getSjUser(){
    	return properties.getProperty("sjUSER");
    }
    public String getSjPSW(){
    	return properties.getProperty("sjPSW");
    }
    public String getLrpUrl(){
    	return properties.getProperty("lrpURL");
    }
    public String getSaveUrl(){
    	return properties.getProperty("saveURL");
    }
    public String getCIQUrl(){
    	return properties.getProperty("ciqURL");
    }
    public String getCIQUser(){
    	return properties.getProperty("ciqUSER");
    }
    public String getCIQPSW(){
    	return properties.getProperty("ciqPSW");
    }
    
}
