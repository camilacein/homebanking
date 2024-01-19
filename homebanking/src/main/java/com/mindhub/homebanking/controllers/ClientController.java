package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.Utils.getNumber;

@RestController
@RequestMapping ("/api")
public class ClientController {
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountServices accountServices;




    @GetMapping("/clients")
    public List <ClientDTO> getAllClient(){

        return clientService.getAllClientDTO();
    }


    @RequestMapping("/clients/{id}")
    public Client getClient(@PathVariable Long id){
        return  clientService.findById(id);
    }

    @PostMapping("/clients")
    public ResponseEntity<String> createClient(
            @RequestParam String name,
            @RequestParam  String lastname,
            @RequestParam  String email,
            @RequestParam  String password,
             @RequestParam AccountType accountType){
        if (name.isBlank()){
            return new ResponseEntity<>("El nombre no puede estar vacio", HttpStatus.FORBIDDEN); //403
        }
        if (lastname.isBlank()){
            return new ResponseEntity<>("El apellido no puede estar vacio", HttpStatus.FORBIDDEN);//403
        }
        if (email.isBlank()){
            return new ResponseEntity<>("El correo no puede estar vacio", HttpStatus.FORBIDDEN);//403
        }
        if (password.isBlank()){
            return new ResponseEntity<>("La contraseña no puede estar vacia", HttpStatus.FORBIDDEN);//403
        }
        if (clientService.existsByEmail(email)){
            return new ResponseEntity<>("El email ya esta registrado", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(name, lastname, email, passwordEncoder.encode(password));


        String number;
        do {
            number= getNumber();

        } while (accountServices.existsByNumber(number));
        Account account= new Account(number, LocalDate.now(),0, accountType);
        clientService.saveClient(client);
        client.addAccount(account);
        accountServices.saveAccount(account);
        return new ResponseEntity<>("Registrado con exito", HttpStatus.CREATED);

    }
    @GetMapping("/clients/current")
    public ResponseEntity<Object> getOneClient(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());

        ClientDTO clientDTO = new ClientDTO(client);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);







    }




}
