package com.store.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.store.demo.service.ProductInventory;

@RestController
public class ProductController {

	public static Logger log = LoggerFactory.getLogger(ProductController.class);
	
	ProductInventory inventory;
	
	@Autowired
	public ProductController(ProductInventory inventory) {
		this.inventory = inventory;
	}
	
	@RequestMapping(method = RequestMethod.GET , path = "/products/{id}")
	public ResponseEntity<String> viewProductDetails(
			@PathVariable(name = "id" , required = true) String id) {
		log.info("Getting details for product {}",id);
		
		return ResponseEntity.status(HttpStatus.OK)
							.body(new Gson().toJson(inventory.getProductDetails(id)));
	}
	
	@RequestMapping(method = RequestMethod.PUT , path = "/products/{id}")
	public ResponseEntity<String> updateProductdetails(
			@PathVariable(name = "id" , required = true)String id,
			@RequestBody String jsonBody) {
		log.info("Getting details for {} with {}",id,jsonBody);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Gson().toJson(inventory.updateProductDetails(id, jsonBody)));
	}
	
}
