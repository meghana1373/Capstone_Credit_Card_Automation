package com.project.framework.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.InputStream;

public class ExcelReader {

    /**
     * Reads data from Excel file placed in src/test/resources/testdata.xlsx
     *
     * @param sheetName The sheet name
     * @param colName   Column header name
     * @param rowNum    Row number (0-based, excluding header row)
     * @return Cell value as String
     */
    public static String getCellData(String sheetName, String colName, int rowNum) {
        try {
            // Load Excel from resources
            InputStream fis = ExcelReader.class.getClassLoader().getResourceAsStream("testdata.xlsx");
            if (fis == null) {
                throw new RuntimeException("Excel file 'testdata.xlsx' not found in resources folder!");
            }

            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in Excel file!");
            }

            // Find column index
            int colNum = -1;
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row (row 0) missing in sheet '" + sheetName + "'");
            }

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && cell.getStringCellValue().trim().equalsIgnoreCase(colName)) {
                    colNum = i;
                    break;
                }
            }
            if (colNum == -1) {
                throw new RuntimeException("Column '" + colName + "' not found in sheet '" + sheetName + "'");
            }

            Row row = sheet.getRow(rowNum);
            if (row == null || row.getCell(colNum) == null) {
                return "";
            }

            Cell cell = row.getCell(colNum);
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue().trim();

        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }
    }
}
