package ��������;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @Description ʹ��PreparedStatementʵ������ڲ�ͬ���ͨ�õĲ�ѯ����
 * @version
 * @date ����11:32:55
 *
 */
public class PreparedStatementQueryTest {
	
	public <T> List<T> getForList(Class<T> clazz,String sql, Object... args){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();

			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			rs = ps.executeQuery();
			// ��ȡ�������Ԫ���� :ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			// ͨ��ResultSetMetaData��ȡ������е�����
			int columnCount = rsmd.getColumnCount();
			//�������϶���
			ArrayList<T> list = new ArrayList<T>();
			while (rs.next()) {
				T t = clazz.getDeclaredConstructor().newInstance();
				// ��������һ�������е�ÿһ����:��t����ָ�������Ը�ֵ
				for (int i = 0; i < columnCount; i++) {
					// ��ȡ��ֵ
					Object columValue = rs.getObject(i + 1);

					// ��ȡÿ���е�����
					// String columnName = rsmd.getColumnName(i + 1);
					String columnLabel = rsmd.getColumnLabel(i + 1);

					// ��t����ָ����columnName���ԣ���ֵΪcolumValue��ͨ������
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columValue);
				}
				list.add(t);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps, rs);

		}

		return null;
	}
}
