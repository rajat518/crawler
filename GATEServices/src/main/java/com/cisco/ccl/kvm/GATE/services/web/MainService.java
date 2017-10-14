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
 */

package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import gate.util.FeatureBearer;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

@Controller
@RequestMapping("/")
public class MainService {

	private static final Logger LOG = LoggerFactory.getLogger(MainService.class);
//public static void main(String[])
	@Value("${gate.file.folder}")
	private String gateFileFolder;
	/*
	 * @Autowired LanguageAnalyserDocumentProcessor processor;
	 */// Object from the Object Pool.

	SerialController controller;

	@Autowired
	AsyncTaskExecutor threadPoolExecutor;

	@Autowired
	AbstractRefreshableApplicationContext context;

	
	// CrawlPR pr ;
	
	public static void main(String args[]) throws Exception {
		
				//String rootUrl = (String) bodyContent.get("root");
			    //List<String> contains = (List<String>) bodyContent.get("contains");
				
				String rootUrl = "http://www.cnn.com/politics";
				List<String> contains = new ArrayList<String>();
				contains.add("cnn");
				String path = "C:/output_store/";
				String querypath = "C:/queries/";
				String resultspath = "C:/queries/";
	        	String froot = "C:/Users/radodle/Documents/raj/";  
	        	String value = "crawler"; 
				Topics topics = null;

				
				
				System.out.println("Url that is crawled is : " +rootUrl); 
				for(String str : contains)
				{
				System.out.println("Conditions that should be meet to URL to pass : "+str);
				}
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
				 
				if(value.equals("crawler")){
					
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
					System.out.println("depth :"+crawler.getDepth());
					crawler.execute();
					System.out.println("Execution done");
					List<Document> filteredDocuments = new ArrayList<>();
					
					for (Document d : corpus) {
						contains.stream().filter( entry -> d.getSourceUrl().toString().contains(entry)).findFirst().ifPresent( c -> { System.out.println("matched Strings Document from Corpus: "+c);filteredDocuments.add(d);});
						//System.out.println("Unfiltered document" + d.getSourceUrl());                                                           
					}
					corpus.clear();
					corpus.addAll(filteredDocuments);
					}else{
						List<Document> docList=new ArrayList<Document>();
						File files =new File(froot);
				    	File[] fileList=files.listFiles();
				    	for(File f:fileList){
				    		String pdfFile =froot+f.getName();
				    	System.out.println(f.getName());
				    	System.out.println("FileNamePDF-----------------"+pdfFile);
				    	PdfReader reader = new PdfReader(pdfFile);

				       int pageNum = reader.getNumberOfPages();
				       StringBuffer sb = new StringBuffer();
				        for (int i=1; i<=pageNum; i++) {
				           String page = PdfTextExtractor.getTextFromPage(reader, i);
				          // System.out.println(page);
				           
				           sb.append(page);
				        }
				       Document d= Factory.newDocument(sb.toString());
				       docList.add(d);
				        } 
						corpus.clear();
						corpus.addAll(docList);
					}
					
					MultipartFileUploader multiPart = new MultipartFileUploader();
					int i= 0;
					DecimalFormat decimalFormat = new DecimalFormat("00000");
					Map<Integer,String> map=new HashMap<Integer,String>();
					StringBuffer strbfr= new StringBuffer();
					
					for(Document da : corpus)
					{	
						String fName = new SimpleDateFormat("yy-MM-dd-HHmm").format(new Date());
						String fPath="";
						String rPath="";
						String dname=""; 
						String fileName = da.getContent().toString();
						fPath= path+fName+i+"thisoutput.txt";
						PrintWriter f0 = new PrintWriter(new FileWriter(fPath));
						int split = fileName.indexOf("\n");
						if(split==-1){
							fileName="";
						}else{
							fileName = fileName.substring(0, split);
						}
						rPath = resultspath+fName+decimalFormat.format(i)+"reult.txt";
						File temp = File.createTempFile("tempfile", ".txt");
						 BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
				    	System.out.println(" File path: "+temp.getAbsolutePath());    
						 bw.write(da.getContent().toString());
						 f0.write(da.getContent().toString());
							f0.close();
							if(da.getSourceUrl()!=null){
							System.out.println(da.getSourceUrl());
						 dname=da.getSourceUrl().toString();
							}else{
							dname=froot;
							}
						System.out.println(" "+dname);
						 bw.write(dname);
				    	 bw.close();
						 i++;
						 String s = multiPart.sendFile(temp,rPath,dname,fileName);
						 System.out.println("s before map:------------------"+s);	
						 map.put(i, s);
						 strbfr.append(s);
						System.out.println("Return Response from server:------------------"+s);	
						System.out.println("R[path: "+rPath);
					 }
					 Writter w = new Writter();
					 w.write(map,querypath,rootUrl);
					/*for(Document da : corpus)
					{	
						String fName = new SimpleDateFormat("yy-MM-dd-HHmm").format(new Date());
						String fPath="";
						String rPath="";
						fPath= path+fName+i+"thisoutput.txt";
						rPath = resultspath+fName+i+"reult.txt";
						PrintWriter f0 = new PrintWriter(new FileWriter(fPath));
						//System.out.println("Content: "+da.getContent().toString());
						//System.out.println("--------------------------------------------------------------------------");
						f0.write(da.getContent().toString());
						f0.close();
						i++;
						multiPart.sendFile(new File(fPath),rPath);
						
					}*/
					//Starting term Raider..
                    File termRaiderPlugin = new File(Gate.getPluginsHome(), "TermRaider");
					System.out.println("plugin:" + termRaiderPlugin.getAbsolutePath());
					File gappFile = new File(new File(termRaiderPlugin, "applications"), "termraider-eng.gapp");
					CorpusController trApp = (CorpusController) PersistenceManager.loadObjectFromFile(gappFile);
					System.out.println("TermRaider existing");
					// running TermRaider plugin with the corpus
					trApp.init();
					trApp.setCorpus(corpus);
					trApp.execute();
					Corpus output_corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
					output_corpus = trApp.getCorpus();
					System.out.println("TermRaider executed successfully!!!");
					System.out.println(output_corpus.getName());
					List<String> ls = output_corpus.getDocumentNames();
					System.out.println("size:" + ls.size());
					/*for (String s : ls) {
						System.out.println("Contents of output corpus:" + s);
					}*/
				
					String filePath = createAndSaveFiles(output_corpus,path);
					CSVReader csvReader= new CSVReader();
				    RdfConnection rdfConnection = new RdfConnection();
					QueryGenerator queryGenerator = new QueryGenerator();
					csvReader.converCSVToXLSX(filePath);
					String query =queryGenerator.generateQueryAndSave(filePath,querypath);
				    rdfConnection.runQuery(query, querypath,rootUrl);
					
				
	} 
			
			

