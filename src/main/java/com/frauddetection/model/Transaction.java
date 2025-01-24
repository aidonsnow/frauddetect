package com.frauddetection.model;

import java.io.Serializable;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private double amount;
    private String accountId;

    public Transaction() {
    }

    public Transaction(String transactionId, double amount, String accountId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.accountId = accountId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}