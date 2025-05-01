package com.ruoyi.workflow.service.impl;

import com.ruoyi.workflow.domain.TaskView;
import com.ruoyi.workflow.mapper.TaskViewMapper;
import com.ruoyi.workflow.service.ITaskViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskViewService implements ITaskViewService {

    @Autowired
    private TaskViewMapper taskViewMapper;

    public TaskViewService() {

    }

    public List<TaskView> getByAssigneeNamePage(String assigneeName , int pageNum, int pageSize) {
         int offset = (pageNum - 1) * pageSize;
         List<TaskView> users = taskViewMapper.selectByAssigneeNamePage(assigneeName, pageSize, offset);
        return users;
    }

}
