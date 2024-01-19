package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.Utils.getNumber;

@RestController
@RequestMapping("/api")
public class AccountController {
    //Autowired es una anotación utilizada en Spring Boot para habilitar la inyección automática de dependencias.
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/all/accounts")
    public List<AccountDTO> getAllAccount() {
        return accountServices.getAllAccountsDTO();
    }

    @GetMapping("/accounts/{id}/transactions")
    public List<TransactionDTO> getOneAccount(@PathVariable Long id) {
        return accountServices.findById(id);

    }
    //Sprint7
    //Creamos un metodo para Crear cuentas
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> CreateAccount(Authentication authentication, @RequestParam AccountType accountType) {

        Client client = clientService.findByEmail(authentication.getName());
        //Creamos el una condicion para saber si tiene Cuentas, y si tiene más 3.
        if (client.getAccounts().size() > 2 ){
            return new ResponseEntity<>("You already have 3 accounts, it is the maximum per customer.", HttpStatus.FORBIDDEN);
        }
        //Creamos un String para posteriormente asignarle un número aleatorio.
        String numberAccount;
        do{
            numberAccount = "VIN-" + getRandomNumber(00000000 , 99999999);
        }while (accountServices.existsByNumber(numberAccount));

        Account account = new Account(numberAccount, LocalDate.now(), 0, accountType);
        clientService.saveClient(client);
        client.addAccount(account);
        accountServices.saveAccount(account);


        return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
    }

    //Creamos el método gerRandomNumber para generar numeros aleatorios para las cuentas.
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @PatchMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id , Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountServices.findByIdAccount(id);
        boolean hasThisAccount = client.getAccounts().contains(account);


        if (!account.getClient().getEmail().equals(client.getEmail())) {
            return new ResponseEntity<>("This account doesn't match with the current client", HttpStatus.FORBIDDEN);
        }

        if (hasThisAccount) {
            account.setStateAccount(false);

            if (account.getBalance() == 0) {
                accountServices.saveAccount(account);
                return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("error: an account with balance greater than 0 can't be deleted", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("error: try again later", HttpStatus.FORBIDDEN);
        }

    }}
