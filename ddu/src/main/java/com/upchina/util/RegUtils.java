package com.upchina.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015-09-01.
 */
public class RegUtils {
    public static String getMatcher(String str,String pattern){
        Pattern pt = Pattern.compile(pattern);
        Matcher m = pt.matcher(str);
        String res="";
        if (m.find()) {// 启动开始匹配
            res=m.group(1);
        }
        return res;
    }

    public static List<String> getMatcherGroups(String str,String pattern){
        Pattern pt = Pattern.compile(pattern);
        Matcher m = pt.matcher(str);
        List<String> list=new ArrayList<String>();
        String res="";
        while (m.find()) {// 启动开始匹配
            res=m.group(1);
            list.add(res);
        }
        return list;
    }

    public static String replace(String str,String pattern,String rep) {
        //将字符串中的.替换成_，因为.是特殊字符，所以要用\.表达，又因为\是特殊字符，所以要用\\.来表达.
        str = str.replaceAll(pattern, rep);
        return str;
    }
}
