package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String name, lastname;
    private List<AccountDTO> accounts;
    private Set<ClienLoanDTO> clienLoanDTOS;
    private List<CardDTO> cardDTOS;
    private String email;
    public List<CardDTO> getCardDTOS() {
        return cardDTOS;
    }
    public ClientDTO(Client client){
        id= client.getId();
        name= client.getName();
        lastname= client.getLastname();
        email = client.getEmail();
        accounts= client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        clienLoanDTOS= client.getClientLoans().stream().map(clientLoan -> new ClienLoanDTO(clientLoan)).collect(Collectors.toSet());
        cardDTOS = client.getCard().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    public Set<ClienLoanDTO> getClienLoanDTOS() {
        return clienLoanDTOS;
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
