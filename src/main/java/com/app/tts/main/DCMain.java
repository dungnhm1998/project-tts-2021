/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tts.main;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author hungdt
 */
public class DCMain {

	public static ApplicationContext appContext;

	public DCMain() {
	}

	public static void main(String[] args) throws Exception {
//		appContext = new ClassPathXmlApplicationContext("app-context.xml");
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
