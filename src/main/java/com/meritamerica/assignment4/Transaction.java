package com.meritamerica.assignment4;

public abstract class Transaction
{
    public BankAccount getSourceAccount()
    {

    }

    public void setSourceAccount(BankAccount sourceAccount)
    {

    }

    public BankAccount getTargetAccount()
    {

    }

    public void setTargetAccount(BankAccount targetAccount)
    {

    }

    public double getAmount()
    {

    }

    public void setAmount(double amount)
    {

    }

    public java.util.Date getTransactionDate()
    {

    }

    public void setTransactionDate(java.util.Date date)
    {

    }

    public String writeToString()
    {

    }
    public static Transaction readFromString(String transactionDataString)
    {

    }

    public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
    {

    }

    public boolean isProcessedByFraudTeam()
    {

    }

    public void setProcessedByFraudTeam(boolean isProcessed)
    {

    }

    public String getRejectionReason()
    {

    }

    public void setRejectionReason(String reason)
    {

    }

}
