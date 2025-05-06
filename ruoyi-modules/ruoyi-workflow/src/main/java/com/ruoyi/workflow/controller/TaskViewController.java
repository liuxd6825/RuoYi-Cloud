package com.ruoyi.workflow.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.web.query.PageQuery;
import com.ruoyi.common.web.query.QueryFactory;
import com.ruoyi.workflow.domain.TaskView;
import com.ruoyi.workflow.service.ITaskViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数配置 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/taskView")
public class TaskViewController extends BaseController {
    @Autowired
    private ITaskViewService taskViewService;

    /**
     * 获取参数配置列表
     */
    @GetMapping("/list")
    public PageInfo<TaskView> list() throws Exception {
        PageQuery pageQuery = QueryFactory.newPageQuery();
        PageInfo<TaskView> list = taskViewService.getByAssigneeName("张三", pageQuery);
        return  list;
    }
}