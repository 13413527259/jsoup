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
		System.out.println(headers); // Êä³öÍ·×Ö¶Î

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

}
