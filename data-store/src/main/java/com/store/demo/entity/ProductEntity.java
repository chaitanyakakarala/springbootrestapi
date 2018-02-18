package com.store.demo.entity;

import java.util.HashMap;
import java.util.Map;

public class ProductEntity {

	private String id;
	
	private String name;
	
	private Map<String,String> current_price = new HashMap<String,String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(Map<String, String> current_price) {
		this.current_price = current_price;
	}
	
	
}
