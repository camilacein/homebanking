package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.synth.ColorType;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CardController {


    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<String> createCards(
            Authentication authentication,
    @RequestParam CardType cardType,
    @RequestParam CardColor colors){
        Client client = clientRepository.findByEmail(authentication.getName());
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

        String number= getRandomNumber() ;
        int cvv= (int) (Math.random() * 999+100);
        String cardHolder = client.getName() +" "+ client.getLastname();
        LocalDate fromDate= LocalDate.now();
        LocalDate truDate= LocalDate.now().plusYears(5);

        Card card= new Card(cardType,number, cvv, fromDate, truDate, cardHolder, colors);
        client.addCard(card);
        cardRepository.save(card);


        return new ResponseEntity<>("Tarjeta creada con exito", HttpStatus.CREATED);

    }
    private String getRandomNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i<4; i++){
            int seccion=(int) (Math.random()*9000+1000);
            cardNumber.append(seccion).append("-");
        }
        return cardNumber.substring(0,cardNumber.length()-1);
    }
    }


