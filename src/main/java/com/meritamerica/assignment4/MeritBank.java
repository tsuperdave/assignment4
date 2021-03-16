package com.meritamerica.assignment4;

import java.io.*;
import java.util.*;

import org.omg.CORBA.PUBLIC_MEMBER;

public class MeritBank
{
    private static CDOffering[] listOfCDOffers;
    private static AccountHolder[] listOfAccountHolders;
    private static long nextAccountNumber = 0L;

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

    static CDOffering[] getCDOfferings(){ return listOfCDOffers; }

    static CDOffering getBestCDOffering(double depositAmount)
    {
        if(listOfCDOffers == null) return null;
        double stored = futureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        int indexBiggest = 0;
        for(int i = 1; i < listOfCDOffers.length; i++)
        {
            double tempStored = futureValue(depositAmount, listOfCDOffers[i].getInterestRate(), listOfCDOffers[i].getTerm());
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
        double biggest = futureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        double secondBiggest = futureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        int indexBiggest = 0;
        int indexSecondBiggest = 0;
        for(int i = 1; i < listOfCDOffers.length; i++)
        {
            double tempStored = futureValue(depositAmount, listOfCDOffers[i].getInterestRate(), listOfCDOffers[i].getTerm());
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

    public static double futureValue(double presentValue, double interestRate, int term)
    {
        return presentValue * Math.pow((1 + interestRate), term);
    }

    static boolean readFromFile(String fileName)
    {
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

                int numInFraudQueue = sc.nextInt();
                for (int j = 0; j < numInFraudQueue; j++)
                {
                    FraudQueue newFraudQ = new FraudQueue();
                    sc.next();
                    // TODO --- finish
                    // determine modifier of txn
                    // 2 is txfr
                    // -1 is deposit
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
            return false;
        }
        return true;
    }

    static boolean writeToFile(String fileName)
    {
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
        // --- Sort list of accountHolders by balance and display in output --- //
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

    private static void setNextAccountNumber(long nextAccountNumber){ MeritBank.nextAccountNumber = nextAccountNumber; }

    public static double recursiveFutureValue(double amount, int years, double interestRate)
    {
        // TODO --- add code
    	// Existing futureValue methods that used to call Math.pow() should now call this method
    }

    public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
    {
        // TODO --- add new code
    	/*
        If transaction does not violate any constraints, deposit/withdraw values from the relevant BankAccounts and add the transaction to the relevant BankAccounts
        If the transaction violates any of the basic constraints (negative amount, exceeds available balance) the relevant exception should be thrown and the processing should terminate
        If the transaction violates the $1,000 suspicion limit, it should simply be added to the FraudQueue for future processing
         */
    }
    
    public static FraudQueue getFraudQueue()
    {
    	// TODO --- add new code
    }
    
    public static BankAccount getBankAccount(long accountId)
    {
    	// TODO --- add new code
    	// return null if account not found
    }
   
}