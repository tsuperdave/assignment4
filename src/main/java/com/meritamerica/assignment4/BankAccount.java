package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

public abstract class BankAccount {


    private List<Transaction> listOfTransactions = new ArrayList<Transaction>();
    java.util.Date accountOpenedOn;
    protected long accountNumber;
    protected double balance;
    protected double interestRate;

    BankAccount(double balance, double interestRate) {
        this(MeritBank.getNextAccountNumber(), balance, interestRate, new Date());
    }

    BankAccount(double balance, double interestRate, Date accountOpenedOn) {
        this(MeritBank.getNextAccountNumber(), balance, interestRate, accountOpenedOn);
    }

    BankAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.accountOpenedOn = accountOpenedOn;
    }

    long getAccountNumber()
    {
        return this.accountNumber;
    }

    double getBalance() {
        return this.balance;
    }

    double getInterestRate()
    {
        return interestRate;
    }

    public double futureValue(int years) {
        return this.balance * (Math.pow(1 + this.interestRate, years));
    }

    Date getOpenedOn()
    {
        return this.accountOpenedOn;
    }

    boolean withdraw(double amount) {
        if ((amount > 0) && (amount <= this.balance)) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    boolean deposit(double amount)
    {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    public void addTransaction(Transaction transaction) {
        this.listOfTransactions.add(transaction);
    }

    public List<Transaction> getTransactions()
    {
        return listOfTransactions;
    }

    String writeToString() {
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

