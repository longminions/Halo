package com.halo.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.halo.gateway.entity.ApiEndpoint;

@Repository
public interface ApiEndpointRepository extends JpaRepository<ApiEndpoint, Long> {
    Optional<ApiEndpoint> findByPathAndMethod(String path, String method);
}