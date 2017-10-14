/*package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QueryGener {

	public String generateQueryAndSave(String filePath, String queryPath)throws IOException {
		System.out.println("Buliding the Query");
		String ter="";
		FileInputStream inputStream = new FileInputStream(new File(filePath));
		Workbook workBook =  new XSSFWorkbook(inputStream);
		StringBuffer br = new StringBuffer();
		br.append("construct {km:TermRaider a owl:Class . ?ss a km:TermRaider ; ?p ?o}").append("\n");
		br.append("where").append("\n");
		br.append("{").append("\n");
		br.append("{").append("\n");
		br.append("select distinct (coalesce(?URI1, ?URI2, ?URI3, ?URI4) as ?s) ?p ?o");
		br.append("\n");
		br.append("where").append("\n");
		br.append("{").append("\n");
		br.append(" bind(rdfs:label as ?p)").append("\n");
		br.append("{").append("values ?o {");
		//System.out.println(br.toString());
		Sheet sheet = workBook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
			sheet.getHeader();  
			Row frow = sheet.getRow(1);
				for(int i=2;i<=sheet.getLastRowNum();i++){
					String term ="";
					Row row = sheet.getRow(i);
					int colcount =  row.getLastCellNum();
						for(int m=0 ; m<row.getLastCellNum();m++){
					if(m==0)
					{
						Cell c = row.getCell(m);
						int num=0;
						//if(c.getStringCellValue().matches("/^[A-Za-z][A-Za-z0-9]*$/"))
						if(c.getStringCellValue().matches("[A-Za-z ]*"))
							 term = c.getStringCellValue();
						for(int j=0;j<term.length();j++){
							char ch = term.charAt(j);
							if(ch==' '){
								num++;
							}
						}
						if(num>1){
							term="";
						}
				}
					if(m==2){
						Cell c = row.getCell(m);
						String s= c.getStringCellValue();
						if(s.equals("MultiWord")&&!term.equals("")){
							br.append("\n");
							br.append(" \"" + term + "\"");
							//System.out.println(" \"" + term + "\"");
						}
					}
						}
			
				}
				
				br.append("OPTIONAL {graph <cmp:map> {?URI1 km:map ?pattern} ?topicURI1 owl:sameAs []}").append("\n");
				br.append("OPTIONAL {graph <cmp:map> {?URI2 km:map ?pattern}} ").append("\n");
				br.append("OPTIONAL {graph <cmp:alt> {?URI3 km:alt ?pattern}}").append("\n");
				br.append("OPTIONAL ").append("\n");
				br.append("{ ?URI4 a km:Undefined ").append("\n");
				br.append("; rdfs:label ?L ").append("\n");
				br.append("filter (replace(lcase(?L), ?what, '')=?pattern)").append("\n");
				br.append("}").append("\n");
				br.append("}").append("\n");
				br.append("}").append("\n");
				br.append("bind(if(!bound(?s), iri(md5(?o)), ?s) as ?ss)").append("\n");
				br.append("filter (!bound(?s))").append("\n");
				br.append("}");
				//String dname = new SimpleDateFormat("yy-MM-dd-HHmmss").format(new Date());
				String fp = queryPath+"_tdif_terms.sparql";
				BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(fp)));
				//System.out.println(br.toString());
				bwr.write(br.toString());
				bwr.flush();
				bwr.close();
				System.out.println("Query built and saved in : "+fp);
				return br.toString();
	}
	public static void main(String args[],String args1[]){
		
		String fp = "C:/output/new.xlxs";
		String qp = "C:/output/";
		
		QueryGener q = new QueryGener();
		try {
			q.generateQueryAndSave(fp, qp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

*/