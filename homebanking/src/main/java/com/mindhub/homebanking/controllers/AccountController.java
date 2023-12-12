package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/all")
    public List <AccountDTO> getAllAccounts(){
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }
//    @GetMapping("{id}")
//    public AccountDTO getAccount(@PathVariable Long id){
//        return accountRepository.findById(id)
//                .map(account -> new AccountDTO(account))
//                .orElse(null);
//    }
    @GetMapping("/{id}/transactions")
    public List<TransactionDTO> getOneAccount(@PathVariable Long id){
        return accountRepository.findById(id)
                .map(account -> account.getTransactions().stream()
                        .map(TransactionDTO -> new TransactionDTO(TransactionDTO))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
