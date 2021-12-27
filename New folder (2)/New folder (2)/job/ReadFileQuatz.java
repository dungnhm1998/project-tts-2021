package com.app.tts.server.job;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ReadFileQuatz /*implements Job*/ extends QuartzJobBean {
	
	/*public String oneLine() {
		String line = null; 
        int countQuartz = ReadFileTXT.count;
        List<String> listQuartz = ReadFileTXT.listData;
        System.out.println(listQuartz);
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            ReadFileTXT.count++;
        }
        return line;
	}*/
	
	/*public Order readOneLine() {
		Order line = null; 
        int countQuartz = ReadCSV.count;
        List<Order> listQuartz = ReadCSV.list;
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            ReadCSV.count++;
        }
        return line;
	}*/
	
	/*public HashMap<String, Object> readOneLine1() {
        HashMap<String, Object> line = null;
        int countQuartz = ReadCSV.count;
        List<HashMap<String, Object>> listQuartz = ReadCSV.convertCSVRecordToList();
        if (countQuartz < listQuartz.size()) {
            line = listQuartz.get(countQuartz);
            ReadCSV.count++;
        }
//        System.out.println(listQuartz);
        return line;
    }*/
	
	public static CSVRecord readOneLine()  {
        CSVRecord line = null;
        int countQuartz = ReadCSV.count;
        List<CSVRecord> listQuartz = ReadCSV.listData;
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            ReadCSV.count += 1;
        }
        return line;
    }
	
	public static Map readOneLine2(){
        int countRecord = ReadCSV.count;
        List<Map> listRecords = ReadCSV.listMapData;
        Map record = new LinkedHashMap();
        if(countRecord < listRecords.size()){
            record = listRecords.get(countRecord);
            ReadCSV.count++;
        }
        return record;
    }

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Map line = readOneLine2();
        if(!line.isEmpty()){
            //file A2075
            for(String columnName : ReadCSV.nameColumnList){
                System.out.print(columnName + " = " + line.get(columnName) + "; ");
            }
        }
		
	}

//	@Override
//	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//		
//
//        if(line1 != null) {
//            Map<String , String> line = line1.toMap();
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
//
//        }

//		System.out.println(readOneLine1());
//		System.out.println(ParamUtil.getString(readOneLine1(), "ORDER"));
//		ReadCSV.convertCSVRecordToList();
//		Order order = readOneLine();
//		System.out.println(order);
//		System.out.println(user.getEmail());
		
//		String id = UUID.randomUUID().toString().substring(5, 20);
//		String encodePassword = Md5Code.md5(user.getPassword()); 
//		try {
//			SubService.insertUser(id, user.getEmail(), encodePassword, user.getPhone());
//			System.out.println("Success id : "+id);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}
