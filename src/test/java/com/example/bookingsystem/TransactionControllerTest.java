package com.example.bookingsystem;

import com.example.bookingsystem.dto.RejectedTransactionsResponse;
import com.example.bookingsystem.entity.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testTransactions_all_accepted() {
        Transaction transaction1 = new Transaction();
        transaction1.setFirstName("John");
        transaction1.setLastName("Doe");
        transaction1.setEmail("john@doe.com");
        transaction1.setAmount(100);
        transaction1.setTransactionTypeNumber("haha1");

        Transaction transaction2 = new Transaction();
        transaction2.setFirstName("testFirstName");
        transaction2.setLastName("testLastName");
        transaction2.setEmail("test@email.com");
        transaction2.setAmount(150);
        transaction2.setTransactionTypeNumber("haha2");

        Flux<Transaction> transactionFlux = Flux.just(transaction1, transaction2);

        RejectedTransactionsResponse response = webTestClient.post()
                .uri("/transactions")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(transactionFlux, Transaction.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(RejectedTransactionsResponse.class)
                .getResponseBody()
                .blockFirst();

        assertNotNull(response);
        assertEquals(0, response.getRejectedTransactions().size());
    }

    @Test
    public void testTransactions_all_rejected() throws JsonProcessingException {
        Transaction transaction1 = new Transaction();
        transaction1.setFirstName("John");
        transaction1.setLastName("Doe");
        transaction1.setEmail("john@doe.com");
        transaction1.setAmount(10000);
        transaction1.setTransactionTypeNumber("TR001");

        Transaction transaction2 = new Transaction();
        transaction2.setFirstName("testFirstName");
        transaction2.setLastName("testLastName");
        transaction2.setEmail("test@email.com");
        transaction2.setAmount(15000);
        transaction2.setTransactionTypeNumber("TR002");

        Flux<Transaction> transactionFlux = Flux.just(transaction1, transaction2);

        RejectedTransactionsResponse response = webTestClient.post()
                .uri("/transactions")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(transactionFlux, Transaction.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(RejectedTransactionsResponse.class)
                .getResponseBody()
                .blockFirst();

        assertNotNull(response);
        assertEquals(2, response.getRejectedTransactions().size());
        String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(response);
        System.out.println(jsonResponse);
    }

    @Test
    public void testTransactions_one_rejected() throws JsonProcessingException {
        Transaction transaction1 = new Transaction();
        transaction1.setFirstName("John");
        transaction1.setLastName("Doe");
        transaction1.setEmail("john@doe.com");
        transaction1.setAmount(10000);
        transaction1.setTransactionTypeNumber("TR001");

        Transaction transaction2 = new Transaction();
        transaction2.setFirstName("testFirstName");
        transaction2.setLastName("testLastName");
        transaction2.setEmail("test@email.com");
        transaction2.setAmount(10);
        transaction2.setTransactionTypeNumber("TR002");

        Flux<Transaction> transactionFlux = Flux.just(transaction1, transaction2);

        RejectedTransactionsResponse response = webTestClient.post()
                .uri("/transactions")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(transactionFlux, Transaction.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(RejectedTransactionsResponse.class)
                .getResponseBody()
                .blockFirst();

        assertNotNull(response);
        assertEquals(1, response.getRejectedTransactions().size());
        assertEquals("john@doe.com", response.getRejectedTransactions().get(0).getEmailId());
        String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(response);
        System.out.println(jsonResponse);
    }
}
