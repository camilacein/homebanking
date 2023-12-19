package com.mindhub.homebanking.models;

import com.mindhub.homebanking.dto.CardDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    public Set<Card> card = new HashSet<>();
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String email;


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();
    public Client( ) {

    }

    public Client( String name, String lastname, String email) {

        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }



    public String getName() {
        return name;
    }


    public String getLastname() {
        return lastname;
    }


    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Set <Account> getAccounts(){
        return  accounts;
    }

    public Set<Card> getCard() {
        return card;
    }
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }

  public void addCard(Card card){
        card.setClient(this);
        this.card.add(card);
  }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }




}
