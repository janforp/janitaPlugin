package com.janita.plugin.autgencode.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：
 *
 * @author zhucj
 * @since 2020/3/8 - 下午7:31
 */
public class StatementUtils {

    /**
     * 查询表名称，表注释
     */
    private static final String SELECT_TABLE_NAME_COMMENT
            = "SELECT TABLE_NAME,table_comment COMMENT "
            + "FROM information_schema.tables "
            + "WHERE TABLE_NAME = ?";

    /**
     * 查询列信息
     */
    private static final String SELECT_COLUMN_INFO
            = "SELECT column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra "
            + "FROM information_schema.columns "
            + "WHERE table_name = '" + "%s" + "' "
            + "AND table_schema = (select database()) "
            + "ORDER BY ordinal_position";

    public static Map<String, String> getTableDescription(Connection conn, String tableName)
            throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, String> map = new HashMap<>(4);
        try {
            String sql = String.format(SELECT_TABLE_NAME_COMMENT, tableName);
            ps = conn.prepareStatement(sql);
            ps.setString(1, tableName);
            rs = ps.executeQuery();
            if (rs.next()) {
                String tableNameFromDb = rs.getString("TABLE_NAME");
                String comment = rs.getString("COMMENT");
                map.put("tableName", tableNameFromDb);
                map.put("tableComment", comment);
            }
            return map;
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Map<String, String>> queryTableColumnList(Connection conn, String tableName)
            throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = String.format(SELECT_COLUMN_INFO, tableName);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Map<String, String>> columns = new ArrayList<>();
            Map<String, String> column = null;
            while (rs.next()) {
                column = new HashMap<>(8);
                column.put("columnName", rs.getString("columnName"));
                column.put("dataType", rs.getString("dataType"));
                column.put("columnComment", rs.getString("columnComment"));
                column.put("columnKey", rs.getString("columnKey"));
                column.put("extra", rs.getString("extra"));
                columns.add(column);
            }
            return columns;
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
