package com.upchina.vo;

import java.lang.reflect.Field;
import java.util.Map;

import com.upchina.util.TimestampConfig;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

import com.google.common.collect.Maps;

public class BaseIn {

    private String appType;

    private String appVersion;

    private String sign;

//    public <T extends BaseOutVo> T createOutVo(Class<T> voClass) {
//        return createOutVo(voClass, true);
//    }

//    public <T extends BaseOutVo> T createOutVo(Class<T> voClass, Boolean checkSign) {
//        T t = Reflections.newInstance(voClass);
//
//        UpChinaError error = validateCommonParam();
//        if (error != null) {
//            return (T) t.setError(error);
//        }
//        if (checkSign && !validateSign()) {
//            return (T) t.setError(UpChinaError.SIGN_ERROR);
//        }
//        if (error == null) {
//            error = this.validate();
//        }
//        t.setError(error);
//        return t;
//    }

    public boolean validateSign() {
        return StringUtils.equals(calSign(this), sign);
    }

    public static String calSign(Object obj) {
        Map<String, Object> map = Maps.newTreeMap();
        try {
            fillFieldValue(map, obj.getClass(), obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return calSign(map);
    }

    public static String calSign(Object... objs) {
        Map<String, Object> map = Maps.newTreeMap();
        try {
            for (Object obj : objs) {
                fillFieldValue(map, obj.getClass(), obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return calSign(map);
    }

    public boolean validateTimestamp(String timestamp) {
        boolean result = true;
        try {
            DateTime input = DateTime.parse(timestamp, TimestampConfig.timestampFormat);
            result = Days.daysBetween(input, new DateTime()).getDays() <= TimestampConfig.dayExpired;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private static void fillFieldValue(Map<String, Object> map, Class<?> clazz, Object obj) throws IllegalArgumentException,
            IllegalAccessException {
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            fillFieldValue(map, superClass, obj);
        }
        for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            String fieldName = f.getName();
            if (fieldName.equals("sign") || fieldName.equals("serialVersionUID") || fieldName.equals("error")) {
                continue;
            }
            Object value = f.get(obj);
            if (value != null) {
                map.put(fieldName, value);
            }
        }
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}