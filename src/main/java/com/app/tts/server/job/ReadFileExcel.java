package com.app.tts.server.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadFileExcel {
	public static final int COLUMN_INDEX_KEY = 0;
	public static final int COLUMN_INDEX_VALUE = 1;
	public static int count = 0;
	static List<Order> orders = new LinkedList<>();

	public static void readFileExcel() {

		String excelFilePath = "D:\\Book1.xlsx";
		orders = readExcel(excelFilePath);

		for (Order oder : orders) {
			System.out.println(oder);
		}

	}

	private static List<Order> readExcel(String excelFilePath) {
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
					// Read cell
					Cell cell = cellIterator.next();
					Object cellValue = getCellValue(cell);
					if (cellValue == null || cellValue.toString().isEmpty()) {
						continue;
					}
					// Set value for book object
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex) {
					case COLUMN_INDEX_KEY:
						Order.setKey((String) getCellValue(cell));
						break;
					case COLUMN_INDEX_VALUE:
						Order.setValue((String) getCellValue(cell));
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

	private static Object getCellValue(Cell cell) {

		org.apache.poi.ss.usermodel.CellType cellType = cell.getCellTypeEnum();
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

	private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
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
