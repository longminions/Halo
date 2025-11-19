package com.longtv.halo.security;

import com.longtv.halo.entity.Acl;
import com.longtv.halo.entity.ApiEndpoint;
import com.longtv.halo.entity.EndpointPermission;
import com.longtv.halo.entity.User;
import com.longtv.halo.repository.AclRepository;
import com.longtv.halo.repository.ApiEndpointRepository;
import com.longtv.halo.repository.EndpointPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessDecisionManager {

    private final ApiEndpointRepository apiEndpointRepository;
    private final EndpointPermissionRepository endpointPermissionRepository;
    private final AclRepository aclRepository;

    public boolean hasAccess(User userEntity, String path, String method) {
        if (userEntity == null || userEntity.getId() == null) {
            return false;
        }

        // Implement access decision logic here
        Optional<ApiEndpoint> endpointOpt = apiEndpointRepository.findByPathAndMethod(path, method);

        if (!endpointOpt.isPresent()) {
            return false; // Endpoint not found, deny access
        }

        ApiEndpoint endpoint = endpointOpt.get();
        List<EndpointPermission> permissions = endpointPermissionRepository.findByEndpoint(endpoint);

        if (permissions == null || permissions.isEmpty()) {
            return false; // No permissions associated with the endpoint, deny access
        }


        for (EndpointPermission permission : permissions) {
            if (permission == null || permission.getPermission() == null) continue;

            Optional<Acl> acl = aclRepository.findGrantedPermission(userEntity.getId(), permission.getPermission().getName());
            if (acl.isPresent()) return true;
        }

        return false; // No matching permissions found, deny access
    }
}

