package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanApplicationDTO {
    private Long id;

    private Double amount;

    private Integer payments;
    private String destinoAccount;


    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Long id, Double amount, Integer payments, String destinoAccount) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.destinoAccount = destinoAccount;
    }

    public Long getId() {
        return id;
    }

    public String getDestinoAccount() {
        return destinoAccount;
    }

    public void setDestinoAccount(String destinoAccount) {
        this.destinoAccount = destinoAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }



}
