package com.longtv.halo.service;

import com.longtv.halo.entity.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

@Service
public class AquariumAction {
	
	private final String deviceApiUrl = "http://your-spring-boot-server/control";

	@Scheduled(cron = "0 0 7 * * *")  // Cron: Giây, Phút, Giờ, Ngày, Tháng, Ngày trong tuần
	public void doEats(FishActionRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForObject(deviceApiUrl + "/turnOn", String.class);
		System.out.println("Device turned ON at 7:00 AM");
	}

	@Scheduled(cron = "0 0 7 * * *")  // Cron: Giây, Phút, Giờ, Ngày, Tháng, Ngày trong tuần
	public void turnOnLamp() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForObject(deviceApiUrl + "/turnOn", String.class);
		System.out.println("Device turned ON at 7:00 AM");
	}
	@Scheduled(cron = "0 0 22 * * *")  // Cron: Giây, Phút, Giờ, Ngày, Tháng, Ngày trong tuần
	public void turnOffLamp() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForObject(deviceApiUrl + "/turnOff", String.class);
		System.out.println("Device turned OFF at 10:00 PM");
	}
}
