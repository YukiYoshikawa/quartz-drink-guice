package yuki.quartz.ex.guice;

import org.quartz.Scheduler;

/**
 * Scheduler Holder
 */
public interface QuartzEx {

    /**
     * return Scheduler
	 *
     * @return Scheduler
     */
    Scheduler getScheduler();
}