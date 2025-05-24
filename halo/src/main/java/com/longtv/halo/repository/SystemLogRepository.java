package com.longtv.halo.repository;

import com.longtv.halo.entity.*;
import org.springframework.data.jpa.repository.*;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
	// Define methods for CRUD operations on SystemLog entity
	// For example:
	// SystemLog save(SystemLog systemLog);
	// Optional<SystemLog> findById(Long id);
	// List<SystemLog> findAll();
	// void deleteById(Long id);
}
