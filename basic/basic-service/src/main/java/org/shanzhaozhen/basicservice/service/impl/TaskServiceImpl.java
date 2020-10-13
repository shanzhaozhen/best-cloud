package org.shanzhaozhen.basicservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.basiccommon.converter.DynamicScheduledTaskConverter;
import org.shanzhaozhen.basiccommon.domain.sys.DynamicScheduledTaskDO;
import org.shanzhaozhen.basiccommon.dto.DynamicScheduledTaskDTO;
import org.shanzhaozhen.basiccommon.dto.MethodInfo;
import org.shanzhaozhen.basiccommon.form.BaseSearchForm;
import org.shanzhaozhen.basiccommon.utils.CustomBeanUtils;
import org.shanzhaozhen.basiccommon.utils.CustomClassUtils;
import org.shanzhaozhen.basiccommon.utils.SpringContextUtils;
import org.shanzhaozhen.basicrepo.mapper.DynamicScheduledTaskMapper;
import org.shanzhaozhen.basicservice.schedule.CronTaskRegistrar;
import org.shanzhaozhen.basicservice.schedule.SchedulingRunnable;
import org.shanzhaozhen.basicservice.service.TaskService;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final CronTaskRegistrar cronTaskRegistrar;

    private final DynamicScheduledTaskMapper dynamicScheduledTaskMapper;

    @Override
    public Page<DynamicScheduledTaskDTO> getTaskPage(BaseSearchForm<DynamicScheduledTaskDTO> baseSearchForm) {
        return dynamicScheduledTaskMapper.getDynamicScheduledTaskPage(baseSearchForm.getPage(), baseSearchForm.getKeyword());
    }

    @Override
    public List<DynamicScheduledTaskDTO> getAllTask() {
        return dynamicScheduledTaskMapper.getAllDynamicScheduledTask();
    }

    @Override
    public DynamicScheduledTaskDTO getTaskById(Long dynamicScheduledTaskId) {
        DynamicScheduledTaskDTO dynamicScheduledTaskDTO = dynamicScheduledTaskMapper.getDynamicScheduledTaskById(dynamicScheduledTaskId);
        Assert.notNull(dynamicScheduledTaskDTO, "获取失败：没有找到该定时任务或已被删除");
        return dynamicScheduledTaskDTO;
    }

    @Override
    @Transactional
    public Long addTask(DynamicScheduledTaskDTO dynamicScheduledTaskDTO) {
        Assert.isTrue(CronSequenceGenerator.isValidExpression(dynamicScheduledTaskDTO.getCron()), "cron表达式不正确");
        DynamicScheduledTaskDO dynamicScheduledTaskDO = DynamicScheduledTaskConverter.toDO(dynamicScheduledTaskDTO);
        this.validateTask(dynamicScheduledTaskDTO);
        dynamicScheduledTaskMapper.insert(dynamicScheduledTaskDO);
        if (dynamicScheduledTaskDO.getOpen()) {
            this.startTask(dynamicScheduledTaskDTO);
        }
        return dynamicScheduledTaskDO.getId();
    }

    @Override
    @Transactional
    public Long updateTask(DynamicScheduledTaskDTO dynamicScheduledTaskDTO) {
        Assert.notNull(dynamicScheduledTaskDTO.getId(), "定时任务id不能为空");
        DynamicScheduledTaskDO dynamicScheduledTaskDO = dynamicScheduledTaskMapper.selectById(dynamicScheduledTaskDTO.getId());
        Assert.notNull(dynamicScheduledTaskDO, "更新失败：没有找到该定时任务或已被删除");
        this.validateTask(dynamicScheduledTaskDTO);
        CustomBeanUtils.copyPropertiesExcludeMeta(dynamicScheduledTaskDTO, dynamicScheduledTaskDO);
        dynamicScheduledTaskMapper.updateById(dynamicScheduledTaskDO);

        if (dynamicScheduledTaskDO.getOpen()) {
            this.startTask(dynamicScheduledTaskDTO);
        } else {
            this.stopTask(dynamicScheduledTaskDO.getId());
        }
        return dynamicScheduledTaskDO.getId();
    }

    @Override
    public void validateTask(DynamicScheduledTaskDTO dynamicScheduledTaskDTO) {
        Assert.isTrue(CronSequenceGenerator.isValidExpression(dynamicScheduledTaskDTO.getCron()), "cron表达式不正确");
        Object object = SpringContextUtils.getBean(dynamicScheduledTaskDTO.getBeanName());
        Assert.notNull(object, "Bean不存在");
        MethodInfo methodInfo = JSON.parseObject(dynamicScheduledTaskDTO.getMethodInfo(), MethodInfo.class);
        Assert.isTrue(ClassUtils.hasMethod(object.getClass(), methodInfo.getMethodName(), methodInfo.getParamTypes()), "该方法不存在");
        // 验证参数是否能正常转换，没报错则正常
        CustomClassUtils.methodParamInfoToParams(dynamicScheduledTaskDTO.getParamInfo());
        Assert.notNull(methodInfo, "没有有效的方法信息");
    }

    @Override
    @Transactional
    public Long deleteTask(Long dynamicScheduledTaskId) {
        DynamicScheduledTaskDTO dynamicScheduledTaskDTO = this.getTaskById(dynamicScheduledTaskId);
        Assert.notNull(dynamicScheduledTaskDTO, "删除失败：没有找到该定时任务或已被删除");
        dynamicScheduledTaskMapper.deleteById(dynamicScheduledTaskId);
        stopTask(dynamicScheduledTaskId);
        return dynamicScheduledTaskId;
    }

    @Override
    public Object runTask(Long taskId) {
        DynamicScheduledTaskDTO dynamicScheduledTaskDTO = this.getTaskById(taskId);
        Assert.notNull(dynamicScheduledTaskDTO, "运行失败：没有找到该定时任务或已被删除");
        return this.runTask(dynamicScheduledTaskDTO);
    }

    @Override
    public Object runTask(@NotNull DynamicScheduledTaskDTO dynamicScheduledTaskDTO) {
        try {
            MethodInfo methodInfo = JSON.parseObject(dynamicScheduledTaskDTO.getMethodInfo(), MethodInfo.class);
            Object[] params = CustomClassUtils.methodParamInfoToParams(dynamicScheduledTaskDTO.getParamInfo());
            return CustomClassUtils.executeMethod(dynamicScheduledTaskDTO.getBeanName(), methodInfo.getMethodName(), methodInfo.getParamTypes(), params);
        } catch (Exception e) {
            log.error("定时任务执行异常 - bean：{}，方法：{}，参数：{}", dynamicScheduledTaskDTO.getBeanName(), dynamicScheduledTaskDTO.getMethodInfo(), dynamicScheduledTaskDTO.getParamInfo(), e);
            throw new IllegalArgumentException("定时任务执行异常");
        }
    }

    @Override
    @Transactional
    public Long startTask(Long taskId) {
        DynamicScheduledTaskDTO dynamicScheduledTaskDTO = this.getTaskById(taskId);
        Assert.notNull(dynamicScheduledTaskDTO, "开始失败：没有找到该定时任务或已被删除");
        return this.startTask(dynamicScheduledTaskDTO);
    }

    @Override
    @Transactional
    public Long startTask(@NotNull DynamicScheduledTaskDTO dynamicScheduledTaskDTO) {
        MethodInfo methodInfo = JSON.parseObject(dynamicScheduledTaskDTO.getMethodInfo(), MethodInfo.class);
        Object[] params = CustomClassUtils.methodParamInfoToParams(dynamicScheduledTaskDTO.getParamInfo());
        SchedulingRunnable task = new SchedulingRunnable(dynamicScheduledTaskDTO.getBeanName(), methodInfo.getMethodName(), methodInfo.getParamTypes(),params);
        cronTaskRegistrar.addCronTask(dynamicScheduledTaskDTO.getId(), task, dynamicScheduledTaskDTO.getCron());
        if (!dynamicScheduledTaskDTO.getOpen()) {
            DynamicScheduledTaskDO dynamicScheduledTaskDO = dynamicScheduledTaskMapper.selectById(dynamicScheduledTaskDTO.getId());
            Assert.notNull(dynamicScheduledTaskDO, "开启失败：没有找到该定时任务或已被删除");
            dynamicScheduledTaskDO.setOpen(true);
            dynamicScheduledTaskMapper.updateById(dynamicScheduledTaskDO);
        }
        return dynamicScheduledTaskDTO.getId();
    }

    @Override
    public Long stopTask(@NotNull Long taskId) {
        cronTaskRegistrar.removeCronTask(taskId);
        DynamicScheduledTaskDO dynamicScheduledTaskDO = dynamicScheduledTaskMapper.selectById(taskId);
        Assert.notNull(dynamicScheduledTaskDO, "停止失败：没有找到该定时任务或已被删除");
        if (dynamicScheduledTaskDO.getOpen()) {
            dynamicScheduledTaskDO.setOpen(false);
            dynamicScheduledTaskMapper.updateById(dynamicScheduledTaskDO);
        }
        return taskId;
    }

    @Override
    public void initTask() {
        log.info("===开始初始化定时任务===");
        List<DynamicScheduledTaskDTO> list = this.getAllTask();
        for (DynamicScheduledTaskDTO dynamicScheduledTaskDTO : list) {
            if (dynamicScheduledTaskDTO.getOpen()) {
                this.startTask(dynamicScheduledTaskDTO);
            }
        }
        log.info("===定时任务初始化完成===");
    }
}
