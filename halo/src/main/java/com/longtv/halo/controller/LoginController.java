package com.longtv.halo.controller;

import com.longtv.halo.security.jwt.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestParam String username, @RequestParam String password) {
		// Here you would typically check the username and password against a database or authentication service
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, password));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return ResponseEntity.ok(jwt);
	}
	
	@GetMapping("/logout")
	public String logout() {
		// Handle logout logic here, such as invalidating the session
		return "index.jsp"; // Redirect to index page on logout
	}
}
