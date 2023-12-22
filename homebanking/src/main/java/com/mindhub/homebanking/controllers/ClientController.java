package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        clientRepository.save(client);
        return new ResponseEntity<>("Registrado con exito", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ResponseEntity<Object> getOneClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client!=null){
            ClientDTO clientDTO = new ClientDTO(client);
            return new ResponseEntity<>(clientDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }




    }



}
