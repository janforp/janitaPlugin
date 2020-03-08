package com.janita.plugin.mybatislog2sql;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.janita.plugin.mybatislog2sql.format.SqlFormatter;
import com.janita.plugin.mybatislog2sql.parse.LogParse;
import com.janita.plugin.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * mybatis日志转换为可执行大sql
 *
 * Preparing: select * from student where id = ? and name = ?
 * Parameters: 1(String), 张三(String)
 * 复制上面的日志
 * 粘贴结果：
 * select
 * *
 * from
 * student
 * where
 * id = '1'
 *
 * @author zhucj
 * @since 202003
 */
public class MybatisLog2SqlAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        CommonUtils.print("MybatisLog2SqlAction.actionPerformed");
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        String log = editor.getSelectionModel().getSelectedText();
        if (StringUtils.isEmpty(log)) {
            return;
        }
        String sql = LogParse.toSql(log);
        if (StringUtils.isEmpty(sql)) {
            return;
        }
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(SqlFormatter.format(sql));
        clipboard.setContents(stringSelection, null);
    }

    public static void main(String[] args) {
        String format = SqlFormatter.format("select * from table where a = ?");
        System.out.println(format);
    }
}
