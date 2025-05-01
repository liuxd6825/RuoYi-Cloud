package com.ruoyi.workflow.controller.requset;

import java.util.Map;

public class JumpRequest {
    String processId;
    String taskId;
    String targetTaskDefKey;
    Map<String,Object> variables;
    String annotation;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTargetTaskDefKey() {
        return targetTaskDefKey;
    }

    public void setTargetTaskDefKey(String targetTaskDefKey) {
        this.targetTaskDefKey = targetTaskDefKey;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }


}
