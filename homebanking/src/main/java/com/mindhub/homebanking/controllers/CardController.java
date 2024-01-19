package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.CardPaymentDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardServices;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.ColorType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.utils.Utils.getCvv;
import static com.mindhub.homebanking.utils.Utils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    TransactionServices transactionServices;
    @Autowired
    ClientService clientService;
    @Autowired
    CardServices cardServices;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<String> createCards(
            Authentication authentication,
    @RequestParam CardType cardType,
    @RequestParam CardColor colors){
        Client client = clientService.findByEmail(authentication.getName());
        long colorType = client.getCard().stream()
                .filter(card -> card.getColor() == colors && card.getCardType() == cardType)
                .count();
        long color = client.getCard().stream().filter(card -> card.getColor() == colors).count();
        long types = client.getCard().stream().filter(card -> card.getCardType() == cardType).count();
        if(types >= 3){
            return new ResponseEntity<>("No puedes tener mas de 3 tarjetas"+ cardType, HttpStatus.FORBIDDEN);
        }
        if (color >=2){
            return new ResponseEntity<>("Solo puedes tener una tarjeta por color"+ colors,HttpStatus.FORBIDDEN);
        }
        if (colorType >= 1){
            return new ResponseEntity<>("Solo puedes tener una tarjeta por color", HttpStatus.FORBIDDEN);
        }
        if (client.getCard().size()>=6){
            return new ResponseEntity<>(
                    "Solo puedes tener 6 tarjetas en total", HttpStatus.FORBIDDEN);
        }

        String number=  getRandomNumber();
        int cvv= getCvv();
        String cardHolder = client.getName() +" "+ client.getLastname();
        LocalDate fromDate= LocalDate.now();
        LocalDate truDate= LocalDate.now().plusYears(5);

        Card card= new Card(cardType, number, cvv, fromDate, truDate, cardHolder, colors );
        client.addCard(card);
        cardServices.saveCard(card);


        return new ResponseEntity<>("Tarjeta creada con exito", HttpStatus.CREATED);

    }
    @CrossOrigin(origins = "*")
    @PostMapping("/cards/payments")
    @Transactional
    public ResponseEntity<String> cardPayment(
            @RequestBody CardPaymentDTO cardPaymentDTO){

        Card card = cardServices.findCardByNumber(cardPaymentDTO.getNumber());
        List<Account> accountList = card.getClient().getAccounts().stream().filter(account ->
                account.getBalance() >= cardPaymentDTO.getAmount()).toList();
        Account firstAccount = accountList.stream().findFirst().orElse(null);

        if (cardPaymentDTO.getNumber().isBlank()){
            return new ResponseEntity<>("You need to put a destination number account", HttpStatus.FORBIDDEN);
        }
        if (cardPaymentDTO.getCvv() < 100 && cardPaymentDTO.getCvv() > 999){
            return new ResponseEntity<>("cvv conteins more than 3 digits" ,HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getAmount() <= 0){
            return new ResponseEntity<>("The amount has to be greater than 0" , HttpStatus.FORBIDDEN);
        }
        if (cardPaymentDTO.getDescription().isBlank()){
            return new ResponseEntity<>("Please provide a description for the current payment" , HttpStatus.FORBIDDEN);
        }
        if(card.getTruDate().isBefore(LocalDate.now())){
            return new ResponseEntity<>("This card is expired, please contact with the bank to get support" , HttpStatus.FORBIDDEN);
        }
        if (firstAccount.getBalance() < cardPaymentDTO.getAmount()){
            return new ResponseEntity<>("insufficient founds" , HttpStatus.FORBIDDEN);
        }
        //Se debe crear una transacción que indique el débito a una de las cuentas con la descripción de la operación

        Transaction paymentTransaction = new Transaction(TransactionType.DEBIT,cardPaymentDTO.getAmount(), cardPaymentDTO.getDescription(), LocalDateTime.now(), firstAccount.getBalance());

        double debitAccount = (firstAccount.getBalance() - cardPaymentDTO.getAmount());

        firstAccount.setBalance(debitAccount);
        firstAccount.addTransaction(paymentTransaction);
        transactionServices.saveTransaction(paymentTransaction);


        return new ResponseEntity<>("Successful Payment", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards/delete")
    public ResponseEntity<String> deleteCards(
            @RequestParam Long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Card cards = cardServices.findById(id);
        if (cards.getState()&& cards.getClient().getEmail().equals(authentication.getName())){
            cardServices.cardDelete(cards);
            return  ResponseEntity.ok("Tarjeta eliminada con exito");

        }
        return new ResponseEntity<>("Tarjeta eliminada", HttpStatus.BAD_REQUEST);


    }
    }


