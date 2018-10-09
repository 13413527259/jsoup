package jsoup;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BWMtest {
	private static String user = "bwmcjh001";
	private static String pass = "bwmcjh2018";
	private static String openId = "";
	private static String token = "";

	private static String url_host = "http://kapi.yika66.com:20153";
	private static String url_login = "http://kapi.yika66.com:20153/User/login?uName=USER&pWord=PASS&Developer=OPENID";
	private static String url_Developer = "http://capi.yika66.com/Code.aspx?uName=USER";
	private static String url_items = "http://kapi.yika66.com:20153/User/getItems?token=TOKEN&tp=ut";
	private static String url_phone = "http://kapi.yika66.com:20153/User/getPhone?&NPhone=170|171|175&Count=COUNT&ItemId=ITEMID&token=TOKEN";
	private static String url_that_phone = "http://kapi.yika66.com:20153/User/getPhone?&Phone=PHONE";
	private static String url_releasePhone = "http://kapi.yika66.com:20153/User/releasePhone?phoneList=LIST&token=TOKEN";
	private static String url_message = "http://kapi.yika66.com:20153/User/getMessage?code=CODE&token=TOKEN";	
	private static String[] phoneList=new String[10];
	
	private Scanner sin=new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		BWMtest bwm = new BWMtest();
		System.out.println("获取openId：");
		bwm.getDeveloper();
		System.out.println("登录：");
		bwm.login();
		System.out.println("获取项目列表：");
//		Map<String, String> items = bwm.getItems();
//		for (String item : items.keySet()) {
//			System.out.println(items.get(item));
//		}
		System.out.println("请选择项目，输入项目id：");
		String itemId=bwm.sin.nextLine();
		bwm.getPhone("3", itemId);
		System.out.println("获取平台公共：");
		bwm.getMessage(null);
		System.out.println("获取短信内容：");
		int i=0;
		String message=null;
		while (true) {
			i+=1;
			System.out.println("第 " +i +"次获取验证码...");
			message = bwm.getMessage(null);
			if (!message.equals("Null")) {
				System.out.println(message);
				break;
			}
			Thread.sleep(2000);
		}
		System.out.println("释放号码：");
		bwm.releasePhone(itemId);
		bwm.releasePhone(itemId);
	}

	private boolean getDeveloper() throws Exception {
		url_Developer = url_Developer.replace("USER", user);
		openId = HttpUtil.request(url_Developer, "GET", null);
		if (openId != null) {
			return true;
		}
		return false;
	}

	private boolean login() throws Exception {
		url_login = url_login.replace("USER", user).replace("PASS", pass).replace("OPENID", openId);
		String respBody = HttpUtil.request(url_login, "GET", null);
		String[] result = respBody.split("&");
		if (result.length > 0) {
			token = result[0];
			return true;
		}
		return false;
	}

	private Map<String, String> getItems() throws Exception {
		Map<String, String> map = new HashMap<>();
		url_items = url_items.replace("TOKEN", token);
		String respBody = HttpUtil.request(url_items, "GET", null);
		String[] items = respBody.split("&");
		for (int i = 0; i < items.length-1; i++) {
			if (i==0 || i%3==0) {
				map.put(items[i], "项目ID："+items[i] +"----项目描述："+items[i+1] +"----单价："+items[i+2]);
			}
		}
		return map;
	}

	private String[] getPhone(String count, String itemId) throws Exception {
		url_phone = url_phone.replace("COUNT", count).replace("ITEMID", itemId).replace("TOKEN", token);
		String respBody = HttpUtil.request(url_phone, "GET", null);
		phoneList = respBody.split(";");
		return null;
	}

	private String getPhone(String phone) throws Exception {
		url_that_phone = url_that_phone.replace("PHONE", phone);
		String respBody = HttpUtil.request(url_phone, "GET", null);
		return respBody;
	}
	
	private String releasePhone(String itemId) throws Exception {
		String phoneStr="";
		for (String item : phoneList) {
			phoneStr+=item+"-"+itemId+";";
		}
		url_releasePhone = url_releasePhone.replace("LIST", phoneStr).replace("TOKEN", token);
		String respBody = HttpUtil.request(url_releasePhone, "GET", null);
		return respBody;
	}
	
	private String getMessage(String phone) throws Exception {
		url_message = url_message.replace("CODE", "utf8").replace("TOKEN", token);
		if (phone!=null) {
			url_message = url_message+"&Phone="+phone;
		}
		String respBody = HttpUtil.request(url_message, "GET", null);
		return respBody;
	}

}
