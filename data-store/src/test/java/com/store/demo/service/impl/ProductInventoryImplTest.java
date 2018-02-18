package com.store.demo.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.store.demo.repository.impl.ProductRepositoryImpl;
import com.store.demo.service.impl.ProductInventoryImpl;

public class ProductInventoryImplTest {
	
	@Mock
	ProductRepositoryImpl repo;
	
	@InjectMocks
	ProductInventoryImpl inventory;

	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		inventory = Mockito.spy(new ProductInventoryImpl(repo));
		inventory.jsonPath = "$['product']['item']['product_description']['title']";
		inventory.currentPrice = "$['current_price']";
		inventory.currencyCode = "$['currency_code']";
	}
	
	@Test
	public void testgetProductDetails() {
		Mockito.when(repo.getProductPriceById("someid"))
				.thenReturn("{'productid':'someid','current_price':'123','currency_code':'USD'}");
		inventory.getProductDetails("someid");
	}
	
	@Test(expected = Exception.class)
	public void testgetProductDetails_Excep() {
		Mockito.when(repo.getProductPriceById("someid"))
				.thenReturn("");
		inventory.getProductDetails("someid");
	}
	
	@Test
	public void testupdateProductDetails() {
		Mockito.when(repo.getProductPriceById("someid"))
			.thenReturn("{'productid':'someid','current_price':'123','currency_code':'USD'}");
		Mockito.when(repo.updateProductPrice(Mockito.anyMap(), Mockito.anyString()))
			.thenReturn("{'productid':'someid','current_price':'123','currency_code':'USD'}");

		String newBody = inventory.updateProductDetails("someid", "{\"productid\":\"someid\",\"current_price\":{\"current_price\":\"456\",\"currency_code\":\"USD\"}}");
		Assert.assertEquals("{\"productid\":\"someid\",\"current_price\":{\"current_price\":\"456\",\"currency_code\":\"USD\"}}", newBody);
		
	}
	
	@Test(expected = Exception.class)
	public void testupdateProductDetails_Excep() {
		Mockito.when(repo.getProductPriceById("someid"))
		.thenReturn("{'productid':'someid','current_price':'123','currency_code':'USD'}");
	Mockito.when(repo.updateProductPrice(Mockito.anyMap(), Mockito.anyString()))
		.thenThrow(new RuntimeException());

	inventory.updateProductDetails("someid", "{\"productid\":\"someid\",\"current_price\":{\"current_price\":\"456\",\"currency_code\":\"USD\"}}");
	}
}
