package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String name, lastname;
    private List<AccountDTO> accounts;
    private Set<ClienLoanDTO> clienLoan;
    private List<CardDTO> cards;

    private String email;

    public ClientDTO(Client client){
        id= client.getId();
        name= client.getName();
        lastname= client.getLastname();
        email = client.getEmail();
        accounts= client.getAccounts().stream().filter(account -> account.getStateAccount()).map(account -> new AccountDTO(account)).collect(Collectors.toList());
        clienLoan= client.getClientLoans().stream().map(clientLoan -> new ClienLoanDTO(clientLoan)).collect(Collectors.toSet());
        cards= client.getCard().stream().filter(card -> card.getState()).map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    public Set<ClienLoanDTO> getClienLoan() {
        return clienLoan;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public Long getId() {
        return id;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }
}
