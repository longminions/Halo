package com.longtv.halo.listener;

import org.springframework.batch.core.*;
import org.springframework.stereotype.*;

@Component
public class AbstractListener implements StepExecutionListener {
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// This method is called before the step execution starts.
		// You can add any pre-processing logic here.
		System.out.println("Before Step: " + stepExecution.getStepName());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// This method is called after the step execution completes.
		// You can add any post-processing logic here.
		System.out.println("After Step: " + stepExecution.getStepName());
		if (stepExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
		
		} else if (stepExecution.getExitStatus().equals(ExitStatus.FAILED)) {
			System.out.println("Step failed.");
		} else {
			System.out.println("Step ended with status: " + stepExecution.getExitStatus());
		}
		return stepExecution.getExitStatus();
	}
}
