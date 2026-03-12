package sn.isi.l3gl.money.controller;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.l3gl.money.model.Account;
import sn.isi.l3gl.money.model.Cashin;
import sn.isi.l3gl.money.model.Transaction;
import sn.isi.l3gl.money.model.Transfer;
import sn.isi.l3gl.money.service.WalletService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WalletController {

    private static final Logger log = LoggerFactory.getLogger(WalletController.class);

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account){
        log.info("REST request to save account");
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.createAccount(account));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> findAllAccounts(){
        log.info("REST request to find all accounts");
        return ResponseEntity.ok(walletService.findAllAccounts());
    }

    @PostMapping("/cashin")
    public ResponseEntity<Transaction> cashin(@RequestBody Cashin cashin){
        log.info("REST request to cashin l3gl money");
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.cashin(cashin));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody Transfer transfer) throws BadRequestException {
        log.info("REST request to transfer l3gl money");
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.transfer(transfer));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> findAllTransactionsByType(@RequestParam(required = false) Transaction.Type type) {
        log.info("REST request to find all transactions by type");
        return ResponseEntity.ok(walletService.findAllTransactionsByType(type));
    }


}
