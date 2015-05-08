package com.TradeApp.app.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Portfolio {
	private String title;
	private final int MAX_PORTFOLIO_SIZE=5;
	private Stock[] stocks;
	private int portfolioSize;
	
	public Portfolio()
	{
		title=" ";
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		portfolioSize=0;
	}
	
	public void addStock(Stock stockToAdd){
		
		stocks[portfolioSize] = stockToAdd;
		portfolioSize++;	
	}
	
	public Stock[] getStocks(){
		return stocks;
		
	}
	
	public String getHtmlString(){
		
		String readDescription = "<h1>"+title+"</h1> </br>";
		for (int i =0; i< portfolioSize; i++)
		{
			
			readDescription+= stocks[i].getHtmlDescription()+"</br>";
		}
		return readDescription;
		
	}
	public void setTitle(String title){
		this.title=title;
	}
	 

}

