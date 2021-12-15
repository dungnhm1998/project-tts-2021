package com.app.tts.main.quartz;

import org.apache.commons.csv.CSVRecord;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.app.tts.services.OrderService.insertOrder;

public class QuartzJob2 /*implements Job*/ extends QuartzJobBean {
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("------------QuartzJob2 - CreateOrder---------------");
//        insertOrder2();
//        System.out.println("--------------------------------------");
//        System.out.println("--------------------------------------");
//    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("------------QuartzJob2 - c2 - CreateOrder---------------");
//        insertOrder2();
//        System.out.println("--------------------------------------");
//        System.out.println("--------------------------------------");
    }

    public void insertOrder2(){
        int countQuartz = ReadFile.count2;
        List<CSVRecord> listQuartz = ReadFile.listData;

        if(countQuartz < listQuartz.size()){
            CSVRecord csvRecord = listQuartz.get(countQuartz);
            ReadFile.count2 += 2;

            try {
                Random rand = new Random();
                String orderId = String.valueOf(rand.nextInt(1000000000));
                String source = csvRecord.get(0);
                String currency = csvRecord.get(1);
                String note = csvRecord.get(2);
                String shippingId = csvRecord.get(3);
                String storeId = "1";
                String referenceId = "1";
                String state = "1";
                String shippingMethod = "1";
                String extraFee = "1";
                String taxAmount = "1";
                String iossNumber = "1";
                int addrVerified = 0;
                String addrVerifiedNote = "1";

                List<Map> orderResultList = insertOrder(orderId, source, currency, note,
                        storeId, referenceId, state, shippingMethod,
                        shippingId, extraFee, taxAmount, iossNumber,
                        addrVerified, addrVerifiedNote);
                System.out.println("------------insert-------------");
                System.out.println(orderResultList);
                System.out.println("-------------------------------");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
