package com.meritamerica.assignment4;

import java.text.*;
import java.util.Date;

public abstract class Transaction
{
    // TODO --- new class
    BankAccount sourceAcct;
    BankAccount targetAcct;
    Date txnDate;
    double amount;
    String rejectReason;
    boolean isProcessed;

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
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public Date getTransactionDate()
    {
        // TODO --- done
        return txnDate;
    }

    public void setTransactionDate(Date date)
    {
        this.txnDate = date;
    }

    public static Transaction readFromString(String transactionDataString) throws ParseException
    {
        // TODO --- done
        // -1,1,1000.0,01/01/2020  type, targetacctnum, amt, date
        String[] tempArr = transactionDataString.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        int tempTypeOfTxn = Integer.parseInt(tempArr[0]);
        long tempTargetAcctNum = Long.parseLong(tempArr[1]);
        double tempAmt = Double.parseDouble(tempArr[2]);
        Date tempOpenDate = dateFormat.parse(tempArr[3]);

        /*
        -1 indicates deposits/withdrawals, 1 or 2 indicate transfers
         */
        BankAccount source =  MeritBank.getBankAccount(tempTypeOfTxn);
        BankAccount targetAcct = MeritBank.getBankAccount(tempTargetAcctNum);

        if (tempTypeOfTxn == -1 && tempAmt < 0) {
                WithdrawTransaction txn = new WithdrawTransaction(targetAcct, tempAmt);
                txn.setTransactionDate(tempOpenDate);
                System.out.println(txn.writeToString());
                return txn;
        } else if(tempTypeOfTxn == -1 && tempAmt > 0){
            DepositTransaction txn = new DepositTransaction(targetAcct, tempAmt);
            txn.setTransactionDate(tempOpenDate);
            System.out.println(txn.writeToString());
            return txn;
        }
        TransferTransaction txn = new TransferTransaction(source, targetAcct, tempAmt);
        txn.setTransactionDate(tempOpenDate);
        System.out.println(txn.writeToString());
        return txn;

    }

    public String writeToString()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder newStr = new StringBuilder();
        if(sourceAcct == null)
        {
            newStr.append(-1);
        }
        else
        {
            newStr.append(sourceAcct.getAccountNumber());
        }
        newStr.append(",");
        newStr.append(targetAcct.getAccountNumber());
        newStr.append(",");
        newStr.append(amount);
        newStr.append(",");
        newStr.append(dateFormat.format(txnDate));

        return newStr.toString();
    }

    public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;
        // TODO --- done

    public boolean isProcessedByFraudTeam()
    {
        // TODO --- done
        return this.isProcessed;
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
