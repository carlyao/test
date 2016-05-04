package com.upchina.rongCloud.test;

import java.util.Date;

import com.upchina.util.DateFormat;

public class TestRongCloud {

	public static void main(String[] args) throws Exception {
//		String key = "sfci50a7cz12i";
//		String secret = "F9Tap8CtODS";
//
//		SdkHttpResult result = null;
//
//		result = ApiHttpClient.getToken(key, secret, "402880ef4a", "asdfa",
//				"http://aa.com/a.png", FormatType.json);
//		System.out.println("gettoken=" + result);
		
		long time = new Date().getTime();
		System.out.println(time);
		Long timestamp = Long.parseLong("1451270208")*1000; 
		Date now = new Date(timestamp);
		String date = DateFormat.GetDateFormat(now, "yyyy-MM-dd HH:mm:ss");
		System.out.println(now);
		System.out.println(date);
	}
}
