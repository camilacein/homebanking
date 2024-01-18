package com.mindhub.homebanking.models;

import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientLoanTest {
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Test
    public void existsClientLoan(){
        List<ClientLoan> clientLoan = clientLoanRepository.findAll();
        assertThat(clientLoan, is(not(empty())));
    }

    @Test
    public void clientLoanTipo(){
        List<ClientLoan> clientLoan = clientLoanRepository.findAll();
        assertThat(clientLoan, hasItem(hasProperty("name", is("personal"))));
    }
}
