package jsoup;

import java.net.URLDecoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RedpacketTest {
	
	private static final String url_openRedpacket="https://h5.ele.me/restapi/marketing/promotion/weixin/OPENID";
	private static final String url_getLuckyNumber="https://h5.ele.me/restapi/marketing/themes/3209/group_sns/REDPACKETID";
	private static String url=null;
	
	public static void main(String[] args) throws Exception {
		System.out.println(openRedpacket("{\"method\":\"phone\",\"group_sn\":\"2a0efbfd41302815\",\"sign\":\"64cd64f8e979e8135c7134b78e7a1bcc\",\"phone\":\"15277287204\",\"device_id\":\"\",\"hardware_id\":\"\",\"platform\":0,\"track_id\":\"undefined\",\"weixin_avatar\":\"http://thirdqq.qlogo.cn/qqapp/101204453/D33EABC02440239D14D4217F6745D01D/40\",\"weixin_username\":\"ÂþË¼²è\",\"unionid\":\"fuck\",\"latitude\":\"\",\"longitude\":\"\"}","perf_ssid=uqgblvkle4jtda3mgtx64rf6kbp7ujzu_2018-10-12; ubt_ssid=nz0x65uufcnt2u486d713skzrlgodkv0_2018-10-12; _utrace=32441460bf4815c36e53f0dda5805439_2018-10-12; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%2264cd64f8e979e8135c7134b78e7a1bcc%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E6%BC%AB%E6%80%9D%E8%8C%B6%22%2C%22openid%22%3A%22D33EABC02440239D14D4217F6745D01D%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221990%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E6%BC%AB%E6%80%9D%E8%8C%B6%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F40%22%7D; track_id=1539355717|da82fc863ee34a8025473cc02cbc412f3ce656a280bfdb3a5a|aaf58d90ae9b0e59347f08d89afca2da; USERID=2234081394; SID=oT3ojJqOXwvqP6yKaZUETdf6zpXcRb2ykeKQ"));
//		String aaa=getRedPacketId("https://h5.ele.me/hongbao/?from=groupmessage#hardware_id=&is_lucky_group=True&lucky_number=0&track_id=&platform=0&sn=2a0efbfd41302815&theme_id=3209&device_id=&refer_user_id=26297205");
//		String bbb=getOpenId("perf_ssid=uqgblvkle4jtda3mgtx64rf6kbp7ujzu_2018-10-12; ubt_ssid=nz0x65uufcnt2u486d713skzrlgodkv0_2018-10-12; _utrace=32441460bf4815c36e53f0dda5805439_2018-10-12; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%2264cd64f8e979e8135c7134b78e7a1bcc%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E6%BC%AB%E6%80%9D%E8%8C%B6%22%2C%22openid%22%3A%22D33EABC02440239D14D4217F6745D01D%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221990%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E6%BC%AB%E6%80%9D%E8%8C%B6%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F40%22%7D; track_id=1539355717|da82fc863ee34a8025473cc02cbc412f3ce656a280bfdb3a5a|aaf58d90ae9b0e59347f08d89afca2da; USERID=2234081394; SID=oT3ojJqOXwvqP6yKaZUETdf6zpXcRb2ykeKQ");
		System.out.println(getLuckyNumber("https://h5.ele.me/hongbao/?from=groupmessage#hardware_id=&is_lucky_group=True&lucky_number=0&track_id=&platform=0&sn=2a0efbfd41302815&theme_id=3209&device_id=&refer_user_id=26297205"));
	}
	public static String getRedPacketId(String url) {
    	Pattern p = Pattern.compile("&sn=(.*?)&");
    	Matcher m = p.matcher(url);
    	String result=null;
    	if (m.find()) {
    		result = m.group(1);
    		System.out.println(result);
    		return result;
		}
		return null;
	}
	public static int getLuckyNumber(String url) throws Exception {
		String redPacketId = getRedPacketId(url);
		url=url_getLuckyNumber.replace("REDPACKETID", redPacketId);
		String body = HttpUtil.request(url, "GET", null);
		if (body.contains("lucky_number")) {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(body);
			Object lucky_number = jsonObject.get("lucky_number");
			return Integer.valueOf(lucky_number.toString());
		}
		return 0;
	}
	public static String getOpenId(String cookie) throws Exception {
		cookie = URLDecoder.decode(cookie, "utf-8");
    	Pattern p = Pattern.compile("\"openid\":\"(.*?)\"");
    	Matcher m = p.matcher(cookie);
    	String result=null;
    	if (m.find()) {
    		result = m.group(1);
    		System.out.println(result);
    		return result;
		}
		return null;
	}
	public static int openRedpacket(String param,String cookie) throws Exception {
		String openId=getOpenId(cookie);
		url=url_openRedpacket.replace("OPENID", openId);
		String body = HttpUtil.logRquest(url, "POST", param, cookie);
		if (body!=null) {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(body);
			JSONArray promotion_records = (JSONArray) jsonObject.get("promotion_records");
			int size = promotion_records.size();
			return size;
		}
		return 0;
	}
}
