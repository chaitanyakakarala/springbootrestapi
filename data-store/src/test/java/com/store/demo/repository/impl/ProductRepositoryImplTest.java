package com.store.demo.repository.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.store.demo.repository.impl.ProductRepositoryImpl;

import static junit.framework.Assert.assertEquals;;

public class ProductRepositoryImplTest {

	@InjectMocks
	ProductRepositoryImpl repoImpl;
	
	@Mock
	MongoClient mongoClient;
	
	@Mock
	DB db;
	
	@Mock
	DBCollection table;
	
	@Mock
	DBCursor cursor;
	
	@Mock
	DBObject dbObj;

	@Mock
	WriteResult res;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repoImpl = new ProductRepositoryImpl(mongoClient);
		repoImpl.dbName = "local";
		repoImpl.collection = "product";
		repoImpl.mongoClient = mongoClient;

		//Mocking method calls
		Mockito.when(mongoClient.getDB("local")).thenReturn(db);
		Mockito.when(db.getCollection("product")).thenReturn(table);
		Mockito.when(table.find(Mockito.any(BasicDBObject.class)))
				.thenReturn(cursor);
		Mockito.when(cursor.hasNext()).thenReturn(true);
		Mockito.when(cursor.next()).thenReturn(dbObj);
		
	}

	@Test
	public void testgetProductPriceById() {
		String product = repoImpl.getProductPriceById("someid");
		assertEquals("dbObj", product);
	}
	
	@Test(expected = Exception.class)
	public void testgetProductPriceById_Excep() {
		Mockito.when(mongoClient.getDB("local"))
		.thenThrow(new RuntimeException("failed to connect DB"));
		repoImpl.getProductPriceById("someid");
	}
	

	
	@Test
	public void testupdateProductPrice() {
		Mockito.doReturn(res)
				.when(table).update(Mockito.any(DBObject.class), 
							Mockito.any(DBObject.class),
							Mockito.anyBoolean(),
							Mockito.anyBoolean());
		Map<String,String> current_price = new HashMap<String,String>();
		current_price.put("current_price","123");
		current_price.put("currency_code", "EUR");
		
		String id = repoImpl.updateProductPrice(current_price, "testid");
	
		assertEquals("testid", id);
	}

	@Test(expected = Exception.class)
	public void testupdateProductPriceExcep() {
		Mockito.doThrow(new RuntimeException())
				.when(table).update(Mockito.any(DBObject.class), 
							Mockito.any(DBObject.class),
							Mockito.anyBoolean(),
							Mockito.anyBoolean());
		Map<String,String> current_price = new HashMap<String,String>();
		current_price.put("current_price","123");
		current_price.put("currency_code", "EUR");
		
		repoImpl.updateProductPrice(current_price, "testid");
	
	}	
	
}
