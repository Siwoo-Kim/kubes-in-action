package com.siwoo.simpleinternalapp;

import com.google.common.collect.ImmutableMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SimpleInternalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleInternalAppApplication.class, args);
	}
	
	@RestController
	public class InternalController {
		
		@GetMapping("/data")
		public ResponseEntity<?> call() {
			return ResponseEntity.ok(ImmutableMap.of("key", "1"));
		}
	}
}
