package com.app.tts.main.siupham;

import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class JobA implements Job{

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("-----------------------------------------------");
        LOGGER.info("=> "+readOneLine());
        System.out.println("-----------------------------------------------");


    }
    // lấy từng order 1
    public Map readOneLine() {
        Map line = null;
        int countQuartz = quzt.count;
        List<Map> listQuartz = quzt.convertCSVRecordToList();
        if (countQuartz < listQuartz.size()) {
            line = listQuartz.get(countQuartz);
            quzt.count++;
        }
//        System.out.println(listQuartz);
        return line;
    }



    private static final Logger LOGGER = Logger.getLogger(JobA.class.getName());
}
