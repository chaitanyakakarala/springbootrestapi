package com.store.demo.repository;

import java.util.Map;

public interface ProductRepository {

	String getProductPriceById(String productId);

	String updateProductPrice(Map<String,String> currency_price,String id);
}
