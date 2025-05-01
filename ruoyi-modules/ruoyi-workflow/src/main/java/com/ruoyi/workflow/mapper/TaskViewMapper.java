package com.ruoyi.workflow.mapper;

import com.ruoyi.common.core.web.page.PageDomain;
import com.ruoyi.workflow.domain.TaskView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskViewMapper {
    List<TaskView> selectByAssigneeNamePage(@Param("assigneeName") String assigneeName, @Param("offset") int offset,  @Param("pageSize") int pageSize);
}


