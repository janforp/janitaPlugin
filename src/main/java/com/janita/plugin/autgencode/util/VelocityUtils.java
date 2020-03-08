package com.janita.plugin.autgencode.util;

import com.janita.plugin.autgencode.bean.ColumnEntity;
import com.janita.plugin.autgencode.bean.GenTemp;
import com.janita.plugin.autgencode.bean.TableEntity;
import com.janita.plugin.autgencode.component.AutoCodeConfigComponent;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：
 *
 * @author zhucj
 * @since 2020/3/8 - 下午9:04
 */
public class VelocityUtils {

    public static Map<String, Object> buildMapForVelocityContext(GenTemp temp) {
        com.intellij.openapi.application.Application application = com.intellij.openapi.application.ApplicationManager.getApplication();
        AutoCodeConfigComponent applicationComponent = application.getComponent(AutoCodeConfigComponent.class);
        String pre = temp.getPre();
        TableEntity tableEntity = temp.getTableEntity();
        boolean hasBigDecimal = temp.isHasBigDecimal();
        boolean hasDate = temp.isHasDate();
        String packageName = applicationComponent.getPackageName();
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

    public static GenTemp buildTableEntity(Map<String, String> table, List<Map<String, String>> columns) {
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
}
