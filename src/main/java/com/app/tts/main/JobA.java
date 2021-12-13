package com.app.tts.main;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class JobA implements Job {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Huong dan Quartz Job--------------------");
        System.out.println(readOneLine());
    }

    public Order readOneLine()  {
        Order line = null;
        int countQuartz = quzt.count;
        List<Order> listQuartz = quzt.orders;
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            quzt.count++;
        }
        return line;
    }

    private static final Logger LOGGER = Logger.getLogger(JobA.class.getName());

}
