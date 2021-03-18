package com.meritamerica.assignment4;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

import com.sun.prism.shader.Solid_ImagePattern_Loader;
import org.omg.CORBA.PUBLIC_MEMBER;

public class MeritBank
{
    private static CDOffering[] listOfCDOffers = new CDOffering[0];
    private static AccountHolder[] listOfAccountHolders = new AccountHolder[0];
    static FraudQueue fraudQueue = new FraudQueue();
    private static long nextAccountNumber = 1L;
    static int accountHolderCount = 0;

    static void addAccountHolder(AccountHolder accountHolder)
    {
        AccountHolder[] tempArr = new AccountHolder[listOfAccountHolders.length +1];
        for(int i = 0; i < listOfAccountHolders.length; i++)
        {
            tempArr[i] = listOfAccountHolders[i];
        }
        tempArr[accountHolderCount] = accountHolder;
        listOfAccountHolders = tempArr;
        accountHolderCount++;
    }

    static AccountHolder[] getAccountHolders()
    {
        return listOfAccountHolders;
    }

    static CDOffering[] getCDOfferings()
    {
        return listOfCDOffers;
    }

    static CDOffering getBestCDOffering(double depositAmount)
    {
        if(listOfCDOffers == null) return null;
        double stored = recursiveFutureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        int indexBiggest = 0;
        for(int i = 1; i < listOfCDOffers.length; i++)
        {
            double tempStored = recursiveFutureValue(depositAmount, listOfCDOffers[i].getInterestRate(), listOfCDOffers[i].getTerm());
            if(tempStored > stored)
            {
                stored = tempStored;
                indexBiggest = i;
            }
        }
        return listOfCDOffers[indexBiggest];
    }

    static CDOffering getSecondBestCDOffering(double depositAmount)
    {
        if(listOfCDOffers == null) return null;
        double biggest = recursiveFutureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        double secondBiggest = recursiveFutureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        int indexBiggest = 0;
        int indexSecondBiggest = 0;
        for(int i = 1; i < listOfCDOffers.length; i++)
        {
            double tempStored = recursiveFutureValue(depositAmount, listOfCDOffers[i].getInterestRate(), listOfCDOffers[i].getTerm());
            if(tempStored > biggest)
            {
                indexSecondBiggest = indexBiggest;
                indexBiggest = i;
            }
            if(tempStored > secondBiggest && tempStored != biggest)
            {
                indexSecondBiggest = i;
            }
        }
        return listOfCDOffers[indexSecondBiggest];
    }

    static void clearCDOfferings()
    {
        listOfCDOffers = null;
    }

    static void setCDOfferings(CDOffering[] offerings)
    {
        listOfCDOffers = offerings;
    }

    static long getNextAccountNumber()
    {
        return nextAccountNumber++;
    }

    static double totalBalances()
    {
        double total = 0;
        for(AccountHolder ah: listOfAccountHolders)
        {
            total += ah.getCombinedBalance();
        }
        return total;
    }

