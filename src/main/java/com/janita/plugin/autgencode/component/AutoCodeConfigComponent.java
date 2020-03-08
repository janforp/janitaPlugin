package com.janita.plugin.autgencode.component;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.janita.plugin.autgencode.form.AutoCodeConfigForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author 李鹏军
 */
@State(name = "com.platform.gen.component.AutoCodeConfigComponent", storages = {@com.intellij.openapi.components.Storage(file = "$APP_CONFIG$/platform-gen.xml")})
public class AutoCodeConfigComponent implements BaseComponent, Configurable, PersistentStateComponent<AutoCodeConfigComponent> {
    public String databaseUrl = "localhost:3306/platform";
    public String databaseUser = "root";
    public String databasePwd = "123456";
    public String creator = "李鹏军";
    public String projectPath;
    public String email = "939961241@qq.com";
    private AutoCodeConfigForm form;

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        String tmp20 = "AutoCodeConfig";
        if (tmp20 == null) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[]{"com/platform/gen/component/AutoCodeConfigComponent", "getComponentName"}));
        }
        return tmp20;
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
    public void apply() throws ConfigurationException {
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

    @Nullable
    @Override
    public AutoCodeConfigComponent getState() {
        return this;
    }

    @Override
    public void loadState(AutoCodeConfigComponent autoCodeConfigComponent) {
        XmlSerializerUtil.copyBean(autoCodeConfigComponent, this);
    }
}