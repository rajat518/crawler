/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 

package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import com.cisco.ccl.kvm.GATE.services.web.model.Topics;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialController;
import gate.termraider.bank.AbstractTermbank;
import gate.termraider.output.CsvGenerator;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

@Controller
@RequestMapping("/")
public class Mcrawl {

	private static final Logger LOG = LoggerFactory.getLogger(Mcrawl.class);
//public static void main(String[])
	@Value("${gate.file.folder}")
	private String gateFileFolder;
	
	 * @Autowired LanguageAnalyserDocumentProcessor processor;
	 // Object from the Object Pool.

	SerialController controller;

	@Autowired
	AsyncTaskExecutor threadPoolExecutor;

	@Autowired
	AbstractRefreshableApplicationContext context;

	
	// CrawlPR pr ;
	
	public static void main(String args[]) throws Exception{
		
				//String rootUrl = (String) bodyContent.get("root");
			    //List<String> contains = (List<String>) bodyContent.get("contains");
		//String fp = "C:/output_store/17-09-19-175732_tfidfTermbank.csv";
		//String sp = "C:/output/";
		String rootUrl = null;
		//String surl = "http:/www.google.com/";
		Scanner s = new Scanner(new File("C:/Users/radodle/Desktop/Nava/read.txt"));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
		    list.add(s.next());
		}
		s.close();
		System.out.println(list);
		System.out.println(list.size());
		for(int i=0;i<list.size();i++)
		{
			rootUrl = list. get(i).toString();
		}
			
			
			
		for(String obj:list)
		   rootUrl= obj.toString();
		System.out.println("this isss ***** "+rootUrl);
		   
		
		for(int i=0;i<list.size();i++)
		{
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now1 = LocalDateTime.now();
			System.out.println("\nTime of starting\n"+dtf1.format(now1));
				
				String path = "C:/output_store/";
				String querypath = "C:/queries/";
				String resultspath = "C:/queries/";
	        	String froot = "C:/Users/radodle/Documents/raj/";  
	        	String value = "crawler"; 
				//Topics topics = null;
				System.out.println("Url that is crawled is : " +rootUrl); 
				
				System.out.println("The output will be stored in:" +path);
				crawl.CrawlPR crawler = new crawl.CrawlPR();

				Map<String, String> env = System.getenv();

				List<String> keywords = new ArrayList<>();
				Properties props2 = System.getProperties();
				props2.setProperty("gate.plugins.home", "C:/Program Files/GATE_Developer_8.4.1/plugins");
				Properties props3 = System.getProperties();
				props3.setProperty("gate.site.config", "C:/Program Files/GATE_Developer_8.4.1/gate.xml");
				Gate.init();
				Corpus corpus = Factory.newCorpus("public");
				 
				//if(value.equals("crawler")){
					
						rootUrl = list. get(i).toString();
					  creteCrawler(rootUrl, crawler);
					
			//Gate.init();

			URL u = new URL(rootUrl);

			FeatureMap params = Factory.newFeatureMap();

			params.put("sourceUrl", u);
            
			params.put("preserveOriginalContent", new Boolean(true));

			params.put("collectRepositioningInfo", new Boolean(true));

			System.out.println(("Creating doc for " + u));
			
			
			Document doc = (Document)
			Factory.createResource("gate.corpora.DocumentImpl", params);
			corpus.add(doc);
			crawler.setOutputCorpus(corpus);
					System.out.println("Executing the web Crawler");
					System.out.println("the stop after is :"+crawler.getStopAfter());
					System.out.println("Max Page size: "+crawler.getMaxPageSize());
					System.out.println("Max:"+crawler.getMax());
					System.out.println("root:"+crawler.getRoot());
					System.out.println("domain"+crawler.getDomain());
					System.out.println("Corpus name:"+crawler.getOutputCorpus().toString());
					System.out.println("this is keyword sens"+crawler.getKeywordsCaseSensitive());
					System.out.println("Depth : "+crawler.getDepth());
					crawler.execute();
					System.out.println("Execution done");
					List<Document> filteredDocuments = new ArrayList<>();
					
					corpus.clear();
					corpus.addAll(filteredDocuments);
					System.out.print("Name of corpus "+corpus.getName());
					//QueryGenerator qg = new QueryGenerator();
					//qg.generateQueryAndSave(fp,sp);
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					System.out.println("\nTime of completion\n"+dtf.format(now));		
							
	}
		}
	
	
			
				private static void creteCrawler(final String text_data, crawl.CrawlPR crawler) {
				crawler.setKeywords(null);
				crawler.setKeywordsCaseSensitive(true); 
				crawler.setConvertXmlTypes(true);
				crawler.setRoot(text_data);
				crawler.setDomain(crawl.DomainMode.WEB);
				crawler.setDepth(2);
				crawler.setDfs(true);
				crawler.setStopAfter(5);
			
			}
	public void setTaskExector(AsyncTaskExecutor executor) throws IOException {
		this.threadPoolExecutor = executor;
	}
}

{
"value1":"crawler",
 "root" : "http://blogs.cisco.com/cloud/",
"file":"C:/Users/radodle/Documents/raj/",
"contains" : ["cisco"] ,
"path" : "C:/output_store/" ,
"querypath" : "C:/queries/" ,
"resultspath" : "C:/queries/"


 }
 
 1707280942)






*/