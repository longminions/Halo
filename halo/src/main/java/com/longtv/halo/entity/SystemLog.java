package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Data
@Entity
public class SystemLog {
	@Id
	@GeneratedValue
	private Long id;
	
	private String serviceName;
	
	private String stepName;
	
	private String status;
	
	private int writeCount;

	private LocalDateTime startTime;

	private LocalDateTime endTime;
	
	@Lob
	private String message;
	
	@Lob
	private String stackTrace;

}
