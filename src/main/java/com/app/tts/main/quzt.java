package com.app.tts.main;

import com.google.common.io.ByteArrayDataInput;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class quzt {
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_NAME = 1;
    public static final int COLUMN_INDEX_PRICE = 2;
    public static final int COLUMN_INDEX_QUANTITY = 3;
    public static final int COLUMN_INDEX_STATUS = 4;

    public static int count;
    public static List<Order> orders;

    //    Order lastValue = orders.get(orders.size() );
//        System.out.println(lastValue);
//       for ( int i = 0 ; i  < orders.size()  ; i++ ) {
//           System.out.println(orders.get(i));
//           break;
//       }
    public void quaz(){
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        final String excelFilePath = "D://react/Book1.xlsx";
         orders = readExcel(excelFilePath);

        try {
            fileInputStream = new FileInputStream(excelFilePath);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            // line = dong dau tien
            String line = bufferedReader.readLine();
            while (line != null) {
                orders.size();
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

    private List<Order> readExcel(String excelFilePath) {
        List<Order> listBooks = new ArrayList<>();
        try {
            // Get file
            InputStream inputStream = new FileInputStream(new File(excelFilePath));

            // Get workbook
            Workbook workbook = getWorkbook(inputStream, excelFilePath);

            // Get sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Get all rows
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                if (nextRow.getRowNum() == 0) {
                    // Ignore header
                    continue;
                }

                // Get all cells
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                // Read cells and set value for book object
                Order Order = new Order();
                while (cellIterator.hasNext()) {
                    //Read cell
                    Cell cell = cellIterator.next();
                    Object cellValue = getCellValue(cell);
                    if (cellValue == null || cellValue.toString().isEmpty()) {
                        continue;
                    }
                    // Set value for book object
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case COLUMN_INDEX_ID:
                            Order.setId(new BigDecimal((double) cellValue).intValue());
                            break;
                        case COLUMN_INDEX_NAME:
                            Order.setName((String) getCellValue(cell));
                            break;
                        case COLUMN_INDEX_QUANTITY:
                            Order.setQuantity(new BigDecimal((double) cellValue).intValue());
                            break;
                        case COLUMN_INDEX_PRICE:
                            Order.setPrice((Double) getCellValue(cell));
                            break;
                        case COLUMN_INDEX_STATUS:
                            Order.setStatus((String) getCellValue(cell));
                            break;
                        default:
                            break;
                    }

                }
                listBooks.add(Order);
            }

            workbook.close();
            inputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listBooks;
    }

    private Object getCellValue(Cell cell) {

        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }


    private Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }
}
