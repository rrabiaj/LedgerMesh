package com.ledgermesh.accountservice.dto;

import jakarta.ws.rs.SeBootstrap.Instance;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorDTO {
    
    private Instance timestamp;
    private int status;
    private String message;
    private String path;

}
