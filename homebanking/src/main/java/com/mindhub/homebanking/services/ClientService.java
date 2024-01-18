package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {
    void saveClient(Client client);
    Client findById(long id);
    Client findByEmail (String email);
    Client findByNameAndLastname (String name, String lastname);
    boolean existsByEmail (String email);

    List<ClientDTO> getAllClientDTO();
    List<Client> getAllClient();


}
