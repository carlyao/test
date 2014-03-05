package com.augmentum.masterchef.util;

import java.net.URLDecoder;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESPlus {
	private static String strDefaultKey = "national";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	/**
	 * Convert byte array to hex string 
	 * For example: byte[]{8,18} => 0813
	 * 
	 * Against to method 'public static byte[] hexStr2ByteArr(String strIn)'
	 * 
	 * @param arrB
	 * @return 
	 * @throws Exception
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// each byte present by two char
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// convert negative to primitive
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}

			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * Convert hex string to byte array
	 * 
	 * Against to method 'public static String byteArr2HexStr(byte[] arrB)'
	 * 
	 * @param strIn      
	 * @return 
	 * @throws Exception
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 */
	public DESPlus() throws Exception {
		this(strDefaultKey);
	}

	/**
	 * Constructor with specified secret key
	 * 
	 * @param strKey
	 * @throws Exception
	 */
	public DESPlus(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * Encrypt byte array
	 * 
	 * @param arrB
	 * @return 
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * Encrypt string
	 * 
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * return BASE64 format encrypted string
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String encryptToBASE64(String strIn) throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		byte[] t = encrypt(strIn.getBytes());
		
		//for(int i=0; i< t.length; i++) {
		//	System.out.print(t[i]);
		//	System.out.print(",");}
		
		return encoder.encode(t);
	}
	
	/**
	 * Unencrypt byte array
	 * 
	 * @param arrB
	 * @return 
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * Unencrypt string
	 * 
	 * @param strIn
	 * @return 
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	/**
	 * 
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String decryptFromBASE64Padding(String strIn) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] t = decoder.decodeBuffer(strIn);
		System.out.println("size=" + t.length);
		
		int mod = t.length % 8;
		if(0 != mod) {
			int byteLen = ((t.length / 8) + 1) * 8;
			System.out.println("new byte[] len=" + byteLen);
			
			byte[] newByte = new byte[byteLen];
			System.arraycopy(t, 0, newByte, 0, t.length);
			
			for(int i=1; i <= mod; i++) {
				newByte[byteLen-i] = '\0';
			}
			for(int i=0; i < newByte.length; i++) {
				System.out.println(newByte[i]);
			}
			
			return new String(decrypt(newByte), "UTF-8");
		}else{
			return new String(decrypt(t), "UTF-8");
		}
	}
	
	public String decryptFromBASE64(String strIn) throws Exception {
		
		BASE64Decoder d = new BASE64Decoder();
		
		String a = URLDecoder.decode(strIn, "UTF-8");
		a= a.replaceAll("_2", "/").replaceAll("_3", "=").replaceAll("_1", "+");
		//a = URLDecoder.decode(a, "UTF-8");
		//a = strIn.replaceAll("_", "/").replaceAll("-", "=").replaceAll(" ", "+");
		//System.out.println(""+a);
		return new String(decrypt(d.decodeBuffer(a)), "UTF-8");
	}
	
	
	/**
	 * Generate secret key
	 * 
	 * @param arrBTmp
	 * @return 
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		//array length must be 8
		byte[] arrB = new byte[8];

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	public static void main(String[] args) {
		try {
			/*
			key: KeyString
			Data: This is the test data
			CryptText: X2jnAuJ0r2Z0aGUgdGVzdCBkYXRh
			 doKzEGp4baF0aGUgdGVzdCBkYXRh
			*/
			// DESPlus des = new DESPlus();
			//restful/progression/level/definition/1?userId=demoPlayer&ticket=1272609299896&token=123456
			String test = "fdfdsfdsf";
			DESPlus des = new DESPlus("asdfds");
			System.out.println("string before encrypt: " + test);
			String encryptedStr = des.encryptToBASE64(test);
			System.out.println("string after encrypt: " + encryptedStr);
			System.out.println("plain text: " + des.decryptFromBASE64(encryptedStr));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
