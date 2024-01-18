package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private Boolean state = true;
private CardType cardType;
private String number;
private int CVV;
private LocalDate fromDate;
private LocalDate truDate;
private String cardHolder;

private CardColor color;
    @ManyToOne
    @JoinColumn (name="client_id")
private Client client;

    public Card() {
    }

    public Card(CardType cardType, String number, int CVV, LocalDate fromDate, LocalDate truDate, String cardHolder, CardColor color) {
        this.cardType = cardType;
        this.number = number;
        this.CVV = CVV;
        this.fromDate = fromDate;
        this.truDate = truDate;
        this.cardHolder = cardHolder;
        this.color = color;

    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setTruDate(LocalDate truDate) {
        this.truDate = truDate;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setClient(Client client) {
        this.client = client;
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

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardType=" + cardType +
                ", number='" + number + '\'' +
                ", CVV=" + CVV +
                ", fromDate=" + fromDate +
                ", truDate=" + truDate +
                ", cardHolder='" + cardHolder + '\'' +
                ", color=" + color +
                ", client=" + client +
                ",state="+ state +
                '}';
    }
}
