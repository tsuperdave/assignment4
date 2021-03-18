package com.meritamerica.assignment4;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

import com.sun.prism.shader.Solid_ImagePattern_Loader;
import org.omg.CORBA.PUBLIC_MEMBER;

public class MeritBank
{
    private static CDOffering[] listOfCDOffers;
    private static AccountHolder[] listOfAccountHolders;
    static FraudQueue fraudQueue = new FraudQueue();
    private static long nextAccountNumber = 1L;
    static long accountNumber;

    static void addAccountHolder(AccountHolder accountHolder)
    {
        for(int i = 0; i < listOfAccountHolders.length; i++)
        {
            if(listOfAccountHolders[i] == null)
            {
                listOfAccountHolders[i] = accountHolder;
                break;
            }
        }
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
            if(ah != null)
            {
                total += ah.getCombinedBalance();
            }
        }
        return total;
    }

    static boolean readFromFile(String fileName)
    {
        // TODO --- debug
        // debug FraudQueue txn adding
        try(Scanner sc = new Scanner(new FileReader(fileName)))
        {
            setNextAccountNumber(Long.parseLong(sc.next()));

            /*
             Get # of CD Offers,
             create new arr to length,
             iterate over index of arr,
             add CD offer into to arr after parsing data
             */
            CDOffering[] newCDarr = new CDOffering[sc.nextInt()];
            for(int i = 0; i < newCDarr.length; i++)
            {
                newCDarr[i] = CDOffering.readFromString(sc.next());
            }
            setCDOfferings(newCDarr);

            /*
            Create new AcctHolder list arr,
            set length to data pulled from txt file,
            iterate, adding new info on each iteration
             */
            AccountHolder[] newAcctHolderList = new AccountHolder[Integer.parseInt(sc.next())];
            for(int i = 0; i < newAcctHolderList.length; i++)
            {
                /*
                get AH1 name, ssn, then # of chk accts,
                create tempAcctHolder var to store info,
                add this iteration to act holder arr
                 */
                AccountHolder tempAcct = AccountHolder.readFromString(sc.next());
                int numOfCheckAccts = sc.nextInt();
                /*
                Iterate per num of chk accts,
                parse acct num, bal,int rate, date
                 */
                for(int j = 0; j < numOfCheckAccts; j++)
                {
                    CheckingAccount newChk = CheckingAccount.readFromString(sc.next());
                    tempAcct.addCheckingAccount(newChk);

                    int numOfTxns = sc.nextInt();
                    for(int k = 0; k < numOfTxns; k++)
                    {
                        Transaction newTxns = Transaction.readFromString(sc.next());
                        newChk.addTransaction(newTxns);
                    }
                }

                /*
                Iterate per num of sav accts,
                parse acct num, bal,int rate, date
                 */
                int numOfSavAccts = sc.nextInt();
                for(int j = 0; j < numOfSavAccts; j++)
                {
                    SavingsAccount newSav = SavingsAccount.readFromString(sc.next());
                    tempAcct.addSavingsAccount(newSav);

                    int numOfTxns = sc.nextInt();
                    for(int k = 0; k < numOfTxns; k++)
                    {
                        Transaction newTxns = Transaction.readFromString(sc.next());
                        newSav.addTransaction(newTxns);
                    }
                }

                /*
                Iterate per num of cd accts,
                parse acct num, bal,int rate, date
                 */
                int numOfCDAccts = sc.nextInt();
                for(int j = 0; j < numOfCDAccts; j++)
                {
                    CDAccount newCD = CDAccount.readFromString(sc.next());
                    tempAcct.addCDAccount(newCD);

                    int numOfTxns = sc.nextInt();
                    for(int k = 0; k < numOfTxns; k++)
                    {
                        Transaction newTxns = Transaction.readFromString(sc.next());
                        newCD.addTransaction(newTxns);
                    }
                }

                /*
                Iterate per num of txn for fraud queue,
                parse source, target, amt, date
                 */
                int numInFraudQueue = sc.nextInt();
                for (int j = 0; j < numInFraudQueue; j++)
                {
                    Transaction newFraudTxn = Transaction.readFromString(sc.next());
                    fraudQueue.addTransaction(newFraudTxn);
                   // will need to add txn to fraud queue

                }
            }
            listOfAccountHolders = newAcctHolderList;

            /*
            Sorts account holders by current total bal,
            prints to console
             */

            sortAccountHolders();

        }catch(Exception e)
        {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    static boolean writeToFile(String fileName)
    {
        // TODO --- debug
        // Should also read BankAccount transactions and the FraudQueue
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
        {
            bw.write(String.valueOf(nextAccountNumber)); bw.newLine();						// next acct num

            bw.write(String.valueOf(listOfCDOffers.length)); bw.newLine();					// num of CD Offers
            for(int i = 0; i < listOfCDOffers.length; i++)
            {
                bw.write(listOfCDOffers[i].writeToString()); bw.newLine();				    // list CD offers
            }

            bw.write(String.valueOf(listOfAccountHolders.length)); bw.newLine();			// num of account holders
            sortAccountHolders();															// sort account holders then iterate over new array, write offers out
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

    public static double recursiveFutureValue(double amount, double years, double interestRate)
    {
        double futureVal = amount + (amount * years);
        if(years <= 1 || amount <= 0 || interestRate <= 0) return futureVal;
        return recursiveFutureValue(futureVal, --years, interestRate);
        // TODO --- done
    }

    public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
    {
        // TODO --- done?
    	/*
        If transaction does not violate any constraints, deposit/withdraw values from the relevant BankAccounts and add the transaction to the relevant BankAccounts
        If the transaction violates any of the basic constraints (negative amount, exceeds available balance) the relevant exception should be thrown and the processing should terminate i.e. false
        If the transaction violates the $1,000 suspicion limit, it should simply be added to the FraudQueue for future processing
        Need to process Source/Target
        Will need to setup instances of withdraw, deposit and transfer
         */
        BankAccount sourceAcct = transaction.getSourceAccount();
        BankAccount targetAcct = transaction.getTargetAccount();
        if (sourceAcct != null) {
            /*
            Processing instances of a Withdrawal
             */
            if (transaction instanceof WithdrawTransaction) {
                if (transaction.getAmount() < 0) {
                    throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
                }
                if (transaction.getAmount() < -1000) {
                    fraudQueue.addTransaction(transaction);
                    throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
                }
                if (targetAcct.getBalance() + transaction.getAmount() < 0) {
                    throw new ExceedsAvailableBalanceException("Insufficient Funds");
                }
                return true;
            }
        /*
        Processing instances of a Deposit
         */
            if (transaction instanceof DepositTransaction) {
                if (transaction.getAmount() < 0) {
                    throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
                }
                if (transaction.getAmount() > 1000) {
                    fraudQueue.addTransaction(transaction);
                    throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
                }
                return true;
            }
        /*
        Processing instances of a Transfer
         */
            if (transaction instanceof TransferTransaction)
            {
                if (sourceAcct.getBalance() < transaction.getAmount())
                {
                    throw new ExceedsAvailableBalanceException("Insufficient Funds");
                }
                if (transaction.getAmount() < 0) {
                    throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
                }
                if (transaction.getAmount() > 1000) {
                    throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
                }
            }else
            {
                sourceAcct.withdraw(transaction.amount);
                targetAcct.deposit(transaction.amount);
            }
            return true;
        }
        return true;
    }
    
    public static FraudQueue getFraudQueue()
    {
        return fraudQueue;
    }
    
    public static BankAccount getBankAccount(long accountId) {
        // TODO --- done?
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