package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

public abstract class BankAccount {

    java.util.Date accountOpenedOn;
    protected long accountNumber;
    protected double balance;
    protected double interestRate;

    BankAccount(double balance, double interestRate) //
    {
        this(MeritBank.getNextAccountNumber(), balance, interestRate, new java.util.Date());
    }

    BankAccount(double balance, double interestRate, java.util.Date accountOpenedOn) //
    {
        this(MeritBank.getNextAccountNumber(), balance, interestRate, accountOpenedOn);
    }

    BankAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn) //
    {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.accountOpenedOn = accountOpenedOn;
    }

    long getAccountNumber() {
        return this.accountNumber;
    }

    double getBalance() {
        return balance;
    }

    double getInterestRate() {
        return interestRate;
    }

    double futureValue(int years) {
        return getBalance() * Math.pow((1 + getInterestRate()), years);
    }

    java.util.Date getOpenedOn()
    {
        return accountOpenedOn;
    }

    boolean withdraw(double amount) {
        if (amount > 0 && amount <= getBalance()) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    boolean deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    public void addTransaction(Transaction transaction)
    {
        return // txn list;
    }

    public List<Transaction> getTransactions()
    {
        // TODO --- add code
        // will need to utilize interface somehow
        // possible comparator. Need to return any txns over 1k to list?
        return txnList;
    }

    String writeToString()
    {
        String tempAcctNum = String.valueOf(accountNumber),
                tempBalance = String.valueOf(balance),
                tempIntRate = String.valueOf(interestRate),
                tempOpenDate = String.valueOf(accountOpenedOn);
        
        return tempAcctNum + "," +
                tempBalance + "," +
                tempIntRate + "," +
                tempOpenDate; 
    }

}

