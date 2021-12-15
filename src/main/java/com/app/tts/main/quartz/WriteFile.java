package com.app.tts.main.quartz;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class WriteFile {
    public static String writeFile(Map input){
        String message = "write file fail";
        Random rand = new Random();
        String csvFile = "E:\\springboot\\createOrderData2.csv";
        try(
                FileWriter fileWriter = new FileWriter(csvFile, true);
                //Tham số thứ hai cho hàm FileWritertạo sẽ yêu cầu nó nối vào tệp,
                // thay vì viết một tệp mới. (Nếu tệp không tồn tại, nó sẽ được tạo.)
                BufferedWriter writer = new BufferedWriter(fileWriter);
            CSVPrinter csvPrinter = new CSVPrinter(writer,
                    CSVFormat.DEFAULT.withHeader("source","currency","note", "shipping_id"))
        ) {
            csvPrinter.printRecord(ParamUtil.getString(input, AppParams.SOURCE),
                    ParamUtil.getString(input, AppParams.CURRENCY),
                    ParamUtil.getString(input, AppParams.NOTE),
                    String.valueOf(rand.nextInt(1000000000))
                    );
            message = "write file success";
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
