package com.app.tts.main.siupham;

import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.logging.Logger;

public class JobA implements Job {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("*******************loading---->>");
        System.out.println(readOneLine());
    }

    public Order readOneLine()  {
        Order line = null;
        int countQuartz = quzt.count;
        List<Order> listQuartz = quzt.orders;
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            quzt.count++;
        }
        return line;
    }

    private static final Logger LOGGER = Logger.getLogger(JobA.class.getName());

}
