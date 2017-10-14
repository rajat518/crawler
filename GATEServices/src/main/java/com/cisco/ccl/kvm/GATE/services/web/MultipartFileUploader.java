package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class MultipartFileUploader {
	
 /*public static void  main(String args[]) throws Exception {
     File f = new File("C:/queries/_tdif_terms.sparql");
     sendFile(f);
 }*/
	
    public  String sendFile(File file, String resultPath, String url1, String  fileName) {
        String charset = "UTF-8";
        File uploadFile1 = new File("C:/queries/_tdif_terms.sparql");
        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost ("http://ccl-kv-dev2:8085/gate/showcase/topics/from-file") ;
        String requestURL = "http://ccl-kv-dev2:8085/gate/showcase/topics/from-file";
        String boundary = "===" + System.currentTimeMillis() + "===";
        String topics ="";
 try {
        URL url = new URL("http://ccl-kv-dev2:8085/gate/showcase/topics/from-file");
        MultipartEntity multiPartEntity = new MultipartEntity ();
       // postRequest.addHeader("Content-Type","multipart/form-data");
        		postRequest.addHeader("boundary",boundary);
        		System.out.println("The boundry: "+boundary);
        		postRequest.addHeader("Connection","Close");
         FileBody fileBody = new FileBody(file, "application/octect-stream") ;
         //Prepare payload
         multiPartEntity.addPart("file", fileBody) ;
         //Set to request body
         postRequest.setEntity(multiPartEntity) ;         
         //Send request
         HttpResponse response = client.execute(postRequest) ;
         if (response != null)
         {
             System.out.println(response.getStatusLine().getStatusCode());
         }
         BufferedReader br = new BufferedReader(
                 new InputStreamReader((response.getEntity().getContent())));

         System.out.println("Output from Server .... \n");
         		BufferedWriter bos = null;
         		StringBuffer brr = new StringBuffer();
         		bos = new BufferedWriter(new FileWriter(resultPath)); 
         		bos.write("Title:"+fileName);
         		bos.newLine();
         		bos.write("Url:"+url1);
         		bos.newLine();
         		String s;
         		
         		int i=0;
         		while( (topics = br.readLine()) != null) 
         			{
         			brr.append(topics);
         			bos.write(topics);
         			bos.newLine();
         			i++;
         			}
				// System.out.println("Final Response------------------"+brr.toString());
         			bos.close();
         			return brr.toString();
 					} 
  	catch (MalformedURLException e) 
 				{
	  				e.printStackTrace();

  				} 
 	catch (IOException e) 
 				{

 					e.printStackTrace();
 				}
 return topics;
    }
}
