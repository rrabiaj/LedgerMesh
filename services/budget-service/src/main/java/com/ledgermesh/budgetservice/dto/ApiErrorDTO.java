package com.ledgermesh.budgetservice.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ApiErrorDTO {

    private String message;
    private Instant timestamp;
    private int status;
    private String path;
}
