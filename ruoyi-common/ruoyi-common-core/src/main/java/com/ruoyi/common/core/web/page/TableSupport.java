package com.ruoyi.common.core.web.page;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.utils.ServletUtils;

import java.util.Map;

/**
 * 表格数据处理
 * 
 * @author ruoyi
 */
public class TableSupport
{
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";


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


    /**
     * 分页参数合理化
     */
    public static final String REASONABLE = "reasonable";

    public static final String GROUP_COLS = "group-cols";

    public static final String GROUP_KEYS = "group-keys";

    public static final String VALUE_COLS = "value-cols";
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain()
    {
        boolean isPageNum = false;
        boolean isPageSize = false;
        boolean isSort= false;

        PageDomain pageDomain = new PageDomain();
        Map<String, String[]> parasMap = ServletUtils.getRequest().getParameterMap();
        for (Map.Entry<String, String[]> entry : parasMap.entrySet()){
            String key = entry.getKey();
            key = key.toLowerCase();
            switch (key) {
                case "page-num":
                    pageDomain.setPageNum(Convert.toInt(ServletUtils.getParameter(PAGE_NUM), 1));
                    isPageNum = true;
                    break;
                case "page-size":
                    pageDomain.setPageSize(Convert.toInt(ServletUtils.getParameter(PAGE_SIZE), 10));
                    isPageSize = true;
                    break;
                case "sort":
                    pageDomain.setSort(ServletUtils.getParameter(SORT));
                    isSort = true;
                    break;
            }
        }

        if (!isPageNum){
            pageDomain.setPageNum(Convert.toInt(ServletUtils.getParameter(PAGE_NUM), 1));
        }
        if (!isPageSize){
            pageDomain.setPageSize(Convert.toInt(ServletUtils.getParameter(PAGE_SIZE), 10));
        }
        if (!isSort){
            pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        }

        pageDomain.setFilter(ServletUtils.getParameter(FILTER));
        pageDomain.setFields(ServletUtils.getParameter(FIELDS));
        pageDomain.setValueCols(ServletUtils.getParameter(VALUE_COLS));
        pageDomain.setGroupKeys(ServletUtils.getParameter(GROUP_KEYS));
        pageDomain.setGroupCols(ServletUtils.getParameter(GROUP_COLS));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setReasonable(ServletUtils.getParameterToBool(REASONABLE));

        return pageDomain;
    }


    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
