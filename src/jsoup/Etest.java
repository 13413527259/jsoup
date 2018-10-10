package jsoup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Request;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sun.misc.BASE64Decoder;

public class Etest {
	public static Scanner sin = new Scanner(System.in);
	public static final String url = "https://movie.douban.com/subject/26920281/comments";
	public static int phone1 = 13111;
	public static int phone2 = 200001;
	public static String phone = "13836748246";
	public static final String url_captchas = "https://h5.ele.me/restapi/eus/v3/captchas";
	public static final String url_send_code = "https://h5.ele.me/restapi/eus/login/mobile_send_code";
	public static final String url_login = "https://h5.ele.me/restapi/eus/login/login_by_mobile";
	public static final String url_newuser = "http://ele.hongbao.show/webService/shopGatherController?m=elmNew";

	// https://h5.ele.me/restapi/eus/login/mobile_send_code
	// https://h5.ele.me/restapi/bgs/poi/search_poi_nearby_alipay?keyword=桂田大厦&offset=0&limit=5
	// https://accounts.douban.com/login
	// https://movie.douban.com/subject/30122633
	// https://movie.douban.com/subject/30122633/comments?status=P
	public static void main(String[] args) throws Exception {
		// request(url_newuser, "POST", "mobile=13413527257");
		BWMtest bwm = BWMtest.run();
		int i=0;
		while (true) {
			System.out.println("第 "+ (++i) +" 次获取号码：");
			phone=bwm.getOnePhone();
			if (isNewUser(phone)) {
				System.out.println(phone+" >>> 未注册");
				break;
			}
			System.out.println(phone+" >>> 已注册");
			bwm.releasePhone(phone,null);
		}
		System.out.println("获取图片验证吗：");
		Map<String, String> captchas = captchas();
		System.out.println("发送短信验证码：");
		Map<String, String> sendCode = sendCode(captchas.get("captcha_hash"), captchas.get("captcha_value"));
		i=0;
		String msg=null;
		String code=null;
		int index=0;
		while (true) {
			if (i>14) {
				System.out.println("短信验证码获取失败...重新取号...");
				i=0;
				while (true) {
					System.out.println("第 "+ (++i) +" 次获取号码：");
					phone=bwm.getOnePhone();
					if (isNewUser(phone)) {
						System.out.println(phone+" >>> 未注册");
						break;
					}
					System.out.println(phone+" >>> 已注册");
					bwm.releasePhone(phone,null);
				}
				System.out.println("获取图片验证吗：");
				captchas = captchas();
				System.out.println("发送短信验证码：");
				sendCode = sendCode(captchas.get("captcha_hash"), captchas.get("captcha_value"));
			}
			System.out.println(phone+" 第 "+ (++i) +" 次获取短信：");
			msg=bwm.getMessage(phone);
			if (!msg.equals("Null")) {
				System.out.println(msg);
				index=msg.indexOf("您的验证码是");
				code=msg.substring(index+6, index+6+6);
				sendCode.put("code", code);
				bwm.releasePhone(phone,null);
				break;
			}
			Thread.sleep(2000);
		}
		System.out.println("登录：");
		Map<String, String> cookie = login(sendCode.get("code"), sendCode.get("token"));
		// getDocument(initUrl,cookie);

		// System.out.println(URLDecoder.decode("http://https%3a%2f%2fh5.ele.me%2fnewretail%2fp%2fchannel%2f%3fchannel%3dsupermarket&geohash%3dws0e6s40trum/",
		// "utf-8"));
		System.out.println(cookie);
		sin.close();
	}

	public static String request(String urlStr, String method, String param) throws Exception {
		System.out.println("url >>> " + urlStr);
		System.out.println("param >>> " + param);
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Referer",
				"http://ele.hongbao.show/webService/shopGather/elm-new/index.html?channelId=32010380");
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

	public static boolean isNewUser(String mobile) throws IOException, ParseException {
		Map<String, String> cookie = new HashMap<>();
		// cookie.put("userOnlyId", "AJ451ff7d5-8830-4e1f-ad3f-663e3b0fbde2");
		// cookie.put("__cfduid", "d8dc87cdf5a9c2ea206641987d9accba61539062461");
		// cookie.put("acw_tc",
		// "77a7979515390624616554123e80d20d4a65254532e021ac487613aa8a");
		Map<String, String> data = new HashMap<>();
		data.put("mobile", mobile);
		// http://api.hongbao.show/webService/
		data.put("r_url", "");
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/x-www-form-urlencoded");
		header.put("Referer", "http://ele.hongbao.show/webService/shopGather/elm-new/index.html?channelId=32010380");

		Connection connect = Jsoup.connect(url_newuser);
		connect.ignoreContentType(true);
		connect.headers(header);
		connect.cookies(cookie);
		connect.method(Method.POST);
		connect.data(data);
		Document document = connect.post();
		Request request = connect.request();
		List<Object> reqData = (ArrayList) request.data();
		for (int i = 0; i < reqData.size(); i++) {
			System.out.println(reqData.get(i));

		}
		String body = document.body().text();
		System.out.println(body);
		JSONParser parser = new JSONParser();
		// JSONValue.parse("");
		JSONObject jsonObject = (JSONObject) parser.parse(body);
		System.out.println(jsonObject.get("msg"));
		Object resultcode = jsonObject.get("resultcode");
		if (resultcode.toString().equals("200")) {
			JSONObject jsonDetail = (JSONObject) parser.parse(jsonObject.get("result").toString());
			if (jsonDetail.get("error") == null) {
				JSONObject jsonData = (JSONObject) parser.parse(jsonDetail.get("data").toString());
				Object amount = jsonData.get("amount");
				if (amount.toString().equals("15")) {
					return true;
				}else {
					System.out.println("已注册，红包金额："+amount);
				}
			}
		}
		return false;
	}

