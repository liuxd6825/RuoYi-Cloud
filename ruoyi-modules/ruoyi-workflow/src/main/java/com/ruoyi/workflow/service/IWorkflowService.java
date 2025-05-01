package com.ruoyi.workflow.service;

import java.util.Map;

public interface IWorkflowService {
    void jump(String processId, String taskId, String targetTaskDefKey, Map<String,Object> variables, String annotation);
}
