package com.exceldiff.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.exceldiff.model.Employee;

public class ExcelHelper {
  static String[] HEADERs = { "Name", "Email" };
  static String SHEET = "Sheet";

  public static ByteArrayInputStream employeesToExcel(List<Employee> employees) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (Employee employee : employees) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(employee.getName());
        row.createCell(1).setCellValue(employee.getEmail());
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

  public static List<Employee> excelToEmployees(InputStream is) {
    try {
      Workbook workbook = WorkbookFactory.create(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<Employee> employees = new ArrayList<Employee>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        /*if (rowNumber == 0) {
          rowNumber++;
          continue;
        }*/

        Iterator<Cell> cellsInRow = currentRow.iterator();

        Employee employee = new Employee();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
          case 0:
            employee.setName(currentCell.getStringCellValue());
            break;

          case 1:
            employee.setEmail(currentCell.getStringCellValue());
            break;

          default:
            break;
          }

          cellIdx++;
        }

        employees.add(employee);
      }

      workbook.close();

      return employees;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
