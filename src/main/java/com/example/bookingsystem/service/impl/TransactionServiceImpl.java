package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.entity.Account;
import com.example.bookingsystem.entity.Transaction;
import com.example.bookingsystem.enumerator.TransactionStatus;
import com.example.bookingsystem.repository.AccountRepository;
import com.example.bookingsystem.repository.TransactionRepository;
import com.example.bookingsystem.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean transact(Transaction transaction) {
        boolean isTransactionAccepted = false;
        System.out.println(transaction);

        Optional<Account> accountOptional = accountRepository.findByEmail(transaction.getEmail());

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            isTransactionAccepted = processTransaction(transaction, account);
            saveTransaction(transaction, isTransactionAccepted, account.getAccountId());

            if(isTransactionAccepted){
                double newBalance = account.getBalance() - transaction.getAmount();
                saveNewBalance(newBalance, account);
            }
        }

        return isTransactionAccepted;
    }

    private boolean processTransaction(Transaction transaction, Account account) {
        double balance = account.getBalance();
        double transactionAmount = transaction.getAmount();
        if(transactionAmount >  balance){
            return false;
        }
        return true;
    }

    private void saveTransaction(Transaction transaction, boolean isTransactionAccepted, Long accountId) {
        if(isTransactionAccepted){
            transaction.setTransactionStatus(TransactionStatus.ACCEPTED);
        }else {
            transaction.setTransactionStatus(TransactionStatus.REJECTED);
        }
        transaction.setAccountId(accountId);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    private void saveNewBalance(double newBalance, Account account) {
        account.setBalance(newBalance);
        account.setLastUpdatedTimestamp(LocalDateTime.now());
        accountRepository.save(account);
    }
}
