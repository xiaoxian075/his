package com.tf.sdk.pool;

import java.util.Map;
import java.util.concurrent.*;

import org.apache.commons.lang3.StringUtils;

/**
 * 线程池
 * @author chenjx
 * @Version 1.1.1
 * @time   2018年11月13日
 */
public class ExecutorPool {
	
//	/**
//	 * 可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
//	 */
//	private static ExecutorService cachedPool = Executors.newCachedThreadPool();
	
	/**
	 * 定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
	 */
	private static ExecutorService fixedPool = Executors.newFixedThreadPool(3);

	/**
	 * 手动创建线程池，指定等待队列长度，防止默认队列长度（Integer.Max_value）吃太多内存，导致系统不稳定。
	 * 参考资料：https://www.cnblogs.com/sunhaoyu/articles/6955923.html
     * 终止策略：当线程池满，队列也满后，使用调用者线程去执行(CallerRunsPolicy)。
	 */
	private static ExecutorService fixedPoolManual = new ThreadPoolExecutor(5, 200, 0,
			TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1024),
			new ThreadPoolExecutor.CallerRunsPolicy());


	/**
	 *  单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
	 */
	private static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
	
	/**
	 *  定长线程池，支持定时及周期性任务执行
	 */
	private static ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(5);
	private static Map<String, ScheduleTask> scheduleMap = new ConcurrentHashMap<String, ScheduleTask>();
	
	/**
	 * 线程执行
	 * @param task
	 */
	public static void execute(Runnable task) {
		if (task != null) {
			fixedPool.execute(task);
		}
	}

	/**
	 * 通过手动创建线程池执行线程
	 * @param task
	 */
	public static void executeWithManualPool(Runnable task) {
		if (task != null) {
			fixedPoolManual.execute(task);
		}
	}

	/**
	 * 单线程执行
	 * @param task
	 */
	public static void singleExecute(Runnable task) {
		if (task != null) {
			singleExecutor.execute(task);
		}
	}
	
	/**
	 * 延迟执行
	 * @param task
	 * @param delay  单位（秒）
	 */
	public static void scheduleExecute(Runnable task, long delay) {
		if (task != null && delay >= 0) {
			scheduledPool.schedule(task, delay, TimeUnit.SECONDS);
		}
	}
	
	
	
	
	/**
	 * 延迟执行
	 * @param task
	 * @param delay  单位（秒）
	 */
	public static void scheduleExecute(ScheduleTask task, long delay) {
		if (task != null && delay >= 0) {
			scheduledPool.schedule(task, delay, TimeUnit.SECONDS);
		}
	}	
	
	/**
	 * 周期性执行(时间间隔的轮询)
	 * 默认延迟3秒执行
	 * @param task
	 * @param period   单位（秒）
	 */
	public static <T extends ScheduleTask> void ScheduleWithFixedDelay(T task, long period) {
		ScheduleWithFixedDelay(task, 3, period);
	}
	
	/**
	 * 延迟并周期性执行(时间间隔的轮询)
	 * @param task
	 * @param initialDelay	单位（秒）
	 * @param period   单位（秒）
	 */
	public static <T extends ScheduleTask> void ScheduleWithFixedDelay(T task, long initialDelay, long period) {
		if (task != null && initialDelay >= 0 && period > 0) {
			ScheduledFuture<?> future = scheduledPool.scheduleWithFixedDelay(task, initialDelay, period, TimeUnit.SECONDS);
			if (future != null) {
				task.setFuture(future);
				scheduleMap.put(task.getTaskId(), task);
			}
		}
	}
	
	/**
	 * 周期性执行(固定时间的轮询)
	 * @param task
	 * @param period   单位（秒）
	 */
	public static <T extends ScheduleTask> void scheduleAtFixedRate(T task, long period) {
		scheduleAtFixedRate(task, 3, period);
	}
	
	/**
	 * 延迟并周期性执行(固定时间的轮询)
	 * @param task
	 * @param initialDelay	单位（秒）
	 * @param period   单位（秒）
	 */
	public static <T extends ScheduleTask> void scheduleAtFixedRate(T task, long initialDelay, long period) {
		if (task != null && initialDelay >= 0 && period > 0) {
			ScheduledFuture<?> future = scheduledPool.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
			if (future != null) {
				task.setFuture(future);
				scheduleMap.put(task.getTaskId(), task);
			}
		}
	}
	
	/**
	 * 停止周期性执行的任务
	 * @param taskId
	 */
	public static void scheduleRateCancle(String taskId) {
		if (StringUtils.isNotBlank(taskId)) {
			ScheduleTask task = scheduleMap.remove(taskId);
			if (task != null) {
				task.cancel();
			}
		}
	}
}
