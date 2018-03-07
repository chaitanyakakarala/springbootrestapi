package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	@Autowired
	PersonRepository repository;
	
	@GetMapping
	public Iterable<Person> getPersons() {
		return repository.findAll();
	}
	
}