    static boolean readFromFile(String fileName)
    {
        try(Scanner sc = new Scanner(new FileReader(fileName)))
        {
            setNextAccountNumber(0); // need to reset acct num and base off file
            setNextAccountNumber(Long.parseLong(sc.next()));
            System.out.println(nextAccountNumber);

            // --- CD OFFERS --- //
            CDOffering[] newCDarr = new CDOffering[sc.nextInt()];
            System.out.println(newCDarr.length);

            for(int i = 0; i < newCDarr.length; i++)
            {
                newCDarr[i] = CDOffering.readFromString(sc.next());
            }
            setCDOfferings(newCDarr);

            // --- ACCOUNT HOLDER --- //
            AccountHolder[] newAcctHolderList = new AccountHolder[Integer.parseInt(sc.next())];
            System.out.println(newAcctHolderList.length);

            for(int i = 0; i < newAcctHolderList.length; i++)
            {
                AccountHolder tempAcct = AccountHolder.readFromString(sc.next());
                newAcctHolderList[i] = tempAcct;

                // --- CHECK ACCT + TXNs --- //
                int numOfCheckAccts = sc.nextInt();
                System.out.println(numOfCheckAccts);

                for(int j = 0; j < numOfCheckAccts; j++)
                {
                    CheckingAccount newChk = CheckingAccount.readFromString(sc.next());
                    tempAcct.addCheckingAccount(newChk);

                    int numOfTxns = sc.nextInt();
                    System.out.println(numOfTxns);

                    for(int k = 0; k < numOfTxns; k++)
                    {
                        Transaction newTxns = Transaction.readFromString(sc.next());
                        newChk.addTransaction(newTxns);
                    }
                }

                // --- SAVINGS ACCT + TXNs --- //
                int numOfSavAccts = sc.nextInt();
                System.out.println(numOfSavAccts);

                for(int j = 0; j < numOfSavAccts; j++)
                {
                    SavingsAccount newSav = SavingsAccount.readFromString(sc.next());
                    tempAcct.addSavingsAccount(newSav);

                    int numOfTxns = sc.nextInt();
                    System.out.println(numOfTxns);

                    for(int k = 0; k < numOfTxns; k++)
                    {
                        Transaction newTxns = Transaction.readFromString(sc.next());
                        newSav.addTransaction(newTxns);
                    }
                }

                // --- CD ACCT + TXNs ---//
                int numOfCDAccts = sc.nextInt();
                System.out.println(numOfCDAccts);

                for(int j = 0; j < numOfCDAccts; j++)
                {
                    CDAccount newCD = CDAccount.readFromString(sc.next());
                    tempAcct.addCDAccount(newCD);

                    int numOfTxns = sc.nextInt();
                    System.out.println(numOfTxns);

                    for(int k = 0; k < numOfTxns; k++)
                    {
                        Transaction newTxns = Transaction.readFromString(sc.next());
                        newCD.addTransaction(newTxns);
                    }
                }

            }
            // --- FRAUD TXNs ---//
            int numInFraudQueue = sc.nextInt();
            System.out.println(numInFraudQueue);

            for (int j = 0; j < numInFraudQueue; j++)
            {
                Transaction newFraudTxn = Transaction.readFromString(sc.next());
                fraudQueue.addTransaction(newFraudTxn);
            }
            // sortAccountHolders();

            listOfAccountHolders = newAcctHolderList;

            /*
            Sorts account holders by current total bal,
            prints to console
             */



        }catch(Exception e)
        {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    static boolean writeToFile(String fileName)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
        {
            bw.write(String.valueOf(nextAccountNumber)); bw.newLine();

            bw.write(String.valueOf(listOfCDOffers.length)); bw.newLine();
            for(int i = 0; i < listOfCDOffers.length; i++)
            {
                bw.write(listOfCDOffers[i].writeToString()); bw.newLine();
            }

            bw.write(String.valueOf(listOfAccountHolders.length)); bw.newLine();
            sortAccountHolders();
            for(int i = 0; i < listOfAccountHolders.length; i++)
            {
                bw.write(listOfAccountHolders[i].writeToString()); bw.newLine();
            }
            bw.flush();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    static AccountHolder[] sortAccountHolders()
    {
        Arrays.sort(listOfAccountHolders);

        for(int i = 0; i < listOfAccountHolders.length; i++)
        {
            System.out.println(listOfAccountHolders[i].getLastName() + ","
                    + listOfAccountHolders[i].getMiddleName() + ","
                    + listOfAccountHolders[i].getFirstName() + ","
                    + listOfAccountHolders[i].getSSN() + "\n" +
                    " has a combined accounts balance of " +
                    listOfAccountHolders[i].getCombinedBalance());
        }


        return listOfAccountHolders;
    }

    private static void setNextAccountNumber(long nextAccountNumber)
    {
        MeritBank.nextAccountNumber = nextAccountNumber;
    }

    static double futureValue(double presentValue, double interestRate, int term)
    {
        return recursiveFutureValue(presentValue,term,interestRate);
    }

    public static double recursiveFutureValue(double amount, double years, double interestRate)
    {
        double futureVal = amount + (amount * years);
        if(years <= 1 || amount <= 0 || interestRate <= 0) return futureVal;
        return recursiveFutureValue(futureVal, --years, interestRate);
    }

    public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
    {
        BankAccount sourceAcct = transaction.getSourceAccount();
        BankAccount targetAcct = transaction.getTargetAccount();

        if (sourceAcct == null) {
            /*
            Processing instances of a Withdrawal
             */
            if (transaction instanceof WithdrawTransaction) {
                if (transaction.getAmount() < 0) {
                    throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
                }
                if (targetAcct.getBalance() + transaction.getAmount() < 0) {
                    throw new ExceedsAvailableBalanceException("Insufficient Funds");
                }
                if (transaction.getAmount() <= -1000) {
                    fraudQueue.addTransaction(transaction);
                    throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
                }
                return true;
            }
                /*
                Processing instances of a Deposit
                */
            if (transaction.getAmount() < 0) {
                throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
            }
            if (transaction.getAmount() >= 1000) {
                fraudQueue.addTransaction(transaction);
                throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
            }
            return true;
        }

            /*
            Processing instances of a Transfer
             */
            if (sourceAcct.getBalance() < transaction.getAmount())
            {
                throw new ExceedsAvailableBalanceException("Insufficient Funds");
            }
            if (transaction.getAmount() < 0) {
                throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
            }
            if (transaction.getAmount() > 1000)
            {
                fraudQueue.addTransaction(transaction);
                throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
            }
            else
            {
                sourceAcct.withdraw(transaction.amount);
                targetAcct.deposit(transaction.amount);
            }
        return true;
    }
    
    public static FraudQueue getFraudQueue()
    {
        return fraudQueue;
    }
    
    public static BankAccount getBankAccount(long accountId) {
        for (AccountHolder ah: listOfAccountHolders)
        {
            // iterate over checking and match ID to current iteration in any one of the iterations
            for(int i = 0; i < ah.getCheckingAccounts().length; i++)
            {
                if (accountId == ah.getCheckingAccounts()[i].getAccountNumber())
                {
                    return ah.getCheckingAccounts()[i];
                }
            }
            // iterate over savings and match ID to current iteration in any one of the iterations
            for(int i = 0; i < ah.getSavingsAccounts().length; i++)
            {
                if (accountId == ah.getSavingsAccounts()[i].getAccountNumber())
                {
                    return ah.getSavingsAccounts()[i];
                }
            }
            // iterate over CD accts and match ID to current iteration in any one of the iterations
            for(int i = 0; i < ah.getCDAccounts().length; i++)
            {
                if (accountId == ah.getCDAccounts()[i].getAccountNumber())
                {
                    return ah.getCDAccounts()[i];
                }
            }
        }
        // return null if acct not found
        return null;
    }
}