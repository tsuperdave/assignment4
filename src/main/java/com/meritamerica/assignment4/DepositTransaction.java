package com.meritamerica.assignment4;

import java.util.Date;

public class DepositTransaction extends Transaction
{
    DepositTransaction(BankAccount targetAccount, double amount)
    {
        sourceAcct = targetAcct;
        this.targetAcct = targetAccount;
        this.amount = amount;
        this.txnDate = new Date();
    }
}
