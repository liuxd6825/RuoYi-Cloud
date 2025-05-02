package com.ruoyi.workflow.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.workflow.controller.requset.JumpRequest;
import com.ruoyi.workflow.domain.TaskView;
import com.ruoyi.workflow.service.IJumpService;
import com.ruoyi.workflow.service.ITaskViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workflow/jump")
public class JumpController extends BaseController {
    @Autowired
    private IJumpService jumpService;

    /**
     * 获取参数配置列表
     */
    @GetMapping("/jump")
    public void jump(@RequestBody JumpRequest req) {
        jumpService.jump(req.getProcessId(), req.getTaskId(), req.getTargetTaskDefKey(), req.getVariables(), req.getAnnotation());
    }
}