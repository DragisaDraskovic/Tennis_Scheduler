package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Player {

    @NotEmpty(message = "Firstname is required")
    private String firstName;

    @NotEmpty(message = "Lastname is required")
    private String lastName;

    private Date dateOfBirth;

    @Email(message = "Wrong email format")
    private String email;
}
