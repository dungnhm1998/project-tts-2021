package com.app.tts.main.quartz;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class QuartzJob implements Job {// extends QuartzJobBean {
//c1
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("--------------QuartzJob - ReadFile---------------");
        insertTBFile();
//        System.out.println(readOneLine());
        System.out.println("----------------end QuartzJob---------------------");

//    }

//    @Override
//    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("--------------QuartzJob - c1 - ReadFile---------------");
//        //c3
//        try {
//            OrderModel orderModel = readOneRecord();
//            if(orderModel != null) {
//                System.out.println("order = " + orderModel.getOrder() + " ; "
//                        + "ref = " + orderModel.getRef());
//            }else{
//                System.out.println("------------------------order = NULL----------------------------------");
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

// c2
        // in ra : 2.1
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

//            System.out.println(order + " = " + orderVal + "; " +
//                            ref + " = " + refVal + "; " +
//                            createDate + " = " + createDateVal + "; " +
//                            paymentDate + " = " + paymentDateVal + "; " +
//                            productName + " = " + productNameVal + "; " +
//                            customers + " = " + customersVal + "; " +
//                            quantity + " = " + quantityVal + "; " +
//                            amount + " = " + amountVal + "; " +
//                            shippingMethod + " = " + shippingMethodVal + "; " +
//                            state + " = " + stateVal + "; " +
//                            fullFillState + " = " + fullFillStateVal + "; " +
//                            tracking + " = " + trackingVal + "; " +
//                            country + " = " + countryVal + "; " +
//                            zipcode + " = " + zipcodeVal + "; "
//                    );
//        }


        //  in ra : 2.2
//        CSVRecord line1 = readOneLine();
//
//        if(line1 != null) {
//            Map<String , String> line = line1.toMap();

        // in ra 2.3
//        Map line = readOneLine2();
//        if(!line.isEmpty()){
//            //file A2075
//            for(String columnName : ReadFile.nameColumnList){
//                System.out.print(columnName + " = " + line.get(columnName) + "; ");
//            }

            //file createOrder
//                System.out.println("order" + " = " + line.get("ORDER") + "; " +
//                        "ref" + " = " + line.get("REF") + "; " +
//                        "createDate" + " = " + line.get("CREATE DATE") + "; " +
//                        "paymentDate" + " = " + line.get("PAYMENT DATE") + "; " +
//                        "productName" + " = " + line.get("PRODUCT NAME") + "; " +
//                        "customers" + " = " + line.get("CUSTOMERS") + "; " +
//                        "quantity" + " = " + line.get("QUANTITY") + "; " +
//                        "amount" + " = " + line.get("AMOUNT") + "; " +
//                        "shippingMethod" + " = " + line.get("SHIPPING METHOD") + "; " +
//                        "state" + " = " + line.get("STATE") + "; " +
//                        "fullFillState" + " = " + line.get("FULFILL STATE") + "; " +
//                        "tracking" + " = " + line.get("TRACKING") + "; " +
//                        "country" + " = " + line.get("COUNTRY") + "; " +
//                        "zipcode" + " = " + line.get("ZIPCODE") + "; "
//                );

//        }

        System.out.println("----------------------------------------------------------");

    }

    public void insertTBFile() {
        try {
            Map data = OrderService.getTBFileOld();

            if (!data.isEmpty()) {
                String fileId = ParamUtil.getString(data, AppParams.S_ID);
                String userId = ParamUtil.getString(data, AppParams.S_USER_ID);
                String storeId = ParamUtil.getString(data, AppParams.S_STORE_ID);
                String source = ParamUtil.getString(data, AppParams.S_SOURCE);

                ReadFile.csvFile = "E:\\fileDriveTest\\" + fileId;
                ReadFile.readFile();

                //insert 1 lan het file.csv vao tb_file_row
                int countRecord = 0;
                List<Map> listRecords = ReadFile.listMapData;
                while (countRecord < listRecords.size()) {
                    Map<String, String> record = listRecords.get(countRecord);
                    countRecord++;

                    //file_rows
                    Random rand = new Random();
                    String id = String.valueOf(rand.nextInt(1000000000));
                    String name = record.get("Name");
                    String email = record.get("Email");
                    String financialStatus = record.get("Financial Status");

                    String groupColumn = Md5Code.md5(fileId + "," + storeId + "," + userId + "," + name);
                    String lineItemQuantity = record.get("Lineitem quantity");
                    String lineItemName = record.get("Lineitem name");
                    String lineItemSku = record.get("Lineitem sku");

                    String shippingName = record.get("Shipping Name");
                    String shippingStreet = record.get("Shipping Street");
                    String shippingAddress1 = record.get("Shipping Address1");
                    String shippingAddress2 = record.get("Shipping Address2");
                    String shippingCompany = record.get("Shipping Company");
                    String shippingCity = record.get("Shipping City");

                    String shippingZip = record.get("Shipping Zip");
                    String shippingProvince = record.get("Shipping Province");
                    String shippingCountry = record.get("Shipping Country");
                    String shippingPhone = record.get("Shipping Phone");
                    String shippingMethod = record.get("Shipping method");

                    String notes = record.get("Notes");
                    String designFrontUrl = record.get("Design front url");
                    String designBackUrl = record.get("Design back url");
                    String mockFrontUrl = record.get("Mockup front url");
                    String mockBackUrl = record.get("Mockup back url");

                    String currency = record.get("Currency");
                    String unitAmount = record.get("Unit amount");
                    String location = record.get("Location");
                    String state = "Created";

                    List<Map> resultMap = OrderService.insertTBFileRows(
                            userId, storeId, fileId, source,
                            id, name, email, financialStatus,
                            groupColumn, lineItemQuantity, lineItemName, lineItemSku,
                            shippingName, shippingStreet, shippingAddress1, shippingAddress2, shippingCompany, shippingCity,
                            shippingZip, shippingProvince, shippingCountry, shippingPhone, shippingMethod,
                            notes, designFrontUrl, designBackUrl, mockFrontUrl, mockBackUrl,
                            currency, unitAmount, location, state
                    );
                }
                System.out.println("inserted file : " + fileId);
                // update state = done trong tb_file
                OrderService.updateImportFile(fileId, "done");
                System.out.println("updated file : " + fileId);

                File file = new File(ReadFile.csvFile);
                //xoa file tren may, de nguyen thong tin file trong tb_file
                ReadFile.deleteFile(file);
                System.out.println("=======================================================================================");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        // c2.3
    public static Map readOneLine2(){
        int countRecord = ReadFile.count;
        List<Map> listRecords = ReadFile.listMapData;
        Map<String, String> record = new LinkedHashMap();
        if(countRecord < listRecords.size()){
            record = listRecords.get(countRecord);
            ReadFile.count++;

        }
        return record;
    }

//c2
    public static CSVRecord readOneLine()  {
        CSVRecord line = null;
        int countQuartz = ReadFile.count;
        List<CSVRecord> listQuartz = ReadFile.listData;
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            ReadFile.count += 1;
        }
        return line;
    }

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
