package com.ruoyi.workflow.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.web.query.PageQuery;
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
    private DtmClient dtmClient;

    public TaskViewService() {

    }

    public PageInfo<TaskView> getByAssigneeName(String assigneeName, PageQuery pageQuery) {
        if (dtmClient != null) {
            System.out.println(" dtm client is ok \n");
        }
        List<TaskView> users = taskViewMapper.selectByAssigneeName(assigneeName, pageQuery);
        PageInfo pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

}
