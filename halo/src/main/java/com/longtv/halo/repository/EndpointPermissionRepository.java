package com.longtv.halo.repository;

import com.longtv.halo.entity.ApiEndpoint;
import com.longtv.halo.entity.EndpointPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EndpointPermissionRepository extends JpaRepository<EndpointPermission, Long> {
    List<EndpointPermission> findByEndpoint(ApiEndpoint endpoint);
}