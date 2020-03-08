package com.janita.plugin.autgencode.util;

import com.janita.plugin.autgencode.bean.ColumnEntity;
import com.janita.plugin.autgencode.bean.TableEntity;
import com.janita.plugin.autgencode.component.AutoCodeConfigComponent;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author zhucj
 * @since 202003
 */
@SuppressWarnings("all")
public class GenUtils {

    private static GenTemp buildTableEntity(Map<String, String> table, List<Map<String, String>> columns) {
        //配置信息
        Map<String, String> jdbcTypeAndJavaTypeMap = NameUtils.getJdbcTypeAndJavaTypeMap();
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        String tablePrefix = table.get("tableName").split("_")[0];
        String pre = tablePrefix.replace("_", "").toLowerCase();

        //表名转换成Java类名
        String className = NameUtils.tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columnList = new ArrayList<>();
        boolean hasDate = false;
        boolean hasBigDecimal = false;
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));
            //列名转换成Java属性名
            String attrName = NameUtils.columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
            //列的数据类型，转换成Java类型
            String attrType = jdbcTypeAndJavaTypeMap.getOrDefault(columnEntity.getDataType().split("\\(")[0], "unknowType");
            columnEntity.setAttrType(attrType);
            if ("Date".equals(attrType)) {
                hasDate = true;
            }
            if ("BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if (("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null)) {
                tableEntity.setPk(columnEntity);
            }
            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);

        GenTemp temp = new GenTemp();
        temp.setTableEntity(tableEntity);
        temp.setHasBigDecimal(hasBigDecimal);
        temp.setHasDate(hasDate);
        temp.setPre(pre);
        return temp;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns, ZipOutputStream zipOutputStream) {
        final String packageName = "com.platform";
        //配置信息
        GenTemp genTemp = buildTableEntity(table, columns);
        TableEntity tableEntity = genTemp.getTableEntity();
        String pre = genTemp.getPre();
        //若没主键
        if (tableEntity.getPk() == null) {
            //设置columnName为id的为主键
            boolean flag = true;
            for (ColumnEntity columnEntity : tableEntity.getColumns()) {
                if ("id".equals(columnEntity.getAttrname())) {
                    tableEntity.setPk(columnEntity);
                    flag = false;
                    break;
                }
            }
            //若无id字段则第一个字段为主键
            if (flag) {
                tableEntity.setPk(tableEntity.getColumns().get(0));
            }
        }
        //初始化参数
        Properties properties = new Properties();
        try {
            //安装插件后 从jar文件中加载模板文件
            //设置jar包所在的位置
            String sysRoot = GenUtils.class.getResource("").getPath().split("!/com/platform")[0];
            //设置velocity资源加载方式为jar
            properties.setProperty("resource.loader", "jar");
            //设置velocity资源加载方式为jar时的处理类
            properties.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
            properties.setProperty("jar.resource.loader.path", "jar:" + sysRoot);
        } catch (Exception e) {
            // 从类路径加载模板文件
            String filePath = GenUtils.class.getResource("/").getPath();
            properties.setProperty("file.resource.loader.path", filePath);
        }
        properties.put("input.encoding", "UTF-8");
        properties.put("output.encoding", "UTF-8");
        properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
        Velocity.init(properties);
        Map<String, Object> map = buildMap(genTemp, packageName);
        VelocityContext context = new VelocityContext(map);
        //获取模板列表
        List<String> templates = NameUtils.getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter stringWriter = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, stringWriter);
            try {
                //添加到zip
                String fileName = NameUtils.getAllPathFileName(template, tableEntity.getClassName(), packageName, pre);
                fileName = (fileName == null ? "" : fileName);
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                IOUtils.write(stringWriter.toString(), zipOutputStream, "UTF-8");
                stringWriter.close();
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Map<String, Object> buildMap(GenTemp temp, String packageName) {
        com.intellij.openapi.application.Application application = com.intellij.openapi.application.ApplicationManager.getApplication();
        AutoCodeConfigComponent applicationComponent = application.getComponent(AutoCodeConfigComponent.class);
        String pre = temp.getPre();
        TableEntity tableEntity = temp.getTableEntity();
        boolean hasBigDecimal = temp.isHasBigDecimal();
        boolean hasDate = temp.isHasDate();
        //封装模板数据
        Map<String, Object> map = new HashMap<>(32);
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("package", packageName);
        map.put("author", applicationComponent.getCreator());
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("hasDate", hasDate);
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("pre", pre);
        return map;
    }

    public static void main(String[] args) throws Exception {
        Properties pro = new Properties();
        pro.load(DatabaseUtil.class.getClassLoader().getResourceAsStream("velocity.properties"));
        Velocity.init(pro);
        Template template = Velocity.getTemplate("template/Dao.java.vm", "UTF-8");
        System.out.println(template.getData());
    }
}
