package com.ledgermesh.transactionservice.exception;
import java.util.UUID;


public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(UUID Id) {
        super("Transaction with ID " + Id + " not found.");
    }
    
}