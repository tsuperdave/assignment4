package com.meritamerica.assignment4;

import java.util.Date;

public class WithdrawTransaction extends Transaction
{

    WithdrawTransaction(BankAccount targetAccount, double amount)
    {
        this.targetAcct = targetAccount;
        this.amount = amount;
        this.txnDate = new Date();
    }

    @Override
    public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
    {
        if(amount > FRAUD_THRESHOLD) throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        if(amount < 0) throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        if(amount > targetAcct.getBalance()) throw new ExceedsAvailableBalanceException("Insufficient Funds");
        this.targetAcct.withdraw(this.amount);
    }
}
