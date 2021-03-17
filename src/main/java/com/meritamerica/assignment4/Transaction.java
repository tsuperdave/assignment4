package com.meritamerica.assignment4;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction
{
    // TODO --- new class
    // set new instance vars?
    SimpleDateFormat dateFormat;
    private String rejectReason;
    private double amount;
    private static BankAccount sourceAcct;
    private static BankAccount targetAcct;
    private boolean isProcessed;
    java.util.Date txnDate;

    public BankAccount getSourceAccount()
    {
        // TODO --- done
        // get target account for txn
        return this.sourceAcct;
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
        // return txn date
        return txnDate;
    }

    public void setTransactionDate(java.util.Date date)
    {
        this.txnDate = date;
    }

    public static Transaction readFromString(String transactionDataString)
    {
        // TODO --- add new code
        //* -1,1,1000.0,01/01/2020 */
        // parsed data. return Obj dependent on first mod in string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long tempAcctNum = 0;
        double tempAmt = 0, tempIntRate = 0;
        int tempTerm = 0, tempTypeOfTxn = 0;
        Date tempOpenDate = null;
        String[] tempArr = transactionDataString.split(",");
        try
        {
            if (transactionDataString.length() > 0)
            {
                tempTypeOfTxn = Integer.parseInt(tempArr[0]);
                tempAcctNum = Long.parseLong(tempArr[1]);
                tempAmt = Double.parseDouble(tempArr[2]);
                tempIntRate = Double.parseDouble(tempArr[3]);
                tempOpenDate = dateFormat.parse(tempArr[4]);
                tempTerm = Integer.parseInt(tempArr[5]);
            }
        }
        catch(Exception e)
        {
            throw new NumberFormatException();
        }
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
        // TODO --- add new code
        // parse data back to str for writing to file
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;
        // TODO --- done

    public boolean isProcessedByFraudTeam()
    {
        // TODO --- add code
        // if amount > 1k, will need to be processed
        // once processed and determined no fraud, return true, else false
        return false;
    }

    public void setProcessedByFraudTeam(boolean isProcessed)
    {
        this.isProcessed = isProcessed;
    }

    public String getRejectionReason()
    {
        // TODO --- add new code
        // figure out what returns reason why txn was denied/rejected and return
        return "";
    }

    public void setRejectionReason(String reason)
    {
        this.rejectReason = reason;
    }

}
