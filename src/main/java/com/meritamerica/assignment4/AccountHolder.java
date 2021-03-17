package com.meritamerica.assignment4;

import com.sun.org.apache.xpath.internal.operations.Neg;

import java.io.BufferedReader;
public class AccountHolder implements Comparable<AccountHolder>
{
    private static final double BALANCE_LIMIT = 250000;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ssn;
    private CheckingAccount[] checkingAccountList = new CheckingAccount[10];
    private SavingsAccount[] savingsAccountList = new SavingsAccount[10];
    private CDAccount[] cdAccountList = new CDAccount[10];

    AccountHolder(){}

    AccountHolder(
            String firstName,
            String middleName,
            String lastName,
            String ssn
    )
    {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.ssn = ssn;
    }

    String getFirstName(){ return firstName; }
    void setFirstName(String firstName){ this.firstName = firstName; }

    String getMiddleName(){return middleName;}
    void setMiddleName(String middleName){ this.middleName = middleName; }

    String getLastName(){ return lastName; }
    void setLastName(String lastName){ this.lastName = lastName; }

    String getSSN(){ return ssn; }
    void setSSN(String ssn){ this.ssn = ssn; }

    double getCombinedBalance(){ return getCheckingBalance() + getSavingsBalance() + getCDBalance(); }

