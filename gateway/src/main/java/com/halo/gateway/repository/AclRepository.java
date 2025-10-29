package com.halo.gateway.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.halo.gateway.entity.Acl;

@Repository
public interface AclRepository extends JpaRepository<Acl, Long> {

    @Query("SELECT a FROM Acl a WHERE a.user.id = :userId AND a.permission.name = :permissionName AND a.granted = true")
    Optional<Acl> findGrantedPermission(@Param("userId") Long userId, @Param("permissionName") String permissionName);

    List<Acl> findByUserIdAndGrantedTrue(Long userId);
    
    @Query("""
            SELECT a FROM Acl a
            JOIN EndpointPermission ep ON a.permission.id = ep.permission.id
            WHERE a.user.id = :userId
              AND ep.endpoint.path = :path
              AND ep.endpoint.method = :method
              AND a.granted = true
        """)
        Optional<Acl> checkAccess(@Param("userId") Long userId, @Param("path") String path, @Param("method") String method);
        }
