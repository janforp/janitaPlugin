package com.janita.plugin.autgencode.form;

import com.intellij.openapi.options.Configurable;
import com.janita.plugin.autgencode.component.AutoCodeConfigComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

/**
 * 自动代码生成
 *
 * 保存数据库相关配置都form
 *
 * @author zhucj
 * @since 202003
 */
@SuppressWarnings("all")
public class AutoCodeConfigForm implements Configurable {

    /**
     * 主面板如：DIV
     */
    private JPanel rootComponent;

    /**
     * 数据库输入框标题
     */
    private JLabel databaseUrlTitle;

    /**
     * 数据库输入框
     */
    private JTextField databaseUrlInput;

    /****数据库地址****************************************************************************************/

    /**
     * 用户名输入框标题
     */
    private JLabel databaseUserTitle;

    /**
     * 用户名输入框
     */
    private JTextField databaseUserInput;

    /****用户名****************************************************************************************/

    private JLabel databasePwdTitle;

    private JTextField databasePwdInput;

    /****密码****************************************************************************************/

    private JLabel projectPathTitle;

    private JTextField projectPathInput;

    /**
     * 选择路径的按钮
     */
    private JButton projectPathSelBtn;

    /****生成路径****************************************************************************************/

    private JLabel creatorTitle;

    private JTextField creatorInput;

    /****创建人****************************************************************************************/

    private JLabel emailTitle;

    private JTextField emailInput;

    /****邮箱****************************************************************************************/

    private JLabel packageNameTitle;

    private JTextField packageNameInput;

    /****包名称****************************************************************************************/

    public AutoCodeConfigForm() {
        super();
        this.databaseUrlTitle.setLabelFor(this.databaseUrlInput);
        this.databaseUserTitle.setLabelFor(this.databaseUserInput);
        this.databasePwdTitle.setLabelFor(this.databasePwdInput);
        this.creatorTitle.setLabelFor(this.creatorInput);
        this.projectPathTitle.setLabelFor(this.projectPathInput);
        this.emailTitle.setLabelFor(this.emailInput);
        this.packageNameTitle.setLabelFor(this.packageNameInput);
        this.projectPathSelBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(1);
            int i = fileChooser.showOpenDialog(AutoCodeConfigForm.this.rootComponent);
            if (i == 0) {
                File file = fileChooser.getSelectedFile();
                AutoCodeConfigForm.this.projectPathInput.setText(file.getAbsolutePath());
            }
        });
    }

    public JComponent getRootComponent() {
        return this.rootComponent;
    }

    public void setData(AutoCodeConfigComponent data) {
        this.databaseUrlInput.setText(data.getDatabaseUrl());
        this.databaseUserInput.setText(data.getDatabaseUser());
        this.databasePwdInput.setText(data.getDatabasePwd());
        this.creatorInput.setText(data.getCreator());
        this.projectPathInput.setText(data.getProjectPath());
        this.emailInput.setText(data.getEmail());
        this.packageNameInput.setText(data.getPackageName());
    }

    public void getData(AutoCodeConfigComponent data) {
        data.setDatabaseUrl(this.databaseUrlInput.getText().trim());
        data.setDatabaseUser(this.databaseUserInput.getText().trim());
        data.setDatabasePwd(this.databasePwdInput.getText().trim());
        data.setCreator(this.creatorInput.getText().trim());
        data.setProjectPath(this.projectPathInput.getText().trim());
        data.setEmail(this.emailInput.getText().trim());
        data.setPackageName(this.packageNameInput.getText().trim());
    }

    public boolean isModified(AutoCodeConfigComponent data) {
        boolean isModiT1 = (this.databaseUrlInput.getText() != null) && (!this.databaseUrlInput.getText().equals(data.getDatabaseUrl()));
        boolean isModiT2 = (this.databaseUserInput.getText() != null) && (!this.databaseUserInput.getText().equals(data.getDatabaseUser()));
        boolean isModiT3 = (this.databasePwdInput.getText() != null) && (!this.databasePwdInput.getText().equals(data.getDatabasePwd()));
        boolean isModiT4 = (this.creatorInput.getText() != null) && (!this.creatorInput.getText().equals(data.getCreator()));
        boolean isModiT5 = (this.projectPathInput.getText() != null) && (!this.projectPathInput.getText().equals(data.getProjectPath()));
        boolean isModiT6 = (this.emailInput.getText() != null) && (!this.emailInput.getText().equals(data.getEmail()));
        boolean isModiT7 = (this.packageNameInput.getText() != null) && (!this.packageNameInput.getText().equals(data.getPackageName()));
        return (isModiT1) || (isModiT2) || (isModiT3) || (isModiT4) || (isModiT5) || (isModiT6) || (isModiT7);
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "platform-gen";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        com.intellij.openapi.application.Application application = com.intellij.openapi.application.ApplicationManager.getApplication();
        AutoCodeConfigComponent config = application.getComponent(AutoCodeConfigComponent.class);
        setData(config);
        return rootComponent;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() {
        com.intellij.openapi.application.Application application = com.intellij.openapi.application.ApplicationManager.getApplication();
        AutoCodeConfigComponent config = application.getComponent(AutoCodeConfigComponent.class);
        getData(config);
    }
}
