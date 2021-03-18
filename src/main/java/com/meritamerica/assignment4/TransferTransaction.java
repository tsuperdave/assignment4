package com.meritamerica.assignment4;

import java.util.Date;

public class TransferTransaction extends Transaction
{
    TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount)
    {
        this.sourceAcct = sourceAccount;
        this.targetAcct = targetAccount;
        this.amount = amount;
        this.txnDate = new Date();
    }

    @Override
    public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {

    }
}
