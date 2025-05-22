package com.longtv.halo.service;

import org.apache.kafka.clients.consumer.*;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ConsumerNotifyService {
	@KafkaListener(topics = "notify-topic", groupId = "notify-group")
	public void handleNotify(ConsumerRecord<String, String> record) {
		System.out.println("Nhận notify: " + record.value());
	}
}
