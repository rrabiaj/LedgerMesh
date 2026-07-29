package com.ledgermesh.transactionservice.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ApiErrorDTO {

    private Instant timestamp;
    private int status;
    private String message;
    private String path;
    
}
