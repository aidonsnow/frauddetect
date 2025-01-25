package com.frauddetection.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {

    @Test
    void testTransactionGettersAndSetters() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn123");
        transaction.setAccountId("acc456");
        transaction.setAmount(BigDecimal.valueOf(15000));

        assertEquals("txn123", transaction.getTransactionId());
        assertEquals("acc456", transaction.getAccountId());
        assertEquals(BigDecimal.valueOf(15000), transaction.getAmount());
    }
}
