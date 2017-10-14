package com.cisco.ccl.kvm.GATE.services.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Writter {

	public void write(Map file,String Fpath,String rootUrl) throws IOException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("@prefix : <http://api.stardog.com/> .").append("\n");
		sb.append("@prefix afn: <http://jena.hpl.hp.com/ARQ/function#> .").append("\n");
		sb.append("@prefix apf: <http://jena.hpl.hp.com/ARQ/property#> .").append("\n");
		sb.append("@prefix asset: <http://clks.cisco.com/km/asset/> .").append("\n");
		sb.append("@prefix bibo: <http://purl.org/ontology/bibo/> .").append("\n");
		sb.append("@prefix cco: <http://purl.org/ontology/cco/core#> .").append("\n");
		sb.append("@prefix context: <tag:stardog:api:context:> .").append("\n");
		sb.append("@prefix course: <http://clks.cisco.com/km/course/> .").append("\n");
		sb.append("@prefix dbcategory: <http://dbpedia.org/resource/Category:> .").append("\n");
		sb.append("@prefix dbpedia: <http://dbpedia.org/resource/> .").append("\n");
		sb.append("@prefix dc: <http://purl.org/dc/elements/1.1/> .").append("\n");
		sb.append("@prefix dcterms: <http://purl.org/dc/terms/> .").append("\n");
		sb.append("@prefix dctype: <http://purl.org/dc/dcmitype/> .").append("\n");
		sb.append("@prefix department: <http://clks.cisco.com/km/department/> .").append("\n");
		sb.append("@prefix event: <http://purl.org/NET/c4dm/event.owl#> .").append("\n");
		sb.append("@prefix fb: <http://rdf.basekb.com/ns/> .").append("\n");
		sb.append("@prefix fn: <http://www.w3.org/2005/xpath-functions#> .").append("\n");
		sb.append("@prefix foaf: <http://xmlns.com/foaf/0.1/> .").append("\n");
		sb.append("@prefix group: <http://clks.cisco.com/km/group/> .").append("\n");
		sb.append("@prefix industry: <http://clks.cisco.com/km/industry/> .").append("\n");
		sb.append("@prefix job-role: <http://clks.cisco.com/km/job-role/> .").append("\n");
		sb.append("@prefix km: <http://clks.cisco.com/km/> .").append("\n");
		sb.append("@prefix labm: <http://labm/> .").append("\n");
		sb.append("@prefix lifecycle: <http://purl.org/vocab/lifecycle/schema#> .").append("\n");
		sb.append("@prefix location: <http://clks.cisco.com/km/location/> .").append("\n");
		sb.append("@prefix maturity: <http://clks.cisco.com/km/maturity/> .").append("\n");
		sb.append("@prefix mdf: <http://mdf/> .").append("\n");
		sb.append("@prefix org: <http://www.w3.org/ns/org#> .").append("\n");
		sb.append("@prefix owl: <http://www.w3.org/2002/07/owl#> .").append("\n");
		sb.append("@prefix prov: <http://www.w3.org/ns/prov#> .").append("\n");
		sb.append("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .").append("\n");
		sb.append("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .").append("\n");
		sb.append("@prefix sdca: <http://sdca/> .").append("\n");
		sb.append("@prefix sio: <http://semanticscience.org/resource/> .").append("\n");
		sb.append("@prefix sioc: <http://rdfs.org/sioc/ns#> .").append("\n");
		sb.append("@prefix sioct: <http://rdfs.org/sioc/types#> .").append("\n");
		sb.append("@prefix skill: <http://clks.cisco.com/km/skill/> .").append("\n");
		sb.append("@prefix skos: <http://www.w3.org/2004/02/skos/core#> .").append("\n");
		sb.append("@prefix stardog: <tag:stardog:api:> .").append("\n");
		sb.append("@prefix swrlb: <http://www.w3.org/2003/11/swrlb#> .").append("\n");
		sb.append("@prefix umbel: <http://umbel.org/umbel#> .").append("\n");
		sb.append("@prefix user: <http://clks.cisco.com/km/user/> .").append("\n");
		sb.append("@prefix vcard: <http://www.w3.org/2006/vcard/ns#> .").append("\n");
		sb.append("@prefix vivo: <http://vivoweb.org/ontology/core#> .").append("\n");
		sb.append("@prefix voag: <http://voag.linkedmodel.org/voag#> .").append("\n");
		sb.append("@prefix void: <http://rdfs.org/ns/void#> .").append("\n");
		sb.append("@prefix wand: <http://wandinc.com/> .").append("\n");
		sb.append("@prefix wo: <http://purl.org/ontology/wo/core#> .").append("\n");
		sb.append("@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .").append("\n\n");
		int i=0;
		System.out.println("here in writeer");
		//String[]lines = file.split(System.getProperty("line.separator"));
		System.out.println("file size is "+file.size());
		for(int j=0;j<file.size();j++){
		  String line =(String) file.get(j);
		  System.out.println("LIne----------------------------"+line);
		  if(line!=null){
				line =line.substring(line.indexOf(":")+1, line.length());
			// DecimalFormat decimalFormat = new DecimalFormat("00000");
			  //System.out.println("Using DecimalFormat: " + decimalFormat.format(j));
				String wName = new SimpleDateFormat("yyMMddHHmm").format(new Date());
			sb.append("km:tg"+wName+i).append("\t").append("\tkm:tags\t").append("\t").append("\"").append(line.replace("}","").replace("\"","\\\"")).append(",\".").append("\n\n");
			i++;
			//"[\"informationscience\",\"ontology\"]," . n  
		  }
		}
		String s2 = null;
		s2 = rootUrl.replaceAll("[^a-zA-Z0-9.-]", "_").substring(6);
		String dname = new SimpleDateFormat("yy-MM-dd-HHmmss").format(new Date());
		String fp = Fpath+dname+s2+"tagger_results.ttl";
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(fp)));
		//System.out.println(br.toStrinig());
		bwr.write(sb.toString());
		bwr.flush();
		bwr.close();
		System.out.println("Datamined Tags of Url "+s2+" are stored in : "+fp);
		System.out.println("Stored in : "+fp);
	}
}
