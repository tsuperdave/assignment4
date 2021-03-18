package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

public class CheckingAccount extends BankAccount
{
    private static final double INTEREST_RATE = 0.0001;

    CheckingAccount(double openingBalance)
    {
        super(openingBalance, INTEREST_RATE, new java.util.Date());
    }

    CheckingAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn)
    {
        super(accountNumber, balance, interestRate, accountOpenedOn);
    }

    static CheckingAccount readFromString(String accountData) throws ParseException, NumberFormatException
    {
        System.out.println(accountData);

        String[] tempArr = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long tempAcctNum = Long.parseLong(tempArr[0]);
        double tempBal = Double.parseDouble(tempArr[1]), tempIntRate = Double.parseDouble(tempArr[2]);
        Date tempOpenDate = dateFormat.parse((tempArr[3]));

        return new CheckingAccount(tempAcctNum, tempBal, tempIntRate, tempOpenDate);
    }
}