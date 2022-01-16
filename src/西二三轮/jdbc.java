package ��������;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/*
 * ע��id��String����,���ݿ������е����Զ���varchar(100),��Ӧjava�е�String
 * 
 * ������id��"101230101"
 * ������id��"101010100"
 * �Ϻ���id��"101020100"
 * 
 * ÿ������һ�κ󣬶�Ҫ����dbms���һ�����ݣ����õ���dbeaver
 */

public class jdbc {
	public static void main(String[] args) {

		// �����У���ʵ�Ǹ���ʡ����Ϣ
		insertintocity(
				"https://geoapi.qweather.com/v2/city/lookup?key=d6a84939b6cc404798ad94025362aa6b&location=%E7%A6%8F%E5%B7%9E");
		// �����У�ȫ�У���Ϣ
		insertintocity(
				"https://geoapi.qweather.com/v2/city/lookup?key=d6a84939b6cc404798ad94025362aa6b&location=%E5%8C%97%E4%BA%AC");
		// �Ϻ��У�ȫ�У���Ϣ
		insertintocity(
				"https://geoapi.qweather.com/v2/city/lookup?key=d6a84939b6cc404798ad94025362aa6b&location=%E4%B8%8A%E6%B5%B7");

		// ���븣������ ����id
		insertintoweather(
				"https://devapi.qweather.com/v7/weather/3d?key=d6a84939b6cc404798ad94025362aa6b&location=101230101",
				"101230101");

		// ���뱱������ ����id
		insertintoweather(
				"https://devapi.qweather.com/v7/weather/3d?key=d6a84939b6cc404798ad94025362aa6b&location=101010100",
				"101010100");
		// �����Ϻ����� ����id
		insertintoweather(
				"https://devapi.qweather.com/v7/weather/3d?key=d6a84939b6cc404798ad94025362aa6b&location=101020100",
				"101020100");

		// ����̨��ѯ��������������
		PreparedStatementQueryTest query = new PreparedStatementQueryTest();
		String sql = "select * from weather where id=?";
		List<weather> list = query.getForList(weather.class,sql,"101230101");
		list.forEach(System.out::println);

		// ����̨��ɾ�����ݣ�ͨ�÷�����,������ʵ��Ϊ�û�ֻ��Ҫ�ò�ѯ���ɣ����Ҫ���Կ��Դ򿪲���
		//��ɾ�ĵĽ�����������ݿ��п������ﲻ�ṩ����

		PreparedStatementUpdateTest update0 = new PreparedStatementUpdateTest();
//		String sql = "����д��ɾ�����";����:
		String sql1="update weather set tempMax='16' where id=?";
//		update0.update(sql, "����дid");
		//�޸ĸ�������������¶�Ϊ16��
		update0.update(sql1, "101230101");

	}

	// ���������Ϣ
	public static void insertintocity(String path) {
		int i;// ����ѭ�����ñ���
		URL url;// ����API��·��
		URLConnection conn;// ����url����
		InputStream in;// url���ӵ��ֽ���
		BufferedReader br;// �����ַ������ж�ȡ
		String line, str, string;
		StringBuilder res;// �����ַ������Ч��
		JSONObject jsonObject, JO;// ����JSON�ַ����е�JSON����
		JSONArray jsonArray;// ����json�ַ����е�json����
		String id = "", lat = "", lon = "", name = "";// ��Ӧ�����еĲ�ͬ��ֵ
		PreparedStatementUpdateTest p = new PreparedStatementUpdateTest();

		// д�����ݱ�
		try {
			// ����һ��URL����
			url = new URL(path);
			// ����һ��URLConnection���Ӷ���
			conn = url.openConnection();
			// ����һ����������������ҳ
			in = conn.getInputStream();

			GZIPInputStream gzipInputStream = new GZIPInputStream(in);
			res = new StringBuilder();
			line = null;
			br = new BufferedReader((new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)));
			while ((line = br.readLine()) != null) {
				res.append(line);
			}
			str = res.toString();
//			System.out.println(str);//����Ƿ��ȡ�ɹ�

			// JSON�����ַ���
			jsonObject = JSONObject.parseObject(str);
			jsonArray = jsonObject.getJSONArray("location");
			// �����ַ����洢sql��䣬���ڶ�������ͬʱ����
			string = "insert into city(id,lat,lon,name) values ";
			res = new StringBuilder();
			res.append(string);
			// ����JSON�е�����
			for (i = 0; i < jsonArray.size(); i++) {
				// ���������ݷ���
				JO = jsonArray.getJSONObject(i);
				id = JO.getString("id");
				lat = JO.getString("lat");
				lon = JO.getString("lon");
				name = JO.getString("name");
				// ���ַ������뻺���ַ�����
				res.append("('");
				res.append(id);
				res.append("','");
				res.append(lat);
				res.append("','");
				res.append(lon);
				res.append("','");
				res.append(name);
				// ���һ������ʱ�ж�
				if (i < (jsonArray.size() - 1))
					res.append("'),");
				else
					res.append("')");
			}
			string = res.toString();

			// �������ͬʱ����
			p.update(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �����������
	public static void insertintoweather(String path, String id) {
		int i;// ����ѭ�����ñ���
		URL url;// ����API��·��
		URLConnection conn;// ����url����
		InputStream in;// url���ӵ��ֽ���
		BufferedReader br;// �����ַ������ж�ȡ
		String line, str, string;
		StringBuilder res;// �����ַ������Ч��
		JSONObject jsonObject, JO;// ����JSON�ַ����е�JSON����
		JSONArray jsonArray;// ����json�ַ����е�json����
		String fxDate = "", textDay = "", tempMax = "", tempMin = "";// ��Ӧ�����еĲ�ͬ��ֵ
		PreparedStatementUpdateTest p = new PreparedStatementUpdateTest();

		try {

			url = new URL(path);
			// ����һ��URLConnection���Ӷ���
			conn = url.openConnection();
			// ����һ����������������ҳ
			in = conn.getInputStream();

			GZIPInputStream gzipInputStream = new GZIPInputStream(in);
			res = new StringBuilder();
			line = null;
			br = new BufferedReader((new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)));
			while ((line = br.readLine()) != null) {
				res.append(line);
			}
			str = res.toString();

			// JSON�����ַ���
			jsonObject = JSONObject.parseObject(str);
			jsonArray = jsonObject.getJSONArray("daily");
			// �����ַ����洢sql��䣬���ڶ�������ͬʱ����
			string = "insert into weather(id,fxDate,textDay,tempMax,tempMin) values ";
			res = new StringBuilder();
			res.append(string);
			// ����JSON�е�����
			for (i = 0; i < jsonArray.size(); i++) {
				// ���������ݷ���
				JO = jsonArray.getJSONObject(i);
				fxDate = JO.getString("fxDate");
				textDay = JO.getString("textDay");
				tempMax = JO.getString("tempMax");
				tempMin = JO.getString("tempMin");
				// ���ַ������뻺���ַ�����
				res.append("('");
				res.append(id);
				res.append("','");
				res.append(fxDate);
				res.append("','");
				res.append(textDay);
				res.append("','");
				res.append(tempMax);
				res.append("','");
				res.append(tempMin);
				// ���һ������ʱ�ж�
				if (i < (jsonArray.size() - 1))
					res.append("'),");
				else
					res.append("')");
			}
			string = res.toString();

			// �������ͬʱ����
			p.update(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
