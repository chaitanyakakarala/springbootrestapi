package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.CustomerDetails;
import com.demo.repositories.CustomerDetailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {

	@Autowired
	CustomerDetailsRepository repository;

	public List<CustomerDetails> listByName(String customerName) {
		
		return repository.findByNameContains(customerName);
	}
	
	public List<CustomerDetails> listAll(){
		return repository.findAll();
	}
	
	public CustomerDetails addToCustomerRegistry(String customerJson){
		CustomerDetails customerDetails = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			customerDetails = mapper.readValue(customerJson.getBytes(), CustomerDetails.class);
			if(null != customerDetails) {
				return repository.save(customerDetails);
			} else {
				log.error("Please provide valid inputs");
				throw new RuntimeException("Please provide valid inputs");
			}
		} catch (Exception exp) {
			
			log.error(exp.getMessage(),exp);
			throw new RuntimeException(exp.getMessage());
			
		}
	}
}
