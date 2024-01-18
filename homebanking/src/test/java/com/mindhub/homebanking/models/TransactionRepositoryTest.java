package com.mindhub.homebanking.models;

import com.mindhub.homebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existsTransactionsList(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void transactionType(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,hasItem(hasProperty("type", is(TransactionType.CREDIT))));
    }
}
