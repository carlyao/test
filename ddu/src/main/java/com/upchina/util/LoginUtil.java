package com.upchina.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.upchina.Exception.UpChinaError;
import com.upchina.api.param.KvParam;
import com.upchina.api.protocol.HttpGetProtocol;
import com.upchina.api.protocol.HttpPostProtocol;
import com.upchina.vo.BaseOutVo;
import com.upchina.vo.rest.LoginVo;
import com.upchina.vo.rest.RegistVo;
import com.upchina.vo.rest.ReponseResultVo;
import com.upchina.vo.rest.CurrentUserInfoVo;
import com.upchina.vo.rest.UserVo;

public class LoginUtil {

	public static UserVo login(LoginVo loginVo) throws Exception{
		String pwd = loginVo.getPassword();
		String uid = loginVo.getName();
		pwd = Digests.md5(pwd);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("uid", uid);
		jsonObject.addProperty("pwd", pwd );
		jsonObject.addProperty("campaign_id", 28);
		String param = jsonObject.toString();
		String strKey = APIHostUtil.getSSOKey();
		String strmi = getParam(param, strKey);
		String sign = getSign(strmi, strKey);
		
		String url = APIHostUtil.getUserLoginApiHost();
        String result = HttpPostProtocol.builder()
                .url(url)
                .charset(Charsets.UTF_8)
                .send(KvParam.builder()
                        .set("param", strmi)
                        .set("clientId", "up_web")
                        .set("sign", sign));
        String res =RegUtils.getMatcher(result,"param=(.*)&sign");
        String de = SsoEncrypts.decryptStr(res);
        System.out.println("login==============="+de);
        UserVo userVo = (UserVo) JacksonUtil.jsonToBean(de, UserVo.class);
        userVo.setPwd(pwd);
        userVo.setName(uid);
		return userVo;
	}
	
	public static ReponseResultVo register(RegistVo registVo) throws Exception{
		String pwd = registVo.getPassword();
		String uid = registVo.getName();
		String phoneNum = registVo.getPhoneNum();
		String code = registVo.getVerifyCode();
		
		ReponseResultVo registerVo = comfirmCode(phoneNum, code);
		if(registerVo.isResult()){
			String url = APIHostUtil.getUserRegApiHost();
			String result = HttpPostProtocol.builder()
					.url(url)
					.charset(Charsets.UTF_8)
					.send(KvParam.builder()
							.set("UserName", uid)
							.set("Password", pwd)
							.set("CampaignId", 10000)
							.set("Mobile", phoneNum)
							.set("AcceptSms", "Y")
							.set("regType", 1));
			System.out.println("regist==============="+result);
			registerVo = (ReponseResultVo) JacksonUtil.jsonToBean(result, ReponseResultVo.class);
			return registerVo;
		}
		return registerVo;
	} 
	
	public static ReponseResultVo exsitUser(RegistVo registVo) throws Exception{
		String uid = registVo.getName();
		String url = APIHostUtil.getExistUserNameApiHost();
		String result = HttpGetProtocol.builder()
                .url(url+uid)
                .send(KvParam.builder());
		ReponseResultVo registerVo = (ReponseResultVo) JacksonUtil.jsonToBean(result, ReponseResultVo.class);
		return registerVo;
	} 
	
	public static ReponseResultVo getMobileCode(String phoneNum) throws Exception{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("mobile", phoneNum );
		jsonObject.addProperty("CodeType", "sms_cjxmh_code");
		String strMoblieKey = APIHostUtil.getMobileKey();
        String strmi = getParam(jsonObject.toString(),strMoblieKey);
        String sign = getSign(strmi,strMoblieKey);

        String url = APIHostUtil.getMobileGetCodeApiHost();
        String result = HttpGetProtocol.builder()
                .url(url)
                .send(KvParam.builder()
                        .set("key", strmi)
                        .set("clientId", "UPWEBSITE")
                        .set("sign", sign));

        String de = SsoEncrypts.decryptStr(result);
        ReponseResultVo reponseResultVo = (ReponseResultVo) JacksonUtil.jsonToBean(de, ReponseResultVo.class);
		return reponseResultVo;
	} 
	
	public static ReponseResultVo comfirmCode(String phoneNum, String code) throws Exception{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("code", code);
		jsonObject.addProperty("mobile", phoneNum );
		jsonObject.addProperty("CodeType", "sms_cjxmh_code");
		String param = jsonObject.toString();
		String strMoblieKey = APIHostUtil.getMobileKey();
        String strmi = getParam(param, strMoblieKey);
		String sign = getSign(strmi, strMoblieKey);
		String url = APIHostUtil.getMobileComefirmCodeApiHost();
		String result = HttpGetProtocol.builder()
                .url(url)
                //.charset(Charsets.UTF_8)
                .send(KvParam.builder()
                        .set("key", strmi)
                        .set("clientId", "UPWEBSITE")
                        .set("sign", sign));
		System.out.println(result);
		String de = SsoEncrypts.decryptStr(result);
		ReponseResultVo reponseResultVo = (ReponseResultVo) JacksonUtil.jsonToBean(de, ReponseResultVo.class);
		return reponseResultVo;
	} 
	
	public static String resetPassword(String newpwd, String phoneNum, String code){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("username", "carlyaoa");
		jsonObject.addProperty("newpass", newpwd);
		jsonObject.addProperty("mobile", phoneNum);
		jsonObject.addProperty("code", code);
		jsonObject.addProperty("CodeType", "sms_cjxmh_code");
		String param = jsonObject.toString();
		String strMoblieKey = APIHostUtil.getMobileKey();
        String strmi = getParam(param,strMoblieKey);
		String sign = getSign(strmi,strMoblieKey);
		String result = HttpGetProtocol.builder()
                .url("http://113.108.146.106:805/SoftUserService.svc/webRetpwd")
                .send(KvParam.builder()
                        .set("param", strmi)
                        .set("clientId", "up_web")
                        .set("sign", sign));
		System.out.println(result);
		return null;
	} 
	
