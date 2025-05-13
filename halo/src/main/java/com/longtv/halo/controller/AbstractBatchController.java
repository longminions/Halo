package com.longtv.halo.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.*;
import org.springframework.http.*;

import java.util.*;

public abstract class AbstractBatchController {
	
	private JobLauncher jobLauncher; // Inject JobLauncher để chạy Job
	
	// Bạn có thể inject JobRegistry nếu muốn tìm Job theo tên
	// @Autowired
	// private JobRegistry jobRegistry;
	
	/**
	 * Phương thức protected để các lớp con gọi nhằm kích hoạt một Job Batch.
	 *
	 * @param job           Đối tượng Job cần chạy.
	 * @param jobParameters Tham số cho lần chạy Job này (cần đảm bảo tính duy nhất).
	 * @return ResponseEntity báo cáo kết quả chạy Job.
	 */
	protected ResponseEntity<String> launchJob(Job job, JobParameters jobParameters) {
		try {
			// jobRegistry.getJob(jobName); // Nếu bạn dùng JobRegistry để lấy Job theo tên
			
			jobLauncher.run(job, jobParameters);
			return ResponseEntity.ok("Batch Job '" + job.getName() + "' started successfully.");
			
		} catch (JobExecutionAlreadyRunningException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Job '" + job.getName() + "' is already running.");
		} catch (JobRestartException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Job '" + job.getName() + "' failed to restart.");
		} catch (JobInstanceAlreadyCompleteException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Job instance for '" + job.getName() + "' with parameters " + jobParameters + " already completed.");
		} catch (JobParametersInvalidException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Job Parameters for '" + job.getName() + "': " + e.getMessage());
		} catch (Exception e) { // Bắt các ngoại lệ khác có thể xảy ra
			e.printStackTrace(); // Log lỗi
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while launching Job '" + job.getName() + "': " + e.getMessage());
		}
	}
	
	// Bạn cũng có thể thêm một phương thức để lấy JobParameters từ request,
	// tùy thuộc vào cách bạn truyền tham số (ví dụ: từ query parameters, request body).
	// Ví dụ đơn giản:
	protected JobParameters buildJobParameters(Map<String, String> params) {
		JobParametersBuilder builder = new JobParametersBuilder();
		if (params != null) {
			params.forEach((key, value) -> builder.addString(key, value));
		}
		// Luôn thêm một tham số duy nhất (ví dụ: timestamp) để JobInstance là duy nhất
		// trừ khi bạn muốn restart một JobInstance cụ thể.
		builder.addLong("timestamp", System.currentTimeMillis());
		return builder.toJobParameters();
	}
}
