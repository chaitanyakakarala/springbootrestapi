package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entities.Items;

@Repository
public interface ItemsRepository extends JpaRepository<Items,Integer>{

	Items findByProductId(int prodId);
}
