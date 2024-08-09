package com.example.bookingsystem.runner;

import com.example.bookingsystem.entity.Account;
import com.example.bookingsystem.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public DataInitializer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Adding two acounts with balance 1000");
        Account account1 = new Account();
        account1.setFirstName("John");
        account1.setLastName("Doe");
        account1.setEmail("john@doe.com");
        account1.setBalance(1000);
        account1.setOpenedDate(LocalDate.now());

        Account account2 = new Account();
        account2.setFirstName("testFirstName");
        account2.setLastName("testLastName");
        account2.setEmail("test@email.com");
        account2.setBalance(1000);
        account2.setOpenedDate(LocalDate.now());

        accountRepository.saveAll(Arrays.asList(account1, account2));
    }
}
