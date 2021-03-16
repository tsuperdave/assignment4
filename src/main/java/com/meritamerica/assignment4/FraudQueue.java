package com.meritamerica.assignment4;

public class FraudQueue
{
    private Transaction txn;

    FraudQueue(){}

    public void addTransaction(Transaction transaction)
    {
        this.txn = transaction;
    }

    public Transaction getTransaction()
    {
        return txn;
    }

}
