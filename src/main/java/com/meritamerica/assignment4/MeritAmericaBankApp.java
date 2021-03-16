package com.meritamerica.assignment4;

public class MeritAmericaBankApp
{
	
	// TODO --- OVERALL
	/*
	any txn over 1000 must be reviewed by fraud team
	(placed in fraud queue)
	bank requires txfr between accounts (fraud detection here as well too)
	  txfr amounts need to be less than bal in source account
	  must be + num
	BankAccount to be abstract class
	Math.pow() needs to be recursive method
	*/
	
	public static void main(String[] args)
	{
		MeritBank.readFromFile("src/test/testMeritBank_good.txt");
	}
}