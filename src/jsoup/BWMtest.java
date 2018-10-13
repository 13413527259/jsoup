package jsoup;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BWMtest {
	private static String user = "bwmcjh001";
	private static String pass = "bwmcjh2018";
	private static String openId = "";
	private static String token = "";

	private static final String url_host = "http://kapi.yika66.com:20153";
	private static final String url_login = "http://kapi.yika66.com:20153/User/login?uName=USER&pWord=PASS&Developer=OPENID";
	private static final String url_Developer = "http://capi.yika66.com/Code.aspx?uName=USER";
	private static final String url_items = "http://kapi.yika66.com:20153/User/getItems?token=TOKEN&tp=ut";
	private static final String url_phone = "http://kapi.yika66.com:20153/User/getPhone?PhoneType=17&Count=COUNT&ItemId=ITEMID&token=TOKEN";
	private static final String url_that_phone = "http://kapi.yika66.com:20153/User/getPhone?&Phone=PHONE";
	private static final String url_releasePhone = "http://kapi.yika66.com:20153/User/releasePhone?phoneList=LIST&token=TOKEN";
	private static final String url_message = "http://kapi.yika66.com:20153/User/getMessage?code=CODE&token=TOKEN";	
	private static String[] phoneList=new String[10];
	private static String url=null;
	private Scanner sin=new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		BWMtest bwm = new BWMtest();
		System.out.println("获取openId：");
		bwm.getDeveloper();
		System.out.println("登录：");
		bwm.login();
		System.out.println("获取平台公告：");
		bwm.getMessage(null);
		System.out.println("获取项目列表：");
//		Map<String, String> items = bwm.getItems();
//		for (String item : items.keySet()) {
//			System.out.println(items.get(item));
//		}
		System.out.println("请选择项目，输入项目id：");
		String itemId=bwm.sin.nextLine();
		bwm.getPhone("3", itemId);
		System.out.println("获取短信内容：");
		int i=0;
		String message=null;
		while (true) {
			i+=1;
			if (i>14) {
				System.out.println("获取验证码失败。次数："+i);
			}
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

	public static BWMtest run() throws Exception {
		BWMtest bwm = new BWMtest();
		System.out.println("获取openId：");
		bwm.getDeveloper();
		System.out.println("登录：");
		bwm.login();
		System.out.println("获取平台公告：");
		bwm.getMessage(null);
		return bwm;
	}
	
	private boolean getDeveloper() throws Exception {
		url = url_Developer.replace("USER", user);
		openId = HttpUtil.request(url, "GET", null);
		if (openId != null) {
			return true;
		}
		return false;
	}

	private boolean login() throws Exception {
		url = url_login.replace("USER", user).replace("PASS", pass).replace("OPENID", openId);
		String respBody = HttpUtil.request(url, "GET", null);
		String[] result = respBody.split("&");
		if (result.length > 0) {
			token = result[0];
			return true;
		}
		return false;
	}

	private Map<String, String> getItems() throws Exception {
		Map<String, String> map = new HashMap<>();
		url = url_items.replace("TOKEN", token);
		String respBody = HttpUtil.request(url, "GET", null);
		String[] items = respBody.split("&");
		for (int i = 0; i < items.length-1; i++) {
			if (i==0 || i%3==0) {
				map.put(items[i], "项目ID："+items[i] +"----项目描述："+items[i+1] +"----单价："+items[i+2]);
			}
		}
		return map;
	}

	public String getOnePhone() throws Exception {
		url = url_phone.replace("COUNT", "1").replace("ITEMID", "56206").replace("TOKEN", token);
		String respBody = HttpUtil.request(url, "GET", null);
		respBody=respBody.replace(";", "");
		return respBody;
	}
	
	public String[] getPhone(String count, String itemId) throws Exception {
		url = url_phone.replace("COUNT", count).replace("ITEMID", itemId).replace("TOKEN", token);
		String respBody = HttpUtil.request(url, "GET", null);
		phoneList = respBody.split(";");
		return null;
	}

	private String getPhone(String phone) throws Exception {
		url = url_that_phone.replace("PHONE", phone);
		String respBody = HttpUtil.request(url, "GET", null);
		return respBody;
	}
	
	public String releasePhone(String itemId) throws Exception {
		String phoneStr="";
		for (String item : phoneList) {
			phoneStr+=item+"-"+itemId+";";
		}
		url = url_releasePhone.replace("LIST", phoneStr).replace("TOKEN", token);
		String respBody = HttpUtil.request(url, "GET", null);
		return respBody;
	}
	
	public String releasePhone(String phone,String itemId) throws Exception {
		String phoneStr="";
		phoneStr+=phone+"-"+56206+";";
		url = url_releasePhone.replace("LIST", phoneStr).replace("TOKEN", token);
		String respBody = HttpUtil.request(url, "GET", null);
		return respBody;
	}

	public String getMessage(String phone) throws Exception {
		url = url_message.replace("CODE", "utf8").replace("TOKEN", token);
		if (phone!=null) {
			url = url+"&Phone="+phone;
		}
		String respBody = HttpUtil.request(url, "GET", null);
		return respBody;
	}


}
