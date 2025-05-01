package com.ruoyi.workflow.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.workflow.domain.TaskView;
import com.ruoyi.workflow.service.ITaskViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public TableDataInfo list() {
        startPage();
        List<TaskView> list = taskViewService.getByAssigneeNamePage("张三",0, 100);
        return getDataTable(list);
    }
}