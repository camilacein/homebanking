package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

public interface ClientLoanServices {
    void saveClientLoan (ClientLoan clientLoan);
    ClientLoan findById(long id);
}
