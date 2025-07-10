package com.longtv.halo.controller;

import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		// Here you would typically check the username and password against a database or authentication service
		if ("admin".equals(username) && "password".equals(password)) {
			return "dashboard.jsp"; // Redirect to dashboard on successful login
		} else {
			return "index.jsp";
		}
	}
	
	@GetMapping("/logout")
	public String logout() {
		// Handle logout logic here, such as invalidating the session
		return "index.jsp"; // Redirect to index page on logout
	}
}
