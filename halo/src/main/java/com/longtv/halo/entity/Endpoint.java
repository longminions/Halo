package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "endpoint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String path;


    @Column(nullable = false)
    private String httpMethod;


    private String module;
    private String service;


    @OneToMany(mappedBy = "endpoint")
    private List<EndpointPermission> permissions;
}
