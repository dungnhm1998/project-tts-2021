package com.app.tts.server.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.opencsv.exceptions.CsvValidationException;

public class GetCsv {
	public static int n = 0;
	public static List<CSVRecord> listData = new LinkedList();
    public static List<Map> listMapData = new LinkedList<>();
	public static String filename = "C:\\Users\\Admin\\Downloads\\Telegram Desktop\\dung_test_import.csv";
	public static List<String> header = new ArrayList<>();
	public static List<String> nameColumnList = new LinkedList<>();
	
	public static void readCSV() throws CsvValidationException, IOException {

		header.add("Name");
		header.add("Email");
		header.add("Financial Status");
		header.add("Paid at");
		header.add("Created at");
		header.add("Lineitem quantity");
		header.add("Lineitem name");
		header.add("Lineitem sku");
		header.add("Shipping Name");
		header.add("Shipping Street");
		header.add("Shipping Address1");
		header.add("Shipping Address2");
		header.add("Shipping Company");
		header.add("Shipping City");
		header.add("Shipping Zip");
		header.add("Shipping Province");
		header.add("Shipping Country");
		header.add("Shipping Phone");
		header.add("Shipping method");
		header.add("Notes");
		header.add("Id");
		header.add("Design front url");
		header.add("Design back url");
		header.add("Mockup front url");
		header.add("Mockup back url");
		header.add("Check vaild adress");
		header.add("currency");
		header.add("Unit amount");
		header.add("Location");
		try (
	             Reader reader = new FileReader(filename);
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
	        ) {
	            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	            for (CSVRecord csvRecord : csvRecords) {
	                listData.add(csvRecord);
	            }
	            if (listData.isEmpty()) {
	            	System.out.println("file trống");
	            } else {
		            CSVRecord columnName = listData.get(0);
		            int countEmptyNameColumn = 0;
		            
		            for (String key : columnName) {
		                if (key.equals("")) {
		                    countEmptyNameColumn++;
		                }
		                nameColumnList.add(key.replaceAll("\\s\\s+", " ").trim());
		            }
		            
		            boolean checkFullColumn = true;
		            for (String nameColumn : header) {
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
		                    for (String key : nameColumnList) { 
		                        mapOneLine.put(key, csvRecord.get(countColumn));
		                        countColumn++;
		                    }
		                    listMapData.add(mapOneLine);
		                }
		            }
	            }
		
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
