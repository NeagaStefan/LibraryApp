package com.example.libraryapp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long customerId;

    @NotBlank(message = "First name must be filled")
    private String firstName;

    @NotBlank(message = "Last name must be filled")
    private String lastName;

    @NotBlank(message = "This must not be empty")
    @Email(message = "This must contain a valid email address")
    private String email;
    @JsonIgnoreProperties(value = {"course","handler","hibernateLazyInitializer"}, allowSetters = true)
    private String bookTaken;
}
