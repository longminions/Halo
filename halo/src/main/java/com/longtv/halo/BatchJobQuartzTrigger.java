package com.longtv.halo;

import org.quartz.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.quartz.*;
import org.springframework.stereotype.*;
import org.quartz.JobExecutionException;

@Component
public class BatchJobQuartzTrigger extends QuartzJobBean {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job scheduleNotifyRelaxJob;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Quartz triggering batch job...");
		
		try {
			JobParameters jobParameters = new JobParametersBuilder()
					                              .addLong("timestamp", System.currentTimeMillis()) // Ensures uniqueness
					                              .toJobParameters();
			
			jobLauncher.run(scheduleNotifyRelaxJob, jobParameters);
		} catch (Exception e) {
			throw new JobExecutionException("Error running batch job", e);
		}
	}
}