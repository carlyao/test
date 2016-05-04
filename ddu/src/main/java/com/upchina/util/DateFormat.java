package com.upchina.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String GetDateFormat(Date date,String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
    //返回几天前，几小时前，几分钟前之类的日期格式
    public static String GetDateFormat1(Date date){
        String dataStr="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date now;
        try {
            now = new Date();
            long l=now.getTime()-date.getTime();
            long day=l/(24*60*60*1000);
            long hour=(l/(60*60*1000)-day*24);
            long min=((l/(60*1000))-day*24*60-hour*60);
            long s=(l/1000-day*24*60*60-hour*60*60-min*60);


            if(day > 0&& day<=7)
                dataStr=day+"天前";
            else if(day==0&&hour>0 )
                dataStr=hour+"小时前";
            else if(hour==0&&min > 0 )
                dataStr=min+"分钟前";
            else if(min==0&&s>0)
                dataStr=s+"秒前";
            else if(l<1000){
                dataStr=1+"秒前";
            }
            else{
                dataStr=df.format(date);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataStr;
    }


}
