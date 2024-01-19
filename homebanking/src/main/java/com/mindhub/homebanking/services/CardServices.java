package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

public interface CardServices {
    void saveCard (Card card);


    Card findById (Long id);
    boolean existsByNumber (String number);
    Card findCardByNumber(String number);

    void cardDelete(Card card);
}
