package jsoup;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LZtest {
	//http://v2-no-secure-api.jsdama.com/upload
	private static final String url_upload="https://v2-api.jsdama.com/upload";
	private static final String url_report_error="https://v2-api.jsdama.com/report-error";
	private static final String url_check_points="https://v2-api.jsdama.com/check-points";
	private static final int softwareId = 11520;
	private static final String softwareSecret = "OQCFvZ7hI3IQQW0vL3upiZ1c1ia9sUGgzSVk51Ed";
	private static final String username = "lzcjh001";
	private static final String password = "lzCjh001_2018";
	private static final int captchaType = 1001;
	public static void main(String[] args) {
		
	}
	
	public static String upload(String base64) throws Exception {
		Map<String, Object> map=new HashMap<>();
		map.put("softwareId",softwareId);
		map.put("softwareSecret",softwareSecret);
		map.put("username",username);
		map.put("password",password);
		map.put("captchaData",base64);
		map.put("captchaType",captchaType);
		String param = JSONObject.toJSONString(map);
		String body = HttpUtil.logRquest(url_upload, "POST", param);
		return body;
	}
	
	public static String reportError(String captchaId) throws Exception {
		Map<String, Object> map=new HashMap<>();
		map.put("softwareId",softwareId);
		map.put("softwareSecret",softwareSecret);
		map.put("username",username);
		map.put("password",password);
		map.put("captchaId",captchaId);
		map.put("captchaType",captchaType);
		String param = JSONObject.toJSONString(map);
		String body = HttpUtil.logRquest(url_report_error, "POST", param);
		return body;
	}
	
	public static String checkPoints() throws Exception {
		Map<String, Object> map=new HashMap<>();
		map.put("softwareId",softwareId);
		map.put("softwareSecret",softwareSecret);
		map.put("username",username);
		map.put("password",password);
		String param = JSONObject.toJSONString(map);
		String body = HttpUtil.logRquest(url_check_points, "POST", param);
		return body;
	}
}
