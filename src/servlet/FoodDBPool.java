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
				throw new Exception("不能获取Context!");
			Context ctx = (Context) initCtx.lookup("java:comp/env");
			// 获取连接池对象
			Object obj = (Object) ctx.lookup("jdbc/foodDB");
			// 类型转换
			ds = (javax.sql.DataSource) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
