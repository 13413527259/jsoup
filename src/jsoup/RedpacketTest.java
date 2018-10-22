package jsoup;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RedpacketTest {

	private static final String url_openRedpacket = "https://h5.ele.me/restapi/marketing/promotion/weixin/OPENID";
	private static final String url_getLuckyNumber = "https://h5.ele.me/restapi/marketing/themes/3209/group_sns/REDPACKETID";
	private static String url = null;
	
	private static String[] cookies ;
	static {
		cookies =new String[10];
		cookies[0]="account=18276741141;perf_ssid=ffvbvvrupqnbvf0ss38xgzgm7h3cxabd_2018-10-14; ubt_ssid=k3ohcl1zrcwwsqfza0y2dwxl2bqc9umg_2018-10-14; _utrace=1f492f4188a5e2cb39585773848a2538_2018-10-14; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%22f51a6d9405da193b2cfd266e584d994e%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E7%AD%89%E9%A3%8E%E4%B9%9F%E7%AD%89%E4%BD%A0%22%2C%22openid%22%3A%22D59FBED671E5E427C13F1A39626F65F2%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E7%AD%89%E9%A3%8E%E4%B9%9F%E7%AD%89%E4%BD%A0%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F40%22%7D; track_id=1539527418|79e5952e0dc04437f770f4246e209b7e9e7f53f0e3d5de7afb|befff8f0ad24c7e8117ed80e389083b4; USERID=2225504842; SID=yrZdEmAiFAfVcunVN0ngBJwWJeJGpz8WKFjg";
		cookies[1]="account=15277287204;perf_ssid=uqgblvkle4jtda3mgtx64rf6kbp7ujzu_2018-10-12; ubt_ssid=nz0x65uufcnt2u486d713skzrlgodkv0_2018-10-12; _utrace=32441460bf4815c36e53f0dda5805439_2018-10-12; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%2264cd64f8e979e8135c7134b78e7a1bcc%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E6%BC%AB%E6%80%9D%E8%8C%B6%22%2C%22openid%22%3A%22D33EABC02440239D14D4217F6745D01D%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221990%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E6%BC%AB%E6%80%9D%E8%8C%B6%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD33EABC02440239D14D4217F6745D01D%2F40%22%7D; track_id=1539355717|da82fc863ee34a8025473cc02cbc412f3ce656a280bfdb3a5a|aaf58d90ae9b0e59347f08d89afca2da; USERID=2234081394; SID=oT3ojJqOXwvqP6yKaZUETdf6zpXcRb2ykeKQ";
		cookies[2]="account=14797710725;perf_ssid=gbdk460rnag8ftf7gc6s0s5wev3q1t1g_2018-10-14; ubt_ssid=m98o9wvfagvg4hh6wh0vuexiwb7hqucg_2018-10-14; _utrace=32652d7a6cadb55781f194ac322378ff_2018-10-14; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%226d46c313ae2f5e98221042c632cbb92d%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FEBEA98D5ACF8C4A3DBF4764AF1E5C993%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FEBEA98D5ACF8C4A3DBF4764AF1E5C993%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FEBEA98D5ACF8C4A3DBF4764AF1E5C993%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FEBEA98D5ACF8C4A3DBF4764AF1E5C993%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FEBEA98D5ACF8C4A3DBF4764AF1E5C993%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E5%91%86%E8%90%8C%E7%9C%BC%22%2C%22openid%22%3A%22EBEA98D5ACF8C4A3DBF4764AF1E5C993%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E5%91%86%E8%90%8C%E7%9C%BC%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FEBEA98D5ACF8C4A3DBF4764AF1E5C993%2F40%22%7D; track_id=1539527860|698a9d4ca351b21a7738a22e8f1b19900493f516d347010349|dfb7adf25767496c682969a16fc37581; USERID=2230012994; SID=9h9RlOsZzOLUSPn6NCP7OUcCvcpqVPm9ucuQ";
		cookies[3]="account=15706596021;perf_ssid=ys0xa75xhmg3sgcuj4ts2rxe994ia42e_2018-10-14; ubt_ssid=ajxucwkxi2omx2o3e6efttwd4c2cytjr_2018-10-14; _utrace=fc5f68e484068e39207537d353b2883a_2018-10-14; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%22b3225995880342b7a5b568ffcf970510%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F13702FA623DF912DD822E9FF212F2484%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F13702FA623DF912DD822E9FF212F2484%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F13702FA623DF912DD822E9FF212F2484%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F13702FA623DF912DD822E9FF212F2484%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F13702FA623DF912DD822E9FF212F2484%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E8%BD%BB%E9%A2%A6%22%2C%22openid%22%3A%2213702FA623DF912DD822E9FF212F2484%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E8%BD%BB%E9%A2%A6%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F13702FA623DF912DD822E9FF212F2484%2F40%22%7D; track_id=1539527993|b5ffd878c7ed0a9494cb3d7df26015e21614b51f4ec898609a|92a23ab44531c0e81ce8061a0600b539; USERID=2250533306; SID=gVk6Nj9eJaA80qlcqds7ZIPDGuULzuNZIrWw";
		cookies[4]="account=13471023703;perf_ssid=0bzkr1gmt2kyovth7nobuho70ihvia8a_2018-10-14; ubt_ssid=206b1oo0n0dvy0ocl3dh6ygrkszdj2cy_2018-10-14; _utrace=1943368fbed6222f0101080bc1e9d017_2018-10-14; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%2236587fc0cefb90930923a188faa57d6d%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F79162E801AA4C3B38C08BBB47ABF9F41%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F79162E801AA4C3B38C08BBB47ABF9F41%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F79162E801AA4C3B38C08BBB47ABF9F41%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F79162E801AA4C3B38C08BBB47ABF9F41%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F79162E801AA4C3B38C08BBB47ABF9F41%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E9%9C%9C%E7%99%BD%22%2C%22openid%22%3A%2279162E801AA4C3B38C08BBB47ABF9F41%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E9%9C%9C%E7%99%BD%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F79162E801AA4C3B38C08BBB47ABF9F41%2F40%22%7D; track_id=1539528148|3dda1a3da8b2e506cd827eeab01e6132586c1afeed260fb386|75c644cb5644d39e1f17e290e01d4e4a; USERID=2249125226; SID=vA3gfRaavouQqmcJsNbM5r5Jcw4N2Gt3qJeg";
		cookies[5]="account=14797711709;perf_ssid=0b0w10n6hxh3u9c3yet0wqjdxom07mbd_2018-10-15; ubt_ssid=pz0j66g9k7frh3d263ycv8atxmllq91u_2018-10-15; _utrace=89d6e9dcfc2c0fcbd834d762c591ecd7_2018-10-15; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%2263603ee192ec9cdd38f166178e94b982%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA1B8F3EDA2CCA09592C26D97316D7CDB%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA1B8F3EDA2CCA09592C26D97316D7CDB%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA1B8F3EDA2CCA09592C26D97316D7CDB%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA1B8F3EDA2CCA09592C26D97316D7CDB%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA1B8F3EDA2CCA09592C26D97316D7CDB%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E5%A5%87%E5%A6%99%E7%9A%84%E4%BA%BA%E5%95%8A%22%2C%22openid%22%3A%22A1B8F3EDA2CCA09592C26D97316D7CDB%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E5%A5%87%E5%A6%99%E7%9A%84%E4%BA%BA%E5%95%8A%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA1B8F3EDA2CCA09592C26D97316D7CDB%2F40%22%7D; track_id=1539534171|3bc0fa78f43dd9faa9a2eb8fb8e7caf627f91ecb6bf48ffe22|a69b189c44b281546915697db4969a44; USERID=2231831498; SID=K7hE0Nil7tUnajLKUBluHIT5huOMlflFqncw";
		cookies[6]="account=15706575129;perf_ssid=5885nj3dtokq58ep2i91xzd5g9e62izj_2018-10-15; ubt_ssid=au971jbix80lo3n5ocsbtzftz7tqnuqv_2018-10-15; _utrace=87b692d11546d8c95e631ab6d6fbd833_2018-10-15; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%227baf5c000c1d2ce5cf5859d9fa52113d%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F39A2246677FF78A6A90BCFE9090849A5%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F39A2246677FF78A6A90BCFE9090849A5%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F39A2246677FF78A6A90BCFE9090849A5%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F39A2246677FF78A6A90BCFE9090849A5%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F39A2246677FF78A6A90BCFE9090849A5%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E9%9F%AC%E5%85%89%E5%85%BB%E6%99%A6%22%2C%22openid%22%3A%2239A2246677FF78A6A90BCFE9090849A5%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E9%9F%AC%E5%85%89%E5%85%BB%E6%99%A6%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F39A2246677FF78A6A90BCFE9090849A5%2F40%22%7D; track_id=1539534285|dedbff8f5c2f1d5f7ffd98c8ab1e01d835c70ea40d203c143c|9f60b0b3189c844e38d4576fd30270dc; USERID=2248511378; SID=gKsa341F0FdrBTqOmKWZzzuy8HMnsja4vwWQ";
		cookies[7]="account=18207725741;perf_ssid=ubkrizao382hd85jf587pztg055x16ag_2018-10-15; ubt_ssid=hvpm5d13smvpenviaxxf0ncz1yxlhtsh_2018-10-15; _utrace=e17946fbc2b34a09719146e68dc2de45_2018-10-15; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%22f51a6d9405da193b2cfd266e584d994e%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E7%AD%89%E9%A3%8E%E4%B9%9F%E7%AD%89%E4%BD%A0%22%2C%22openid%22%3A%22D59FBED671E5E427C13F1A39626F65F2%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E7%AD%89%E9%A3%8E%E4%B9%9F%E7%AD%89%E4%BD%A0%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FD59FBED671E5E427C13F1A39626F65F2%2F40%22%7D; track_id=1539534302|2d71da89090d4e664431cde2d26899052565fbeeff53df6ab3|103477f0df0b829592c4e42159a862b0; USERID=2235309002; SID=932GvxgZDGqk5Sb2wd3yCgMYKlHyk8O8HEGA";
		cookies[8]="account=15706590328;perf_ssid=9c96pcurd1give08vkjat9vqa3kgzo6s_2018-10-15; ubt_ssid=enr81cx7kzzcqe1mhf3qjmgkq1n0096t_2018-10-15; _utrace=1047e8373b1a8c464c1132abf289ff33_2018-10-15; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22constellation%22%3A%22%22%2C%22eleme_key%22%3A%225af43850beabfd9a8bd938a8a9806b39%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FDE612F05AA0F34D096405606A23B0AF2%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FDE612F05AA0F34D096405606A23B0AF2%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FDE612F05AA0F34D096405606A23B0AF2%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FDE612F05AA0F34D096405606A23B0AF2%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FDE612F05AA0F34D096405606A23B0AF2%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E6%98%A5%E5%AE%B5%E5%81%87%E5%AF%90%22%2C%22openid%22%3A%22DE612F05AA0F34D096405606A23B0AF2%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221990%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E6%98%A5%E5%AE%B5%E5%81%87%E5%AF%90%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FDE612F05AA0F34D096405606A23B0AF2%2F40%22%7D; track_id=1539534581|460c23f10f3e9b3505b4b6fba4f1a013ce0961fc498142e54d|f195e3f68d2b560bad3daa2b95531f64; USERID=2249063642; SID=LZL2PBERDHEaYLcIVm08NNQ8r78vgSNm0sdA";
	}

	public static void main(String[] args) throws Exception {
		url="https://h5.ele.me/hongbao/#hardware_id=&is_lucky_group=True&lucky_number=0&track_id=&platform=0&sn=110793922a2a7c32&theme_id=3233&device_id=&refer_user_id=2238853242";
		Map<String, Object> param=new HashMap<>();
		param.put("device_id", "");
		param.put("hardware_id", "");
		param.put("latitude", "");
		param.put("longitude", "");
		param.put("platform", 0);
		param.put("unionid", "fuck");
		param.put("weixin_avatar", "http://thirdqq.qlogo.cn/qqapp/101204453/FC8486BF4A505A66DA4A2A7DA27A91EF/40");
		param.put("weixin_username", "admin");
		param.put("method", "phone");
		String redpacketId=getRedPacketId(url);
		param.put("group_sn", redpacketId);
		int luckyNumber = getLuckyNumber(url);
		System.out.println("最大包是："+luckyNumber);
		int openCount =0;
		String paramStr=null;
		String sign=null;
		String trackId =null;
		int i=0;
		while (i<luckyNumber) {
			synchronized ("test") {
				Thread.sleep(1000);
				param.put("phone", getPhone(cookies[i]));
				sign = getSign(cookies[i]);
				trackId = getTrackId(cookies[i]);
				param.put("sign", sign);
				param.put("track_id", trackId);
				paramStr=JSONObject.toJSONString(param);
				System.out.println(paramStr);
				openCount = openRedpacket(paramStr,cookies[i]);
				System.out.println("开包数量："+openCount);
				if (openCount==luckyNumber-1) {
					break;
				}
				i++;
			}
		}
	}

	public static String getRedPacketId(String url) {
		Pattern p = Pattern.compile("&sn=(.*?)&");
		Matcher m = p.matcher(url);
		String result = null;
		if (m.find()) {
			result = m.group(1);
			System.out.println(result);
			return result;
		}
		return null;
	}

	public static int getLuckyNumber(String url) throws Exception {
		String redPacketId = getRedPacketId(url);
		url = url_getLuckyNumber.replace("REDPACKETID", redPacketId);
		String body = HttpUtil.request(url, "GET", null);
		if (body.contains("lucky_number")) {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(body);
			Object lucky_number = jsonObject.get("lucky_number");
			return Integer.valueOf(lucky_number.toString());
		}
		return 0;
	}
	public static String getPhone(String cookie) throws Exception {
		cookie = URLDecoder.decode(cookie, "utf-8");
		Pattern p = Pattern.compile("account=(.*?);");
		Matcher m = p.matcher(cookie);
		String result = null;
		if (m.find()) {
			result = m.group(1);
			System.out.println(result);
			return result;
		}
		return null;
	}
	public static String getSign(String cookie) throws Exception {
		cookie = URLDecoder.decode(cookie, "utf-8");
		Pattern p = Pattern.compile("\"eleme_key\":\"(.*?)\"");
		Matcher m = p.matcher(cookie);
		String result = null;
		if (m.find()) {
			result = m.group(1);
			System.out.println(result);
			return result;
		}
		return null;
	}
	public static String getTrackId(String cookie) throws Exception {
		cookie = URLDecoder.decode(cookie, "utf-8");
		Pattern p = Pattern.compile("track_id=(.*?);");
		Matcher m = p.matcher(cookie);
		String result = null;
		if (m.find()) {
			result = m.group(1);
			System.out.println(result);
			return result;
		}
		return null;
	}
	public static String getOpenId(String cookie) throws Exception {
		cookie = URLDecoder.decode(cookie, "utf-8");
		Pattern p = Pattern.compile("\"openid\":\"(.*?)\"");
		Matcher m = p.matcher(cookie);
		String result = null;
		if (m.find()) {
			result = m.group(1);
			System.out.println(result);
			return result;
		}
		return null;
	}

	public static int openRedpacket(String param, String cookie) throws Exception {
		String openId = getOpenId(cookie);
		url = url_openRedpacket.replace("OPENID", openId);
		String body = HttpUtil.logRquest(url, "POST", param, cookie);
		if (body != null) {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(body);
			JSONArray promotion_records = (JSONArray) jsonObject.get("promotion_records");
			int size = promotion_records.size();
			return size;
		}
		return 0;
	}
}
