package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private Boolean stateAccount;
    private AccountType accountType;

    private String number;
    private LocalDate creationDate;
    private double balance;
  private List<TransactionDTO> transactions;

    public AccountDTO(Account account){
        id=account.getId();
        number=account.getNumber();
        creationDate=account.getCreationDate();
        balance= account.getBalance();
        stateAccount = account.getStateAccount();
        transactions =account.getTransactions().stream().filter(AccountDTO -> account.getStateAccount()).map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
        accountType = account.getAccountType();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Boolean getStateAccount() {
        return stateAccount;
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

    public List<TransactionDTO> getTransactionsDTO() {
        return transactions;
    }
}
