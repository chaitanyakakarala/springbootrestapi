package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.Product;
import com.demo.service.CustomerService;
import com.demo.service.ProductService;
import com.demo.service.SaleService;

@RestController
public class ProductController {

	@Autowired
	ProductService prodServ;
	
	@Autowired
	SaleService saleService;
	
	@Autowired
	CustomerService customerService;
	
	//Search the item as take the name search with like operator in db table 
	//return the list of entities for a match.
	@RequestMapping(path="/search/{name}" , method = RequestMethod.GET)
	public List<Product> searchItemByName(@PathVariable(name="name" , required = false)String name){
		if(null != name) {
			return prodServ.search(name);	
		}else {
			return prodServ.listAll();	
		}
	}

	//Sell means take the product id from the body 
	//check the valid id
	//check the count > 0
	//If yes reduce count by one and return product entity
	//Takes input of product json as a body
	@RequestMapping(path="/sell/{prodid}",method=RequestMethod.POST)
	 public Product sellItem(@PathVariable(name = "prodid") Integer prodid
			 ,@RequestBody String customerDetails) {
		//First sale the item and reduce the count
		Product prodSold = saleService.saleItem(prodid);	
		//Second .Call the customer service end point for registry
		customerService.registerCustomer(customerDetails , prodSold);

		return prodSold;
		 
	 }
	 
}
