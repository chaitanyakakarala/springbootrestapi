package com.store.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class AppConfig {

	@Value("${datastore.mongo.host}")
	String host;
	
	@Value("${datastore.mongo.port}")
	int port;
	
	@Bean
	public MongoClient mongoClient() throws Exception {
		return new MongoClient(host,port);
	}
	
}
