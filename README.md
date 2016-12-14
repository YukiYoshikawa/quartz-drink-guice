# quartz-drink-guice
Quartz + Guice(Dependency Injection)


## Getting started

Add dependency in your pom.xml or other build tool's configuration file.

```xml
<dependency>
    <groupId>net.y-yuki</groupId>
    <artifactId>quartz-drink-guice</artifactId>
    <version>0.1.1</version>
</dependency>
```

<br>

Define a job class

```java
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class MyJob1a implements org.quartz.Job {

    public MyJob1a() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(">> MyJob1a is execute start.");
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(">> MyJob1a is execute end.");
    }
}
```

```java
import com.google.inject.ImplementedBy;

@ImplementedBy(TrialQuartzImpl.class)
public interface TrialQuartz {

    void run();
}
```

```java
import net.yyuki.quartz.ex.guice.QuartzEx;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Singleton
public class TrialQuartzImpl implements TrialQuartz {

    private final QuartzEx quartz;

    @Inject
    public TrialQuartzImpl(QuartzEx quartz) {
        this.quartz = quartz;
    }

    @Override
    public void run() {
        JobDetail job1 = JobBuilder.newJob(MyJob1a.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMilliseconds(100)
                        .repeatForever())
                .build();

        Scheduler scheduler = quartz.getScheduler();
        try {
            scheduler.start();
            scheduler.scheduleJob(job1, trigger1);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
```

DI Container start

```java
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.yyuki.quartz.ex.guice.QuartzModule;

public class QuartzTrialMain {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new QuartzModule());

        TrialQuartz trialQuartz = injector.getInstance(TrialQuartz.class);

        trialQuartz.run();
    }
}
```