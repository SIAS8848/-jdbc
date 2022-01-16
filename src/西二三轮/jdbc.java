package 西二三轮;

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
 * 注：id是String类型,数据库中所有的属性都是varchar(100),对应java中的String
 * 
 * 福州市id："101230101"
 * 北京市id："101010100"
 * 上海市id："101020100"
 * 
 * 每次运行一次后，都要进入dbms清空一次数据，我用的是dbeaver
 */

public class jdbc {
	public static void main(String[] args) {

		// 福州市（其实是福建省）信息
		insertintocity(
				"https://geoapi.qweather.com/v2/city/lookup?key=d6a84939b6cc404798ad94025362aa6b&location=%E7%A6%8F%E5%B7%9E");
		// 北京市（全市）信息
		insertintocity(
				"https://geoapi.qweather.com/v2/city/lookup?key=d6a84939b6cc404798ad94025362aa6b&location=%E5%8C%97%E4%BA%AC");
		// 上海市（全市）信息
		insertintocity(
				"https://geoapi.qweather.com/v2/city/lookup?key=d6a84939b6cc404798ad94025362aa6b&location=%E4%B8%8A%E6%B5%B7");

		// 插入福州天气 根据id
		insertintoweather(
				"https://devapi.qweather.com/v7/weather/3d?key=d6a84939b6cc404798ad94025362aa6b&location=101230101",
				"101230101");

		// 插入北京天气 根据id
		insertintoweather(
				"https://devapi.qweather.com/v7/weather/3d?key=d6a84939b6cc404798ad94025362aa6b&location=101010100",
				"101010100");
		// 插入上海天气 根据id
		insertintoweather(
				"https://devapi.qweather.com/v7/weather/3d?key=d6a84939b6cc404798ad94025362aa6b&location=101020100",
				"101020100");

		// 控制台查询福州市天气数据
		PreparedStatementQueryTest query = new PreparedStatementQueryTest();
		String sql = "select * from weather where id=?";
		List<weather> list = query.getForList(weather.class,sql,"101230101");
		list.forEach(System.out::println);

		// 控制台增删改数据（通用方法）,但是其实作为用户只需要用查询即可，如果要测试可以打开测试
		//增删改的结果可以在数据库中看，这里不提供返回

		PreparedStatementUpdateTest update0 = new PreparedStatementUpdateTest();
//		String sql = "这里写增删改语句";例如:
		String sql1="update weather set tempMax='16' where id=?";
//		update0.update(sql, "这里写id");
		//修改福州市三日最高温度为16度
		update0.update(sql1, "101230101");

	}

	// 插入城市信息
	public static void insertintocity(String path) {
		int i;// 用于循环所用变量
		URL url;// 调用API的路径
		URLConnection conn;// 建立url连接
		InputStream in;// url连接的字节流
		BufferedReader br;// 用于字符流逐行读取
		String line, str, string;
		StringBuilder res;// 缓冲字符串提高效率
		JSONObject jsonObject, JO;// 处理JSON字符串中的JSON对象
		JSONArray jsonArray;// 处理json字符串中的json数组
		String id = "", lat = "", lon = "", name = "";// 对应数据中的不同键值
		PreparedStatementUpdateTest p = new PreparedStatementUpdateTest();

		// 写入数据表
		try {
			// 创建一个URL对象
			url = new URL(path);
			// 创建一个URLConnection连接对象
			conn = url.openConnection();
			// 创建一个输入流来接收网页
			in = conn.getInputStream();

			GZIPInputStream gzipInputStream = new GZIPInputStream(in);
			res = new StringBuilder();
			line = null;
			br = new BufferedReader((new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)));
			while ((line = br.readLine()) != null) {
				res.append(line);
			}
			str = res.toString();
//			System.out.println(str);//检测是否获取成功

			// JSON解析字符串
			jsonObject = JSONObject.parseObject(str);
			jsonArray = jsonObject.getJSONArray("location");
			// 缓冲字符串存储sql语句，便于多条数据同时插入
			string = "insert into city(id,lat,lon,name) values ";
			res = new StringBuilder();
			res.append(string);
			// 遍历JSON中的数组
			for (i = 0; i < jsonArray.size(); i++) {
				// 数组内数据分析
				JO = jsonArray.getJSONObject(i);
				id = JO.getString("id");
				lat = JO.getString("lat");
				lon = JO.getString("lon");
				name = JO.getString("name");
				// 将字符串加入缓冲字符串中
				res.append("('");
				res.append(id);
				res.append("','");
				res.append(lat);
				res.append("','");
				res.append(lon);
				res.append("','");
				res.append(name);
				// 最后一条数据时判断
				if (i < (jsonArray.size() - 1))
					res.append("'),");
				else
					res.append("')");
			}
			string = res.toString();

			// 多个数据同时插入
			p.update(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入天气情况
	public static void insertintoweather(String path, String id) {
		int i;// 用于循环所用变量
		URL url;// 调用API的路径
		URLConnection conn;// 建立url连接
		InputStream in;// url连接的字节流
		BufferedReader br;// 用于字符流逐行读取
		String line, str, string;
		StringBuilder res;// 缓冲字符串提高效率
		JSONObject jsonObject, JO;// 处理JSON字符串中的JSON对象
		JSONArray jsonArray;// 处理json字符串中的json数组
		String fxDate = "", textDay = "", tempMax = "", tempMin = "";// 对应数据中的不同键值
		PreparedStatementUpdateTest p = new PreparedStatementUpdateTest();

		try {

			url = new URL(path);
			// 创建一个URLConnection连接对象
			conn = url.openConnection();
			// 创建一个输入流来接收网页
			in = conn.getInputStream();

			GZIPInputStream gzipInputStream = new GZIPInputStream(in);
			res = new StringBuilder();
			line = null;
			br = new BufferedReader((new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)));
			while ((line = br.readLine()) != null) {
				res.append(line);
			}
			str = res.toString();

			// JSON解析字符串
			jsonObject = JSONObject.parseObject(str);
			jsonArray = jsonObject.getJSONArray("daily");
			// 缓冲字符串存储sql语句，便于多条数据同时插入
			string = "insert into weather(id,fxDate,textDay,tempMax,tempMin) values ";
			res = new StringBuilder();
			res.append(string);
			// 遍历JSON中的数组
			for (i = 0; i < jsonArray.size(); i++) {
				// 数组内数据分析
				JO = jsonArray.getJSONObject(i);
				fxDate = JO.getString("fxDate");
				textDay = JO.getString("textDay");
				tempMax = JO.getString("tempMax");
				tempMin = JO.getString("tempMin");
				// 将字符串加入缓冲字符串中
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
				// 最后一条数据时判断
				if (i < (jsonArray.size() - 1))
					res.append("'),");
				else
					res.append("')");
			}
			string = res.toString();

			// 多个数据同时插入
			p.update(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
