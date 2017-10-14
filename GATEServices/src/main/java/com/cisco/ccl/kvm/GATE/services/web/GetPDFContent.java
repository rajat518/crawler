package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class GetPDFContent {

    public static void rea(String filename) throws IOException {
    	File files =new File("C:/Users/radodle/Downloads/CAGs used in DWs/New folder/");
    	File[] fileList=files.listFiles();
    	for(File f:fileList){
    		String pdfFile =filename+f.getName();
    	System.out.println(f.getName());
    	PdfReader reader = new PdfReader(pdfFile);

       int pageNum = reader.getNumberOfPages();
       StringBuffer sb = new StringBuffer();
        for (int i=1; i<=pageNum; i++) {
           String page = PdfTextExtractor.getTextFromPage(reader, i);
          // System.out.println(page);
           
           sb.append(page);
        }
        
           String fn = "C:/Users/radodle/Downloads/CAGs used in DWs/New folder/new"+FilenameUtils.removeExtension(f.getName())+".txt";
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(fn)));
       	//System.out.println(br.toStrinig());
       	bwr.write(sb.toString());
       	bwr.flush();
       	bwr.close();
        }  
    }
    
    public static void main(String args[]){
    	String filename="C:/Users/radodle/Downloads/CAGs used in DWs/New folder/";
    			try {
			GetPDFContent.rea(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }}
