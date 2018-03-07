package com.example.demo;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;

@Service
public class PostgresHealthIndicatorImpl implements PostgresHealthIndicator{

	private static final String COMMAND_PATTERN = "C:\\Program Files\\PostgreSQL\\9.6\\bin\\pg_isready.exe -U postgres -h localhost -p 5432";
	
	@Override
	  public Health health() {
	    Health.Builder result = null;
	    try {
	      String output = this.runCommand();
	      System.out.println(output);
	      if (output.indexOf("accepting connections") != -1) {
	        result = Health.up();
	      } else if (output.indexOf("rejecting connections") != -1 || output.indexOf("no response") != -1) {
	        result = Health.down().withDetail("reason", output);
	      }
	    } catch (Exception e) {
	      
	      result = Health.down().withException(e);
	    }
	    return result.build();
	  }
	
	public String runCommand() throws Exception {
		
		Process process = Runtime.getRuntime().exec(COMMAND_PATTERN);
	    InputStream cmdOutput = process.getInputStream();
	    return new String(IOUtils.toByteArray(cmdOutput));

	}

}
