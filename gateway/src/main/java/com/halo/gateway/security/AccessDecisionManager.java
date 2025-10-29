package com.halo.gateway.security;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.halo.gateway.entity.Acl;
import com.halo.gateway.entity.ApiEndpoint;
import com.halo.gateway.entity.EndpointPermission;
import com.halo.gateway.entity.User;
import com.halo.gateway.repository.AclRepository;
import com.halo.gateway.repository.ApiEndpointRepository;
import com.halo.gateway.repository.EndpointPermissionRepository;

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
