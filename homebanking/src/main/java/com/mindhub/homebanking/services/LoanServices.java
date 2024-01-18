package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanServices {
Loan findById(Long id);
    List<LoanDTO> getLoansDTO();
    List<Loan> getLoans();

    void saveLoan(Loan loan);
}
