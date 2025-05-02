package com.ruoyi.workflow.service;

import com.ruoyi.workflow.service.result.NextNode;

import java.util.List;
import java.util.Map;

public interface INextNodeService {
    List<NextNode> getNextNodes(String taskId);
}
