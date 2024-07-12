package com.shashi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeting {

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello";
	}
	
	 @PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String userEndpoint() {
		return "Hello, User";
	}
	 @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String adminEndpoin() {
		return "Hello, Admin";
	}
}
