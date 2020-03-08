package com.janita.plugin.autgencode.util;

import com.janita.plugin.autgencode.bean.GenTemp;
import com.janita.plugin.autgencode.bean.TableEntity;
import com.janita.plugin.autgencode.component.AutoCodeConfigComponent;

import java.util.Date;
import java.util.HashMap;
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
}
