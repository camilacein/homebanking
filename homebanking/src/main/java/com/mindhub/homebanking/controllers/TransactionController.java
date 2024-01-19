package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionServices;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountServices accountServices;

    @Autowired
    TransactionServices transactionServices;

    @PostMapping("/transactions")
    public ResponseEntity<String> createTransaction(Authentication authentication,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description,
                                                    @RequestParam String accountOrigen,
                                                    @RequestParam String accountDestino){


        if (accountOrigen.equals(accountDestino)){
            return new ResponseEntity<>("Transferencia no realizada, las cuentas no pueden ser iguales", HttpStatus.FORBIDDEN);
        }
        if (accountOrigen.isBlank()){
            return new ResponseEntity<>("Transferencia no realizada, la cuenta de origen no puede estar vacia", HttpStatus.FORBIDDEN);
        }
        if (accountDestino.isBlank()){
            return new ResponseEntity<>("Transferencia no realizada, la cuenta de destino no puede estar vacia", HttpStatus.FORBIDDEN);
        }
        if (description.isBlank()){
            return new ResponseEntity<>("Transferencia no realizada, debe ingresar una descripcion", HttpStatus.FORBIDDEN);
        }
        if (amount<=0){
            return new ResponseEntity<>("Transferencia no realizada, tienes que ingresar un monto", HttpStatus.FORBIDDEN);
        }
        if (amount.isNaN()){
            return new ResponseEntity<>("Transferencia no realizada, tienes que ingresar un monto", HttpStatus.FORBIDDEN);
        }
        Account origenAccount = accountServices.findByNumber(accountOrigen);
        Account destinoAccount = accountServices.findByNumber(accountDestino);

        if (origenAccount.getBalance()<amount){
            return new ResponseEntity<>("Fondos insuficientes", HttpStatus.FORBIDDEN);
        }
        if (!origenAccount.getClient().getEmail().equals(authentication.getName())){
            return new ResponseEntity<>("Esta cuenta no te pertenece", HttpStatus.FORBIDDEN);
        }
        if (origenAccount== null|| destinoAccount == null){
            return new ResponseEntity<>("No se encuentra la cuenta destino o origen", HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebito= new Transaction(TransactionType.DEBIT,amount, description, LocalDateTime.now(), origenAccount.getBalance());
        Transaction transactionCredito= new Transaction(TransactionType.CREDIT,amount, description, LocalDateTime.now(), destinoAccount.getBalance());

        origenAccount.setBalance(origenAccount.getBalance()-amount);
        destinoAccount.setBalance(destinoAccount.getBalance()+amount);



        origenAccount.addTransaction(transactionDebito);
        destinoAccount.addTransaction(transactionCredito);

        transactionServices.saveTransaction(transactionDebito);
        transactionServices.saveTransaction(transactionCredito);

        accountServices.saveAccount(origenAccount);
        accountServices.saveAccount(destinoAccount);

        return new ResponseEntity<>("Transferencia realizada con exito", HttpStatus.CREATED);
    }



}
