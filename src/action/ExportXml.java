package action;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class ExportXml {
	public static void export(String content, String fileName) {
		String filePath = "C://" + fileName + ".xml";		
		OutputStreamWriter out;
		try {
			out = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");//改变xml编码，没起作用
			out.write(content);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
