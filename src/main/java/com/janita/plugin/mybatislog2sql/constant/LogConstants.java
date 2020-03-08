package com.janita.plugin.mybatislog2sql.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 日志常量
 *
 * @author zhucj
 * @since 20200308
 */
public class LogConstants {

    /**
     * sql语句前缀
     */
    public static final String PREFIX_SQL = "Preparing: ";

    /**
     * 参数占位符
     */
    public static final String PARAM_PLACEHOLDER = "\\?";

    /**
     * 换行符
     */
    public static final String BREAK_LINE = "\n";

    /**
     * 无sql参数前缀
     */
    public static final String PREFIX_PARAMS_WITHOUT_SPACE = "Parameters:";

    /**
     * 空格
     */
    public static final String SPACE = " ";

    /**
     * sql参数前缀
     */
    public static final String PREFIX_PARAMS = PREFIX_PARAMS_WITHOUT_SPACE + SPACE;

    /**
     * 参数值分隔符
     */
    public static final String PARAM_SEPARATOR = ", ";

    /**
     * 左括号
     */
    public static final String LEFT_BRACKET = "(";

    /**
     * 右括号
     */
    public static final String RIGHT_BRACKET = ")";

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * null值
     */
    public static final String NULL = "null";

    /**
     * 不需要加单引号的类型
     */
    public static final List<String> NON_QUOTED_TYPES = Arrays.asList("Integer", "Long", "Double", "Float", "Boolean");
}