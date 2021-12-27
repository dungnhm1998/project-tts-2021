package com.app.tts.server.job;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ReadCSV {
	public static List<CSVRecord> listData = new LinkedList();
	public static List<Map> listMapData = new LinkedList<>();
	public static int count = 0;
	public static int count2 = 1;
	// danh sach ten cac cot trong file
	public static List<String> nameColumnList = new LinkedList<>();

	public static void readFile() {
		String csvFile = "D://A2075_lWepIUUrLYTNBUCBwwAp1PW0Z_1620898574338.csv"; // Orders_export_1639471684871.csv";
		try (// {
				Reader reader = new FileReader(csvFile);
				// CSVParser csvParser = null;
//	                try{
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);// .withHeader() chi dinh key = ten cot
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
			if (checkFullColumn) {
				for (int number = 1; number < listData.size(); number++) {
					CSVRecord csvRecord = listData.get(number);
					Map<String, String> mapOneLine = new LinkedHashMap<>();
					int countColumn = 0;
					for (String key : nameColumnList) { // columnName) {
						// them vao map key, value tuong ung
						mapOneLine.put(key, csvRecord.get(countColumn));
						countColumn++;
					}
					listMapData.add(mapOneLine);
				}
			}
			System.out.println("---------------start-----------");
			System.out.println("------------------------------------------");
			System.out.println("----------------------------------------------");

//	            }catch (IllegalArgumentException e){
//	                    // chi chap nhan 1 cot k co nameColumn con lai se nhay vao ngoai le nay
//	                    System.out.println("-------------------------------");
//	                    System.out.println("---------------end-----------");
//	                    System.out.println();
//	                    System.out.println("khong co header");
//	                    System.out.println("------------------------------------------");
//	                    System.out.println("----------------------------------------------");
//	                }
//	           Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			//
//	           for(CSVRecord csvRecord : csvRecords){
//	               listData.add(csvRecord);
			//
			//
//	               System.out.println("------------readFile-------------");
			//
//	               System.out.println(csvRecord.get(0));
//	               System.out.println("-------------------------------");
//	           }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> nameColumnOutputA2075() {
		List<String> nameColumnOutput = new LinkedList<>();
		nameColumnOutput.add("Name");
		nameColumnOutput.add("Lineitem quantity");
		nameColumnOutput.add("Lineitem sku");
		nameColumnOutput.add("Shipping Name");
		nameColumnOutput.add("Shipping Address1");
		nameColumnOutput.add("Shipping Address2");
		nameColumnOutput.add("Shipping City");
		nameColumnOutput.add("Shipping Zip");
		nameColumnOutput.add("Shipping Province");
		nameColumnOutput.add("Shipping Country");
		nameColumnOutput.add("Design front url");
		nameColumnOutput.add("Design back url");
		nameColumnOutput.add("Mockup front url");
		nameColumnOutput.add("Mockup back url");
		nameColumnOutput.add("Check vaild adress");
		return nameColumnOutput;
	}

	public static List<String> nameColumnOutputcreateOrderData2() {
		List<String> nameColumnOutput = new LinkedList<>();
		nameColumnOutput.add("ORDER");
		nameColumnOutput.add("REF");
		nameColumnOutput.add("CREATE DATE");
		nameColumnOutput.add("PAYMENT DATE");
		nameColumnOutput.add("PRODUCT NAME");
		nameColumnOutput.add("CUSTOMERS");
		nameColumnOutput.add("QUANTITY");
		nameColumnOutput.add("AMOUNT");
		nameColumnOutput.add("SHIPPING METHOD");
		nameColumnOutput.add("STATE");
		nameColumnOutput.add("FULFILL STATE");
		nameColumnOutput.add("TRACKING");
		nameColumnOutput.add("COUNTRY");
		nameColumnOutput.add("ZIPCODE");
		return nameColumnOutput;
	}
}