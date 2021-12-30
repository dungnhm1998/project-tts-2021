package com.app.tts.server.handler.importFile;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Readfile {


    private static int count = 0;
    private static List<CSVRecord> listData = new LinkedList();
    public static List<Map> listMapData = new ArrayList<>();
    public static List<String> nameColumnList = new ArrayList<>();

    public static void readFile(String fileCsv) {

        try (
                Reader reader = new FileReader(fileCsv);

                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        ) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                listData.add(csvRecord);
            }

            CSVRecord columnName = listData.get(0);
            int countEmptyNameColumn = 0;

            nameColumnList.clear();


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


//                for (int number = 1; number < listData.size(); number++) {
//                    CSVRecord csvRecord = listData.get(number);
//                    Map mapOneLine = new HashMap();
//                    int countColumn = 0;
//
//                        // them vao map key, value tuong ung
//                        mapOneLine.put( "Name"                 , csvRecord.get(0));
//                        mapOneLine.put( "Email"                , csvRecord.get(1));
//                        mapOneLine.put( "Financial Status"     , csvRecord.get(2));
//                        mapOneLine.put( "Paid at"              , csvRecord.get(3));
//                        mapOneLine.put( "Created at"           , csvRecord.get(4));
//                        mapOneLine.put( "Lineitem quantity"    , csvRecord.get(5));
//                        mapOneLine.put( "Lineitem name"        , csvRecord.get(6));
//                        mapOneLine.put( "Lineitem sku"         , csvRecord.get(7));
//                        mapOneLine.put( "Shipping Name"        , csvRecord.get(8));
//                        mapOneLine.put( "Shipping Street"      , csvRecord.get(9));
//                        mapOneLine.put( "Shipping Address1"    , csvRecord.get(10));
//                        mapOneLine.put( "Shipping Address2"    , csvRecord.get(11));
//                        mapOneLine.put( "Shipping Company"     , csvRecord.get(12));
//                        mapOneLine.put( "Shipping City"        , csvRecord.get(13));
//                        mapOneLine.put( "Shipping Zip"         , csvRecord.get(14));
//                        mapOneLine.put( "Shipping Province"    , csvRecord.get(15));
//                        mapOneLine.put( "Shipping Country"     , csvRecord.get(16));
//                        mapOneLine.put( "Shipping Phone"       , csvRecord.get(17));
//                        mapOneLine.put( "Shipping method"      , csvRecord.get(18));
//                        mapOneLine.put( "Notes"                , csvRecord.get(19));
//                        mapOneLine.put( "Id"                   , csvRecord.get(20));
//                        mapOneLine.put( "Design front url"     , csvRecord.get(21));
//                        mapOneLine.put( "Design back url"      , csvRecord.get(22));
//                        mapOneLine.put( "Mockup front url"     , csvRecord.get(23));
//                        mapOneLine.put( "Mockup back url"      , csvRecord.get(24));
//                        mapOneLine.put( "Check vaild adress"   , csvRecord.get(25));
//                        mapOneLine.put( "Currency"             , csvRecord.get(26));
//                        mapOneLine.put( "Unit amount"          , csvRecord.get(27));
//                        mapOneLine.put( "Location"             , csvRecord.get(28));
//
//                    listMapData.add(mapOneLine);
//                }

            }
            System.out.println("---------------loading...");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> nameColumnOutputA2075() {
        List<String> nameColumnOutput = new LinkedList<>();
        nameColumnOutput.add("Name");
        nameColumnOutput.add("Email");
        nameColumnOutput.add("Financial Status");
        nameColumnOutput.add("Paid at");
        nameColumnOutput.add("Created at");
        nameColumnOutput.add("Lineitem quantity");
        nameColumnOutput.add("Lineitem name");
        nameColumnOutput.add("Lineitem sku");
        nameColumnOutput.add("Shipping Name");
        nameColumnOutput.add("Shipping Street");
        nameColumnOutput.add("Shipping Address1");
        nameColumnOutput.add("Shipping Address2");
        nameColumnOutput.add("Shipping Company");
        nameColumnOutput.add("Shipping City");
        nameColumnOutput.add("Shipping Zip");
        nameColumnOutput.add("Shipping Province");
        nameColumnOutput.add("Shipping Country");
        nameColumnOutput.add("Shipping Phone");
        nameColumnOutput.add("Shipping method");
        nameColumnOutput.add("Notes");
        nameColumnOutput.add("Id");
        nameColumnOutput.add("Design front url");
        nameColumnOutput.add("Design back url");
        nameColumnOutput.add("Mockup front url");
        nameColumnOutput.add("Mockup back url");
        nameColumnOutput.add("Check vaild adress");
        nameColumnOutput.add("Currency");
        nameColumnOutput.add("Unit amount");
        nameColumnOutput.add("Location");
        return nameColumnOutput;
    }
}
