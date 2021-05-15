package com.siwoo.simpleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class SimpleAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleAppApplication.class, args);
	}

	
	@RestController
	private static class HomeController {
		private int max = 0;
		
		@GetMapping("")
		public String home(HttpServletRequest request) throws UnknownHostException {
			if (max == 5)
				throw new RuntimeException();
			System.out.println("Received request from " + request.getRemoteAddr());
			max++;
			return  "You've hit " + InetAddress.getLocalHost().getHostName() + "\n";
		}
		
	}
}
