package com.meritamerica.assignment4;

public class CDOffering
{
    protected int term;
    protected double interestRate;

    CDOffering(int term, double interestRate) {
        this.term = term;
        this.interestRate = interestRate;
    }

    int getTerm() {
        return this.term;
    }

    double getInterestRate() {
        return this.interestRate;
    }

    static CDOffering readFromString(String cdOfferingDataString) {

        int tempTerm = 0;
        double tempIntRate = 0;
        int comma = cdOfferingDataString.indexOf(",");
        if(comma >= 0) {
            tempTerm = Integer.parseInt(cdOfferingDataString.substring(0, comma));
            tempIntRate = Double.parseDouble(cdOfferingDataString.substring(comma + 1));
        } else {
            System.out.println("CD Offering data format incorrect");
            throw new NumberFormatException();
        }
        return new CDOffering(tempTerm, tempIntRate);
    }

    String writeToString() {
        return this.term + "," + this.interestRate + "\n";
    }

}