package com.longtv.halo.controller;

import com.longtv.halo.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
public class NotifyController {

	@Autowired
	private NotifyKafkaProducer producer;
	
	@PostMapping
	public ResponseEntity<String> sendNotify(@RequestBody String message) {
		producer.send(message);
		return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
	}
}
