package com.halo.gateway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.halo.gateway.entity.ApiEndpoint;
import com.halo.gateway.entity.EndpointPermission;

@Repository
public interface EndpointPermissionRepository extends JpaRepository<EndpointPermission, Long> {
    List<EndpointPermission> findByEndpoint(ApiEndpoint endpoint);
}