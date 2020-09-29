package org.shanzhaozhen.basicservice.schedule;

import java.util.concurrent.ScheduledFuture;

public final class CustomScheduledTask {

    public volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }

}
