package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
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

public class QueryGenerator {

	public String generateQueryAndSave(String filePath, String queryPath)throws IOException {
		System.out.println("Buliding the Query");
		String ter="";
		FileInputStream inputStream = new FileInputStream(new File(filePath));
		Workbook workBook =  new XSSFWorkbook(inputStream);
		StringBuffer br = new StringBuffer();
		br.append("construct {km:TermRaider a owl:Class . ?ss a km:TermRaider ; ?p ?o ; km:weight ?weight }").append("\n");
		br.append("where").append("\n");
		br.append("{").append("\n");
		br.append("{").append("\n");
		br.append("select distinct (coalesce(?URI1, ?URI2, ?URI3, ?URI4) as ?s) ?p ?o ?weight");
		br.append("\n");
		br.append("where").append("\n");
		br.append("{").append("\n");
		br.append(" bind(rdfs:label as ?p)").append("\n");
		br.append("{").append("values (?o ?weight)  {");
		//System.out.println(br.toString());
		//System.out.println("**************************************************");
		Sheet sheet = workBook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
			sheet.getHeader();  
			Row frow = sheet.getRow(1);
				for(int i=2;i<=sheet.getLastRowNum();i++){
					String term ="";
					String multoword = "";
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
							multoword=s;
							br.append("\n");
							br.append("( \"" + term + "\" ");
							//System.out.println(" \"" + term + "\"");
						}
					}
					if(m==3 && multoword.equals("MultiWord")){
						Cell c1=row.getCell(m);
						String s1=c1.getStringCellValue();
						//System.out.println(s1);
						br.append(s1.substring(0, 3)+")");
						multoword="";
					}
						}
			
				}
				br.append("\n").append("}");
				br.append("Values ?what {' and |[ ()_&/-]'}").append("\n");
				br.append(" bind(replace(lcase(?o), ?what, '') as ?pattern)").append("\n");
				br.append("}").append("\n");
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
}
