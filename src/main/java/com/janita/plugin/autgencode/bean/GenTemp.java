package com.janita.plugin.autgencode.bean;

/**
 * 类说明：生成表对象的时候的一个临时对象，用来保存有效字段
 *
 * @author zhucj
 * @since 2020/3/8 - 下午7:53
 */
public class GenTemp {

    private boolean hasDate = false;

    private boolean hasBigDecimal = false;

    private TableEntity tableEntity;

    private String pre;

    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    public boolean isHasBigDecimal() {
        return hasBigDecimal;
    }

    public void setHasBigDecimal(boolean hasBigDecimal) {
        this.hasBigDecimal = hasBigDecimal;
    }

    public TableEntity getTableEntity() {
        return tableEntity;
    }

    public void setTableEntity(TableEntity tableEntity) {
        this.tableEntity = tableEntity;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }
}
