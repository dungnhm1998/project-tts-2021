package com.app.tts.server.job;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class ReadCSV {
	public static int count = 1;
	public static List<User> list = new LinkedList();

	public static void readCSV() throws FileNotFoundException {
		list = new CsvToBeanBuilder<User>(new FileReader("D://demofile.csv"))
				.withType(User.class).withSkipLines(0).build().parse();
		
//		list.forEach(System.out::println);
//		System.out.println(list);
	}
}
