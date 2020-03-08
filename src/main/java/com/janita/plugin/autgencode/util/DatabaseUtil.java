package com.janita.plugin.autgencode.util;

import com.janita.plugin.autgencode.bean.DatabaseConfigParamBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 自动代码生成数据库链接
 *
 * @author zhucj
 * @since 202003
 */
public class DatabaseUtil {

    private Connection conn;

    private DatabaseConfigParamBean bean;

    public DatabaseUtil(DatabaseConfigParamBean bean) {

        this.bean = bean;
    }

    public Connection getConnection() throws Exception {
        try {
            if (this.conn != null) {
                return this.conn;
            }
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(this.bean.getTxtDatabaseUrl(), this.bean.getTxtDatabaseUser(), this.bean.getTxtDatabasePwd());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("数据库连接失败！");
            throw e;
        }
    }
}