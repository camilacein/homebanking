package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreationLoanDTO {
    private Long id;

    private String name;
    private double maxAmount;
    private double interest;

    private List<Integer> payments;

//    public CreationLoanDTO(Loan loan) {
//        this.name = loan.getName();
//        this.maxAmount = loan.getMaxAmount();
//        this.interest = loan.getInterest();
//        this.payments = loan.getPayments();
//
//    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public double getInterest() {
        return interest;
    }

    public List<Integer> getPayments() {
        return payments;
    }

}
