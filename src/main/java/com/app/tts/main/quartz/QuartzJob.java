package com.app.tts.main.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class QuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Huong dan Quartz Job--------------------");
        System.out.println(readOneLine());

    }

    public String readOneLine()  {
        String line = null;
        int countQuartz = ReadFile.count;
        List<String> listQuartz = ReadFile.listData;
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            ReadFile.count++;
        }
        return line;
    }
}
