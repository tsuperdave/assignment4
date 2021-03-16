package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

public class CheckingAccount extends BankAccount
{
	private static double checkingInterestRate = 0.0001;

    CheckingAccount(double openingBalance)
    {
        super(openingBalance, checkingInterestRate);
    }

    CheckingAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn)
    {
        super(accountNumber, balance, interestRate, accountOpenedOn);
    }

    static CheckingAccount readFromString(String accountData) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long tempAcctNum = 0;
        double tempBal = 0, tempIntRate = 0;
        Date tempOpenDate = null;
        String[] tempArr = accountData.split(",");
        if(accountData.length() > 0)
        {
            tempAcctNum = Long.parseLong(tempArr[0]);
            tempBal = Double.parseDouble(tempArr[1]);
            tempIntRate = Double.parseDouble(tempArr[2]);
            tempOpenDate = dateFormat.parse((tempArr[3]));
        }
        else
        {
            System.err.println("Checking account data format incorrect");
            throw new NumberFormatException();
        }
        return new CheckingAccount(tempAcctNum, tempBal, tempIntRate, tempOpenDate);
    }
}