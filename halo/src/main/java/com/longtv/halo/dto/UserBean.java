package com.longtv.halo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @JsonProperty
    private Long id;

    @jakarta.validation.constraints.NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @JsonProperty
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @JsonProperty
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @JsonProperty
    private String email;

    /**
     * Returns the full name of the user
     * @return String containing the user's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}