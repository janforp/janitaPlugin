package com.janita.plugin.autgencode.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：
 *
 * @author zhucj
 * @since 2020/3/8 - 下午7:43
 */
public class NameUtils {

    static final String ENTITY = "Entity.java.vm";

    static final String DAO_JAVA = "Dao.java.vm";

    static final String DAO_XML = "Dao.xml.vm";

    static final String SERVICE = "Service.java.vm";

    static final String SERVICE_IMPL = "ServiceImpl.java.vm";

    static final String CONTROLLER = "Controller.java.vm";

    static final String LIST_HTML = "list.html.vm";

    static final String LIST_JS = "list.js.vm";

    static final String MENU = "menu.sql.vm";

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[] { '_' }).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getAllPathFileName(String template, String className, String packageName, String tablePrefix) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains(ENTITY)) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains(DAO_JAVA)) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains(DAO_XML)) {
            return packagePath + "dao" + File.separator + className + "Dao.xml";
        }

        if (template.contains(SERVICE)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains(SERVICE_IMPL)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(CONTROLLER)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains(LIST_HTML)) {
            return "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "page"
                    + File.separator + tablePrefix + File.separator + className.toLowerCase() + ".html";
        }

        if (template.contains(LIST_JS)) {
            return "main" + File.separator + "webapp" + File.separator + "js" + File.separator + tablePrefix + File.separator + className.toLowerCase() + ".js";
        }

        if (template.contains(MENU)) {
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }

    /**
     * 获取配置信息
     */
    public static Map<String, String> getJdbcTypeAndJavaTypeMap() {
        Map<String, String> map = new HashMap<>(32);
        map.put("char", "String");
        map.put("varchar", "String");
        map.put("tinytext", "String");
        map.put("text", "String");
        map.put("mediumtext", "String");
        map.put("longtext", "String");
        map.put("tinyint", "Integer");
        map.put("smallint", "Integer");
        map.put("mediumint", "Integer");
        map.put("int", "Integer");
        map.put("integer", "Integer");
        map.put("bigint", "Long");
        map.put("float", "Float");
        map.put("double", "Double");
        map.put("decimal", "BigDecimal");
        map.put("date", "Date");
        map.put("datetime", "Date");
        map.put("timestamp", "Date");
        return map;
    }

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("template/" + ENTITY);
        templates.add("template/" + DAO_JAVA);
        templates.add("template/" + DAO_XML);
        templates.add("template/" + SERVICE);
        templates.add("template/" + SERVICE_IMPL);
        templates.add("template/" + CONTROLLER);
        templates.add("template/" + LIST_HTML);
        templates.add("template/" + LIST_JS);
        templates.add("template/" + MENU);
        return templates;
    }
}
