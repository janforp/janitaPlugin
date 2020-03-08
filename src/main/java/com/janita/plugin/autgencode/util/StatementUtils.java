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
 * 类说明：查询数据库
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
            + "WHERE TABLE_NAME = '" + "%s" + "' ";

    /**
     * 查询列信息
     */
    private static final String SELECT_COLUMN_INFO
            = "SELECT column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra "
            + "FROM information_schema.columns "
            + "WHERE table_name = '" + "%s" + "' "
            + "AND table_schema = (select database()) "
            + "ORDER BY ordinal_position";

    /**
     * 表名称，表注释
     *
     * @param conn 数据库链接
     * @param tableName 表名称
     * @return 表名称，表注释
     * @throws Exception 数据库异常
     */
    public static Map<String, String> getTableDescription(Connection conn, String tableName) throws Exception {
        Map<String, String> map = new HashMap<>(4);
        String sql = String.format(SELECT_TABLE_NAME_COMMENT, tableName);
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String tableNameFromDb = resultSet.getString("TABLE_NAME");
                String comment = resultSet.getString("COMMENT");
                map.put("tableName", tableNameFromDb);
                map.put("tableComment", comment);
            }
            return map;
        }
    }

    /**
     * 查询列信息
     *
     * @param conn 数据库链接
     * @param tableName 表名称
     * @return 查询列信息
     * @throws Exception 数据库异常
     */
    public static List<Map<String, String>> queryTableColumnList(Connection conn, String tableName) throws Exception {
        String sql = String.format(SELECT_COLUMN_INFO, tableName);
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Map<String, String>> columnMapList = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> columnMap = buildOneColumnMap(resultSet);
                columnMapList.add(columnMap);
            }
            return columnMapList;
        }
    }

    private static Map<String, String> buildOneColumnMap(ResultSet resultSet) throws SQLException {
        Map<String, String> columnMap = new HashMap<>(8);
        columnMap.put("columnName", resultSet.getString("columnName"));
        columnMap.put("dataType", resultSet.getString("dataType"));
        columnMap.put("columnComment", resultSet.getString("columnComment"));
        columnMap.put("columnKey", resultSet.getString("columnKey"));
        columnMap.put("extra", resultSet.getString("extra"));
        return columnMap;
    }
}
