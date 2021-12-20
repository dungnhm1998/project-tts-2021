package com.app.tts.main.siupham;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class quzt {
    public static List<CSVRecord> listData = new LinkedList();

    public static List<Map> listMapData = new LinkedList<>();
    public static List<Order> orders = new LinkedList<>();
    public static int count = 0;
    public static List<Map> od = new LinkedList<>();
    public static List<String> nameColumnList = new LinkedList<>();

    public static void quaz() throws SQLException {
        String fileName = "D:/react/Orders_export_1639447990953.csv";

        try {
            orders = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(Order.class)
                    .withSkipLines(0)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }


    }

    public static void readFile() {
        String csvFile = "D:/react/A2075_lWepIUUrLYTNBUCBwwAp1PW0Z_1620898574338.csv";
        try (
             Reader reader = new FileReader(csvFile);

             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        ) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                listData.add(csvRecord);
            }

            CSVRecord columnName = listData.get(0);
            int countEmptyNameColumn = 0;

            for (String key : columnName) {
                if (key.equals("")) {
                    countEmptyNameColumn++;
                }
                // loai bo het dau cach thua; trim() loai bo dau cach dau, cuoi
                nameColumnList.add(key.replaceAll("\\s\\s+", " ").trim());
            }
            System.out.println("co " + countEmptyNameColumn + " cot khong co ten cot");

            List<String> nameColumnOutput = nameColumnOutputA2075();


            boolean checkFullColumn = true;
            for (String nameColumn : nameColumnOutput) {
                if (!nameColumnList.contains(nameColumn)) {
                    System.out.println("thieu cot: " + nameColumn);
                    checkFullColumn = false;
                }
            }
            if(checkFullColumn) {
                for (int number = 1; number < listData.size(); number++) {
                    CSVRecord csvRecord = listData.get(number);
                    Map<String, String> mapOneLine = new LinkedHashMap<>();
                    int countColumn = 0;
                    for (String key : nameColumnList) { //columnName) {
                        // them vao map key, value tuong ung
                        mapOneLine.put(key, csvRecord.get(countColumn));
                        countColumn++;
                    }
                    listMapData.add(mapOneLine);
                }
            }
            System.out.println("---------------loading...");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<String> nameColumnOutputA2075(){
        List<String> nameColumnOutput = new LinkedList<>();
        nameColumnOutput.add("order");
        nameColumnOutput.add("fulfill state");
        nameColumnOutput.add("create date");
        nameColumnOutput.add("payment date");
        nameColumnOutput.add("product name");
        nameColumnOutput.add("ref");
        nameColumnOutput.add("customers");
        nameColumnOutput.add("quantity");
        nameColumnOutput.add("amount");
        nameColumnOutput.add("shipping method");
        nameColumnOutput.add("state");
        nameColumnOutput.add("tracking");
        nameColumnOutput.add("country");
        nameColumnOutput.add("zipcode");
        return nameColumnOutput;
    }








    public static List<Map> convertCSVRecordToList() {

        String csvFile = "D:/react/Orders_export_1639447990953.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Map Map = new HashMap<>();

        List<Map> MapList = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(csvFile));


            while ((line = br.readLine()) != null) {


                String[] data = line.split(cvsSplitBy);

                Map.put("order", data[0]);
                Map.put("ref", data[1]);
                Map.put("create date", data[2]);
                Map.put("PAYMENT DATE", data[3]);
                Map.put("PRODUCT NAME", data[4]);
                Map.put("CUSTOMERS", data[5]);
                Map.put("quantity", data[6]);
                Map.put("amount", data[7]);
                Map.put("SHIPPING METHOD", data[8]);
                Map.put("STATE", data[9]);
                Map.put("FULFILL STATE", data[10]);
                Map.put("tracking", data[11]);
                Map.put("COUNTRY", data[12]);
                Map.put("ZIPCODE", data[13]);


                MapList.add(Map);

//                System.out.println(MapList);


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return MapList;
    }


    private static final Logger LOGGER = Logger.getLogger(quzt.class.getName());

}
