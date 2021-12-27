package com.app.tts.server.handler.order;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Quartz implements Job {
	 
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Hello Java");
	}
 
}
