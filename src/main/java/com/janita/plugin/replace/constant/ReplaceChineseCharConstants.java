package com.janita.plugin.replace.constant;

/**
 * 类说明：
 *
 * @author zhucj
 * @since 2020/3/8 - 下午12:07
 */
public class ReplaceChineseCharConstants {

    public static final String CACHE_KEY = "replaceCharCacheKey";

    public static final String DEFAULT_CACHE_VALUE = "， , 。 . ： : ； ; ！ ! ？ ? “ \" ” \" ‘ ' ’ ' 【 [ 】 ] （ ( ） ) 「 { 」 } 《 < 》 >".replace(" ", "\n");

    public static final String SEP_CHAR = "\n";

    public static final char BIAS_LINE = '/';

}
