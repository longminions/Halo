package com.longtv.halo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PingController {

	// This class is currently empty, but you can add methods to handle ping requests.
	// For example, you might want to create a method that returns a simple "pong" response.
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
}
