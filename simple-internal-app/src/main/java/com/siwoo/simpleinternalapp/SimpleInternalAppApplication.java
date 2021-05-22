package com.siwoo.simpleinternalapp;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class SimpleInternalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleInternalAppApplication.class, args);
	}
	
	@Slf4j
	@RestController
	public static class InternalController {
		private int reqCnt = 0;
		
		@GetMapping("/data")
		public ResponseEntity<?> call() {
			return ResponseEntity.ok(ImmutableMap.of("key", "1"));
		}

		@GetMapping("/version")
		public ResponseEntity<?> version(HttpServletRequest req) {
			log.info("Received request from {}", req.getRemoteAddr());
			log.info("Smth changed");
			if (++reqCnt >= 5) {
				return ResponseEntity.status(500).body(ImmutableMap.of("error", "internal error"));
			}
			return ResponseEntity.ok(ImmutableMap.of("version", System.getenv("APP_VERSION")));
		}

	}
}
