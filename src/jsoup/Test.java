package jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Request;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	private static final String url="https://movie.douban.com/subject/26920281/comments";
	private static final String loginUrl="https://accounts.douban.com/login";
	//https://h5.ele.me/login/
	//https://h5.ele.me/restapi/bgs/poi/search_poi_nearby_alipay?keyword=桂田大厦&offset=0&limit=5
	//https://accounts.douban.com/login
	//https://movie.douban.com/subject/30122633
	//https://movie.douban.com/subject/30122633/comments?status=P
	public static void main(String[] args) throws Exception {
		Map<String, String> cookie = login();
		String initUrl=url;
		getDocument(initUrl,cookie);
//		int index=0;
//		String msg="MSG&388&18443587142&【饿了么】您的验证码是619451，在5分钟内有效。如非本人操作请忽略本短信。[End]";
//		index=msg.indexOf("您的验证码是");
//		msg=msg.substring(index+6, index+6+6);
//		System.out.println(msg);
		
	}
	
	private static Map<String, String> login() throws IOException {
		Map<String, String> param=new HashMap<>();
		param.put("form_email", "13413527259");
		param.put("form_password", "dbcjh2018");
		param.put("login", "登陆");
		param.put("redir", "https://movie.douban.com");
		param.put("source", "None");
//		param.put("captcha-id", "Tc9Zgwwl7bKBOtYOHYlzqnxk:en");
//		param.put("captcha-solution", "rhythm");
		Connection connect = Jsoup.connect(loginUrl);
		connect.ignoreContentType(true);
		connect.data(param);
		connect.method(Method.POST);
		Document document = connect.post();
		Request request = connect.request();
		List<Object> reqData = (ArrayList) request.data();
		for (int i = 0; i < reqData.size(); i++) {
			System.out.println(reqData.get(i));
			
		}
		Response response = connect.response();
		Map<String, String> cookies = response.cookies();
		for (String item : cookies.keySet()) {
			System.out.println(item +">>>"+ cookies.get(item));
		}
		System.out.println(document.data());
		return cookies;
	}
	
	private static void getDocument(String urlStr,Map<String, String> cookie) throws IOException {
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
			String nextUrl=url+href;
			System.out.println("nextUrl: "+nextUrl);
			if (href!=""&&!href.equals("")&&href!=null) {
				getDocument(nextUrl,cookie);
			}
		}
	}
}
