package com.ruoyi.workflow.service.impl;

import com.ruoyi.workflow.service.IWorkflowService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.instance.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WorkflowService implements IWorkflowService {

    @Autowired
    private ProcessEngine processEngine;

    public void jump(String processId, String taskId, String targetTaskDefKey, Map<String,Object> variables, String annotation){
        Task task = this.processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = this.processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (processInstance == null) {
            throw new RuntimeException("Process instance not found "+ processId);
        }

        if(processInstance.isSuspended()){
            throw new RuntimeException("Process instance is suspended"+ processId);
        }

        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();

        // 多实例节点需要加入#multiInstanceBody
        boolean isMultiInstance = isMultiInstanceActivity((DelegateExecution)execution, task.getExecutionId());
        if (isMultiInstance) {
            targetTaskDefKey = targetTaskDefKey+"#multiInstanceBody";
        }
        this.processEngine.getRuntimeService().createProcessInstanceModification(processId)
                .cancelAllForActivity(task.getTaskDefinitionKey()) // 取消当前节点所有活动中的Task任务
                .startBeforeActivity(targetTaskDefKey) // 目标节点Id，在流程图中看，固定值（一般起一个正规的名字）
                .setVariables(variables)
                .setAnnotation("跳转:"+annotation) // 为当前实例的修改添加注释，虽然没有实际业务作用，但是推荐使用
                .execute();
    }


    /**
     * 判断一个节点是否为多实例节点
     *
     * @param execution 监听器 DelegateExecution 对象
     * @param activityId 节点Id
     * @return 是否多实例节点
     */
    public boolean isMultiInstanceActivity(DelegateExecution execution, String activityId){
        Activity activity = execution.getBpmnModelInstance().getModelElementById(activityId);
        return activity.getLoopCharacteristics() != null;
    }


}