    /* CHECKING ACCOUNT */
    CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException
    {
        // TODO -- add new code
        /*
        Need to modify exception class methods to accept a string to display
         */
        if((openingBalance + (getCombinedBalance() - getCDBalance()) >= BALANCE_LIMIT))
        {
            throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit. Unable to open new account at this time");
        }
        CheckingAccount newCheckingAccount = new CheckingAccount(openingBalance);
        DepositTransaction transaction = new DepositTransaction(newCheckingAccount, openingBalance);

        try
        {
            MeritBank.processTransaction(transaction);
        }
        catch(ExceedsFraudSuspicionLimitException e)
        {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch(NegativeAmountException e)
        {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return newCheckingAccount;
    }

    CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException
    {
        // TODO --- add new code
        /*
        If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
        Should also add a deposit transaction with the opening balance
         */
        if ((getCheckingBalance() + (getCombinedBalance() - getCDBalance()) >= BALANCE_LIMIT))
        {
            throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit. Unable to open new account at this time");
        }

        DepositTransaction transaction = new DepositTransaction(checkingAccount, getCheckingBalance());

        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e)
        {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e)
        {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return checkingAccount;
    }

    CheckingAccount[] getCheckingAccounts()
    {
        return checkingAccountList;
    }

    int getNumberOfCheckingAccounts()
    {
        int numOfAccounts = 0;
        for(CheckingAccount i: checkingAccountList)
        {
            if(i != null)
            {
                numOfAccounts++;
            }
        }
        return numOfAccounts;
    }

    double getCheckingBalance()
    {
        double sum = 0;
        for(int i = 0; i < checkingAccountList.length + 1; i++)
        {
            if(checkingAccountList[i] == null)
            {
                break;
            }
            sum += checkingAccountList[i].getBalance();
        }
        return sum;
    }

    /* SAVINGS ACCOUNT */
    SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException
    {
        // TODO -- add new code
        /*
        Need to modify exception class methods to accept a string to display
         */
        if((openingBalance + (getCombinedBalance() - getCDBalance()) >= BALANCE_LIMIT))
        {
            throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit. Unable to open new account at this time");
        }
        SavingsAccount newSavingsAccount = new SavingsAccount(openingBalance);
        DepositTransaction transaction = new DepositTransaction(newSavingsAccount, openingBalance);

        try
        {
            MeritBank.processTransaction(transaction);
        }
        catch(ExceedsFraudSuspicionLimitException e)
        {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch(NegativeAmountException e)
        {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return newSavingsAccount;
    }

    SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException
    {
        // TODO --- add new code
        /*
        If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
        Should also add a deposit transaction with the opening balance
         */
        if ((getSavingsBalance() + (getCombinedBalance() - getCDBalance()) >= BALANCE_LIMIT))
        {
            throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit. Unable to open new account at this time");
        }

        DepositTransaction transaction = new DepositTransaction(savingsAccount, getSavingsBalance());

        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e)
        {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e)
        {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return savingsAccount;
    }

    SavingsAccount[] getSavingsAccounts()
    {
        return savingsAccountList;
    }

    int getNumberOfSavingsAccounts()
    {
        int numOfAccounts = 0;
        for(SavingsAccount i: savingsAccountList)
        {
            if(i != null)
            {
                numOfAccounts++;
            }
        }
        return numOfAccounts;
    }

    double getSavingsBalance()
    {
        double sum = 0;
        for(int i = 0; i < savingsAccountList.length + 1; i++)
        {
            if(savingsAccountList[i] == null)
            {
                break;
            }
            sum += savingsAccountList[i].getBalance();
        }
        return sum;
    }

    /* CD ACCOUNT */
    CDAccount addCDAccount(CDOffering offering, double openingBalance)
    {
        // TODO --- add new code
        // Should also add a deposit transaction with the opening balance
        if(MeritBank.getCDOfferings() == null) return null;

        CDAccount newCDAccount = new CDAccount(offering, openingBalance);
        DepositTransaction transaction = new DepositTransaction(newCDAccount, openingBalance);

        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e)
        {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e)
        {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return newCDAccount;
    }

    CDAccount addCDAccount(CDAccount cdAccount)
    {
        if(cdAccountList == null)
        {
            return null;
        }
        for(int i = 0; i < cdAccountList.length; i++)
        {
            if(cdAccountList[i] == null)
            {
                cdAccountList[i] = cdAccount;
                break;
            }
        }
        return cdAccount;
    }

    CDAccount[] getCDAccounts()
    {
        return cdAccountList;
    }

    int getNumberOfCDAccounts()
    {
        int numOfAccounts = 0;
        for(CDAccount i: cdAccountList)
        {
            if(i != null)
            {
                numOfAccounts++;
            }
        }
        return numOfAccounts;
    }

    double getCDBalance()
    {
        double sum = 0;
        for(int i = 0; i < cdAccountList.length + 1; i++)
        {
            if(cdAccountList[i] == null)
            {
                break;
            }
            sum += cdAccountList[i].getBalance();
        }
        return sum;
    }

    static AccountHolder readFromString(String accountHolderData) throws Exception
    {
        String[] tempArr = accountHolderData.split(",");
        String tempFirstName = "", tempMidName = "", TempLastName = "", tempSSN = "";

        tempFirstName += tempArr[0];
        tempMidName += tempArr[1];
        TempLastName += tempArr[2];
        tempSSN += tempArr[3];

        return new AccountHolder(tempFirstName, tempMidName, TempLastName, tempSSN);
    }

    String writeToString()
    {
        // Store num of accounts as str to concat into result
        int tempNumOfChk = this.getNumberOfCheckingAccounts(),
                tempNumOfSav = this.getNumberOfSavingsAccounts(),
                tempNumOfCDs = this.getNumberOfCDAccounts();

        // resulting string to add to
        String result = getLastName() + "," + getMiddleName() + "," + getFirstName() + "," + getSSN() + "\n" +
                tempNumOfChk + "\n";

        // loop over checking accounts, run writeToString to return data from class
        for(int i = 0; i < tempNumOfChk; i++)
        {
            result += this.checkingAccountList[i].writeToString() + "\n";
        }
        result += tempNumOfSav + "\n";

        // loop over savings accounts, run writeToString to return data from class
        for(int i = 0; i < tempNumOfSav; i++)
        {
            result += this.savingsAccountList[i].writeToString() + "\n";
        }
        result += tempNumOfCDs + "\n";

        // loop over cd accounts, run writeToString to return data from class
        for(int i = 0; i < tempNumOfCDs; i++)
        {
            result += this.cdAccountList[i].writeToString() + "\n";
        }

        return result;
    }

    @Override
    public int compareTo(AccountHolder otherAccountHolder)
    {
        // --- compares combined balances of obj passed in to current instance of obj -- //
        if(otherAccountHolder == null) return 0;
        if(this.getCombinedBalance() > otherAccountHolder.getCombinedBalance()) return 1;
        else if(this.getCombinedBalance() < otherAccountHolder.getCombinedBalance()) return -1;
        return 0;
    }

}