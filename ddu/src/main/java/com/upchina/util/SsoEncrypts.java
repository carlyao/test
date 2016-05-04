package com.upchina.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.google.common.base.Charsets;

/**
 * Created by Liaozhiwei on 2015/6/9.
 */
public class SsoEncrypts {
	public static final String KEY_MAC = "HmacMD5";
	private static byte[] iv1 = { 0x75, 0x70, 0x63, 0x68, 0x69, 0x6e, 0x61, 0x31 };
	private static Key key = null;

	public void setKey(String strKey) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(strKey.getBytes(Charsets.UTF_8));
			keyFactory.generateSecret(keySpec);
			key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		}
	}

	/* DES加密： */

	/**
	 * 加密 String 明文输入 ,String 密文输出
	 */
	public String encryptStr(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes(Charsets.UTF_8);
			byteMi = this.encryptByte(byteMing);
			strMi = base64en.encode(byteMi);
			System.out.println("加密------------" + strMi);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 加密以 byte[] 明文输入 ,byte[] 密文输出
	 *
	 * @param byteS
	 * @return
	 */
	private byte[] encryptByte(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			IvParameterSpec iv = new IvParameterSpec(iv1);
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// cipher.init(Cipher. ENCRYPT_MODE , key );
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/* DES解密： */
	/**
	 * 解密 以 String 密文输入 ,String 明文输出
	 *
	 * @param strMi
	 * @return
	 */
	public static String decryptStr(String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = decryptByte(byteMi);
			strMing = new String(byteMing, "UTF8");
			System.out.println("解密-------------------" + strMing);
		} catch (Exception e) {
			e.printStackTrace();
			// throw new RuntimeException(
			// "Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 *
	 * @param byteD
	 * @return
	 */
	private static byte[] decryptByte(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {

			IvParameterSpec iv = new IvParameterSpec(iv1);
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/* HMACMD5： */
	public static String encryptHMAC(byte[] data, String key) {
		try {
			SecretKey sk = new SecretKeySpec(key.getBytes(Charsets.UTF_8), KEY_MAC);
			Mac mac = Mac.getInstance(sk.getAlgorithm());
			mac.init(sk);
			byte[] hmacMd5Bytes = mac.doFinal(data);
			return (new BASE64Encoder()).encodeBuffer(hmacMd5Bytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
//		addNote();
//		viewNote();
		// param =
		// "{\"deviceType\":\"123456\",\"deviceId\":\"123456\",\"activeId\":\"1001\"}";
		// strmi = ssoEncrypts.encryptStr(param);
		// sign = SsoEncrypts.encryptHMAC(strmi.getBytes(Charsets.UTF_8),
		// strKey); //生存sign
		// sign = StringUtils.replace(sign, "\r\n", "");
		// String result1 =
		// HttpGetProtocol.builder().url("http://localhost:8080/userTicket/openTicket")
		// .send(KvParam.builder()
		// .set("param", strmi)
		// .set("sign", sign)
		// );
		// System.out.println("result============="+result1);
		// //String param =
		// "{\"Mobile\":\"18688865120\",\"CodeType\":\"Sms_Agu_Code\"}";
		// String param =
		// "{\"uid\":\"test3\",\"pwd\":\""+Digests.md5("up123")+"\",\"campaign_id\":\"28\"}";
		// System.out.println(param);
		//
		// SsoEncrypts ssoEncrypts = new SsoEncrypts();
		// String strKey = "sp438998";
		// ssoEncrypts.setKey(strKey);
		//
		// String strmi = ssoEncrypts.encryptStr(param); //加密
		// strmi = StringUtils.replace(strmi, "\r\n", "");
		//
		// String sign = SsoEncrypts.encryptHMAC(strmi.getBytes(Charsets.UTF_8),
		// strKey); //生存sign
		// sign = StringUtils.replace(sign, "\r\n", "");
		// System.out.println("sign----------" + sign);
		// System.out.println("1.登录接口测试开始----------");
		// //System.out.println("----------"+de);
		// String result = HttpPostProtocol.builder()
		// .url("http://ssoapi.0135135.com/sso/web/uv/")
		// .charset(Charsets.UTF_8)
		// .send(KvParam.builder()
		// .set("param", strmi)
		// .set("clientId", "up_web")
		// .set("sign", sign));
		// sign = StringUtils.replace(sign, "\r\n", "");
		// String res =RegUtils.getMatcher(result,"param=(.*)&sign");
		// String de = ssoEncrypts.decryptStr(res);
		// String hqrights=RegUtils.getMatcher(de,
		// "\"hqrights\":\"(.*)\",\"hqrtime\"");
		// String rd=RegUtils.getMatcher(de, ",\"rd\":\"(.*)\",\"result");
		// System.out.println("----------登录接口测试结束----------");
		//
		// System.out.println("2.个人中心获取个人资料----------");
		// //String drd=BlowFish.encrypt(Digests.md5("up123"), rd);
		// String drd=BlowFish.decrypt(Digests.md5("up123"), rd).trim();
		// System.out.println("rd="+rd+"---drd="+drd+"---");
		// String result2 = HttpPostProtocol.builder()
		// .url("http://app.0135135.com/sasweb/xysidkdydnhensydn_cdhds.dyshg/dsfyewlrndsfpoidsfewlkdsnf.cxgdsf_hdsfnew_gz/AjaxUserCenter/GetUserInfo.cspx")
		// .charset(Charsets.UTF_8)
		// .send(KvParam.builder()
		// .set("userName", "test3")
		// .set("hqrights", hqrights)
		// .set("cipher", BlowFish.encrypt(drd,"test3")));
		// System.out.println("个人资料json="+result2);
		// System.out.println("2.个人中心获取个人资料结束----------");
		//
		// System.out.println("2.个人中心获取个人资料----------");
		// System.out.println("2.个人中心获取个人资料结束----------");
		//
		// System.out.println("2.个人中心获取个人资料----------");
		// System.out.println("2.个人中心获取个人资料结束----------");
		//
		// System.out.println("2.个人中心获取个人资料----------");
		// System.out.println("2.个人中心获取个人资料结束----------");

	}

//	private static void viewNote() throws Exception {
//		long time = new Date().getTime();
		
//		UserFriend inVo = new UserFriend();
//		inVo.setUserId(10969268);
//		inVo.setId(148);
//		noteInVo.setTime(String.valueOf(time));
		
//		inVo.setAppId("天天牛");
//		inVo.setAppVersion("1.0");
//		inVo.setPlatformType("android");
//		inVo.setDeviceType("mi");
//		
//		String param = JacksonUtil.beanToJson(inVo);
//		System.out.println(param);
//		
//		SsoEncrypts ssoEncrypts = new SsoEncrypts();
//		String strKey = "sp438998";
//		ssoEncrypts.setKey(strKey);
//		String strmi = ssoEncrypts.encryptStr(param);
//		strmi = StringUtils.replace(strmi, "\r\n", "");
//		String sign = SsoEncrypts.encryptHMAC(strmi.getBytes(Charsets.UTF_8), strKey); // 生存sign
//		sign = StringUtils.replace(sign, "\r\n", "");
//		
//		String url = "http://localhost:9091/userFriend/getListAllFriend";
//		String url = "http://localhost:9091/note/view";
//		String result = HttpPostProtocol.builder().url(url)
//				.send(KvParam.builder().set("param", strmi).set("sign", sign));
		
//		BaseOutVo res = (BaseOutVo) JacksonUtil.jsonToBean(result, BaseOutVo.class);
////		new SsoEncrypts().setKey("sp438998");
//		String str=SsoEncrypts.decryptStr(res.getResultData().toString());
//		NoteOutVo nov = (NoteOutVo) JacksonUtil.jsonToBean(str, NoteOutVo.class);
		
//		String res="E+b6Qd8KtI9kFW1WGIvZdOHsRZ87ADTElOYBOcpzkE4IUrH2Wa0Jd4HsZslfUP4JEy3bTrcbmYVLf2ZTbcZ+Rtxq6sw+6fMW49ycp7nIqyUW323u3nRrByjgWuuhAZnh3NGpB17kPvnH3Ar4+yrL3MuO73d86zXW";
//		new SsoEncrypts().setKey("sp438998");
//		String str=SsoEncrypts.decryptStr(res);
//		NoteOutVo nov = (NoteOutVo) JacksonUtil.jsonToBean(str, NoteOutVo.class);
		
//		String str="{\"content\":\"ceshibi\",\"summary\":\"ceshi\",\"title\":\"ceshibiji\",\"type\":\"1\",\"cost\":\"\",\"tagId\":[7,13],\"userId\":\"10969268\"}";
//		NoteInVo nov = (NoteInVo) JacksonUtil.jsonToBean(str, NoteInVo.class);
//		System.out.println(nov);
		
//		JSONObject a=new JSONObject();
//		a.put("content", "ceshibi");
//		a.put("userId", 10969268);
////		JSONArray list=new JSONArray();
////		list.put(3);
////		list.put(7);
//		ArrayList list=new ArrayList<Integer>();
//		list.add(7);
//		list.add(3);
//		a.put("tagId", list);
//		System.err.println(a);
//	}
	
//	private static void addNote() throws Exception {
//		long time = new Date().getTime();
//
//		NoteInVo noteInVo = new NoteInVo();
//		noteInVo.setUserId(10969266);
//		noteInVo.setTitle("test");
//		noteInVo.setContent("Test");
//		noteInVo.setSummary("Test");
//		noteInVo.setType(2);
//		noteInVo.setCost(new BigDecimal(123));
//		List<Integer> tagId = new ArrayList<Integer>();
//		tagId.add( 7);
//		noteInVo.setTagId(tagId);
//		noteInVo.setTime(String.valueOf(time));
//		
//		noteInVo.setAppId("天天牛");
//		noteInVo.setAppVersion("1.0");
//		noteInVo.setPlatformType("android");
////		noteInVo.setPlatformVersion("5.0");
//		noteInVo.setDeviceType("mi");
////		noteInVo.setDeviceId("uuid");
//		// String param =
//		// "{\"userId\":\"shang93\",\"userName\":\"shang93\",\"actionId\":\"1\",\"deviceType\":\"ios\",\"deviceId\":\"09DE4BB4-07BA-4369-8624-46EF5B436086\",\"source\":\"test\",\"time\":\"1446087765847\"}";
//		String param = JacksonUtil.beanToJson(noteInVo);
//		System.out.println(param);
//		SsoEncrypts ssoEncrypts = new SsoEncrypts();
//		String strKey = "sp438998";
//		ssoEncrypts.setKey(strKey);
//
//		String url = "http://localhost:9091/note/add";
//		// String url = "http://192.168.6.170:8081/userTicket/addTicket";
//		String strmi = ssoEncrypts.encryptStr(param);
//		strmi = StringUtils.replace(strmi, "\r\n", "");
//		String sign = SsoEncrypts.encryptHMAC(strmi.getBytes(Charsets.UTF_8), strKey); // 生存sign
//		sign = StringUtils.replace(sign, "\r\n", "");
//		String result = HttpPostProtocol.builder().url(url)
//				.send(KvParam.builder().set("param", strmi).set("sign", sign));
//		System.out.println(result);
//	}
}
