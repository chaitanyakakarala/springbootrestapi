package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication
@EnableSidecar
public class SidecarPostgresApplication {

	public static void main(String[] args) {
		SpringApplication.run(SidecarPostgresApplication.class, args);
	}
}
