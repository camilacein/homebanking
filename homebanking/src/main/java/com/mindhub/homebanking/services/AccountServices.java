package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountServices {
    Account findByNumber(String number);

    Account findByIdAccount(long id);

    List<AccountDTO> getAllAccountsDTO();

    List<Account> getAllAccount();

    List<TransactionDTO> findById(long id);

    boolean existsByNumber(String numberAccount);

    void saveAccount(Account account);

    void deleteAccount(Account account);

}
