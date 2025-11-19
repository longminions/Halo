package com.longtv.halo.repository;

import com.longtv.halo.entity.ApiEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ApiEndpointRepository extends JpaRepository<ApiEndpoint, Long> {
    Optional<ApiEndpoint> findByPathAndMethod(String path, String method);
}