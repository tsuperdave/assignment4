package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction
{
    // TODO --- new class
    // set new instance vars?
    private final double FRAUD_REVIEW_TRIGGER = 1000.0;
    protected static BankAccount sourceAcct;
    protected static BankAccount targetAcct;
    SimpleDateFormat dateFormat;
    java.util.Date txnDate;
    private String rejectReason;
    private double amount;
    private boolean isProcessed;


    public BankAccount getSourceAccount()
    {
        // TODO --- done
        return sourceAcct;
    }

    public void setSourceAccount(BankAccount sourceAccount)
    {
        this.sourceAcct = sourceAccount;
    }

    public BankAccount getTargetAccount()
    {
        return targetAcct;
    }

    public void setTargetAccount(BankAccount targetAccount)
    {
        this.targetAcct = targetAccount;
    }

    public double getAmount()
    {
        // TODO --- done
        return this.amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public java.util.Date getTransactionDate()
    {
        // TODO --- done
        return txnDate;
    }

    public void setTransactionDate(java.util.Date date)
    {
        this.txnDate = date;
    }

    public static Transaction readFromString(String transactionDataString) throws ParseException {
        // TODO --- add new code
        //* -1,1,1000.0,01/01/2020 */
        // parsed data. return Obj dependent on first mod in string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long tempAcctNum = 0;
        double tempAmt = 0, tempIntRate = 0;
        int tempTerm = 0, tempTypeOfTxn = 0;
        Date tempOpenDate = null;
        String[] tempArr = transactionDataString.split(",");

        if (transactionDataString.length() > 0)
        {
            tempTypeOfTxn = Integer.parseInt(tempArr[0]);
            tempAcctNum = Long.parseLong(tempArr[1]);
            tempAmt = Double.parseDouble(tempArr[2]);
            tempIntRate = Double.parseDouble(tempArr[3]);
            tempOpenDate = dateFormat.parse(tempArr[4]);
            tempTerm = Integer.parseInt(tempArr[5]);
        }
        else
        {
            throw new NumberFormatException();
        }

        /*
        Returns type of TXN based off first value on TXN string (-1, 1 or 2)
        Assuming can put if statements after Exception, since data parse checks are first
         */
        if(tempTypeOfTxn < 0 && tempAmt > 0)
        {
            return new DepositTransaction(targetAcct, tempAmt);
        }else if(tempTypeOfTxn < 0 && tempAmt < 0)
        {
            return new WithdrawTransaction(targetAcct, tempAmt);
        }
        return new TransferTransaction(sourceAcct, targetAcct, tempAmt);

    }

    public String writeToString()
    {
        return null;
    }

    public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;
        // TODO --- done

    public boolean isProcessedByFraudTeam()
    {
        // TODO --- done
        return isProcessed;
    }

    public void setProcessedByFraudTeam(boolean isProcessed)
    {
        this.isProcessed = isProcessed;
    }

    public String getRejectionReason()
    {
        // TODO --- done
        return rejectReason;
    }

    public void setRejectionReason(String reason)
    {
        this.rejectReason = reason;
    }

}
