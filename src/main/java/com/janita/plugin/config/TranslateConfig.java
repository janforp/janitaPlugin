package com.janita.plugin.config;

import org.apache.xmlgraphics.image.codec.util.PropertyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhucj
 */
public class TranslateConfig {

    private static Properties properties;

    public static final String TRANSLATE_URL = (String) properties.get("youao.api.url");

    static {
        try {
            load();
        } catch (IOException e) {
            //ignore
        }
    }

    static void load() throws IOException {
        InputStream resourceAsStream = PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties");
        properties = new Properties();
        properties.load(resourceAsStream);
    }

    public static void main(String[] args) {
        System.out.println(TRANSLATE_URL);
    }
}
