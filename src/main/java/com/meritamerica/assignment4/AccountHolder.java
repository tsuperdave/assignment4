package com.meritamerica.assignment4;

import com.sun.org.apache.xpath.internal.operations.Neg;

import java.io.BufferedReader;
public class AccountHolder implements Comparable<AccountHolder> {
    private static final double BALANCE_LIMIT = 250000;
    final double FRAUD_THRESHOLD = 1000;
    private String firstName;
    private String middleName;
    private String lastName;
    private String ssn;
    private CheckingAccount[] checkingAccountList = new CheckingAccount[0];
    private SavingsAccount[] savingsAccountList = new SavingsAccount[0];
    private CDAccount[] cdAccountList = new CDAccount[0];

    AccountHolder() {
    }

    AccountHolder(
            String firstName,
            String middleName,
            String lastName,
            String ssn
    ) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.checkingAccountList = new CheckingAccount[0];
        this.savingsAccountList = new SavingsAccount[0];
        this.cdAccountList = new CDAccount[0];
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getMiddleName() {
        return middleName;
    }

    void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getSSN() {
        return ssn;
    }

    void setSSN(String ssn) {
        this.ssn = ssn;
    }

    /* CHECKING ACCOUNT */
    CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException {

        return this.addCheckingAccount(new CheckingAccount(openingBalance));
    }

    CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException {
        if ((this.getCheckingBalance() + (this.getCombinedBalance() - this.getCDBalance()) >= BALANCE_LIMIT)) {
            throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit. Unable to open new account at this time");
        } else if(checkingAccount.getBalance() > FRAUD_THRESHOLD) {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }

        //DepositTransaction transaction = new DepositTransaction(checkingAccount, checkingAccount.getBalance());
        checkingAccount.addTransaction(new DepositTransaction(checkingAccount, checkingAccount.getBalance()));
/*
        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e) {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e) {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
*/
        CheckingAccount[] tempArr = new CheckingAccount[this.checkingAccountList.length + 1];
        for(int i = 0; i<this.checkingAccountList.length; i++) {
            tempArr[i] = this.checkingAccountList[i];
        }
        tempArr[tempArr.length - 1] = checkingAccount;
        this.checkingAccountList = tempArr;

        return checkingAccount;
    }

    CheckingAccount[] getCheckingAccounts() {
        return checkingAccountList;
    }

    int getNumberOfCheckingAccounts() {
        int numOfAccounts = 0;
        for(CheckingAccount i: checkingAccountList) {
            if(i != null) {
                numOfAccounts++;
            }
        }
        return numOfAccounts;
    }

    double getCheckingBalance() {
        double sum = 0;
        for(CheckingAccount chkAcct: this.checkingAccountList) {
            sum += chkAcct.balance;
        }

        return sum;
    }

    /* SAVINGS ACCOUNT */
    SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException {
        return this.addSavingsAccount(new SavingsAccount(openingBalance));
    }

    SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException {

        /*
        If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
        Should also add a deposit transaction with the opening balance
         */
        if (this.getSavingsBalance() + (this.getCombinedBalance() - this.getCDBalance()) >= BALANCE_LIMIT) {
            throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit. Unable to open new account at this time");
        } else if(savingsAccount.getBalance() > FRAUD_THRESHOLD){
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }

        // DepositTransaction transaction = new DepositTransaction(savingsAccount, getSavingsBalance());
        savingsAccount.addTransaction(new DepositTransaction(savingsAccount, savingsAccount.getBalance()));
/*
        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e) {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e) {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
*/
        SavingsAccount[] tempArr = new SavingsAccount[this.savingsAccountList.length + 1];
        for(int i = 0; i<this.savingsAccountList.length; i++) {
            tempArr[i] = this.savingsAccountList[i];
        }
        tempArr[tempArr.length - 1] = savingsAccount;
        this.savingsAccountList = tempArr;

        return savingsAccount;

    }

    SavingsAccount[] getSavingsAccounts() {
        return savingsAccountList;
    }

