package com.ruoyi.workflow.config;

import com.ruoyi.common.web.mybatis.PagingInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    // 配置SqlSessionFactory (自动注入数据源)
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);  // 绑定数据源

        // 设置MyBatis全局配置项
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名映射
        factoryBean.setConfiguration(configuration);

        // 添加拦截器插件（核心步骤）
        factoryBean.setPlugins(
                 // 自定义拦截器
                new PagingInterceptor()  // MyBatis-Plus插件（如分页）
        );

        // 设置类型别名包（实体类所在包）
        factoryBean.setTypeAliasesPackage("com.rouyi.workflow.domain");

        // 指定XML映射文件路径（可选，若XML与Mapper接口同目录可省略）
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml"));

        return factoryBean.getObject();
    }
}