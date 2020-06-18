package org.shanzhaozhen.basicservice.schedule;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CronTaskRegistrar implements DisposableBean {

    private final Map<Long, CustomScheduledTask> customScheduledTasks = new ConcurrentHashMap<>();

    private final TaskScheduler taskScheduler;

    public CronTaskRegistrar(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    /**
     * 新增定时任务
     * @param taskId
     * @param task
     * @param cronExpression
     */
    public void addCronTask(Long taskId, Runnable task, String cronExpression) {
        addCronTask(taskId, new CronTask(task, cronExpression));
    }

    public void addCronTask(@NotNull Long taskId, @NotNull CronTask cronTask) {
        Assert.isTrue(CronSequenceGenerator.isValidExpression(cronTask.getExpression()), "cron表达式不正确");
        if (this.customScheduledTasks.containsKey(taskId)) {
            this.removeCronTask(taskId);
        }
        this.customScheduledTasks.put(taskId, scheduleCronTask(cronTask));
    }

    /**
     * 移除定时任务
     * @param taskId
     */
    public void removeCronTask(Long taskId) {
        CustomScheduledTask customScheduledTask = this.customScheduledTasks.remove(taskId);
        if (customScheduledTask != null) {
            customScheduledTask.cancel();
        }
    }

    public CustomScheduledTask scheduleCronTask(CronTask cronTask) {
        CustomScheduledTask customScheduledTask = new CustomScheduledTask();
        customScheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());

        return customScheduledTask;
    }

    @Override
    public void destroy() throws Exception {
        for (CustomScheduledTask task : this.customScheduledTasks.values()) {
            task.cancel();
        }
        this.customScheduledTasks.clear();
    }

}
