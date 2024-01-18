package com.mindhub.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UtilTest {

    @Test
    public void testAccountNumber(){
        String accountNumber = Utils.getNumber();
        assertThat(accountNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void testCardNumber(){
        String cardNumber = Utils.getRandomNumber();
        assertThat(cardNumber, is(not(emptyOrNullString())));
    }

    @Test
    public void testCardCvv(){
        int cvv = Utils.getCvv();
        assertThat(cvv, is(not(equalTo(000))));
    }
}
