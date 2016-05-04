package com.upchina.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;
import com.upchina.api.param.KvParam;
import com.upchina.api.protocol.HttpPostProtocol;
import com.upchina.vo.rest.input.UserOrderInVo;

/**
 * BlowFish �ӽ���
 * 
 * @author Administrator
 *
 */
public class BlowFish {

	// public static final String KEY="911e7838309fdd7889950740781ffffc";

	public static final String ALGORITHM = "Blowfish";

	public static final String TRANSFORMATION = "blowfish/CBC/NoPadding";

	private static byte[] iv = { 0x75, 0x70, 0x63, 0x68, 0x69, 0x6e, 0x61, 0x31 };

	/**
	 * ����
	 * @param key
	 * @param input ��Ҫ���ܵĴ�
	 * @return
	 */
	public static String decrypt(String key, String input) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
			IvParameterSpec spec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
			byte[] data = Encodes.decodeBase64(input);
			byte[] decryptData = cipher.doFinal(data, 0, data.length);
			return new String(decryptData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * ����
	 * @param key
	 * @param input ��Ҫ���ܵĴ�
	 * @return
	 */
	public static String encrypt(String key, String input) {
		try {
			if (StringUtils.isEmpty(input))
				return input;
			// ����8λ�����油" "
			StringBuffer buffer = new StringBuffer();
			buffer.append(input);
			if (input.length() % 8 != 0) {
				int length = 0;
				if (input.length() < 8) {
					length = 8 - input.length();
				} else {
					length = 8 - input.length() % 8;
				}
				for (int i = 0; i < length; i++) {
					buffer.append(" ");
				}
			}
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
			IvParameterSpec spec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ByteArrayInputStream bis = new ByteArrayInputStream(buffer
					.toString().getBytes());
			CipherOutputStream cos = new CipherOutputStream(bos, cipher);
			int theByte = 0;
			while ((theByte = bis.read()) != -1) {
				cos.write(theByte);
			}
			cos.close();
			bis.close();
			return Encodes.encodeBase64(bos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}
	
	public static void main(String[] args) throws Exception {
		long time = new Date().getTime();

		UserOrderInVo inVo = new UserOrderInVo();
		inVo.setUserId(10969268);
		inVo.setOrderId(62);
		inVo.setOrderType(4);
		inVo.setCount(1);
		inVo.setTime(String.valueOf(time));
		String param = JacksonUtil.beanToJson(inVo);
		System.out.println(param);
		SsoEncrypts ssoEncrypts = new SsoEncrypts();
		String strKey = "sp438998";
		ssoEncrypts.setKey(strKey);

		String url = "http://localhost:9091/userOrder/add";
		// String url = "http://192.168.6.170:8081/userTicket/addTicket";
		String strmi = BlowFish.encrypt(strKey,param);
//		strmi = StringUtils.replace(strmi, "\r\n", "");
		String sign = SsoEncrypts.encryptHMAC(strmi.getBytes(Charsets.UTF_8), strKey); // 生存sign
		sign = StringUtils.replace(sign, "\r\n", "");
		String result = HttpPostProtocol.builder().url(url)
				.send(KvParam.builder().set("param", strmi).set("sign", sign));
		System.out.println(result);
	}
		
	/*public static void main(String[] args) {
		String json="{\"BUSINESS_ID\":1,\"BUSINESS_ORDER_ID\":81,\"TIMESTAMP\":1458548437369,\"BUSINESS_CODE\":62,\"BUSINESS_TYPE\":4,\"BUSINESS_NAME\":\"牛牛牛\",\"BUSINESS_COUNT\":1,\"PRICE\":666,\"CHANNEL\":\"\",\"FEE_RATE\":0.00,\"PERIOD\":\"\",\"PAYER_NAME\":\"carltest12000\",\"PAYEE_NAME\":\"carltest11000\",\"EXPIRE_DATE\":\"\",\"CALLBACK\":\"http://172.16.8.233:8080/userOrder/payCallBack\"}";
//			String json="{"BUSINESS_ID":1,"BUSINESS_ORDER_ID":81,"TIMESTAMP":1458548437369,"BUSINESS_CODE":62,"BUSINESS_TYPE":4,"BUSINESS_NAME":"牛牛牛","BUSINESS_COUNT":1,"PRICE":666,"CHANNEL":"","FEE_RATE":0.00,"PERIOD":"","PAYER_NAME":"carltest12000","PAYEE_NAME":"carltest11000","EXPIRE_DATE":"","CALLBACK":"http://172.16.8.233:8080/userOrder/payCallBack"}
//			String json="{"BUSINESS_ID":1,"BUSINESS_ORDER_ID":81,"TIMESTAMP":1458548437369,"BUSINESS_CODE":62,"BUSINESS_TYPE":4,"BUSINESS_NAME":"牛牛牛","BUSINESS_COUNT":1,"PRICE":666,"CHANNEL":"","FEE_RATE":0.00,"PERIOD":"","PAYER_NAME":"carltest12000","PAYEE_NAME":"carltest11000","EXPIRE_DATE":"","CALLBACK":"http://172.16.8.233:8080/userOrder/payCallBack
		String encrypted = BlowFish.encrypt("123", json);
		System.out.println(encrypted);
		String decrypted=BlowFish.decrypt("123", encrypted);
		System.out.println(decrypted);
	}*/
	
}
