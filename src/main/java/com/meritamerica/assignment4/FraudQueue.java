package com.meritamerica.assignment4;

import java.util.ArrayList;
import java.util.List;

public class FraudQueue {
    private List<Transaction> listOfTransactions = new ArrayList<Transaction>();

    FraudQueue(){}

    public void addTransaction(Transaction transaction) {
        listOfTransactions.add(transaction);
    }

    List<Transaction> getTransaction() {
        return listOfTransactions;
    }

}
