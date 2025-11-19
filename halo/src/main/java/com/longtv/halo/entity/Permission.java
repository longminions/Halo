package com.longtv.halo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permission")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


    private String category;


    private String description;


    @OneToMany(mappedBy = "permission")
    private List<RolePermission> roles;


    @OneToMany(mappedBy = "permission")
    private List<EndpointPermission> endpointPermissions;


    @OneToMany(mappedBy = "permission")
    private List<AclOverride> overrides;
}