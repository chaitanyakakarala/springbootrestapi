package com.store.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.store.demo.controller.ProductController;
import com.store.demo.entity.ProductEntity;
import com.store.demo.service.ProductInventory;

public class ProductControllerTest {

	@Mock
	ProductInventory inv;

	@InjectMocks
	ProductController controller;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = Mockito.spy(new ProductController(inv));
		controller.inventory = inv;
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testviewProductDetails() throws Exception {
		Mockito.when(inv.getProductDetails("someid")).thenReturn(new ProductEntity());
		
		mockMvc.perform(get("/products/{id}","someid")).andExpect(status().isOk());		
	}
	
	@Test(expected = Exception.class)
	public void testviewProductDetails5XX() throws Exception {
		Mockito.when(inv.getProductDetails("someid")).thenThrow(new RuntimeException());
		
		mockMvc.perform(get("/products/{id}","someid")).andExpect(status().isInternalServerError());		
	}
	
	@Test
	public void testupdateProductdetails() throws Exception {
		Mockito.when(inv.updateProductDetails("someid", "somebody"))
				.thenReturn("somestring");
		
		mockMvc.perform(put("/products/{id}","someid").content("somebody"))
				.andExpect(status().isAccepted());
	}
	
	@Test(expected = Exception.class)
	public void testupdateProductdetails5xx() throws Exception {
		Mockito.when(inv.updateProductDetails("someid", "somebody"))
				.thenThrow(new RuntimeException());
		
		mockMvc.perform(put("/products/{id}","someid").content("somebody"))
				.andExpect(status().isInternalServerError());
	}
}
