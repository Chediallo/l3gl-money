package sn.isi.l3gl.money.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sn.isi.l3gl.money.model.Account;
import sn.isi.l3gl.money.model.Cashin;
import sn.isi.l3gl.money.model.Transaction;
import sn.isi.l3gl.money.model.Transfer;
import sn.isi.l3gl.money.repository.AccountRepository;
import sn.isi.l3gl.money.repository.TransactionRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class WalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private static final String CASHIN = "CASHIN";

    private static final String TRANSFER = "TRANSFER";

    public WalletService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account createAccount(Account account) {
        log.info("Request to create new account : {}", account);
        var existingAccount = accountRepository.findByOwnerAndPhoneNumber(account.getOwner(), account.getPhoneNumber());
        if (existingAccount.isPresent()){
            log.error("Account : {} with phoneNumber {} already exists ", existingAccount.get().getOwner(), existingAccount.get().getPhoneNumber());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Le compte " + account.getOwner() + " avec le numéro " + account.getPhoneNumber() + " existe déja");
        }
        account.setBalance(0);
        return accountRepository.save(account);
    }

    public Account findAccountById(Long accountId) {
        log.info("Request to find account by accountId : {}", accountId);
        return accountRepository.findById(accountId)
            .orElseThrow(() -> {
                log.error("Account {} not found", accountId);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Le compte " + accountId + " n'existe pas");
            });
    }

    @Transactional
    public Transaction cashin(Cashin cashin) {
        log.info("Request to cashin : {}", cashin);
        //Rechercher le compte de reception par id
        var account = findAccountById(cashin.getAccountId());

        //Mettre a jour le solde de reception et enregistrer
        account.setBalance(account.getBalance() + cashin.getAmount());
        accountRepository.save(account);

        //Generer un id de la transaction et les informations supplementaires
        var transaction = Transaction.builder()
            .accountId(cashin.getAccountId())
            .amount(cashin.getAmount())
            .operationType(Transaction.Type.CASHIN)
            .source(cashin.getSource())
            .status(Transaction.Status.SUCCESS)
            .transactionId(generateRandomTransactionId(CASHIN))
            .transactionDate(Instant.now())
            .build();

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(Transfer transfer) {
        log.info("Request to transfer amount : {} from {} to {} with payload {}", transfer.getAmount(), transfer.getFrom(), transfer.getTo(), transfer);

        //Verifier que le compte d'envoi existe
        var senderAccount = findAccountById(transfer.getFrom());

        //Verifier que le compte de reception existe
        var receiverAccount = findAccountById(transfer.getTo());

        //Si le solde est insuffisant
        if (senderAccount.getBalance() < transfer.getAmount()) {
            log.error("Insufficient balance {}", transfer.getAmount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le solde du compte " + transfer.getFrom() + " est insuffisant : " + senderAccount.getBalance());
        }

        //Generer un ID transaction et mettre quelques infos
        var transaction = Transaction.builder()
            .fromAccountId(transfer.getFrom())
            .toAccountId(transfer.getTo())
            .amount(transfer.getAmount())
            .operationType(Transaction.Type.TRANSFER)
            .transactionId(generateRandomTransactionId(TRANSFER))
            .transactionDate(Instant.now())
            .build();

        //Methode qui peut faire echouer la transaction
        mayFail(transaction);

        //Debiter le compte d'envoi
        senderAccount.setBalance(senderAccount.getBalance() - transfer.getAmount());

        //Crediter le compte de reception
        receiverAccount.setBalance(receiverAccount.getBalance() + transfer.getAmount());

        //Enregistrer les modifications du compte d'envoi
        accountRepository.save(senderAccount);

        //Enregistrer les modifications du compte de reception
        accountRepository.save(receiverAccount);

        //Si la transaction s'est bien passe on met le status a SUCCESS
        transaction.setStatus(Transaction.Status.SUCCESS);

        //Enregistrer la transaction finale
        return transactionRepository.save(transaction);

    }

    public List<Transaction> findAllTransactionsByType(Transaction.Type type) {
        log.info("Request to find all transactions by type : {}", type);

        List<Transaction> transactions = type == null
            ? transactionRepository.findAll()
            : transactionRepository.findByOperationType(type);

        return transactions.stream()
            .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
            .toList();
    }

    public List<Account> findAllAccounts(){
        return accountRepository.findAll();
    }

    private void mayFail(Transaction transaction) {

        if (new Random().nextBoolean()) {
            log.error("May fail : true failed to transfer {} from {} to {}", transaction.getAmount(), transaction.getFromAccountId(), transaction.getToAccountId());
            transaction.setStatus(Transaction.Status.FAILED);
            transactionRepository.save(transaction);
            throw new RuntimeException("Une erreur interne du serveur s'est produite");
        }

    }

    private String generateRandomTransactionId(String type) {
        var random = new Random();
        String timestamp = Instant.now()
            .truncatedTo(ChronoUnit.SECONDS)
            .toString();

        if (type.equals(CASHIN)) {
            var txnId = "TXN-CI-" + random.nextInt(10) + "-" + timestamp;
            log.info("Generated cashin transactionId : {}", txnId);
            return txnId;
        }

        var txnId = "TXN-TR-" + random.nextInt(10) + "-" + timestamp;
        log.info("Generated transfer transactionId : {}", txnId);
        return txnId;
    }


}
