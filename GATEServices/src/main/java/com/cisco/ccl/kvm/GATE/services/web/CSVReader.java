package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CSVReader {


      public void converCSVToXLSX(String filePath){
    	  System.out.println("Coverting the Csv file(tfidfTermbank.csv) into xlsx file");
        try{
        String xlsxFileAddress = filePath; //xlsx file address
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet("sheet1");
        String currentLine=null;
        int RowNum=0;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((currentLine = br.readLine()) != null) {
            String str[] = currentLine.split(",");
            RowNum++;
            XSSFRow currentRow=sheet.createRow(RowNum);
            for(int m=0;m<str.length;m++){
                currentRow.createCell(m).setCellValue(str[m]);
            }
        }

        FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
        workBook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println("Done");
    } catch (Exception ex) {
        System.out.println(ex.getMessage()+"Exception in try");
    }
    
      }
}