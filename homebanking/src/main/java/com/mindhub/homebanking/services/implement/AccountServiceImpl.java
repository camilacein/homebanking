package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.Utils.getNumber;

@Service
public class AccountServiceImpl implements AccountServices {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public Account findByIdAccount(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<AccountDTO> getAllAccountsDTO() {
        return getAllAccount().stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public List<TransactionDTO> findById(long id) {
        return accountRepository.findById(id)
                .map(account -> account.getTransactions()
                        .stream()
                        .map(transaction -> new TransactionDTO(transaction))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public boolean existsByNumber(String numberAccount) {
        return accountRepository.existsByNumber(numberAccount);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Account account) {
        Account accounts = accountRepository.findById(account.getId()).orElse(null);
        accounts.setStateAccount(false);
        accountRepository.save(accounts);
    }
}

