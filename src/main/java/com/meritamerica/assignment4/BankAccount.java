package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

public abstract class BankAccount {

    List<Transaction> listOfTransactions = new ArrayList<Transaction>();
    Date accountOpenedOn;
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

    long getAccountNumber() {
        return this.accountNumber;
    }

    double getBalance() {
        return this.balance;
    }

    double getInterestRate() {
        return interestRate;
    }

    Date getOpenedOn()
    {
        return this.accountOpenedOn;
    }

    public double futureValue(int years) {
        return this.balance * (Math.pow(1 + this.interestRate, years));
    }

    boolean withdraw(double amount) {
        if ((amount > 0) && (amount <= this.balance)) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    boolean deposit(double amount){
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    public void addTransaction(Transaction transaction) {
        this.listOfTransactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return listOfTransactions;
    }
    
    /*
    public static BankAccount readFromString(String accountData) throws ParseException {
        String[] data = accountData.split(",");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        int tempAcctNum = Integer.parseInt(data[0]);
        double tampBal = Double.parseDouble(data[1]);
        double tempIntRate = Double.parseDouble(data[2]);
        Date tempOpenDate = formatter.parse(data[3]);

        return new BankAccount(tempAcctNum, tampBal, tempIntRate, tempOpenDate);
    }
    */

    String writeToString() {
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(this.accountOpenedOn);
        String[] newStr = {String.valueOf(this.accountNumber), String.valueOf(this.balance), String.format("%.4f", this.interestRate), formattedDate};
        return String.join(",", newStr);
    }
}

