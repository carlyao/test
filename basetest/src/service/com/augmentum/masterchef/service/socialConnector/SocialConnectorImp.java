package com.augmentum.masterchef.service.socialConnector;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.augmentum.masterchef.gameserver.config.ConfigService;
import com.augmentum.masterchef.util.HttpClientUtil;
import com.augmentum.masterchef.util.JsonResponseUtil;
import com.augmentum.masterchef.vo.SocialConnectorAccountInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorFriendInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorInfoVo;
import com.augmentum.masterchef.vo.SocialConnectorSimpleAccountInfoVo;

/**   
 * This class is used for ...   
 * @author carl.yao  
 *  2013-6-13 上午10:11:38   
 */
@Component
public class SocialConnectorImp implements SocialConnector{

	@Autowired
	private ConfigService configService;
	
	@Override
	public SocialConnectorFriendInfoVo getFriends(String token, String provider,
			String accountID) throws Exception{
		String url = configService.getSocailConnectorUrl();
		int offset = 0;
		int limit = 10;
		String userUrl = "http://"+url+"/friends/"+provider+"/"+accountID+"?token="+token+"&offset="+offset+"&limit="+limit;
		String userCallback = HttpClientUtil.getInstance().getMethod(userUrl, null);
		SocialConnectorFriendInfoVo socialConnectorInfoVo = JsonResponseUtil.convertJsonResponseObject(userCallback, SocialConnectorFriendInfoVo.class);
		return socialConnectorInfoVo;
	}

	@Override
	public SocialConnectorFriendInfoVo getSocialConnectorFriends(String token) throws Exception {
		String url = configService.getSocailConnectorUrl();
		String userUrl = "http://"+url+"/friends?token="+token;
		String userCallback = HttpClientUtil.getInstance().getMethod(userUrl, null);
		SocialConnectorFriendInfoVo socialConnectorInfoVo = JsonResponseUtil.convertJsonResponseObject(userCallback, SocialConnectorFriendInfoVo.class);
		return socialConnectorInfoVo;
	}

	@Override
	public SocialConnectorInfoVo getUserInfor(String token) throws Exception {
		String url = configService.getSocailConnectorUrl();
		String userUrl = "http://"+url+"/user/info?token="+token;
		String userCallback = HttpClientUtil.getInstance().getMethod(userUrl, null);
		SocialConnectorInfoVo socialConnectorInfoVo = JsonResponseUtil.convertJsonResponseObject(userCallback, SocialConnectorInfoVo.class);
		return socialConnectorInfoVo;
	}

	@Override
	public SocialConnectorAccountInfoVo getUserAccount(String token)
			throws Exception {
		String url = configService.getSocailConnectorUrl();
		String userUrl = "http://"+url+"/accounts?token="+token;
		String userCallback = HttpClientUtil.getInstance().getMethod(userUrl, null);
		SocialConnectorAccountInfoVo socialConnectorAccountInfoVo = JsonResponseUtil.convertJsonResponseObject(userCallback, SocialConnectorAccountInfoVo.class);
		return socialConnectorAccountInfoVo;
	}

	@Override
	public void pulish(String token,
			List<SocialConnectorSimpleAccountInfoVo> simpleAccountInfoVos,
			String content) throws Exception {
		String url = configService.getSocailConnectorUrl();
		StringBuffer requestBody = new StringBuffer();
		requestBody.append("accounts=" + JsonResponseUtil.toString(simpleAccountInfoVos)).append("&");
		requestBody.append("token="+token).append("&");
		requestBody.append("data="+content).append("&");
		requestBody.append("_method=POST").append("&");
		String userUrl = "http://"+url+"/publish";
		String userCallback = HttpClientUtil.getInstance().postMethod(userUrl,requestBody.toString(),null);
		System.out.println(userCallback);
	}

	@Override
	public void feedContent(String token, String contenturl) throws Exception {
		String url = configService.getSocailConnectorUrl();
		StringBuffer requestBody = new StringBuffer();
		requestBody.append("token="+token).append("&");
		requestBody.append("url="+contenturl).append("&");
		requestBody.append("_method=POST").append("&");
		String userUrl = "http://"+url+"/connect/feed";
		String userCallback = HttpClientUtil.getInstance().postMethod(userUrl,requestBody.toString(),null);
		System.out.println(userCallback);
	}

	@Override
	public void pulishImg(String token,
			List<SocialConnectorSimpleAccountInfoVo> simpleAccountInfoVos,
			String content, File image) throws Exception {
		String url = configService.getSocailConnectorUrl();
		String userUrl = "http://"+url+"/publish/img";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(userUrl);
		
		FileBody bin = new FileBody(image);
		StringBody comment = new StringBody("Filename: " + image.getName());
		StringBody accounts = new StringBody(JsonResponseUtil.toString(simpleAccountInfoVos));
		StringBody stoken = new StringBody(token);
		StringBody scontent = new StringBody(content);

		MultipartEntity reqEntity = new MultipartEntity();
		reqEntity.addPart("file", bin);
		reqEntity.addPart("comment", comment);
		reqEntity.addPart("accounts", accounts);
		reqEntity.addPart("token", stoken);
		reqEntity.addPart("content", scontent);
		httppost.setEntity(reqEntity);
		httppost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		InputStream is = resEntity.getContent();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		System.out.println(buffer.toString());
		String callbackString = buffer.toString();
	}


}

