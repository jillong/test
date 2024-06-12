package org.example.scheduler;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.example.thread.HeartbeatThread;
import org.example.utils.AnalysisPropertiesUtil;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HeartbeatTaskScheduler {

    private HeartbeatTaskScheduler() {

    }

    public static void run() {

        ScheduledThreadPoolExecutor scheduledHeartbeat = new ScheduledThreadPoolExecutor(
                10,
                new BasicThreadFactory.Builder().daemon(true).namingPattern("eye-thread-").build(),
                new ThreadPoolExecutor.AbortPolicy());
        int heartbeat = AnalysisPropertiesUtil.getMonitorProperties().getHeartbeat();
        scheduledHeartbeat.scheduleAtFixedRate(new HeartbeatThread(), 10, heartbeat, TimeUnit.SECONDS);

    }
}
