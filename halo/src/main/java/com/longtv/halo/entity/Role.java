package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


    private String description;


    @OneToMany(mappedBy = "role")
    private List<UserRole> users;


    @OneToMany(mappedBy = "role")
    private List<RolePermission> permissions;
}
