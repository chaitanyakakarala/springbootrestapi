package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.CustomerDetails;
import com.demo.service.CustomerService;


@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(path="/search/{name}" , method = RequestMethod.GET)
	public List<CustomerDetails> getCustomerDetails(@PathVariable(name="name" , required = false)String name){
		
		if(null != name) {
			return customerService.listByName(name);
		}else {
			return customerService.listAll();
		}
	}
	
	@RequestMapping(path="/insert",method=RequestMethod.POST)
	public ResponseEntity<CustomerDetails> enterDetails(@RequestBody String CustomerDetails) {
		
		
		return new ResponseEntity<CustomerDetails>(customerService.addToCustomerRegistry(CustomerDetails),
				HttpStatus.CREATED);
		
	}
}