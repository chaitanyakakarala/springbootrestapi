package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

	List<Product> findByNameContains(String name);
	
}
