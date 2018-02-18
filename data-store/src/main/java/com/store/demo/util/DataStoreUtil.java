package com.store.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class DataStoreUtil {

	public static String makeJsonFromObj(Object obj)throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);

	}

	
	public static String calculateJsonPath(String json , String jsonPath) {
		
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		return JsonPath.read(document, jsonPath).toString();
	}
/*
	public static void main(String[] args) {
		
	}*/
	
	
/*	public static void main(String[] args) throws IOException {
		String json = FileUtils.readFileToString(new File("src/main/resources/data.json"), "UTF-8");
		
		System.out.println(calculateJsonPath(json, "$['product']['item']['product_description']['title']"));
	}*/
	
/*	public static void main(String... args)throws Exception {
		// TODO Auto-generated method stub
		 * {"name":"namefromjsondb","id":"someoid","current_price":"anothermaphere"}
		makeJsonFromObj(null);
	}*/
}
