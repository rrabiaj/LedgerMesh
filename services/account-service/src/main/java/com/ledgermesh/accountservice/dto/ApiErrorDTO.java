package com.ledgermesh.accountservice.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorDTO {
    
    private Instant timestamp;
    private int status;
    private String message;
    private String path;

}
