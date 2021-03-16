package com.meritamerica.assignment4;

import java.text.SimpleDateFormat;

public abstract class Transaction
{
    // TODO --- new class
    // set new instance vars?
    SimpleDateFormat dateFormat;
    private String rejectReason;
    private double amount;
    private BankAccount sourceAcct;
    private BankAccount targetAcct;
    private boolean isProcessed;
    java.util.Date txnDate;

    public BankAccount getSourceAccount()
    {
        // TODO --- done
        // get target account for txn
        return sourceAcct;
    }

    public void setSourceAccount(BankAccount sourceAccount)
    {
        this.sourceAcct = sourceAccount;
    }

    public BankAccount getTargetAccount()
    {
        // TODO --- done
        //  get target account for txn
        return targetAcct;
    }

    public void setTargetAccount(BankAccount targetAccount)
    {
        this.targetAcct = targetAccount;
    }

    public double getAmount()
    {
        // TODO --- add new code
        // get amount of txn from source
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public java.util.Date getTransactionDate()
    {
        // TODO --- done
        // return txn date
        return txnDate;
    }

    public void setTransactionDate(java.util.Date date)
    {
        this.txnDate = date;
    }

    public String writeToString()
    {
        // TODO --- add new code
        // parse data back to str for writing to file
        try
        {

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static Transaction readFromString(String transactionDataString)
    {
        // TODO --- add new code
        //* -1,1,1000.0,01/01/2020 */
        // above data needs to be parse
        try
        {

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new Transaction() { // pass in txn data here
        }
    }

    public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
    {
        // TODO --- add new code
        //
    }

    public boolean isProcessedByFraudTeam()
    {
        // TODO --- add code
        // if amount > 1k, will need to be processed
        // once processed and determined no fraud, return true, else false
    }

    public void setProcessedByFraudTeam(boolean isProcessed)
    {
        this.isProcessed = isProcessed;
    }

    public String getRejectionReason()
    {
        // TODO --- add new code
        // figure out what returns reason why txn was denied/rejected and return
    }

    public void setRejectionReason(String reason)
    {
        this.rejectReason = reason;
    }

}
