package com.app.tts.main.quartz;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;


public class ReadFile {
    public static List<CSVRecord> listData = new LinkedList();
    public static int count = 1;
    public static int count2 = 1;
    public static void readFile() {
        String csvFile = "E:\\springboot\\createOrderData2.csv"; //Orders_export_1639471684871.csv";
        try{
            Reader reader = new FileReader(csvFile);
            CSVParser csvParser = null;
                try{
                     csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());//.withHeader() chi dinh key = ten cot
                    Iterable<CSVRecord> csvRecords = csvParser.getRecords();

                    for(CSVRecord csvRecord : csvRecords){
                        listData.add(csvRecord);


                        System.out.println("------------test-loop-------------");

                        System.out.println(csvRecord.get(0));
                        System.out.println("-------------------------------");
                    }
            }catch (IllegalArgumentException e){
                    // chi chap nhan 1 cot k co nameColumn con lai se nhay vao ngoai le nay
                    System.out.println("---------------end-----------");
                    System.out.println("khong co header");
                    System.out.println("------------------------------------------");
                    System.out.println("----------------------------------------------");
                }
//           Iterable<CSVRecord> csvRecords = csvParser.getRecords();
//
//           for(CSVRecord csvRecord : csvRecords){
//               listData.add(csvRecord);
//
//
//               System.out.println("------------readFile-------------");
//
//               System.out.println(csvRecord.get(0));
//               System.out.println("-------------------------------");
//           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<OrderModel> readFileModel() throws FileNotFoundException {
        String fileName = "E:\\springboot\\createOrderData2.csv";

        List<OrderModel> orders = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(OrderModel.class)
                .build()
                .parse();

        return orders;
    }
}
