package com.longtv.halo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "acl_override")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(AclOverrideId.class)
public class AclOverride {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;


    @Column(nullable = false)
    private Boolean granted;
}
