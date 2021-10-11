package ru.otus.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiError {
    private String errorMessage;
}
