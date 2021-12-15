package com.app.tts.main.quartz;

import org.apache.commons.csv.CSVRecord;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.FileNotFoundException;
import java.util.List;

public class QuartzJob /*implements Job*/ extends QuartzJobBean {
//c1
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("--------------QuartzJob - ReadFile---------------");
//        System.out.println(readOneLine());
//        System.out.println("-------------------------------------");
//
//    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("--------------QuartzJob - c1 - ReadFile---------------");
        //c3
        try {
            OrderModel orderModel = readOneRecord();
            if(orderModel != null) {
                System.out.println("order = " + orderModel.getOrder() + " ; "
                        + "ref = " + orderModel.getRef());
            }else{
                System.out.println("------------------------order = NULL----------------------------------");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

// c2
//        CSVRecord colum = ReadFile.listData.get(0);
//        String order = colum.get(0);
//        String ref = colum.get(1);
//        String createDate = colum.get(2);
//        String paymentDate = colum.get(3);
//        String productName = colum.get(4);
//        String customers = colum.get(5);
//        String quantity = colum.get(6);
//        String amount = colum.get(7);
//        String shippingMethod = colum.get(8);
//        String state = colum.get(9);
//        String fullFillState = colum.get(10);
//        String tracking = colum.get(11);
//        String country = colum.get(12);
//        String zipcode = colum.get(13);
//
//        CSVRecord line = readOneLine();
//        if(line != null) {
//            String orderVal = line.get(0);
//            String refVal = line.get(1);
//            String createDateVal = line.get(2);
//            String paymentDateVal = line.get(3);
//            String productNameVal = line.get(4);
//            String customersVal = line.get(5);
//            String quantityVal = line.get(6);
//            String amountVal = line.get(7);
//            String shippingMethodVal = line.get(8);
//            String stateVal = line.get(9);
//            String fullFillStateVal = line.get(10);
//            String trackingVal = line.get(11);
//            String countryVal = line.get(12);
//            String zipcodeVal = line.get(13);
//
//            System.out.println(order + " = " + orderVal + "; " +
//                    ref + " = " + refVal + "; ");
//        }
        System.out.println("----------------------------------------------------------");
    }
//c2
//    public static CSVRecord readOneLine()  {
//        CSVRecord line = null;
//        int countQuartz = ReadFile.count;
//        List<CSVRecord> listQuartz = ReadFile.listData;
//        if(countQuartz < listQuartz.size()){
//            line = listQuartz.get(countQuartz);
//            ReadFile.count += 1;
//        }
//        return line;
//    }

    public static OrderModel readOneRecord() throws FileNotFoundException {
        OrderModel orderModel = null;
        int countQuartz = ReadFile.count;
        List<OrderModel> listQuartz = ReadFile.readFileModel();
        if(countQuartz < listQuartz.size()){
            orderModel = listQuartz.get(countQuartz);
            ReadFile.count += 1;
        }
        return orderModel;
    }

}
