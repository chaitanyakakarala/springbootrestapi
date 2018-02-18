package com.store.demo.repository.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.store.demo.controller.ProductController;
import com.store.demo.repository.ProductRepository;

@Service
public class ProductRepositoryImpl implements ProductRepository{

	public static Logger log = LoggerFactory.getLogger(ProductRepositoryImpl.class);
	
	MongoClient mongoClient;
	
	@Value("${datastore.mongo.dbname}")
	public String dbName;
	
	@Value("${datastore.mongo.collection}")
	public String collection;
	
	@Autowired
	public ProductRepositoryImpl(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	@Override
	public String getProductPriceById(String productId) {
		DBCollection table = mongoClient.getDB(dbName).getCollection(collection);
		
		BasicDBObject obj = new BasicDBObject();
		obj.put("productid", productId);
		//{"productid",""+productId}
		log.info("Updating product '{}'",productId);
		
		DBCursor cursor = table.find(obj);//db.product.find({"productid":"13860428"})
		while(cursor.hasNext()) {
			
			String name = cursor.next().toString();
			log.info("Name '{}' for '{}'",productId,name);
			
			return name;
		}

		return null;
	}

	@Override
	public String updateProductPrice(Map<String,String> currency_price,String id) {
		
		log.info("Updating product '{}' with pricing '{}'",id ,currency_price);
		DBCollection table = mongoClient.getDB(dbName).getCollection(collection);
		
		BasicDBObject old = new BasicDBObject();
		old.put("productid", id);
		
		BasicDBObject newObj = new BasicDBObject();
		newObj.put("productid", id);
		newObj.put("current_price", currency_price.get("current_price"));
		newObj.put("currency_code", currency_price.get("currency_code"));
		
		table.update(old, newObj, false, false);
		log.info("Updating completed for '{}'",id);
		
		return id;
	}
	
}
