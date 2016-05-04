package com.upchina.api.rongYun;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;
import io.rong.util.HttpUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Created by 99131 on 2016/4/8.
 */
public class RongYunApi extends ApiHttpClient {
    /**
     * 禁言用户
     *
     * @param appKey
     * @param appSecret
     * @param userId
     * @param format
     * @return
     * @throws Exception
     */
    public static SdkHttpResult noSaying(String appKey, String appSecret,String userId, String groupId, String minute, FormatType format) throws Exception {
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(appKey, appSecret, "http://api.cn.ronghub.com/group/user/gag/add." + format.toString());
        StringBuilder sb = new StringBuilder();
        sb.append("userId=").append(URLEncoder.encode(userId, "UTF-8"));
        sb.append("&groupId=").append(URLEncoder.encode(groupId == null ? "" : groupId, "UTF-8"));
        sb.append("&minute=").append(URLEncoder.encode(minute == null ? "" : minute, "UTF-8"));
        HttpUtil.setBodyParameter(sb, conn);
        return HttpUtil.returnResult(conn);
    }
}
