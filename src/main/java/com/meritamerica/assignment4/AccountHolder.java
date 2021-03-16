package com.meritamerica.assignment4;

import java.io.BufferedReader;
public class AccountHolder implements Comparable<AccountHolder>
{
    /* INSTANCE VARIABLES */
    private static final double BALANCE_LIMIT = 250000;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ssn;
    private CheckingAccount[] checkingAccountList = new CheckingAccount[10];
    private SavingsAccount[] savingsAccountList = new SavingsAccount[10];
    private CDAccount[] cdAccountList = new CDAccount[10];

    /*METHODS*/
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

    /* GETTERS/SETTERS */
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
    CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException
    {
        /* TODO -- add new code
        If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
        Should also add a deposit transaction with the opening balance
         */
        if((openingBalance + (getCombinedBalance() - getCDBalance()) < BALANCE_LIMIT))
        {
            return addCheckingAccount(new CheckingAccount(openingBalance));
        }
        return null;
    }

    CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException
    {
        /*
        If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
        Should also add a deposit transaction with the opening balance
         */
        if((checkingAccount.getBalance() + (getCombinedBalance() - getCDBalance()) < BALANCE_LIMIT))
        {
            for(int i = 0; i < checkingAccountList.length; i++)
            {
                if(checkingAccountList[i] == null)
                {
                    checkingAccountList[i] = checkingAccount;
                    break;
                }
            }
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
        If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
        Should also add a deposit transaction with the opening balance
         */
        if((openingBalance + (getCombinedBalance() - getCDBalance()) < BALANCE_LIMIT))
        {
            return addSavingsAccount(new SavingsAccount(openingBalance));
        }
        return null;
    }

    SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException
    {
        /*
        If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
        Should also add a deposit transaction with the opening balance
         */
        if((savingsAccount.getBalance() + (getCombinedBalance() - getCDBalance()) < BALANCE_LIMIT))
        {
            for(int i = 0; i < savingsAccountList.length; i++)
            {
                if(savingsAccountList[i] == null)
                {
                    savingsAccountList[i] = savingsAccount;
                    break;
                }
            }
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
        // Should also add a deposit transaction with the opening balance
        if(MeritBank.getCDOfferings() == null)
        {
            return null;
        } else {
            return addCDAccount(new CDAccount(offering, openingBalance));
        }
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
        String tempFirstName = "", tempMidName = "", TempLastName = "", tempSSN = "";
        String[] tempArr = accountHolderData.split(",");
        if(accountHolderData.length() > 0)
        {
            /* "Doe,,John,1234567890" */
            tempFirstName = tempArr[0];
            tempMidName = tempArr[1];
            TempLastName = tempArr[2];
            tempSSN = tempArr[3];
        }
        else
        {
            System.out.println("Account Holder data format incorrect");
            throw new Exception();
        }
        return new AccountHolder(tempFirstName, tempMidName, TempLastName, tempSSN);
    }

    @Override
    public int compareTo(AccountHolder otherAccountHolder)
    {
        // --- compare combined balances of obj passed in to current instance of obj -- //
        if(otherAccountHolder == null) return 0;
        if(this.getCombinedBalance() > otherAccountHolder.getCombinedBalance()) return 1;
        else if(this.getCombinedBalance() < otherAccountHolder.getCombinedBalance()) return -1;
        return 0;
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


}