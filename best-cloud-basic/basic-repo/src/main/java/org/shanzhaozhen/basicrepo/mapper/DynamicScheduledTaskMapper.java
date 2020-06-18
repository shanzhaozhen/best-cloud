package org.shanzhaozhen.basicrepo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.basiccommon.domain.sys.DynamicScheduledTaskDO;
import org.shanzhaozhen.basiccommon.dto.DynamicScheduledTaskDTO;

import java.util.List;

public interface DynamicScheduledTaskMapper extends BaseMapper<DynamicScheduledTaskDO> {

    @Select("select id, name, cron, bean_name, method_info, param_info, open, description, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_dynamic_scheduled_task " +
            "where name like concat ('%', #{keyword}, '%') or description like concat ('%', #{keyword}, '%')")
    Page<DynamicScheduledTaskDTO> getDynamicScheduledTaskPage(Page<DynamicScheduledTaskDTO> page, @Param("keyword") String keyword);

    @Select("select id, name, cron, bean_name, method_info, param_info, open, description, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_dynamic_scheduled_task " +
            "where id = #{dynamicScheduledTaskId} ")
    DynamicScheduledTaskDTO getDynamicScheduledTaskById(@Param("dynamicScheduledTaskId") Long dynamicScheduledTaskId);

    @Select("select id, name, cron, bean_name, method_info, param_info, open, description, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_dynamic_scheduled_task")
    List<DynamicScheduledTaskDTO> getAllDynamicScheduledTask();
}
