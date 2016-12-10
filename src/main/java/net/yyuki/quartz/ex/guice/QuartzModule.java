package net.yyuki.quartz.ex.guice;

import com.google.inject.AbstractModule;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Module for making settings for QuartzEx
 */
public final class QuartzModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SchedulerFactory.class).toInstance(new StdSchedulerFactory());
        bind(GuiceJobFactory.class);
        bind(QuartzEx.class).to(QuartzExImpl.class);
    }
}