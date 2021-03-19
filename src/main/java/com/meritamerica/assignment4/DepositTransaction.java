package com.meritamerica.assignment4;

import java.util.Date;

public class DepositTransaction extends Transaction
{
    DepositTransaction(BankAccount targetAccount, double amount)
    {
        this.targetAcct = targetAccount;
        this.amount = amount;
    }
    @Override
    public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
    {
        if(amount > FRAUD_THRESHOLD) throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        if(amount < 0) throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        this.targetAcct.withdraw(this.amount);
    }
}