    int getNumberOfSavingsAccounts() {
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

    double getSavingsBalance() {
        double sum = 0;
        for(SavingsAccount savAcct: savingsAccountList) {
            sum += savAcct.balance;
        }
        return sum;
    }

    /* CD ACCOUNT */
    CDAccount addCDAccount(CDOffering offering, double openingBalance) throws ExceedsFraudSuspicionLimitException, NegativeAmountException {
        if(offering == null) return null;

        CDAccount newCDAccount = new CDAccount(offering, openingBalance);
        DepositTransaction transaction = new DepositTransaction(newCDAccount, openingBalance);

        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e) {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e) {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        CDAccount[] tempArr = new CDAccount[this.cdAccountList.length + 1];
        for(int i = 0; i<this.cdAccountList.length; i++) {
            tempArr[i] = this.cdAccountList[i];
        }
        tempArr[tempArr.length - 1] = newCDAccount;
        this.cdAccountList = tempArr;

        return newCDAccount;
    }

    CDAccount addCDAccount(CDAccount cdAccount) throws ExceedsFraudSuspicionLimitException, NegativeAmountException {

        DepositTransaction transaction = new DepositTransaction(cdAccount, cdAccount.getBalance());

        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e) {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e) {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        CDAccount[] tempArr = new CDAccount[this.cdAccountList.length + 1];
        for(int i = 0; i < this.cdAccountList.length; i++) {
            tempArr[i] = this.cdAccountList[i];
        }
        tempArr[tempArr.length - 1] = cdAccount;
        this.cdAccountList = tempArr;

        return cdAccount;
    }

    CDAccount[] getCDAccounts() {
        return cdAccountList;
    }

    int getNumberOfCDAccounts() {
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

    double getCDBalance() {
        double sum = 0;
        for(CDAccount cdAcct: cdAccountList) {
            sum += cdAcct.balance;
        }
        return sum;
    }

    static AccountHolder readFromString(String accountHolderData) throws Exception {
        System.out.println(accountHolderData);

        String[] tempArr = accountHolderData.split(",");
        String tempFirstName = "", tempMidName = "", TempLastName = "", tempSSN = "";

        tempFirstName += tempArr[0];
        tempMidName += tempArr[1];
        TempLastName += tempArr[2];
        tempSSN += tempArr[3];

        return new AccountHolder(tempFirstName, tempMidName, TempLastName, tempSSN);
    }

    String writeToString() {
        // Store num of accounts as str to concat into result
        int tempNumOfChk = this.getNumberOfCheckingAccounts(),
                tempNumOfSav = this.getNumberOfSavingsAccounts(),
                tempNumOfCDs = this.getNumberOfCDAccounts();

        // resulting string to add to
        String result = getLastName() + "," + getMiddleName() + "," + getFirstName() + "," + getSSN() + "\n" +
                tempNumOfChk + "\n";

        // loop over checking accounts, run writeToString to return data from class
        for(int i = 0; i < tempNumOfChk; i++) {
            result += this.checkingAccountList[i].writeToString() + "\n";
        }
        result += tempNumOfSav + "\n";

        // loop over savings accounts, run writeToString to return data from class
        for(int i = 0; i < tempNumOfSav; i++) {
            result += this.savingsAccountList[i].writeToString() + "\n";
        }
        result += tempNumOfCDs + "\n";

        // loop over cd accounts, run writeToString to return data from class
        for(int i = 0; i < tempNumOfCDs; i++) {
            result += this.cdAccountList[i].writeToString() + "\n";
        }

        return result;
    }

    double getCombinedBalance() {
        return this.getCheckingBalance() + this.getSavingsBalance() + this.getCDBalance();
    }

    @Override
    public int compareTo(AccountHolder otherAccountHolder) {
        if(this.getCombinedBalance() == otherAccountHolder.getCombinedBalance()) return 0;
        else if(this.getCombinedBalance() < otherAccountHolder.getCombinedBalance()) return -1;
        else return 1;
    }

}