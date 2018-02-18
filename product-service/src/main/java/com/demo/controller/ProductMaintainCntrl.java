package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.Items;
import com.demo.entities.Product;
import com.demo.service.ProductService;

@RestController
public class ProductMaintainCntrl {

	@Autowired
	ProductService prodService;

	@RequestMapping(path = "/add", method = RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@RequestBody(required = true) String product) {

		return new ResponseEntity<Product>(prodService.addProduct(product),
				HttpStatus.CREATED);
	}

	@RequestMapping(path = "/import", method = RequestMethod.POST)
	public ResponseEntity<Items> acquireItems(@RequestBody(required = true) String itemsBody) {
		
		return new ResponseEntity<Items>(prodService.acquireProduct(itemsBody),
				HttpStatus.CREATED);
	}
	
	@RequestMapping(path = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@PathVariable(name = "id") int id,
			@RequestBody(required = true) String product) {

		return new ResponseEntity<Product>(prodService.updateProduct(id, product),
				HttpStatus.CREATED);
	}
}
