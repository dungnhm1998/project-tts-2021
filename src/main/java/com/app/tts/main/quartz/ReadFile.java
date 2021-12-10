package com.app.tts.main.quartz;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class ReadFile {
    public static List<String> listData = new LinkedList();
    public static int count = 0;
    public static void readFile() {
        String url = "E:\\springboot\\createOrderData.txt";
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            // line = dong dau tien
            String line = bufferedReader.readLine();
            while (line != null) {
                listData.add(line);
//                System.out.println(line);
//                System.out.println("---------------1-----------------");
                // xet line = dong tiep theo
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
