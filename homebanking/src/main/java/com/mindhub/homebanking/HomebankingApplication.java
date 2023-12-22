package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication<accountRepository> {
	@Autowired
	public PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  ClientLoanRepository clientLoanRepository,
									  LoanRepository loanRepository,
									  CardRepository cardRepository){
		return args -> {
			Client cliente1 = new Client ("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("1234"));
			Client cliente2 = new Client("Camila", "Cein","ceincamila@gmail.com", passwordEncoder.encode("1234"));
//			System.out.println(cliente1);
			clientRepository.save(cliente1);
		    System.out.println(cliente1);
			clientRepository.save(cliente2);
			System.out.println(cliente2);

			Account account1= new Account("VIN001", LocalDate.now(),5000);
			Account account2= new Account("VIN002",LocalDate.now().plusDays(1),7500);
			Account account3= new Account("VIN003", LocalDate.now(), 10000);
			Account account4= new Account("VIN004", LocalDate.now(),5000);
			cliente1.addAccount(account1);
			cliente1.addAccount(account2);
			cliente2.addAccount(account3);
			cliente2.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			System.out.println(account1);
			System.out.println(account2);
			System.out.println(account3);
			System.out.println(account4);
			Transaction transaction1 = new Transaction(TransactionType.DEBIT, 5000, "debito", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 5000, "credito", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, 7000, "debito", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 9000, "credito", LocalDateTime.now());
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			Loan hipotecario = new Loan("hipotecario",500000, List.of(12,24,36,48,60));
			Loan personal = new Loan("personal",100000,List.of(6,12,24));
			Loan automotriz = new Loan("automotriz",300000, List.of(6,12,24,36));
			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);

			ClientLoan hipotecarioMelba= new ClientLoan(400000,60);
			ClientLoan personalMelba= new ClientLoan(50000,12);
			ClientLoan automotrizCamila= new ClientLoan(300000,36);
			ClientLoan personalCamila= new ClientLoan(100000,24);

			hipotecario.addClientLoans(hipotecarioMelba);
			personal.addClientLoans(personalMelba);
			automotriz.addClientLoans(automotrizCamila);
			personal.addClientLoans(personalCamila);

			cliente1.addClientLoan(hipotecarioMelba);
			cliente1.addClientLoan(personalMelba);
			cliente2.addClientLoan(automotrizCamila);
			cliente2.addClientLoan(personalCamila);

			clientLoanRepository.save(hipotecarioMelba);
			clientLoanRepository.save(personalMelba);
			clientLoanRepository.save(automotrizCamila);
			clientLoanRepository.save(personalCamila);
			Card debit = new Card(CardType.DEBIT, "3325-6745-7876-4445",990,LocalDate.of(2021,04,26),LocalDate.of(2026,04,26),"Melba Morel", CardColor.GOLD );
			Card credit = new Card(CardType.CREDIT, "2234-6745-552-7888",750,LocalDate.of(2021,04,26), LocalDate.of(2026,04,26),"Melba Morel", CardColor.TITANIUM);
			Card debit1= new Card(CardType.DEBIT, "5255-4443-5007-1093", 105, LocalDate.of(2023,12,15), LocalDate.of(2028,12,15), "Camila Cein", CardColor.SILVER);
			cliente1.addCard(debit);
			cliente1.addCard(credit);
			cliente2.addCard(debit1);
			cardRepository.save(debit);
			cardRepository.save(credit);
			cardRepository.save(debit1);





		};
	}

}
