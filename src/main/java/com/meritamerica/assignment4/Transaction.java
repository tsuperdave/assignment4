package com.meritamerica.assignment4;

import java.text.*;
import java.util.Date;

public abstract class Transaction
{
    final double FRAUD_THRESHOLD = 1000;
    BankAccount sourceAcct;
    BankAccount targetAcct;
    Date txnDate;
    double amount;
    String rejectReason;
    boolean isProcessed;

    public void setSourceAccount(BankAccount sourceAccount)
    {
        this.sourceAcct = sourceAccount;
    }

    public BankAccount getSourceAccount()
    {
        return this.sourceAcct;
    }

    public void setTargetAccount(BankAccount targetAccount)
    {
        this.targetAcct = targetAccount;
    }

    public BankAccount getTargetAccount()
    {
        return this.targetAcct;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setTransactionDate(Date date)
    {
        this.txnDate = date;
    }

    public Date getTransactionDate()
    {
        return this.txnDate;
    }

    public static Transaction readFromString(String transactionDataString) throws ParseException
    {
        System.out.println(transactionDataString);
        // -1,1,1000.0,01/01/2020  type, targetacctnum, amt, date
        String[] tempArr = transactionDataString.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // long tempTypeOfTxn = Long.parseLong(tempArr[0]);
        // long tempTargetAcctNum = Long.parseLong(tempArr[1]);
        double tempAmt = Double.parseDouble(tempArr[2]);
        Date tempOpenDate = dateFormat.parse(tempArr[3]);

        /*
        -1 indicates deposits/withdrawals, 1 or 2 indicate transfers
         */
        BankAccount source;
        if(tempArr[0].equals("-1"))
        {
            source = null;
        }else{
            source =  MeritBank.getBankAccount(Long.parseLong(tempArr[0]));
        }

        BankAccount targetAcct = MeritBank.getBankAccount(Long.parseLong(tempArr[1]));

        if(Integer.parseInt(tempArr[0]) == -1)
            if (Double.parseDouble(tempArr[2]) < 0)
            {
                    // System.out.println(transactionDataString);
                    WithdrawTransaction txn = new WithdrawTransaction(targetAcct, tempAmt);
                    txn.setTransactionDate(tempOpenDate);
                    return txn;
            } else
            {
                // System.out.println(transactionDataString);
                DepositTransaction txn = new DepositTransaction(targetAcct, tempAmt);
                txn.setTransactionDate(tempOpenDate);
                return txn;
            }
            // System.out.println(transactionDataString);
            TransferTransaction txn = new TransferTransaction(source, targetAcct, tempAmt);
            txn.setTransactionDate(tempOpenDate);
            return txn;

    }

    public String writeToString() {
        String formattedDate = new String("dd/MM/yyyy");
        StringBuilder newStr = new StringBuilder();

        if(sourceAcct == null) {
            newStr.append(-1);
        } else {
            newStr.append(sourceAcct.getAccountNumber());
        }
        newStr.append(",").append(targetAcct.getAccountNumber()).append(",").append(amount).append(",").append(String.format(formattedDate, txnDate));

        return newStr.toString();
    }

    public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;

    public boolean isProcessedByFraudTeam()
    {
        return this.isProcessed;
    }

    public void setProcessedByFraudTeam(boolean isProcessed)
    {
        this.isProcessed = isProcessed;
    }

    public String getRejectionReason()
    {
        return rejectReason;
    }

    public void setRejectionReason(String reason)
    {
        this.rejectReason = reason;
    }

}
