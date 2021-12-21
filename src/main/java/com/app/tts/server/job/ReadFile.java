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

public class ReadFile {

	public static List<CSVRecord> listData = new LinkedList();
	public static List<Map> listMapData = new LinkedList<>();
	public static int count = 0;
	public static List<String> nameColumnList = new LinkedList<>();
	public static List<String> nameColumnList1 = new LinkedList<>();

	public static void readCSV(String csvFile) {
//		String csvFile = "D://CSV file/dung_test_import.csv";
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

			boolean checkFullColumn = true;
			int count1=0;
			
			for (String key : columnName) {
				count1++;
				if (key.isEmpty()) {
					countEmptyNameColumn++;
					System.out.println("cot thu: "+count1+" thieu ten cot");
					checkFullColumn = false;
				}else {
					key = key.replaceAll("\\s\\s+", "");
					nameColumnList.add(key);
				}
			}
			System.out.println("co " + countEmptyNameColumn + " cot khong co ten cot");

			List<String> nameColumnOutput = nameColumnOutput();

//			boolean checkFullColumn = true;
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> nameColumnOutput() {
		List<String> nameColumnOutput = new LinkedList<>();
		nameColumnOutput.add("Id");
		nameColumnOutput.add("Name");
		nameColumnOutput.add("Financial status");
		nameColumnOutput.add("Paid at");
		nameColumnOutput.add("Created at");
		nameColumnOutput.add("Lineitem quantity");
		nameColumnOutput.add("Lineitem name");
		nameColumnOutput.add("Lineitem sku");
		nameColumnOutput.add("Shipping name");
		nameColumnOutput.add("Shipping street");
		nameColumnOutput.add("Shipping address1");
		nameColumnOutput.add("Shipping address2");
		nameColumnOutput.add("Shipping company");
		nameColumnOutput.add("Shipping city");
		nameColumnOutput.add("Shipping zip");
		nameColumnOutput.add("Shipping province");
		nameColumnOutput.add("Shipping country");
		nameColumnOutput.add("Shipping phone");
		nameColumnOutput.add("Notes");
		nameColumnOutput.add("Check vaild adress");
		nameColumnOutput.add("Design front url");
		nameColumnOutput.add("Design back url");
		nameColumnOutput.add("Mockup front url");
		nameColumnOutput.add("Mockup back url");
		nameColumnOutput.add("Shipping method");
		return nameColumnOutput;
	}
}
