package com.meritamerica.assignment4;

import java.text.*;
import java.util.Date;

// add Override for withdraw/deposit so if term < startDate, return false

public class CDAccount extends BankAccount
{

    protected CDOffering cdOffering;

    CDAccount(CDOffering offering, double balance)
    {
        super(balance, offering.getInterestRate(), new java.util.Date());
        this.cdOffering = offering;
    }

    CDAccount(long accountNumber, double balance, double interestRate, int term, Date accountOpenedOn)
    {
        super(accountNumber, balance, interestRate, accountOpenedOn);
        this.cdOffering = new CDOffering(term, interestRate);
    }

    int getTerm()
    {
        return this.cdOffering.getTerm();
    }

    public double futureValue()
    {
        // TODO --- add new code
        return BankAccount.recursiveFutureValue();
    }

    static CDAccount readFromString(String accountData) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long tempAcctNum = 0;
        double tempBal = 0, tempIntRate = 0;
        int tempTerm = 0;
        Date tempOpenDate = null;
        String[] tempArr = accountData.split(",");

        if(accountData.length() > 0)
        {
            tempAcctNum = Long.parseLong(tempArr[0]);
            tempBal = Double.parseDouble(tempArr[1]);
            tempIntRate = Double.parseDouble(tempArr[2]);
            tempOpenDate = dateFormat.parse(tempArr[3]);
            tempTerm = Integer.parseInt(tempArr[4]);
        }
        else
        {
            throw new NumberFormatException();
        }
        return new CDAccount(tempAcctNum, tempBal, tempIntRate, tempOpenDate, tempTerm);
    }

    String writeToString()
    {
        String tempAcctNum = String.valueOf(super.getAccountNumber()),
                tempBal = String.valueOf(this.balance),
                tempIntRate = String.valueOf(getInterestRate()),
                tempOpenDate = String.valueOf(this.startDate),
                tempTerm = String.valueOf(this.getTerm());

        return tempAcctNum + "," +
                tempBal + "," +
                tempIntRate + "," +
                tempOpenDate + "," +
                tempTerm;
    }

    @Override
    boolean withdraw(double amount)
    {
        return false;
    }

    @Override
    boolean deposit (double amount)
    {
        return false;
    }


}