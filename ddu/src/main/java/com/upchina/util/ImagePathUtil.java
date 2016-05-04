package com.upchina.util;

import java.io.IOException;
import java.util.Properties;

public class ImagePathUtil {

    public static String UUD_KEY = "UUD_KEY";
    public static String UUD_SECRET = "UUD_SECRET";
    public static String UPLOAD_ROOT_PATH = "UPLOAD_ROOT_PATH";
    public static String UPLOAD_GROUP_PATH = "UPLOAD_GROUP_PATH";
    public static String UPLOAD_ATTACHMENT_PATH = "UPLOAD_ATTACHMENT_PATH";
    public static String UPLOAD_CETIFICATE_PATH = "UPLOAD_CETIFICATE_PATH";
    public static String UPLOAD_PATH = "UPLOAD_PATH";
    public static String RONG_CLOUD_MESSAGE = "RONG_CLOUD_MESSAGE";
    public static String IMG_HOST = "IMG_HOST";
    public static String NO_SAYING_TIME = "NO_SAYING_TIME";


    private static Properties properties;

    private ImagePathUtil() {

    }

    private static String getProperty(String key) {
        if (properties == null) {
            properties = PropertyUtils.getPropertiesByPath("/properties/ddu.properties");
        }
        return properties.getProperty(key);
    }

    public static String getHost(String key) {
        String host = getProperty(key);
        return host;
    }

    public static String getNoSayingTime() {
        return getHost(NO_SAYING_TIME);
    }

    public static String getUploadPath() {
        return getHost(UPLOAD_PATH);
    }

    public static String getUploadGroupPath() {
        return getUploadRootPath() + getHost(UPLOAD_GROUP_PATH);
    }

    public static String getUploadAttachmentPath() {
        return getUploadRootPath() + getHost(UPLOAD_ATTACHMENT_PATH);
    }
    public static String getUploadCetificateImgHost() {
    	return getImgHost() + getUploadPath() + getHost(UPLOAD_CETIFICATE_PATH);
    }

    public static String getUploadGroupUrl() {
        return getUploadPath() + getHost(UPLOAD_GROUP_PATH);
    }

    public static String getUploadAttachmentUrl() {
        return getUploadPath() + getHost(UPLOAD_ATTACHMENT_PATH);
    }

    public static String getUploadRootPath() {
        return getHost(UPLOAD_ROOT_PATH);
    }

    public static String getKey() {
        return getHost(UUD_KEY);
    }

    public static String getSecret() {
        return getHost(UUD_SECRET);
    }

    public static String getRongCloudMessagePath() {
        return getHost(RONG_CLOUD_MESSAGE);
    }

    public static String getImgHost() {
        return getHost(IMG_HOST);
    }

    public static void main(String[] args) throws IOException {
//		System.out.println(ImagePathUtil.getUploadRootPath());
//        System.out.println(ImagePathUtil.getUploadGroupPath());
        System.out.println(ImagePathUtil.getUploadCetificateImgHost());
    }

}
