package com.app.tts.main;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class mainQuazt {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("huongdanjavaTrigger", "group")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(10)
                                .repeatForever()
                )

                .build();

        JobDetail job = JobBuilder.newJob(JobA.class)
                .withIdentity("huongdanjavaJob", "group")
                .build();


        Scheduler scheduler = new StdSchedulerFactory().getScheduler();


        scheduler.start();
        scheduler.scheduleJob(job, trigger);
        System.out.println("=======================");
        Thread.sleep(30000);
        scheduler.shutdown();
    }
}
