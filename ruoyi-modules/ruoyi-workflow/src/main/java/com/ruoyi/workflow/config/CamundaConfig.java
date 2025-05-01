package com.ruoyi.workflow.config;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class CamundaConfig {

    @Autowired
    public DataSource dataSource;

    @Bean
    public ProcessEngine processEngine(DataSource dataSource) throws Exception {
        // Create an instance of ProcessEngineConfigurationImpl
        ProcessEngineConfigurationImpl configuration =
                (ProcessEngineConfigurationImpl) ProcessEngineConfiguration
                        .createStandaloneProcessEngineConfiguration();

        configuration.setDataSource(dataSource);

        // Set the isolation level check configuration
        //configuration.setSkipIsolationLevelCheck(false); // This will skip the isolation level check
        configuration.setDatabaseSchemaUpdate("true");

        // Use ProcessEngineFactoryBean to configure and create the engine
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(configuration);

        // Return the ProcessEngine instance
        return processEngineFactoryBean.getObject();
    }

}