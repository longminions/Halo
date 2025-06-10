package com.longtv.halo.controller;

import com.longtv.halo.entity.*;
import com.longtv.halo.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aqua/action")
public class  AquariumController {
	
	private String deviceState = "OFF";
	
	@Autowired
	private AquariumAction aquariumAction;
	
	@PostMapping("/eat")
	private HttpStatus doEat(@RequestBody FishActionRequest fishActionReq ) {
		aquariumAction.doEats();
		return HttpStatus.OK ;
	}

	@GetMapping("/control")
	public String controlDevice() {
		return deviceState;
	}
	
	@GetMapping("/turnOn")
	public String turnOn() {
		deviceState = "ON";
		return "Device is ON";
	}
	
	@GetMapping("/turnOff")
	public String turnOff() {
		deviceState = "OFF";
		return "Device is OFF";
	}
}
