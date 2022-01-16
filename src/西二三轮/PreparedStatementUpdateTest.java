package ��������;



import java.sql.Connection;
import java.sql.PreparedStatement;


/*
 * ʹ��PreparedStatement���滻Statement,ʵ�ֶ����ݱ����ɾ�Ĳ���
 * 
 * 
 * 
 * 
 */
public class PreparedStatementUpdateTest {
	
	
	
	//ͨ�õ���ɾ�Ĳ���
	public void update(String sql,Object ...args){//sql��ռλ���ĸ�����ɱ��βεĳ�����ͬ��
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			//1.��ȡ���ݿ������
			conn = JDBCUtils.getConnection();
			//2.Ԥ����sql��䣬����PreparedStatement��ʵ��
			ps = conn.prepareStatement(sql);
			//3.���ռλ��
			for(int i = 0;i < args.length;i++){
				ps.setObject(i + 1, args[i]);//С�Ĳ����������󣡣�!!!!!!!!
			}
			//4.ִ��
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//5.��Դ�Ĺر�
			JDBCUtils.closeResource(conn, ps);
			
		}
		
		
	}
	


}
