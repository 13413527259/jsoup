package jsoup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpUtil {
	
	public static String request(String urlStr, String method, String param) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setConnectTimeout(5000);
//		conn.setRequestProperty("Accept", "application/json");
//		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		conn.setRequestProperty("Referer",
//				"http://ele.hongbao.show/webService/shopGather/elm-new/index.html?channelId=32010380");
		BufferedWriter writer = null;
		if (method.equals("POST")) {
			conn.setRequestMethod("POST");
			if (param != null) {
				conn.setDoOutput(true);
				writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				writer.write(param);
				writer.flush();
				writer.close();
				writer = null;
			}
		}

		Map<String, List<String>> headers = conn.getHeaderFields();

		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((tempLine = reader.readLine()) != null) {
			resultBuffer.append(tempLine);
		}
		reader.close();
		reader = null;

		String result = resultBuffer.toString();
		System.err.println("resp >>> " + result);
		return result;
	}
	public static String logRquest(String urlStr, String method, String param) throws Exception {
		System.out.println("url >>> " + urlStr);
		System.out.println("param >>> " + param);
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setConnectTimeout(5000);
//		conn.setRequestProperty("Accept", "application/json");
//		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		conn.setRequestProperty("Referer",
//				"http://ele.hongbao.show/webService/shopGather/elm-new/index.html?channelId=32010380");
		BufferedWriter writer = null;
		if (method.equals("POST")) {
			conn.setRequestMethod("POST");
			if (param != null) {
				conn.setDoOutput(true);
				writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				writer.write(param);
				writer.flush();
				writer.close();
				writer = null;
			}
		}

		Map<String, List<String>> headers = conn.getHeaderFields();
		System.out.println(headers); // 输出头字段

		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((tempLine = reader.readLine()) != null) {
			resultBuffer.append(tempLine);
		}
		reader.close();
		reader = null;

		String result = resultBuffer.toString();
		System.err.println("resp >>> " + result);
		return result;
	}
	
	public static String logRquest(String urlStr, String method, String param,String cookie) throws Exception {
		System.out.println("url >>> " + urlStr);
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
		conn.setRequestProperty("Referer","https://h5.ele.me/hongbao/?from=groupmessage");
		conn.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; Android 5.1; OPPO R9tm Build/LMY47I; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043128 Safari/537.36 V1_AND_SQ_7.0.0_676_YYB_D PA QQ/7.0.0.3135 NetType/4G WebP/0.3.0 Pixel/1080");
		conn.setRequestProperty("cookie", cookie);
		conn.setDoOutput(true);// 使用 URL 连接进行输出 
		conn.setDoInput(true);// 使用 URL 连接进行输入 
		conn.setUseCaches(false);// 忽略缓存 
		conn.setRequestMethod("POST");// 设置URL请求方法

		// 建立输出流，并写入数据 
		BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream())); 
		outputStream.write(param); 
		outputStream.close();

		// 获得响应状态 
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		int responseCode = conn.getResponseCode(); 
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功 
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
			reader.close();
			reader = null;
		}else {
			System.out.println("请求失败："+responseCode+ " ---- "+conn.getResponseMessage());
			return null;
		}
		String result = resultBuffer.toString();
		System.err.println("resp >>> " + result);
		return result;
	}

}
