package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserRoleId.class)
public class UserRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}