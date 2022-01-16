package ��������;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 * @Description �������ݿ�Ĺ�����
 * @author 
 * @version
 * @date ����9:10:02
 *
 */
public class JDBCUtils {
	
	/**
	 * 
	 * @Description ��ȡ���ݿ������
	 * @author 
	 * @date ����9:11:23
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		// 1.��ȡ�����ļ��е�4��������Ϣ
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

		Properties pros = new Properties();
		pros.load(is);

		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");

		// 2.��������
		Class.forName(driverClass);

		// 3.��ȡ����
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	/**
	 * 
	 * @Description �ر����Ӻ�Statement�Ĳ���
	 * @author 
	 * @date ����9:12:40
	 * @param conn
	 * @param ps
	 */
	public static void closeResource(Connection conn,Statement ps){
		try {
			if(ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Description �ر���Դ����
	 * @author 
	 * @date ����10:21:15
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	public static void closeResource(Connection conn,Statement ps,ResultSet rs){
		try {
			if(ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

