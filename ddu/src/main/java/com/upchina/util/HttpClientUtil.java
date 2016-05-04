package com.upchina.util;

import com.upchina.Exception.BusinessException;
import com.upchina.Exception.UpChinaError;
import com.upchina.vo.rest.output.ResultOutVo;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by 99131 on 2016/3/30.
 */

public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static Object useHttpClient(HttpServletRequest request, String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        List<NameValuePair> parameters = new ArrayList<>();
        Enumeration<String> params = request.getAttributeNames();
        try{
            while (params.hasMoreElements()) {
                String param = params.nextElement();
                String value = request.getParameter(param);
                NameValuePair nameValuePair = new BasicNameValuePair(param, value);
                parameters.add(nameValuePair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            //取得HTTP response
            httpResponse = httpclient.execute(httpPost);
            //若状态码为200 ok
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //取出回应字串
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            } else {
                throw new BusinessException(UpChinaError.ERROR);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new BusinessException(UpChinaError.ERROR);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage());
                    throw new BusinessException(UpChinaError.ERROR);
                }
            }
        }
    }
}
