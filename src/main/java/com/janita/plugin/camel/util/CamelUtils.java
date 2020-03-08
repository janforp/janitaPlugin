package com.janita.plugin.camel.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 类说明：
 *
 * @author zhucj
 * @since 2020/3/8 - 下午3:26
 */
public class CamelUtils {

    public static final String UNDER_LINE = "_";

    public static boolean isFirstUpper(String text) {
        if (StringUtils.isEmpty(text)) {
            return false;
        }
        return StringUtils.equals(text.substring(0, 1), text.substring(0, 1).toUpperCase());
    }

    public static boolean isAllLowerCase(String text) {
        return StringUtils.equals(text, text.toLowerCase());
    }

    public static boolean isAllUpperCase(String text) {
        return StringUtils.equals(text, text.toUpperCase());
    }

    /**
     * snake_case ----> snakeCase
     */
    public static String toCamelCase(String text) {
        StringBuilder builder = new StringBuilder();
        String[] arr = text.split(UNDER_LINE);
        Arrays.stream(arr).forEach(item -> {
            if (item.length() >= 1) {
                builder.append(item.substring(0, 1).toUpperCase()).append(item.substring(1));
            } else {
                builder.append(UNDER_LINE);
            }
        });
        return builder.toString();
    }

    /**
     * snakeCase ------> snake_case
     */
    public static String toSnakeCase(String text) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                result.append(UNDER_LINE).append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
