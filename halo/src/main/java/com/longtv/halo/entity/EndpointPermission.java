package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endpoint_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(EndpointPermissionId.class)
public class EndpointPermission {


    @Id
    @ManyToOne
    @JoinColumn(name = "endpoint_id")
    private Endpoint endpoint;


    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
