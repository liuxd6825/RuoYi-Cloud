package com.ruoyi.common.web.query;

public class PageQuery {
    private String filter="";
    private String fields="";
    private String sort="";
    private String groupCols="";
    private String groupKeys="";
    private String valueCols="";
    private Integer pageNum;
    private Integer pageSize;


    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGroupCols() {
        return groupCols;
    }

    public void setGroupCols(String groupCols) {
        this.groupCols = groupCols;
    }

    public String getGroupKeys() {
        return groupKeys;
    }

    public void setGroupKeys(String groupKeys) {
        this.groupKeys = groupKeys;
    }

    public String getValueCols() {
        return valueCols;
    }

    public void setValueCols(String valueCols) {
        this.valueCols = valueCols;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
