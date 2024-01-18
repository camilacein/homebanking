package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HomebankingApplicationTests {

//	@Test
//	public void findByNumberTest() {
//		String accountNumber = "VIN0987654321";
//		Account account = new Account(accountNumber, LocalDate.now(), 2000.0);
//		accountRepository.save(account);
//		Account foundAccount = accountRepository.findByNumber(accountNumber);
//		assertNotNull(foundAccount);
//		assertEquals(accountNumber, foundAccount.getNumber());
//	}
//
//	@Test
//	public void existsByNumberTest() {
//		// Given
//		String existingNumber = "111222333";
//		Account existingAccount = new Account("VIN004", LocalDate.now(),3000);
//		accoun.save(existingAccount);
//
//		// When
//		boolean existsExistingNumber = accountRepository.existsByNumber(existingNumber);
//		boolean existsNonExistingNumber = accountRepository.existsByNumber("999888777");
//
//		// Then
//		assertTrue(existsExistingNumber);
//		assertFalse(existsNonExistingNumber);
//	}

}
