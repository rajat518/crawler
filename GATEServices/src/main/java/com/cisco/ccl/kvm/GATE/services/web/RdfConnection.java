package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RdfConnection {

    public void runQuery(String args,String path,String rootUrl) throws Exception {
        System.out.print("Connecting to Stardgo Db : master\n");
    	String fpath = path+"_tdif_terms.sparql";
        String url = "http://ccl-kv-dev3:5820/master/query";
        String content1 = new String(Files.readAllBytes(Paths.get(fpath))); 
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Accept", "text/plain");
        conn.setRequestProperty("Method", "POST");

        //conn.setRequestMethod("POST");

        String userpass = "admin" + ":" + "admin";
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
        conn.setRequestProperty ("Authorization", basicAuth);
// construct {?ss ?p ?o} where { { select distinct (coalesce(?URI1, ?URI2, ?URI3, ?URI4) as ?s) ?p ?o where { bind(rdfs:label as ?p) {values ?o { "audio webinar" "personal information" "networking student""partner program guide"  "cisco refresh program" }Values ?what {' and |[ ()_&/-]'} bind(replace(lcase(?o), ?what, '') as ?pattern) } OPTIONAL {graph <cmp:map> {?URI1 km:map ?pattern} ?topicURI1 owl:sameAs []} OPTIONAL {graph <cmp:map> {?URI2 km:map ?pattern}} OPTIONAL {graph <cmp:alt> {?URI3 km:alt ?pattern}} OPTIONAL  { ?URI4 a km:Undefined  ; rdfs:label ?L filter (replace(lcase(?L), ?what, '')=?pattern) } } } bind(if(!bound(?s), iri(md5(?o)), ?s) as ?ss) filter (!bound(?s)) }
        
        String data =  new StringBuilder().append("query=").append(URLEncoder.encode(content1,"UTF-8")).toString();
        //System.out.println(data);
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(data);
        out.close();
        String s3 = null;
		s3 = rootUrl.replaceAll("/", "_").replaceAll("//", "_").replaceAll("[^a-zA-Z0-9.-]", "_").substring(10,22);
        String dname = new SimpleDateFormat("yy-MM-dd-HHmmss").format(new Date());
        String queryPath = path+dname+s3+"finalresults.ttl";
      
        System.out.print("Connected to Stardog and running the Query and saving the results\n");
        BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
       BufferedWriter bos = null;
       bos = new BufferedWriter(new FileWriter(queryPath)); // 17-10-07-1817w.indeed.comfinalresults.ttl 
        String s;
        while( (s = in.readLine()) != null) {
            //System.out.println(s);
            bos.write(s);
            bos.newLine();
        }
        bos.close();
        System.out.print("Results are saved in : "+queryPath);

    }

}
