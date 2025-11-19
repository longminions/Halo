package com.longtv.halo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class EndpointPermissionId implements java.io.Serializable {
    private Long endpoint;
    private Long permission;
}
