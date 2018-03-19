package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaDemo1Application implements CommandLineRunner{

	 @Autowired
	  private KafkaTemplate<String, String> kafkaTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaDemo1Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("we are in run method");
		    kafkaTemplate.send("test", "somesamplepayload");
	}
}
