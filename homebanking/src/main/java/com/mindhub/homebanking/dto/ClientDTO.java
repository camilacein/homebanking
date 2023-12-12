package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String name, lastname;
    private List<AccountDTO> accounts;
    public ClientDTO(Client client){
        id= client.getId();
        name= client.getName();
        lastname= client.getLastname();
        accounts= client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
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
