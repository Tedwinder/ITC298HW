// Assignment 4 by Ted Callow
// 1. In this exercise, you’ll create a database that you can use with the Tip Calculator app.
//Add a database class
//2. Start Android Studio and open the project named HM_ch13_ex4_TipCalculator.
//3. Review the code. Note that it includes a Tip class that you can use to store the data for a tip.
//4. Add a database class that creates a table with these column names and data types:
// _id             INTEGER
// bill_date       INTEGER
//bill_amount      REAL
//tip_percent      REAL
//5. When the database class creates the database, it should also insert two rows of test data.
// (You can use 0 for the bill date values,
// but make up some bill amount and tip percent values such as 40.60 and .15.)
//6. Add a public getTips method that
// returns an ArrayList<Tip> object that contains all columns and rows from the database table.
//7. Switch to the activity class. Then, add code to its onResume method that calls the getTips method and
// loops through all saved tips. For each saved tip, this code should use LogCat logging to send
// the bill date milliseconds, the bill amount, and the tip percent to the LogCat view.




package com.murach.tipcalculator;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tip {

    private long id;
    private long dateMillis;
    private float billAmount;//note REAL in DB
    private float tipPercent;//note REAL in DB
    
    public Tip() {
        setId(0);
        setDateMillis(System.currentTimeMillis());
        setBillAmount(0);
        setTipPercent(.15f);
    }

    public Tip(long id, long dateMillis, float billAmount, float tipPercent) {
        this.setId(id);
        this.setDateMillis(dateMillis);
        this.setBillAmount(billAmount);
        this.setTipPercent(tipPercent);
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDateMillis() {
		return dateMillis;
	}

	@SuppressLint("SimpleDateFormat")
	public String getDateStringFormatted() {
    	// set the date with formatting
    	Date date = new Date(dateMillis);
    	SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
    	return sdf.format(date);
	}

	public void setDateMillis(long dateMillis) {
		this.dateMillis = dateMillis;
	}

	public float getBillAmount() {
		return billAmount;
	}

	public String getBillAmountFormatted() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(billAmount);
	}

	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}

	public float getTipPercent() {
		return tipPercent;
	}
	
	public String getTipPercentFormatted() {
        NumberFormat percent = NumberFormat.getPercentInstance();
        return percent.format(tipPercent);    			
	}

	public void setTipPercent(float tipPercent) {
		this.tipPercent = tipPercent;
	}    
}