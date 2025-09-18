package com.longtv.halo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "User Data Transfer Object")
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @JsonProperty
    @ApiModelProperty(value = "User's unique identifier", example = "1")
    private Long id;

    @jakarta.validation.constraints.NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @JsonProperty
    @ApiModelProperty(value = "User's first name", required = true, example = "John")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @JsonProperty
    @ApiModelProperty(value = "User's last name", required = true, example = "Doe")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @JsonProperty
    @ApiModelProperty(value = "User's email address", required = true, example = "john.doe@example.com")
    private String email;

    /**
     * Returns the full name of the user
     * @return String containing the user's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}