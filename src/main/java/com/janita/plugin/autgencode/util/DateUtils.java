package com.janita.plugin.autgencode.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 *
 * @author zhucj
 * @since 202003
 */
public class DateUtils {

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}
