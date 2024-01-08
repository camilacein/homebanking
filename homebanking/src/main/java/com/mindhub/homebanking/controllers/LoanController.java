package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
 private ClientLoanRepository clientLoanRepository;
    @Autowired
   private LoanServices loanServices;
    @Autowired
    private TransactionRepository  transactionRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
   private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;
    @GetMapping("/loans")
        public List<LoanDTO> getLoans(){
            return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
        }


   @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan( Authentication authentication,
            @RequestBody LoanApplicationDTO loanApplicationDTO) {
        Loan loan = loanServices.findById(loanApplicationDTO.getId());
        Client client= clientRepository.findByEmail(authentication.getName());
       Account destinoAccount = accountRepository.findByNumber(loanApplicationDTO.getDestinoAccount());


        if (loanApplicationDTO.getAmount() == null) {
            return new ResponseEntity<>("Debes poner un monto al pedir el prestamo", HttpStatus.FORBIDDEN);
        }
        if(!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("No se puede pedir esa cantidad de cuotas", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0) {
            return new ResponseEntity<>("El monto no puede ser menor a 0", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() >= loan.getMaxAmount()) {
            return new ResponseEntity<>("Prestamo no aceptable", HttpStatus.FORBIDDEN);
        }
        if (destinoAccount==null){
            return new ResponseEntity<>("Esta cuenta no existe", HttpStatus.FORBIDDEN);
        }
        if (loan==null){
            return new ResponseEntity<>("Este prestamo no existe", HttpStatus.FORBIDDEN);
        }




        Transaction transactionPrestamo = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loan.getName() + "prestamo aprobado", LocalDateTime.now());
        destinoAccount.setBalance(destinoAccount.getBalance() + loanApplicationDTO.getAmount());


       ClientLoan clientLoan= new ClientLoan(loanApplicationDTO.getAmount()*1.2, loanApplicationDTO.getPayments());
       loan.addClientLoans(clientLoan);
       client.addClientLoan(clientLoan);
       clientLoanRepository.save(clientLoan);
       destinoAccount.addTransaction(transactionPrestamo);
       transactionRepository.save(transactionPrestamo);


        accountRepository.save(destinoAccount);
        return new ResponseEntity<>("Prestamo creado con exito", HttpStatus.CREATED);


    }
}
