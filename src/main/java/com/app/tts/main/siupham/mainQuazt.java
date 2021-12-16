package com.app.tts.main.siupham;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class mainQuazt {

    public static void main(String[] args) throws SchedulerException, InterruptedException, SQLException, IOException {

        quzt.convertCSVRecordToList();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("orderProductTrigger", "group")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(1)
                                .repeatForever()
                )

                .build();

        JobDetail job = JobBuilder.newJob(JobA.class)
                .withIdentity("orderProductJob", "group")
                .build();


        Scheduler scheduler = new StdSchedulerFactory().getScheduler();


        scheduler.start();
        scheduler.scheduleJob(job, trigger);
        System.out.println("************************");
//        Thread.sleep(30000);
//        scheduler.shutdown();
    }
}
