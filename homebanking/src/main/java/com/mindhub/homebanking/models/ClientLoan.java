package com.mindhub.homebanking.models;

import jakarta.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Loan loan;
    private double amount;
    private Integer payments;
    @ManyToOne

    private Client client;

    public ClientLoan() {}

    public ClientLoan(double amount,Integer payments){
        this.amount = amount;
        this.payments = payments;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public Loan getLoan() {
        return loan;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", loan=" + loan +
                ", amount=" + amount +
                ", payments=" + payments +
                ", client=" + client +
                '}';
    }
}
