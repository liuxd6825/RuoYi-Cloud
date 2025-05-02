package com.ruoyi.workflow.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.workflow.domain.TaskView;

import java.util.List;

public interface ITaskViewService {
    PageInfo<TaskView> getByAssigneeName(String assigneeName , int pageNum, int pageSize);
}
