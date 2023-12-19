package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private CardType cardType;
    private String number;
    private int CVV;
    private LocalDate fromDate;
    private LocalDate truDate;
    private String cardHolder;
    private CardColor color;
    private Client client;



    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardType = card.getCardType();
        this.number = card.getNumber();
        this.CVV = card.getCVV();
        this.fromDate = card.getFromDate();
        this.truDate = card.getTruDate();
        this.cardHolder = card.getCardHolder();
        this.color = card.getColor();
    }

    public Long getId() {
        return id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getNumber() {
        return number;
    }

    public int getCVV() {
        return CVV;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getTruDate() {
        return truDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardColor getColor() {
        return color;
    }

    public Client getClient() {
        return client;
    }
}
