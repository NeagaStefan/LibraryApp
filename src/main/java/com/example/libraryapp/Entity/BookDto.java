package com.example.libraryapp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long bookId;

    @NotBlank(message = "The title must not be empty")
    private String title;

    @NotBlank(message = "The author name must not be empty")
    private String authorName;
    @JsonIgnoreProperties(value = {"course","handler","hibernateLazyInitializer"}, allowSetters = true)
    private String ISBN;
    @JsonIgnoreProperties(value = {"course","handler","hibernateLazyInitializer"}, allowSetters = true)
    private String status;


    @JsonIgnoreProperties(value = {"course","handler","hibernateLazyInitializer"}, allowSetters = true)
    private Long takenBy;
}
