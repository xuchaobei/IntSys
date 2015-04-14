package servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FoodDBPool {
	public static javax.sql.DataSource ds;
	static {
		try {
			Context initCtx = new InitialContext();
			if (initCtx == null)
				throw new Exception("���ܻ�ȡContext!");
			Context ctx = (Context) initCtx.lookup("java:comp/env");
			// ��ȡ���ӳض���
			Object obj = (Object) ctx.lookup("jdbc/foodDB");
			// ����ת��
			ds = (javax.sql.DataSource) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
