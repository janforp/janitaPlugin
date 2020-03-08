package com.janita.plugin.autgencode.dialog;

import com.janita.plugin.autgencode.bean.DatabaseConfigParamBean;
import com.janita.plugin.autgencode.component.AutoCodeConfigComponent;
import com.janita.plugin.autgencode.util.DatabaseUtil;
import com.janita.plugin.autgencode.util.DateUtils;
import com.janita.plugin.autgencode.util.GenUtils;
import com.janita.plugin.autgencode.util.StatementUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 自动代码生成
 *
 * @author zhucj
 * @since 202003
 */
@SuppressWarnings("all")
public class AutoCodeDialog extends JDialog {

    private JPanel contentPane;

    private JButton buttonOk;

    private JButton buttonCancel;

    private JTextField tableName;

    public AutoCodeDialog() {
        setContentPane(this.contentPane);
        setModal(true);
        getRootPane().setDefaultButton(this.buttonOk);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setTitle("platform-gen");

        this.buttonOk.addActionListener(e -> AutoCodeDialog.this.onOk());
        this.buttonCancel.addActionListener(e -> AutoCodeDialog.this.onCancel());
        setDefaultCloseOperation(0);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                AutoCodeDialog.this.onCancel();
            }

        });
        this.contentPane.registerKeyboardAction(e -> AutoCodeDialog.this.onCancel(), KeyStroke.getKeyStroke(27, 0), 1);
    }

    private void onOk() {
        DatabaseConfigParamBean bean = buildDatabaseConfigParamBean();
        if (bean != null) {
            if (creatFile(bean)) {
                JOptionPane.showMessageDialog(getContentPane(), "代码生成执行完毕！");
                dispose();
            }
        }
    }

    private void onCancel() {
        dispose();
    }

    private DatabaseConfigParamBean buildDatabaseConfigParamBean() {
        boolean booleanValue = paramCheck();
        if (!booleanValue) {
            return null;
        }
        com.intellij.openapi.application.Application application
                = com.intellij.openapi.application.ApplicationManager.getApplication();
        AutoCodeConfigComponent config = application.getComponent(AutoCodeConfigComponent.class);

        DatabaseConfigParamBean bean = new DatabaseConfigParamBean();
        bean.setTxtDatabaseUrl(
                "jdbc:mysql://" + config.getDatabaseUrl() + "?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf-8");
        bean.setTxtDatabaseUser(config.getDatabaseUser());
        bean.setTxtDatabasePwd(config.getDatabasePwd());
        bean.setTxtCreator(config.getCreator());
        bean.setTxtProjectPath(config.getProjectPath());
        bean.setTxtEmail(config.getEmail());
        String tableNames = this.tableName.getText().trim();
        bean.setTxtTableName(tableNames);
        return bean;
    }

    private Boolean paramCheck() {
        Boolean checkResult = Boolean.TRUE;
        if ("".equals(this.tableName.getText().trim())) {
            JOptionPane.showMessageDialog(this, "请填写数据库表名！");
            checkResult = Boolean.FALSE;
        }
        return checkResult;
    }

    private boolean creatFile(DatabaseConfigParamBean bean) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        com.intellij.openapi.application.Application application = com.intellij.openapi.application.ApplicationManager.getApplication();
        AutoCodeConfigComponent config = application.getComponent(AutoCodeConfigComponent.class);
        String[] tableNames = bean.getTxtTableName().split(",");
        DatabaseUtil dbUtil = new DatabaseUtil(bean);
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        try {
            for (String tableName : tableNames) {
                Connection connection = dbUtil.getConnection();
                //查询表信息
                Map<String, String> tableNameCommentMap = StatementUtils.getTableDescription(connection, tableName);
                if (tableNameCommentMap.size() == 0) {
                    JOptionPane.showMessageDialog(this, "代码生成错误！" + tableName + "表不存在！");
                    return false;
                }
                //查询列信息
                List<Map<String, String>> columnMap = StatementUtils.queryTableColumnList(connection, tableName);
                //生成代码
                GenUtils.generatorCode(tableNameCommentMap, columnMap, zipOutputStream);
            }
            zipOutputStream.close();
            FileOutputStream out = new FileOutputStream(config.getProjectPath() + "/" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + ".zip");
            IOUtils.write(outputStream.toByteArray(), out);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "代码生成错误！" + generateMessage(e));
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 主要功能: 根据异常生成Log日志信息 注意事项:无
     *
     * @param exception 异常信息
     * @return String 日志信息
     */
    private String generateMessage(Exception exception) {
        // 记录详细日志到LOG文件
        String message = "";
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            if (stackTraceElement.toString().startsWith("com.platform")) {
                message += "类名：" + stackTraceElement.getFileName() + ";方法："
                        + stackTraceElement.getMethodName() + ";行号："
                        + stackTraceElement.getLineNumber() + ";异常信息:"
                        + exception.getMessage();
                break;
            }
            if (stackTraceElement.toString().startsWith("org.springframework.web.method.annotation")) {
                message += "类名：" + stackTraceElement.getFileName() + ";方法："
                        + stackTraceElement.getMethodName() + ";行号："
                        + stackTraceElement.getLineNumber() + ";异常信息:"
                        + exception.getMessage();
                break;
            }
        }
        exception.printStackTrace();
        return message;
    }

    public static void main(String[] args) {
        AutoCodeDialog dialog = new AutoCodeDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}