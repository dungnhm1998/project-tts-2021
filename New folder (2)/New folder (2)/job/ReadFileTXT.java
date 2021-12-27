//package com.app.tts.server.job;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReadFileTXT {
//	public static int count = 0;
//	public static List<String> listData = new ArrayList<String>();
//
//	public static void readFile() {
//		String fileName = "D:\\Orders_export_1638932107906.csv";
//		FileInputStream f = null;
//		BufferedReader br = null;
//		try {
//			f = new FileInputStream(fileName);
//			br = new BufferedReader(new InputStreamReader(f));
//			String line = br.readLine();
//			while(line != null){
//				listData.add(line);
//				line = br.readLine();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//            try {
//                br.close();
//                f.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//		System.out.println("list " + listData);
//    }
//}
