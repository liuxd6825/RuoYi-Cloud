package com.ruoyi.workflow.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.web.page.PageDomain;
import com.ruoyi.common.web.query.PageQuery;
import com.ruoyi.workflow.domain.TaskView;

import java.util.List;

public interface ITaskViewService {
    PageInfo<TaskView> getByAssigneeName(String assigneeName , PageQuery pageQuery);
}
