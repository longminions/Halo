package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RolePermissionId.class)
public class RolePermission {

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
