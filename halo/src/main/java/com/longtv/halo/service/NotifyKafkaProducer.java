package com.longtv.halo.service;

import org.springframework.kafka.core.*;
import org.springframework.stereotype.*;

@Service
public class NotifyKafkaProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public NotifyKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void send(String message) {
		kafkaTemplate.send("your-topic", message);
	}
}
