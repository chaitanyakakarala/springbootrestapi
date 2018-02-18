/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,Archimedes Trajano	(trajano@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.store.intcase;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.store.demo.controller.ProductController;
import com.store.demo.repository.impl.ProductRepositoryImpl;
import com.store.demo.service.ProductInventory;
import com.store.demo.service.impl.ProductInventoryImpl;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import junit.framework.Assert;

@SuppressWarnings(value = { "deprecation" })
/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = DataStoreApplication.class)*/
public class TestExampleReadMeCode {

	static MongodExecutable mongodExecutable = null;
	
	@BeforeClass
	public static void testStandard() throws IOException {
		// ->
		Map<String,String> args = new HashMap<String,String>();
		MongodStarter starter = MongodStarter.getDefaultInstance();

		int port = 27017;
		IMongodConfig mongodConfig = new MongodConfigBuilder()
				.version(Version.Main.PRODUCTION)
				.net(new Net(port, Network.localhostIsIPv6()))
				.withLaunchArgument("--storageEngine", "mmapv1")
				.build();

		
		try {
			mongodExecutable = starter.prepare(mongodConfig);
			MongodProcess mongod = mongodExecutable.start();

			MongoClient mongo = new MongoClient("localhost", port);
			DB db = mongo.getDB("local");
			BasicDBObject obj = new BasicDBObject();
			obj.put("productid", "someid");
			obj.put("name", "somename");
			obj.put("current_price", 123);
			obj.put("currency_code", "USD");
			
			DBCollection col = db.createCollection("product", new BasicDBObject());
			col.save(obj);

			
		} finally {
			
		}
	}
	
	MongoClient clent = null;
	
	ProductRepositoryImpl repo = null;
	
	ProductInventoryImpl inventory = null;
	
	ProductController controler = null;
	
	@Test
	public void test() throws Exception {
		//SpringApplication.run(DataStoreApplication.class, new String[] {});
		Assert.assertEquals(1, 1);
		clent = new MongoClient("localhost", 27017);
		repo = new ProductRepositoryImpl(clent);
		repo.dbName = "local";
		repo.collection = "product";
		
		inventory = new ProductInventoryImpl(repo);
		inventory.jsonPath = "$['product']['item']['product_description']['title']";
		inventory.currentPrice = "$['current_price']";
		inventory.currencyCode = "$['currency_code']";

		controler = new ProductController(inventory);
		ResponseEntity<String> response = controler.viewProductDetails("someid");
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode().toString(), HttpStatus.OK.toString());
		Assert.assertTrue(response.getBody().contains("{\"id\":\"someid\",\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"currency_code\":\"USD\",\"current_price\":\"123\"}}"));
	
		response = controler.updateProductdetails("someid", "{\"id\":\"someid\",\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"currency_code\":\"USD\",\"current_price\":\"567\"}}");
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode().toString(), HttpStatus.ACCEPTED.toString());
		Assert.assertTrue(response.getBody().contains("{\\\"id\\\":\\\"someid\\\",\\\"name\\\":\\\"The Big Lebowski (Blu-ray)\\\",\\\"current_price\\\":{\\\"currency_code\\\":\\\"USD\\\",\\\"current_price\\\":\\\"567\\\"}}"));
	}
	
	@AfterClass
	public static void stopMongo() {
		if (mongodExecutable != null)
			mongodExecutable.stop();
	}
	

}