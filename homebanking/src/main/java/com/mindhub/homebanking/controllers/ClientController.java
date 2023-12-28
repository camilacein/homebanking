package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
//    public ClientController(ClientRepository clientRepository){
//        this.clientRepository=clientRepository;
//    }
@Autowired

public PasswordEncoder passwordEncoder;
@Autowired
    AccountRepository accountRepository;



    @GetMapping("/clients")
    public List <ClientDTO> getAllClient(){
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }


    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse( null));
    }

    @PostMapping("/clients")
    public ResponseEntity<String> createClient(
            @RequestParam String name,
            @RequestParam  String lastname,
            @RequestParam  String email,
            @RequestParam  String password){
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
        if (clientRepository.existsByEmail(email)){
            return new ResponseEntity<>("El email ya esta registrado", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(name, lastname, email, passwordEncoder.encode(password));


        String number;
        do {
            number= "VIN"+getRandomNumber(00000001,99999999);

        } while (accountRepository.existsByNumber(number));
        Account account= new Account(number, LocalDate.now(),0);
        clientRepository.save(client);
        client.addAccount(account);
        accountRepository.save(account);
        return new ResponseEntity<>("Registrado con exito", HttpStatus.CREATED);

    }
    @GetMapping("/clients/current")
    public ResponseEntity<Object> getOneClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());

            ClientDTO clientDTO = new ClientDTO(client);
            return new ResponseEntity<>(clientDTO, HttpStatus.OK);





    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }



}
