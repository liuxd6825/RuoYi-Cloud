package com.ruoyi.workflow.service.impl;

import com.ruoyi.workflow.domain.TaskView;
import com.ruoyi.workflow.mapper.TaskViewMapper;
import com.ruoyi.workflow.service.ITaskViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.dtm.client.DtmClient;

import java.util.List;

@Service
public class TaskViewService implements ITaskViewService {

    @Autowired
    private TaskViewMapper taskViewMapper;
    @Autowired
    DtmClient dtmClient;

    public TaskViewService() {

    }

    public List<TaskView> getByAssigneeNamePage(String assigneeName, int pageNum, int pageSize) {
        if (dtmClient != null) {
            System.out.println(" dtm client is ok \n");
        }
        int offset = (pageNum - 1) * pageSize;
        List<TaskView> users = taskViewMapper.selectByAssigneeNamePage(assigneeName, pageSize, offset);
        return users;
    }

}
