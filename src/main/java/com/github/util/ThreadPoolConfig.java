package com.github.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/** Bean配置管理 */
@EnableAsync
@Configuration
@EnableScheduling
public class ThreadPoolConfig implements AsyncConfigurer, SchedulingConfigurer {

	private static final long MONITOR_RUNNING_PERIOD = 5 * 1000L;
	private static ThreadPoolTaskScheduler taskScheduler = null;
	static {
		taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
		taskScheduler.setAwaitTerminationSeconds(60 * 60);
		taskScheduler.setThreadNamePrefix("executor-");
		taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		taskScheduler.initialize();
		taskScheduler.scheduleAtFixedRate(new ThreadPoolMonitor("taskScheduler monitor", taskScheduler.getScheduledThreadPoolExecutor()), MONITOR_RUNNING_PERIOD);
	}


	@Override
	public Executor getAsyncExecutor() {
		return taskScheduler;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		return taskScheduler;
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return taskScheduler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler);
	}

	static class ThreadPoolMonitor extends TimerTask {

		private String name;
		private ThreadPoolExecutor executor;
		private final Logger logger = LoggerFactory.getLogger(this.getClass());

		public ThreadPoolMonitor(String name, ThreadPoolExecutor executor) {
			this.name = name;
			this.executor = executor;
		}
		public void run() {
			logger.debug(String
					.format("[%s monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s, Queue.size: %d",
							this.name,
							this.executor.getPoolSize(), this.executor.getCorePoolSize(),
							this.executor.getActiveCount(), this.executor.getCompletedTaskCount(),
							this.executor.getTaskCount(), this.executor.isShutdown(),
							this.executor.isTerminated(), this.executor.getQueue().size()));
		}
	}


}

