package com.ruoyi.workflow;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * 工作流模块
 *
 * @author liuxd
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
@EnableProcessApplication
public class RuoYiWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuoYiWorkflowApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  工作流启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

}
