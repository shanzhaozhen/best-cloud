package org.shanzhaozhen.basicservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.basiccommon.dto.DynamicScheduledTaskDTO;
import org.shanzhaozhen.basiccommon.form.BaseSearchForm;

import java.util.List;

public interface TaskService {

    /**
     * 获取定时任务的分页列表
     * @param baseSearchForm
     * @return
     */
    Page<DynamicScheduledTaskDTO> getTaskPage(BaseSearchForm<DynamicScheduledTaskDTO> baseSearchForm);

    /**
     * 获取所有定时任务
     * @return
     */
    List<DynamicScheduledTaskDTO> getAllTask();

    /**
     * 通过定时任务id获取
     * @param dynamicScheduledTaskId
     * @return
     */
    DynamicScheduledTaskDTO getTaskById(Long dynamicScheduledTaskId);

    /**
     * 新增定时任务
     * @param dynamicScheduledTaskDTO
     * @return
     */
    Long addTask(DynamicScheduledTaskDTO dynamicScheduledTaskDTO);

    /**
     * 修改定时任务
     * @param dynamicScheduledTaskDTO
     * @return
     */
    Long updateTask(DynamicScheduledTaskDTO dynamicScheduledTaskDTO);

    /**
     * 检查定时任务调用的方式和参数是否有误
     * @param dynamicScheduledTaskDTO
     */
    void validateTask(DynamicScheduledTaskDTO dynamicScheduledTaskDTO);

    /**
     * 删除定时任务(通过定时任务id删除)
     * @param dynamicScheduledTaskId
     */
    Long deleteTask(Long dynamicScheduledTaskId);

    /**
     * 执行定时任务（通过id查找）
     * @param taskId
     * @return
     */
    Object runTask(Long taskId);

    /**
     * 执行定时任务（通过实体执行）
     * @param dynamicScheduledTaskDTO
     * @return
     */
    Object runTask(DynamicScheduledTaskDTO dynamicScheduledTaskDTO);

    /**
     * 开始定时任务（通过id查找）
     * @param taskId
     * @return
     */
    Long startTask(Long taskId);

    /**
     * 开始定时任务（通过实体执行）
     * @param dynamicScheduledTaskDTO
     * @return
     */
    Long startTask(DynamicScheduledTaskDTO dynamicScheduledTaskDTO);

    /**
     * 停止定时任务（通过id查找）
     * @param taskId
     * @return
     */
    Long stopTask(Long taskId);

    /**
     * 初始化定时任务
     * @return
     */
    void initTask();

}
