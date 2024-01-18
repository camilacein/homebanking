package com.mindhub.homebanking.models;

import com.mindhub.homebanking.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;
    @Test
    public void existsCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    @Test
    public void cardsType(){
        List<Card> cards= cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("cardType", is(CardType.DEBIT))));
    }
}
