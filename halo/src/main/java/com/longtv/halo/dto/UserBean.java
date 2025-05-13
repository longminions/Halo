package com.longtv.halo.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
public class UserBean {
    
    @JsonProperty
    private Long id;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String email;

}
