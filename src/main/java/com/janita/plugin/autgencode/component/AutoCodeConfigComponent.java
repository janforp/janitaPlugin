package com.janita.plugin.autgencode.component;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.options.Configurable;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.janita.plugin.autgencode.form.AutoCodeConfigForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 自动代码生成
 *
 * @author zhucj
 * @since 202003
 */
@SuppressWarnings("all")
@State(name = "com.janita.plugin.autgencode.component.AutoCodeConfigComponent", storages = { @com.intellij.openapi.components.Storage(file = "$APP_CONFIG$/auto-gen-code.xml") })
public class AutoCodeConfigComponent implements BaseComponent, Configurable, PersistentStateComponent<AutoCodeConfigComponent> {

    private String databaseUrl = "dev.mydhouse.com:3306/test";

    private String databaseUser = "cms";

    private String databasePwd = "1qaz@WSX";

    private String creator = "janita";

    private String projectPath;

    private String email = "804979367@qq.com";

    private String packageName = "com.janita.demo";

    private AutoCodeConfigForm form;

    @NotNull
    @Override
    public String getComponentName() {
        return "AutoCodeConfig";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (this.form == null) {
            this.form = new AutoCodeConfigForm();
        }
        return this.form.getRootComponent();
    }

    @Override
    public boolean isModified() {
        return (this.form != null) && (this.form.isModified(this));
    }

    @Override
    public void apply() {
        if (this.form != null) {
            this.form.getData(this);
        }
    }

    @Override
    public void reset() {
        if (this.form != null) {
            this.form.setData(this);
        }
    }

    @Override
    public void disposeUIResources() {
        this.form = null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "platform-gen";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUrl() {
        return this.databaseUrl;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabaseUser() {
        return this.databaseUser;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDatabasePwd() {
        return this.databasePwd;
    }

    public void setDatabasePwd(String databasePwd) {
        this.databasePwd = databasePwd;
    }

    public String getProjectPath() {
        return this.projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Nullable
    @Override
    public AutoCodeConfigComponent getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AutoCodeConfigComponent autoCodeConfigComponent) {
        XmlSerializerUtil.copyBean(autoCodeConfigComponent, this);
    }
}