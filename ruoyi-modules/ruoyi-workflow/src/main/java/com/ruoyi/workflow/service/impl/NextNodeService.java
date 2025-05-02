package com.ruoyi.workflow.service.impl;

import com.ruoyi.workflow.service.result.NextNode;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class NextNodeService {
    static final String ExclusiveGateway = "exclusiveGateway";
    static final String ParallelGateway ="parallelGateway";
    @Autowired
    private ProcessEngine processEngine;

    public void NextNodeRequest(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
    }

    public List<NextNode> getNextNodes(String taskId){
        TaskService taskService = processEngine.getTaskService();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processDefinitionId = task.getProcessDefinitionId();
        String nodeId = task.getTaskDefinitionKey();

        BpmnModelInstance modelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        return getNextNodes(modelInstance, nodeId);
    }

    private List<NextNode> getNextNodes(BpmnModelInstance modelInstance, String nodeId)   {
        List<NextNode> nodes = new ArrayList<>();
        Collection<SequenceFlow> relList = getSequenceFlow(modelInstance, nodeId);

        for (SequenceFlow rel : relList) {
            FlowNode target = rel.getTarget();
            String typeName = target.getElementType().getTypeName();
            String targetId = target.getAttributeValue("id");
            String targetName = target.getAttributeValue("name");
            NextNode node = new NextNode();
            node.setId(targetId);
            node.setName(targetName);
            node.setType(typeName);
            nodes.add(node);
            String lowTypeName = typeName.toLowerCase();
            if (lowTypeName.equals(ExclusiveGateway) || lowTypeName.equals(ParallelGateway)){
                node.setOutNodes(getNextNodes(modelInstance, targetId));
            }
        }
        return nodes;
    }

    private Collection<SequenceFlow> getSequenceFlow(BpmnModelInstance modelInstance, String sourceRef) {
        FlowNode node = (FlowNode) modelInstance.getModelElementById(sourceRef);
        return node.getOutgoing();
    }





}
