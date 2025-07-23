package com.checkpoint.weather;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {

    public void writeWeatherToExcel(List<CityTemperature> results, String filename) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Weather Results");

        // כותרות
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("City");
        headerRow.createCell(1).setCellValue("Temperature (°C)");

        int rowNum = 1;
        for (CityTemperature ct : results) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ct.getCity());
            row.createCell(1).setCellValue(ct.getTemperature());
        }

        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
