package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.CreationLoanDTO;
import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.RoleType.ADMIN;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    ClientLoanServices clientLoanServices;
    @Autowired
    TransactionServices transactionServices;
    @Autowired
 private ClientService clientService;
    @Autowired
   private LoanServices loanServices;
    @Autowired
    private AccountServices accountServices;

    @GetMapping("/loans")
        public List<LoanDTO> getLoans(){
            return loanServices.getLoansDTO();
        }

    @PatchMapping ("/loans")
    public ResponseEntity<String> payLoan (Authentication authentication,
                                           @RequestParam Long id,
                                           @RequestParam String number){

        Client client = clientService.findByEmail(authentication.getName());

        ClientLoan clientLoan = clientLoanServices.findById(id);
        Account account = accountServices.findByNumber(number);
        double interest = clientLoan.getLoan().getInterest();

        Double paymentAmount = (clientLoan.getAmount() / clientLoan.getPayments()) * interest ;
        System.out.println(paymentAmount);

        if (client == null ){
            return new ResponseEntity<>("Debes iniciar sesion", HttpStatus.FORBIDDEN);
        }

        if (account.getBalance() < paymentAmount){
            return new ResponseEntity<>(" Fondos insuficientes", HttpStatus.FORBIDDEN);
        }

        clientLoan.setAmount(clientLoan.getAmount() - paymentAmount);
        clientLoan.setPayments(clientLoan.getPayments() - 1);
        clientLoanServices.saveClientLoan(clientLoan);

        account.setBalance(account.getBalance() - paymentAmount);

        accountServices.saveAccount(account);


        return new ResponseEntity<>("Prestamo pagado", HttpStatus.CREATED);

    }


        @Transactional
        @PostMapping("/loans/create")
        public ResponseEntity<Object> createLoans(Authentication authentication, @RequestBody CreationLoanDTO creationLoanDTO){
            Client client = clientService.findByEmail(authentication.getName());
            List<Integer> payments = creationLoanDTO.getPayments();
            if (client.getRole() != ADMIN){
                return new ResponseEntity<>("No eres administrador", HttpStatus.FORBIDDEN);
            }
            if (creationLoanDTO.getName().isBlank()){
                return ResponseEntity.badRequest().body("El nombre no puede estar vacio");
            }
            if(creationLoanDTO.getInterest()==0){
                return ResponseEntity.badRequest().body("El interes no puede ser 0");

            }
            if (creationLoanDTO.getMaxAmount()<0){
                return ResponseEntity.badRequest().body("El monto maximo no puede ser 0");
            }
            if (payments.size() == 0){
                return ResponseEntity.badRequest().body("Las cuotas no pueden ser menor a 0");
            }
            Loan newLoan = new Loan(creationLoanDTO.getName(), creationLoanDTO.getMaxAmount(), creationLoanDTO.getPayments(), creationLoanDTO.getInterest());
            loanServices.saveLoan(newLoan);

        return ResponseEntity.accepted().body("Prestamo creado con exito");
        }




   @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan( Authentication authentication,
            @RequestBody LoanApplicationDTO loanApplicationDTO) {
        Loan loan = loanServices.findById(loanApplicationDTO.getId());
        Client client= clientService.findByEmail(authentication.getName());
       Account destinoAccount = accountServices.findByNumber(loanApplicationDTO.getDestinoAccount());


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




        Transaction transactionPrestamo = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loan.getName() + "prestamo aprobado", LocalDateTime.now(), destinoAccount.getBalance());
        destinoAccount.setBalance(destinoAccount.getBalance() + loanApplicationDTO.getAmount());


       ClientLoan clientLoan= new ClientLoan(loanApplicationDTO.getAmount()*loan.getInterest(), loanApplicationDTO.getPayments());
       loan.addClientLoans(clientLoan);
       client.addClientLoan(clientLoan);
       clientLoanServices.saveClientLoan(clientLoan);
       destinoAccount.addTransaction(transactionPrestamo);
       transactionServices.saveTransaction(transactionPrestamo);


        accountServices.saveAccount(destinoAccount);
        return new ResponseEntity<>("Prestamo creado con exito", HttpStatus.CREATED);


    }
}
