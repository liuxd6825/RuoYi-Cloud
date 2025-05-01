package com.ruoyi.workflow.service;

import com.ruoyi.workflow.domain.TaskView;

import java.util.List;

public interface ITaskViewService {
    List<TaskView> getByAssigneeNamePage(String assigneeName , int offset, int pageSize);
}
