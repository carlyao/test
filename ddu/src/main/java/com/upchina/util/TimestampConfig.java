package com.upchina.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author alex
 */
public class TimestampConfig {

    public static final DateTimeFormatter timestampFormat = DateTimeFormat
            .forPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter dateFormat = DateTimeFormat
            .forPattern("yyyyMMdd");

    public static final DateTimeFormatter timestampFullFormat = DateTimeFormat
            .forPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter dateFullFormat = DateTimeFormat
            .forPattern("yyyy-MM-dd");

    public static final int dayExpired = 2;

}
