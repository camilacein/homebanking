package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime dateTime;
    private double balance;

    public TransactionDTO(Transaction transaction){
        id= transaction.getId();
        type= transaction.getType();
        amount= transaction.getAmount();
        description=transaction.getDescription();
        dateTime= transaction.getDateTime();
        balance= transaction.getBalance();
    }

    public double getBalance() {
        return balance;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
