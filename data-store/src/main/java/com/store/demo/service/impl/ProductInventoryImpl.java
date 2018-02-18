package com.store.demo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.store.demo.entity.ProductEntity;
import com.store.demo.repository.impl.ProductRepositoryImpl;
import com.store.demo.service.ProductInventory;
import com.store.demo.util.DataStoreUtil;

@Service
public class ProductInventoryImpl implements ProductInventory{

	public static Logger log = LoggerFactory.getLogger(ProductInventoryImpl.class);
	
	@Value("${datastore.prod.json.path}")
	public String jsonPath;
	
	@Value("${datastore.prod.json.path.current_price}")
	public String currentPrice;
	
	@Value("${datastore.prod.json.path.currency}")
	public String currencyCode;
	
	private ProductRepositoryImpl repository;
	
	@Autowired
	public ProductInventoryImpl(ProductRepositoryImpl repository) {
		this.repository = repository;
	}
	
	@Override
	public ProductEntity getProductDetails(String id) {
		
		log.info("Getting product details for '{}'",id);
		try {
			
			String jsonForName = FileUtils.readFileToString(new ClassPathResource("data.json").getFile(), "UTF-8");
			String jsonForPrice = repository.getProductPriceById(id);
			
			Map<String,String> current_price = new HashMap<String,String>(2);
			if(null != jsonForPrice) {
				current_price.put("current_price", DataStoreUtil.calculateJsonPath(jsonForPrice, currentPrice).toString());
				current_price.put("currency_code", DataStoreUtil.calculateJsonPath(jsonForPrice, currencyCode).toString());	
			} 
			
			if(current_price.isEmpty()) {
				log.error("No pricing details found for '{}'",id);
				throw new RuntimeException("Pricing details not found for id "+id);
			}
				
			
			ProductEntity entity = new ProductEntity();
			entity.setId(id);
			entity.setName(DataStoreUtil.calculateJsonPath(jsonForName, jsonPath));
			entity.setCurrent_price(current_price);
			
			return entity;
			
		}catch(Exception exp) {
			log.error(exp.getMessage(),exp);
			throw new RuntimeException(exp.getMessage());
		}
		
	}

	@Override
	public String updateProductDetails(String id, String body) {
		
		log.info("Updating details for '{}'",id);
		
		String jsonForPrice = repository.getProductPriceById(id);
		if(null == jsonForPrice || jsonForPrice.isEmpty()) {
			log.info("No product with '{}'",id);
			throw new RuntimeException("No product detail found for "+id);
		}
		
		String newPrice = DataStoreUtil.calculateJsonPath(body, "['current_price']['current_price']");
		String newCurncy = DataStoreUtil.calculateJsonPath(body, "['current_price']['currency_code']");
		
		Map<String,String> current_price = new HashMap<String,String>(2);
		if(null != jsonForPrice) {
			current_price.put("current_price", newPrice );
			current_price.put("currency_code", newCurncy);	
		}
		
		repository.updateProductPrice(current_price, id);
		log.info("Updated product '{}' with new json '{}'",id,body);
		
		return body;
	}

}
