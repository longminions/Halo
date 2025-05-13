package com.longtv.halo.controller;

import org.springframework.batch.core.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public class ScheduleNotifyRelax extends AbstractBatchController {

	@Qualifier("ScheduleNotifyRelaxBatchJob") // Sử dụng @Qualifier để chỉ định bean Job cần inject
	private Job ScheduleNotifyRelaxBatchJob;
	
	@PostMapping("/start")
	public ResponseEntity<String> startAppointmentSchedulingJob(@RequestBody Map<String, String> jobParamsMap) {
		JobParameters jobParameters = buildJobParameters(jobParamsMap); // Sử dụng phương thức từ lớp cha
		
		// Gọi phương thức launchJob từ lớp cha để chạy Job
		return launchJob(ScheduleNotifyRelaxBatchJob, jobParameters);
	}
}
