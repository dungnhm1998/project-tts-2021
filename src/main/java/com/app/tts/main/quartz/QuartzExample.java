package com.app.tts.main.quartz;

import com.app.tts.services.BeanDrive;
import com.app.tts.services.DriveFile;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class QuartzExample {
    public static ApplicationContext appContext;

    public static void main(String[] args) throws SchedulerException {
        appContext = new ClassPathXmlApplicationContext("app-context.xml");

//        ReadFile.readFile();
//
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("huongdanjavaTrigger", "group")
//                .withSchedule(
//                        SimpleScheduleBuilder.simpleSchedule()
//                                .withIntervalInSeconds(5)
//                                .repeatForever()
//                )
//                .build();
//
//        JobDetail job = JobBuilder.newJob(QuartzJob.class)
//                .withIdentity("huongdanjavaJob", "group")
//                .build();
//
//
//        Trigger trigger2 = TriggerBuilder.newTrigger()
//                .withIdentity("huongdanjavaTrigger2", "group1")
//                .withSchedule(
//                        SimpleScheduleBuilder.simpleSchedule()
//                                .withIntervalInSeconds(10)
//                                .repeatForever()
//                )
//                .build();
//
//        JobDetail job2 = JobBuilder.newJob(QuartzJob2.class)
//                .withIdentity("huongdanjavaJob2", "group1")
//                .build();
//
//        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//        scheduler.start();
//        scheduler.scheduleJob(job, trigger);
//        scheduler.scheduleJob(job2, trigger2);

    }
}