	public static Map<String, String> captchas() throws IOException, ParseException {
		Map<String, String> result = new HashMap<>();
		Map<String, String> data = new HashMap<>();
		data.put("captcha_str", phone);

		Connection connect = Jsoup.connect(url_captchas);
		connect.ignoreContentType(true);
		connect.method(Method.POST);
		connect.data(data);
		Document document = connect.post();
		Request request = connect.request();
		List<Object> reqData = (ArrayList) request.data();
		for (int i = 0; i < reqData.size(); i++) {
			System.out.println(reqData.get(i));
		}
		String captcha = document.body().text();
		System.out.println(captcha);
		JSONParser parser = new JSONParser();
		// JSONValue.parse("");
		JSONObject jsonObject = (JSONObject) parser.parse(captcha);
		String captcha_hash = (String) jsonObject.get("captcha_hash");
		result.put("captcha_hash", captcha_hash);
		String captchaStr = (String) jsonObject.get("captcha_image");
		if (captchaStr != null) {
			captchaStr = captchaStr.substring("data:image/jpeg;base64,".length(), captchaStr.length());
			System.out.println(captchaStr);
		}
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(captchaStr);
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
		String file = "e://" + new Date().getTime() + ".jpg";
		System.out.println(file);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(b);
		fileOutputStream.flush();
		fileOutputStream.close();
		System.out.println("请输入图形验证码");

		String nextLine = null;
		nextLine = sin.nextLine();
		result.put("captcha_value", nextLine);
		return result;
	}

	public static Map<String, String> sendCode(String captcha_hash, String captcha_value)
			throws IOException, ParseException {
		Map<String, String> result = new HashMap<>();
		Map<String, String> data = new HashMap<>();
		data.put("mobile", phone);
		data.put("captcha_hash", captcha_hash);
		data.put("captcha_value", captcha_value);

		Connection connect = Jsoup.connect(url_send_code);
		connect.ignoreContentType(true);
		connect.method(Method.POST);
		connect.data(data);
		Document document = null;
		try {
			document = connect.post();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("验证码不正确，请重新输入");
			String nextLine = null;
			nextLine = sin.nextLine();
			return sendCode(captcha_hash, nextLine);
		}
		Request request = connect.request();
		List<Object> reqData = (ArrayList) request.data();
		for (int i = 0; i < reqData.size(); i++) {
			System.out.println(reqData.get(i));
		}
		String token = document.body().text();
		System.out.println(token);
		JSONParser parser = new JSONParser();
		// JSONValue.parse("");
		JSONObject jsonObject = (JSONObject) parser.parse(token);
		String tokenStr = (String) jsonObject.get("validate_token");
		result.put("token", tokenStr);
		System.out.println("请输入短信验证码");
		String nextLine = null;
//		nextLine = sin.nextLine();
		
		result.put("code", nextLine);
		return result;
	}

	public static Map<String, String> login(String code, String token) throws IOException, ParseException {
		Map<String, String> cookie = new HashMap<>();
		cookie.put("perf_ssid", "ydy7x8ftlgz9le0fk6ifasbxaeyv7j69_2018-09-25");
		cookie.put("ubt_ssid", "jg77sedwsj91c3lqbc0rwiorw5yrbyg0_2018-09-25");
		cookie.put("_utrace", "2dc40edd80a3c0c0a9a845025f75edb8_2018-09-25");
		cookie.put("track_id",
				"1537864108|773ee26f253b7c15a2d556b4115254391d8d04e0b9bccc8913|6cad10e81bb1d56cf7237c82ef5aec47");

		Map<String, String> data = new HashMap<>();
		data.put("mobile", phone);
		data.put("validate_code", code);
		data.put("validate_token", token);

		Connection connect = Jsoup.connect(url_login);
		connect.ignoreContentType(true);
		connect.cookies(cookie);
		connect.method(Method.POST);
		connect.data(data);
		Document document = null;
		try {
			document = connect.post();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("验证码不正确，请重新输入");
			String nextLine = null;
			nextLine = sin.nextLine();
			return login(nextLine, token);
		}
		Request request = connect.request();
		List<Object> reqData = (ArrayList) request.data();
		for (int i = 0; i < reqData.size(); i++) {
			System.out.println(reqData.get(i));

		}
		Response response = connect.response();
		Map<String, String> cookies = response.cookies();
		for (String item : cookies.keySet()) {
			System.out.println(item + ">>>" + cookies.get(item));
		}
		return cookies;
	}

	private static void getDocument(String urlStr, Map<String, String> cookie) throws IOException {
		Connection connect = Jsoup.connect(urlStr);
		connect.cookies(cookie);
		Document document = connect.ignoreContentType(true).get();
		Elements shorts = document.getElementsByClass("short");
		for (Element element : shorts) {
			System.out.println(element.html());
		}
		Elements next = document.getElementsByClass("next");
		for (Element element : next) {
			String href = element.attr("href");
			String nextUrl = url + href;
			System.out.println("nextUrl: " + nextUrl);
			if (href != "" && !href.equals("") && href != null) {
				getDocument(nextUrl, cookie);
			}
		}
	}
}
