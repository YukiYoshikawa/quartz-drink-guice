package net.yyuki.quartz.ex.guice;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Scheduler Holder
 */
@Singleton
final class QuartzExImpl implements QuartzEx {

	private final Scheduler scheduler;

	@Inject
    QuartzExImpl(SchedulerFactory factory, GuiceJobFactory jobFactory) throws SchedulerException {
        this.scheduler = factory.getScheduler();
    	this.scheduler.setJobFactory(jobFactory);
        this.scheduler.start();
	}

	/**
	 * return Scheduler
	 *
	 * @return Scheduler
	 */
	@Override
	public final Scheduler getScheduler() {
	    return this.scheduler;
	}
}