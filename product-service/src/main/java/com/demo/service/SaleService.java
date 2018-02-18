package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Items;
import com.demo.entities.Product;
import com.demo.repository.ItemsRepository;
import com.demo.repository.ProductRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SaleService {
	
	@Autowired
	ItemsRepository itemRepo;
	
	@Autowired
	ProductRepo prodRepo;
	
	public Product saleItem(int prodId) {
		
		Items item = itemRepo.findByProductId(prodId);
		if(null != item) {
			if (item.getCount() <=0) {
				throw new RuntimeException("Item Stock Nil. Please import");
			}
			item.setCount(item.getCount()-1);
			itemRepo.save(item);
			
			return prodRepo.findOne(item.getProductId());
		}else {
			throw new RuntimeException("Item Not registered with system"
					+ ". Raise request for stock");
		}
	}
	
}
