package com.longtv.halo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserRoleId implements java.io.Serializable {
    private Long user;
    private Long role;
}
