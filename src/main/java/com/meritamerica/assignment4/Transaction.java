package com.meritamerica.assignment4;

import java.text.*;
import java.util.Date;

public abstract class Transaction
{
    final double FRAUD_THRESHOLD = 1000;
    protected BankAccount sourceAcct;
    protected BankAccount targetAcct;
    protected Date txnDate;
    protected double amount;
    private String rejectReason;
    private boolean isProcessed;

    public void setSourceAccount(BankAccount sourceAccount) {
        this.sourceAcct = sourceAccount;
    }

    public BankAccount getSourceAccount() {
        return this.sourceAcct;
    }

    public void setTargetAccount(BankAccount targetAccount) {
        this.targetAcct = targetAccount;
    }

    public BankAccount getTargetAccount() {
        return this.targetAcct;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setTransactionDate(Date date) {
        this.txnDate = date;
    }

    public Date getTransactionDate() {
        return this.txnDate;
    }

    public static Transaction readFromString(String transactionDataString) throws ParseException {
        System.out.println(transactionDataString);

        String[] tempArr = transactionDataString.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        double tempAmt = Double.parseDouble(tempArr[2]);
        Date tempOpenDate = dateFormat.parse(tempArr[3]);

        /*
        -1 indicates deposits/withdrawals, 1 or 2 indicate transfers
         */
        BankAccount source;
        if(tempArr[0].equals("-1")) {
            source = null;
        } else {
            source =  MeritBank.getBankAccount(Long.parseLong(tempArr[0]));
        }

        BankAccount targetAcct = MeritBank.getBankAccount(Long.parseLong(tempArr[1]));

        if(Integer.parseInt(tempArr[0]) == -1)
            if (Double.parseDouble(tempArr[2]) < 0) {
                    WithdrawTransaction txn = new WithdrawTransaction(targetAcct, tempAmt);
                    txn.setTransactionDate(tempOpenDate);
                    return txn;
            } else {
                DepositTransaction txn = new DepositTransaction(targetAcct, tempAmt);
                txn.setTransactionDate(tempOpenDate);
                return txn;
            }
            TransferTransaction txn = new TransferTransaction(source, targetAcct, tempAmt);
            txn.setTransactionDate(tempOpenDate);
            return txn;

    }

    public String writeToString() {
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder newStr = new StringBuilder();

        if(this.getSourceAccount() == null) {
            newStr.append("-1,");
        } else {
            newStr.append(this.getSourceAccount().getAccountNumber());
        }
        newStr.append(this.getTargetAccount().accountNumber).append(",").append(this.amount).append(",").append(formattedDate.format(this.txnDate));

        return newStr.toString();
    }

    public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;

    public boolean isProcessedByFraudTeam() {
        return this.isProcessed;
    }

    public void setProcessedByFraudTeam(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public String getRejectionReason() {
        return rejectReason;
    }

    public void setRejectionReason(String reason) {
        this.rejectReason = reason;
    }

}
