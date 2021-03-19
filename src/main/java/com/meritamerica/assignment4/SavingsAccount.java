package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

public class SavingsAccount extends BankAccount {

    private static final double INTEREST_RATE = 0.01;

    SavingsAccount(double openingBalance) {
        super(openingBalance, INTEREST_RATE, new java.util.Date());
    }

    SavingsAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) {
        super(accountNumber, balance, interestRate, accountOpenedOn);
    }

    static SavingsAccount readFromString(String accountData) throws ParseException, NumberFormatException {
        String[] tempArr = accountData.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        long tempAcctNum = Long.parseLong(tempArr[0]);
        double tempBal = Double.parseDouble(tempArr[1]), tempIntRate = Double.parseDouble(tempArr[2]);
        Date tempOpenDate = dateFormat.parse((tempArr[3]));

        return new SavingsAccount(tempAcctNum, tempBal, tempIntRate, tempOpenDate);
    }
}