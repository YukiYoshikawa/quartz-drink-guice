package yuki.quartz.ex.guice;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Scheduler Holder
 */
@Singleton
final class QuartzImpl implements QuartzEx {

	private final Scheduler scheduler;

	@Inject
    QuartzImpl(SchedulerFactory factory, GuiceJobFactory jobFactory) throws SchedulerException {
		this.scheduler = factory.getScheduler();
		this.scheduler.setJobFactory(jobFactory);
		this.scheduler.start();
	}

	/**
	 * return Scheduler
	 *
	 * @return Scheduler
	 */
	public final Scheduler getScheduler() {
		return this.scheduler;
	}
}