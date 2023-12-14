package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

public class ClienLoanDTO {
    private Long id;
    private Long loan;
    private String name;
    private double amount;
    private Integer payments;


    public ClienLoanDTO(ClientLoan clientLoan){
        id= clientLoan.getId();
        loan= clientLoan.getLoan().getId();
        name= clientLoan.getLoan().getName();
        amount= clientLoan.getAmount();
        payments=clientLoan.getPayments();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getLoan() {
        return loan;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

}
