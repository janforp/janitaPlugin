package com.janita.plugin.autgencode.bean;

/**
 * 自动代码生成
 *
 * @author zhucj
 * @since 202003
 */
public class DatabaseConfigParamBean {

    private String txtDatabaseUrl;

    private String txtDatabaseUser;

    private String txtDatabasePwd;

    private String txtProjectPath;

    private String txtTableName;

    private String txtCreator;

    private String txtEmail;

    private String packageName;

    public String getTxtProjectPath() {
        return this.txtProjectPath;
    }

    public void setTxtProjectPath(String txtProjectPath) {
        this.txtProjectPath = txtProjectPath;
    }

    public String getTxtTableName() {

        return this.txtTableName;
    }

    public void setTxtTableName(String txtTableName) {

        this.txtTableName = txtTableName;
    }

    public String getTxtCreator() {

        return this.txtCreator;
    }

    public void setTxtCreator(String txtCreator) {

        this.txtCreator = txtCreator;
    }

    public String getTxtDatabaseUrl() {

        return this.txtDatabaseUrl;
    }

    public void setTxtDatabaseUrl(String txtDatabaseUrl) {

        this.txtDatabaseUrl = txtDatabaseUrl;
    }

    public String getTxtDatabaseUser() {

        return this.txtDatabaseUser;
    }

    public void setTxtDatabaseUser(String txtDatabaseUser) {

        this.txtDatabaseUser = txtDatabaseUser;
    }

    public String getTxtDatabasePwd() {

        return this.txtDatabasePwd;
    }

    public void setTxtDatabasePwd(String txtDatabasePwd) {

        this.txtDatabasePwd = txtDatabasePwd;
    }

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {

        return "" + "txtDatabaseUrl:" + this.txtDatabaseUrl + "\n"
                + "txtDatabaseUser:" + this.txtDatabaseUser + "\n"
                + "txtDatabasePwd:" + this.txtDatabasePwd + "\n"
                + "txtProjectPath:" + this.txtProjectPath + "\n"
                + "txtTableName:" + this.txtTableName + "\n"
                + "txtCreator:" + this.txtCreator + "\n"
                + "txtEmail:" + this.txtEmail + "\n"
                + "packageName:" + this.packageName + "\n";

    }
}