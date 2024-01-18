package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardServices;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

import static com.mindhub.homebanking.utils.Utils.getCvv;
import static com.mindhub.homebanking.utils.Utils.getRandomNumber;

@Service
public class CardServiceImpl implements CardServices {

    @Autowired
    CardRepository cardRepository;


    @Override
    public void cardDelete(Card card){
        Card cards = cardRepository.findById(card.getId()).orElse(null);
        cards.setState(false);
        cardRepository.save(cards);

    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findById(Long id){
        return cardRepository.findById(id).orElse(null);
    }


    @Override
    public boolean existsByNumber(String number) {
        return false;
    }
}
