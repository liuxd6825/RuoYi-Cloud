package com.ruoyi.common.web.query;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.utils.ServletUtils;

public class QueryFactory {

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "page-num";
    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "page-size";
    /**
     * 数据过滤
     */
    public static final String FILTER = "filter";
    /**
     * 字段项
     */
    public static final String FIELDS = "fields";
    /**
     * 排序
     */
    public static final String SORT = "sort";
    public static final String GROUP_COLS = "group-cols";
    public static final String GROUP_KEYS = "group-keys";
    public static final String VALUE_COLS = "value-cols";
    /**
     * 封装分页对象
     */
    public static PageQuery newPageQuery()
    {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setSort(ServletUtils.getParameter(SORT));
        pageQuery.setPageNum(Convert.toInt(ServletUtils.getParameter(PAGE_NUM), 1));
        pageQuery.setPageSize(Convert.toInt(ServletUtils.getParameter(PAGE_SIZE), 10));
        pageQuery.setFilter(ServletUtils.getParameter(FILTER));
        pageQuery.setFields(ServletUtils.getParameter(FIELDS));
        pageQuery.setValueCols(ServletUtils.getParameter(VALUE_COLS));
        pageQuery.setGroupKeys(ServletUtils.getParameter(GROUP_KEYS));
        pageQuery.setGroupCols(ServletUtils.getParameter(GROUP_COLS));
        return pageQuery;
    }

}
