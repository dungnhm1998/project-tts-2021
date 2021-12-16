package com.app.tts.main.siupham;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class quzt {


    public static List<Order> orders = new LinkedList<>();
    public static int count = 0;
    public static List<Map> od = new LinkedList<>();

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
