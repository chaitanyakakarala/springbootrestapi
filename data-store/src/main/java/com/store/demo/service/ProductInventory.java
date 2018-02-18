package com.store.demo.service;

import com.store.demo.entity.ProductEntity;

public interface ProductInventory {

	ProductEntity getProductDetails(String id);
	
	String updateProductDetails(String id,String body);

	
}
