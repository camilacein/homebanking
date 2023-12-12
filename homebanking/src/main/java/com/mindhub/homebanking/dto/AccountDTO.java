package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;


    private String number;
    private LocalDate creationDate;
    private double balance;
  private Set<TransactionDTO> transactionsDTO;

    public AccountDTO(Account account){
        id=account.getId();
        number=account.getNumber();
        creationDate=account.getCreationDate();
        balance= account.getBalance();
        transactionsDTO =account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());


    }

    public Long getId() {
        return id;
    }



    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransaction() {
        return transactionsDTO;
    }
}
