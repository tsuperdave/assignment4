package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

public class SavingsAccount extends BankAccount
{

    SavingsAccount(double openingBalance)
    {
        super(openingBalance, 0.01, new java.util.Date());
    }

    SavingsAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn)
    {
        super(accountNumber, balance, interestRate, accountOpenedOn);
    }

    static SavingsAccount readFromString(String accountData) throws ParseException
    {
        String[] tempArr = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long tempAcctNum = 0;
        double tempBal = 0, tempIntRate = 0;
        Date tempOpenDate = null;
        if(accountData.length() > 0)
        {
            tempAcctNum = Long.parseLong(tempArr[0]);
            tempBal = Double.parseDouble(tempArr[1]);
            tempIntRate = Double.parseDouble(tempArr[2]);
            tempOpenDate = dateFormat.parse((tempArr[3]));
        }
        else
        {
            System.out.println("Savings account data format incorrect");
            throw new NumberFormatException();
        }
        return new SavingsAccount(tempAcctNum, tempBal, tempIntRate, tempOpenDate);
    }
}