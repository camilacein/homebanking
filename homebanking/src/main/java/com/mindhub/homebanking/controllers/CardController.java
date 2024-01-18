package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardServices;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.ColorType;
import java.time.LocalDate;

import static com.mindhub.homebanking.utils.Utils.getCvv;
import static com.mindhub.homebanking.utils.Utils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    ClientService clientService;
    @Autowired
    CardServices cardServices;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<String> createCards(
            Authentication authentication,
    @RequestParam CardType cardType,
    @RequestParam CardColor colors){
        Client client = clientService.findByEmail(authentication.getName());
        long colorType = client.getCard().stream()
                .filter(card -> card.getColor() == colors && card.getCardType() == cardType)
                .count();
        long color = client.getCard().stream().filter(card -> card.getColor() == colors).count();
        long types = client.getCard().stream().filter(card -> card.getCardType() == cardType).count();
        if(types >= 3){
            return new ResponseEntity<>("No puedes tener mas de 3 tarjetas"+ cardType, HttpStatus.FORBIDDEN);
        }
        if (color >=2){
            return new ResponseEntity<>("Solo puedes tener una tarjeta por color"+ colors,HttpStatus.FORBIDDEN);
        }
        if (colorType >= 1){
            return new ResponseEntity<>("Solo puedes tener una tarjeta por color", HttpStatus.FORBIDDEN);
        }
        if (client.getCard().size()>=6){
            return new ResponseEntity<>(
                    "Solo puedes tener 6 tarjetas en total", HttpStatus.FORBIDDEN);
        }

        String number=  getRandomNumber();
        int cvv= getCvv();
        String cardHolder = client.getName() +" "+ client.getLastname();
        LocalDate fromDate= LocalDate.now();
        LocalDate truDate= LocalDate.now().plusYears(5);

        Card card= new Card(cardType, number, cvv, fromDate, truDate, cardHolder, colors );
        client.addCard(card);
        cardServices.saveCard(card);


        return new ResponseEntity<>("Tarjeta creada con exito", HttpStatus.CREATED);

    }

    @PatchMapping("/clients/current/cards/delete")
    public ResponseEntity<String> deleteCards(
            @RequestParam Long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Card cards = cardServices.findById(id);
        if (cards.getState()&& cards.getClient().getEmail().equals(authentication.getName())){
            cardServices.cardDelete(cards);
            return  ResponseEntity.ok("Tarjeta eliminada con exito");

        }
        return new ResponseEntity<>("Tarjeta eliminada", HttpStatus.BAD_REQUEST);


    }
    }


