package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

// add Override for withdraw/deposit so if term < startDate, return false

public class CDAccount extends BankAccount {

    protected CDOffering cdOffering;
    private final Date accountOpenedOn;
    private double balance;
    private int term;

    CDAccount(CDOffering offering, double balance)
    {
        super(balance, offering.getInterestRate());
        this.cdOffering = offering;
        this.accountOpenedOn = new Date();
    }

    CDAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn, int term)
    {
        super(accountNumber, balance, interestRate, accountOpenedOn);
        this.term = term;
        this.accountOpenedOn = accountOpenedOn;
    }

    double getBalance()
    {
        return super.getBalance();
    }

    double getInterestRate()
    {
        return super.interestRate;
    }

    long getAccountNumber()
    {
        return  super.accountNumber;
    }

    int getTerm()
    {
        return term;
    }

    Date getStartDate()
    {
        return super.accountOpenedOn;
    }

    public double futureValue()
    {
        return MeritBank.recursiveFutureValue(super.getBalance(), cdOffering.getTerm(), cdOffering.getInterestRate());
    }

    static CDAccount readFromString(String accountData) throws ParseException, NumberFormatException
    {
        System.out.println(accountData);

        String[] tempArr = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long tempAcctNum = Long.parseLong(tempArr[0]);
        double tempBal = Double.parseDouble(tempArr[1]), tempIntRate = Double.parseDouble(tempArr[2]);
        Date tempOpenDate = dateFormat.parse(tempArr[3]);
        int tempTerm = Integer.parseInt(tempArr[4]);

        return new CDAccount(tempAcctNum, tempBal, tempIntRate, tempOpenDate, tempTerm);
    }

    String writeToString()
    {
        String tempAcctNum = String.valueOf(this.getAccountNumber()),
                tempBal = String.valueOf(this.balance),
                tempIntRate = String.valueOf(this.getInterestRate()),
                tempOpenDate = String.valueOf(this.accountOpenedOn),
                tempTerm = String.valueOf(this.term);

        return tempAcctNum + "," +
                tempBal + "," +
                tempIntRate + "," +
                tempOpenDate + "," +
                tempTerm;
    }

    @Override
    boolean withdraw(double amount)
    {
        Date date = new Date();
        int years = accountOpenedOn.getYear() - date.getYear();
        if (years > term) {
            if (amount <= balance && amount > 0) {
                balance -= amount;
                return true;
            }
        }
        return false;
    }

    @Override
    boolean deposit(double amount)
    {
        Date date = new Date();
        int years = accountOpenedOn.getYear() - date.getYear();
        if(years > term)
        {
            if(amount > 0)
            {
                balance += amount;
                return true;
            }
        }
        return false;
    }


}