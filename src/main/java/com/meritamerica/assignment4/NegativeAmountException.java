package com.meritamerica.assignment4;

public class NegativeAmountException extends Exception
{
    NegativeAmountException(String error)
    {
        super(error);
    }
}
