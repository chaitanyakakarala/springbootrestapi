package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ DataSourceProperties.class })
public class AppConfig {
  
  @Autowired
  private DataSourceProperties dsProperties;

  @Autowired
  private DiscoveryClient discoveryClient;

  @Value("${sidecar.appName:GENERIC SIDECAR}")
  private String dbServiceName;

  @Bean
  public DataSource dataSource() {
    ServiceInstance instance = this.discoveryClient.getInstances("GENERIC SIDECAR").iterator().next();
    return this.createDataSource(instance.getHost(), instance.getPort());
  }

  private DataSource createDataSource(String host, int port) {
    String jdbcUrl = String.format(this.dsProperties.getUrl(), host, port);
    DataSourceBuilder factory = DataSourceBuilder
        .create()
        .url(jdbcUrl)
        .username(this.dsProperties.getUsername())
        .password(this.dsProperties.getPassword())
        .driverClassName(this.dsProperties.getDriverClassName());
    return factory.build();
  }
}
