package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/accounts/all")
    public List <AccountDTO> getAllAccounts(){
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }
//    @GetMapping("/accounts/{id}")
//    public AccountDTO getAccount(@PathVariable Long id){
//        return accountRepository.findById(id)
//                .map(account -> new AccountDTO(account))
//                .orElse(null);
//    }
    @GetMapping("/accounts/{id}/transactions")
    public List<TransactionDTO> getOneAccount(@PathVariable Long id){
        return accountRepository.findById(id)
                .map(account -> account.getTransactions().stream()
                        .map(TransactionDTO -> new TransactionDTO(TransactionDTO))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
    @PostMapping ("/clients/current/accounts")
    public ResponseEntity<String> CreateAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size()>=3){
            return new ResponseEntity<>("No puedes tener mas de 3 cuentas", HttpStatus.FORBIDDEN); //403
        }
        String number;
        do {
            number= "VIN"+getRandomNumber(00000001,99999999);

        } while (accountRepository.existsByNumber(number));
        Account account= new Account(number,LocalDate.now(),0);
        client.addAccount(account);
        accountRepository.save(account);


        return new ResponseEntity<>("Cuenta creada con exito", HttpStatus.CREATED);

    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
