package com.longtv.halo;

import org.quartz.*;
import org.springframework.context.annotation.*;

@Configuration
public class QuartzConfig {
	
	@Bean
	public JobDetail batchJobDetail() {
		return JobBuilder.newJob(BatchJobQuartzTrigger.class)
				       .withIdentity("batchJob")
				       .storeDurably()
				       .build();
	}
	
	@Bean
	public Trigger batchJobTrigger() {
		return TriggerBuilder.newTrigger()
				       .forJob(batchJobDetail())
				       .withIdentity("batchJobTrigger")
				       .withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * * * ?")) // Runs every 30 minutes
				       .build();
	}
}
