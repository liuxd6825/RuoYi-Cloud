package com.ruoyi.common.web.mybatis;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.web.rsql.SQLProcess;
import com.ruoyi.common.web.rsql.Process;
import com.ruoyi.common.web.query.PageQuery;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Map;


@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class})
})
public class PagingInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = handler.getBoundSql();

        // 获取分页参数（需通过ThreadLocal或参数解析传递）
        PageQuery pageQuery =getPageQuery(boundSql.getParameterObject());
        if (pageQuery!=null){
            setPageQuery(boundSql, pageQuery);
        }

        return invocation.proceed();
    }

    // 取得分页参数
    private PageQuery getPageQuery(Object param) {
        if (param instanceof Map<?,?>) {
            Map<?, ?> paramMap = (Map<?, ?>) param;
            if (paramMap.containsKey("pageQuery")){
                Object paramValue = paramMap.get("pageQuery");
                if (paramValue instanceof PageQuery) {
                    return (PageQuery) paramValue;
                }
            }
        }
        return null;
    }

    // 设置分页信息
    private void setPageQuery(BoundSql boundSql , PageQuery pageQuery) throws Exception {
        if (pageQuery == null){
            return ;
        }
        // 原始SQL
        String originalSql = boundSql.getSql();

        // 生成COUNT SQL统计总数
        String countSql = "SELECT COUNT(1) FROM (" + originalSql + ") _total";
        // 执行COUNT查询并设置total（需通过JDBC执行）

        String filter = pageQuery.getFilter();
        if (StringUtils.isNotEmpty(filter)){
            SQLProcess proc = new SQLProcess("");
            Process.parse(filter, proc);
            filter = proc.getSQL();
        }

        // 分页SQL
        String paginatedSql = StringUtils.format(" SELECT * FROM ({}) _data ", originalSql);
        if (StringUtils.isNotEmpty(filter)){
            paginatedSql = StringUtils.format("{} WHERE {} ", paginatedSql, filter);
        }

        int offset = (pageQuery.getPageNum() - 1) * pageQuery.getPageSize();
        // 修改原始SQL为分页语句（以MySQL为例）
        paginatedSql =StringUtils.format("{} LIMIT {},{} ", paginatedSql , offset, pageQuery.getPageSize());

        // 反射修改BoundSql中的SQL内容
        MetaObject metaObject = SystemMetaObject.forObject(boundSql);
        metaObject.setValue("sql", paginatedSql);
    }

}