			private static String createAndSaveFiles(Corpus output_corpus,String path) throws GateException {
				String filePath="";
				FeatureBearer tb1 = (FeatureBearer) output_corpus.getFeatures().get("tfidfTermbank");
				AbstractTermbank tb4 = (AbstractTermbank) output_corpus.getFeatures().get(2);
				AbstractTermbank tb2 = (AbstractTermbank) output_corpus.getFeatures().get("hyponymyTermbank");
				AbstractTermbank tb3 = (AbstractTermbank) output_corpus.getFeatures().get("annotationTermbank");
				System.out.println("tb1:" + tb1);
				System.out.println("tb2:" + tb2);
				System.out.println("tb3:" + tb3);
				String fileName = new SimpleDateFormat("yy-MM-dd-HHmmss").format(new Date());
				filePath= path+fileName+"_tfidfTermbank.csv";
				CsvGenerator generator = new CsvGenerator();
				File outputFile1 = new File(path+fileName+"_tfidfTermbank.csv");
				File outputFile2 = new File(path+fileName+"_hyponymyTermbank.csv");
				File outputFile3 = new File(path+fileName+"_annotationTermbank.csv");
				double threshold1 = 0;
				double threshold2 = 0;
				double threshold3 = 0;
				generator.generateAndSaveCsv((AbstractTermbank) tb1, threshold1, outputFile1);
				generator.generateAndSaveCsv(tb2, threshold2, outputFile2);
				generator.generateAndSaveCsv(tb3, threshold3, outputFile3);
				System.out.println("CSV files created!!!");
				System.out.println("Term Raider output is stored in : "+path);
				return filePath;
			}
			
				private static void creteCrawler(final String text_data, crawl.CrawlPR crawler) {
				crawler.setKeywords(null);
				crawler.setKeywordsCaseSensitive(true); 
				crawler.setConvertXmlTypes(true);
				crawler.setRoot(text_data);
				crawler.setDomain(crawl.DomainMode.WEB);
				crawler.setDepth(2);
				crawler.setDfs(true);
				crawler.setStopAfter(4);
			
			}
		

	
	

	public void setTaskExector(AsyncTaskExecutor executor) throws IOException {
		this.threadPoolExecutor = executor;
	}
		
	
	}

/*{
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


