package com.ruoyi.workflow.controller;

import com.ruoyi.workflow.controller.requset.JumpRequest;
import com.ruoyi.workflow.controller.requset.NextNodeRequest;
import com.ruoyi.workflow.service.IJumpService;
import com.ruoyi.workflow.service.INextNodeService;
import com.ruoyi.workflow.service.result.NextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class NextNodeController {
    @Autowired
    private INextNodeService service;

    /**
     * 获取参数配置列表
     */
    @PutMapping("/jump")
    public List<NextNode> jump(@RequestBody NextNodeRequest req) {
        return service.getNextNodes(req.getNodeId());
    }
}