	public static void main(String[] args) throws Exception {
		LoginVo loginVo = new LoginVo();
		loginVo.setPassword("up123");
		loginVo.setName("test3");
		RegistVo registVo = new RegistVo();
		registVo.setPassword(loginVo.getPassword());
		registVo.setName(loginVo.getName());
		registVo.setPhoneNum("18207131568");;
		registVo.setVerifyCode("315535");
//		register(registVo);
		UserVo userVo = login(loginVo);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("CLTP", "2");
		paraMap.put("CVER", "a");
		paraMap.put("GVER", "HSO1");
		paraMap.put("JYMM", "111111");
		paraMap.put("HKK", userVo.getCid());
		paraMap.put("MAC", "a");
		paraMap.put("SECK", "wxy7i88urh240eqf@");
		paraMap.put("YYB", "81");
		paraMap.put("ZHLB", "1");
		String url = "http://192.168.101.103:9999";
		Object result = HttpRequest.sendRequest(url, paraMap, "5003");
		System.out.println(result);
//		getMobileCode("18207131568");
//		comfirmCode("18207131568","315535");
//		exsitUser(registVo);
//		resetPassword("123!","13641723008","783046");
	}
	
	public static String getParam(String josn, String key){
		SsoEncrypts ssoEncrypts = new SsoEncrypts();
        ssoEncrypts.setKey(key);
        
        String strmi = ssoEncrypts.encryptStr(josn);  //加密
        strmi = StringUtils.replace(strmi, "\r", "");
		strmi = StringUtils.replace(strmi, "\n", "");
        return strmi;
	}
	
	
	public static String getSign(String strmi, String key){
		String sign = SsoEncrypts.encryptHMAC(strmi.getBytes(Charsets.UTF_8), key); //生存sign
		sign = StringUtils.replace(sign, "\r", "");
		sign = StringUtils.replace(sign, "\n", "");
        System.out.println("sign----------" + sign);
        return sign;
	}

	public static BaseOutVo getResponseInfo(ReponseResultVo reponseResultVo) {
		BaseOutVo res = new BaseOutVo();
		int retCode = reponseResultVo.getRetcode();
		switch (retCode) {
		case 10000:
			res.setResultMsg(UpChinaError.REGISTER_SUCCESS.message);	
			break;
		case 10002:
			res.setResultMsg(UpChinaError.REGISTER_EXIST_USER.message);	
			break;
		case 10003:
			res.setResultMsg(UpChinaError.REGISTER_MISS_PARAM.message);	
			break;
		case 10004:
			res.setResultMsg(UpChinaError.REGISTER_PHONENUM_ERROR.message);	
			break;
		case 10005:
			res.setResultMsg(UpChinaError.REGISTER_SYSTEM_ERROR.message);
			break;
		case 10006:
			res.setResultMsg(UpChinaError.REGISTER_USER_HAVE_REGIST.message);
			break;
		case 10007:
			res.setResultMsg(UpChinaError.REGISTER_VERIFYCODE_OUTTIME.message);
			break;
		case 10009:
			res.setResultMsg(UpChinaError.REGISTER_USER_HAVE_PHONENUM.message);
			break;
		case 10010:
			res.setResultMsg(UpChinaError.REGISTER_PHONENUM_HAVE_REGIST.message);
			break;
		case 10021:
			res.setResultMsg(UpChinaError.REGISTER_SENT_THRID_TIMES.message);
			break;
		case 10023:
			res.setResultMsg(UpChinaError.REGISTER_CODE_ERROR.message);
			break;
		default:
			res.setResultMsg(UpChinaError.REGISTER_SYSTEM_ERROR.message);
			break;
		}
		res.setResultCode(String.valueOf(reponseResultVo.getRetcode()));
		return res;
	}
	
	
	public static CurrentUserInfoVo getCurrentUserInfo(UserVo user) {
		CurrentUserInfoVo userInfo = null;
		String drd = BlowFish.decrypt(user.getPwd(), user.getRd()).trim();
		String result1 = HttpPostProtocol
				.builder()
				.url(APIHostUtil.getUserInfoHost())
				.charset(Charsets.UTF_8)
				.send(KvParam.builder().set("userName", user.getName()).set("hqrights", user.getHqrights())
						.set("cipher", BlowFish.encrypt(drd, user.getName())));
		if (StringUtils.isEmpty(result1)) {// 说明用户没有个人资料信息，要添加资料
			String result2 = HttpPostProtocol
					.builder()
					.url(APIHostUtil.getAddUserInfoHost())
					.charset(Charsets.UTF_8)
					.send(KvParam.builder().set("hqrights", user.getHqrights())
							.set("cipher", BlowFish.encrypt(drd, user.getName())).set("Name", user.getName())
							.set("UserName", user.getName()).set("Userid", user.getCid()));
			if (result2.equals("1")) {
				userInfo = new CurrentUserInfoVo();
				userInfo.setName(user.getName());
				userInfo.setUserName(user.getNicName());
				userInfo.setUserId(Integer.parseInt(user.getCid()));
				return userInfo;
			}
		}
		try {
			userInfo = (CurrentUserInfoVo) JacksonUtil.jsonToBean(result1, CurrentUserInfoVo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
    }
}
