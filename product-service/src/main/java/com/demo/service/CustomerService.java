package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.entities.CustomerDetails;
import com.demo.entities.Product;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {

	@Autowired
	RestTemplate template;
	
	@Value("${demo.custServUrl}")
	String customerSevUrl;
	
	
	public void registerCustomer(String customerDetailstr, Product productSold) {
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setName(customerDetailstr);
		customerDetails.setPurchasedetails(productSold.getName() + productSold.getPrice());
		customerDetails.setRemarks(productSold.getPrice().toString());
		try{
			String response = template.postForObject(customerSevUrl, customerDetails, String.class);
			System.out.println(response);
		}catch(Exception exp) {
			log.error(exp.getMessage(),exp);
		}
		return ;
	}
	
}
