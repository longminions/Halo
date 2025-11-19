package com.longtv.halo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class RolePermissionId implements java.io.Serializable {
    private Long role;
    private Long permission;
}
