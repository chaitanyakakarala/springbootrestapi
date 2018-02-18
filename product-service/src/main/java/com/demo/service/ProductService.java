package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Items;
import com.demo.entities.Product;
import com.demo.repository.ItemsRepository;
import com.demo.repository.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	ItemsRepository itemsRepo;
	
	public Product addProduct(String input) {
		Product product = null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			product = mapper.readValue(input.getBytes(), Product.class);
			
			return productRepo.save(product);
		}catch(Exception exp ) {
			log.error(exp.getMessage(),exp);
			throw new RuntimeException(exp);
		}
	}
	
	public Product updateProduct(int id,String input) {
		 Product product = productRepo.findOne(id);
		 try {
			 if(null != product) {
				 ObjectMapper mapper = new ObjectMapper();
				 product = mapper.readValue(input.getBytes(), Product.class);
				 product.setId(id);
			 }else {
				 log.error(String.format("%s not existing in system", id));
				 throw new RuntimeException(String.format("%s not existing in system", id));
			 }
			 return productRepo.save(product);
		 }catch(Exception exp) {
			 log.error(exp.getMessage(),exp);
			 throw new RuntimeException(exp);
		 }
	}
	
	public List<Product> search(String name) {
		
		return productRepo.findByNameContains(name);
	}
	
	public List<Product> listAll() {
		
		return productRepo.findAll();
	}
	
	public Items acquireProduct(String itemsJson) {
		 ObjectMapper mapper = new ObjectMapper();
		 Items items = null;
		 try{
			 items = mapper.readValue(itemsJson.getBytes(), Items.class);
			 if(null != items) {
				 Items existing = itemsRepo.findByProductId(items.getProductId());
				 if(null != existing) {
					 existing.setCount(existing.getCount() + items.getCount());
					 return itemsRepo.save(existing);
				 }else {
					 return itemsRepo.save(items);
				 }
			 }else {
				 log.error("Improper format json ... Please post valid json ");
				 throw new RuntimeException("Improper format json ... Please post valid json ");
			 }
		 }catch(Exception exp) {
			 log.error(exp.getMessage(),exp);
			 throw new RuntimeException(exp.getMessage());
		 }
	}
	
}
