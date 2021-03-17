package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

public abstract class BankAccount {


    private List<Transaction> listOfTransactions;
    java.util.Date accountOpenedOn;
    protected long accountNumber;
    protected double balance;
    protected double interestRate;

    BankAccount(double balance, double interestRate) //
    {
        this(MeritBank.getNextAccountNumber(), balance, interestRate, new java.util.Date());
    }

    BankAccount(double balance, double interestRate, Date accountOpenedOn) //
    {
        this(MeritBank.getNextAccountNumber(), balance, interestRate, accountOpenedOn);
    }

    BankAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) //
    {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.accountOpenedOn = accountOpenedOn;
    }

    long getAccountNumber()
    {
        return this.accountNumber;
    }

    double getBalance()
    {
        return balance;
    }

    double getInterestRate()
    {
        return interestRate;
    }

    public double futureValue(int years)
    {
        return (balance * (Math.pow((1 + interestRate), years)));
    }

    public static double recursiveFutureValue(double amount, int term, double interestRate)
    {
        // TODO --- done
        double futureVal = amount + (amount * term);
        if(term <= 1 || amount <= 0 || interestRate <= 0) return futureVal;
        return recursiveFutureValue(futureVal, --term, interestRate);
    }

    Date getOpenedOn()
    {
        return accountOpenedOn;
    }

    boolean withdraw(double amount)
    {
        if (amount > 0 && amount <= getBalance()) {
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

    public void addTransaction(Transaction transaction)
    {
        listOfTransactions.add(transaction);
    }

    public List<Transaction> getTransactions()
    {
        // TODO --- done?
        for(Transaction txn: listOfTransactions)
        {
            System.out.println(txn);
            listOfTransactions.add(txn);
        }
        /*
        Iterator<Transaction> txnIt = listOfTransactions.iterator();
        while(txnIt.hasNext())
        {
            System.out.println(txn);
            listOfTransactions.add(txn);
        }
        */
        return listOfTransactions;
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

