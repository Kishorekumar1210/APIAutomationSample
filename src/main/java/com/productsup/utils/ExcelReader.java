package com.productsup.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	
	public String sheetName;
	public static File fileName;
	public FileInputStream fileInputStream;
	public String keyname = null;
	public static XSSFWorkbook excelWorkbook;

	/***Opening Excel Workbook
	 * 
	 * @throws Exception
	 */
	public void openExcelWorkBook() throws Exception {


		fileName = new File(System.getProperty("user.dir") + "//src//test//resources//TestData//APITestData.xlsx");
		fileInputStream = new FileInputStream(fileName);
		excelWorkbook = new XSSFWorkbook(fileInputStream);

	}

	
	/*****
	 * Get Data From excel
	 * 
	 * @param KeyValue
	 * @param MyColumnName
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	public String getTestData(String KeyValue, String column, String sheetName) throws IOException {

		String columnName = KeyValue;
		String columnValue = null;

		XSSFSheet mySheet = excelWorkbook.getSheet(sheetName);
		int rowCount = mySheet.getLastRowNum() + 1;
		int columnCount = mySheet.getRow(0).getLastCellNum();
		int tempValue = 0;

		for (int iRow = 1; iRow < rowCount; iRow++) {
			XSSFRow row = mySheet.getRow(iRow);
			int flag = 0;
			for (int cellValue = 0; cellValue < row.getPhysicalNumberOfCells(); cellValue++) {
				String data = null;
				XSSFCell cell1 = row.getCell(cellValue, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				if (cell1.getCellType() == CellType.STRING)
					data = cell1.getStringCellValue();
				else if (cell1.getCellType() == CellType.NUMERIC)
					data = String.valueOf(cell1.getNumericCellValue());
				if (columnName.equals(data)) {
					for (int iColumn = 0; iColumn < columnCount; iColumn++) {
						XSSFRow row2 = mySheet.getRow(0);
						XSSFCell cell = row2.getCell(iColumn);
						if (column.equals(cell.getStringCellValue())) {
							tempValue = iColumn;
							flag = 1;
							break;
						}
					}

					XSSFRow row1 = mySheet.getRow(iRow);
					columnValue = row1.getCell(tempValue).getStringCellValue();
					break;
				}
				if (flag == 1) {
					break;
				}

			}
			if (flag == 1) {
				break;
			}
		}

		return columnValue;
	}

	
	/**Close Excel Workbook
	 * 
	 * @throws Exception
	 */
	public void closeWorkBook() throws Exception {
		excelWorkbook.close();
		fileInputStream.close();
	}

}
