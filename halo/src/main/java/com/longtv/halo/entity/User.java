package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean locked = false;

    @Column(nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Acl> acls = new ArrayList<>();
}
