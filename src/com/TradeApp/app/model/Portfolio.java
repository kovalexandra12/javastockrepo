package com.TradeApp.app.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * this class holding portfolio elements
 *
 */
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
	public Portfolio(Portfolio OldPortfolio){
		this.title=OldPortfolio.title;
		this.stocks =  new Stock[MAX_PORTFOLIO_SIZE];
		this.portfolioSize=OldPortfolio.portfolioSize;
		for (int i=0 ;i< portfolioSize ;i++ )
		{
			this.stocks[i]=new Stock(OldPortfolio.getStocks()[i]);
		}
	}
	/**
	 * this method adding new stock to stock array
	 */
	
	public void addStock(Stock stockToAdd){
		
		stocks[portfolioSize] = stockToAdd;
		portfolioSize++;	
	}
	
	public Stock[] getStocks(){
		return stocks;
		
	}
	
	/**
	 * this method returns portfolio HTML summary 
	 */
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
	
	
	/**
	 * this method removes stock from specific index place from stocks array
	 */
	public void removeStockAtIndex(int index)
	{
		Stock[] newStocks= new Stock[MAX_PORTFOLIO_SIZE];
		int j=0;
		for (int i =0; i<MAX_PORTFOLIO_SIZE ;i++)
		{
			if (i!=index)
			{
				newStocks[j]=stocks[i];
				j++;
			}
			
		}
		stocks=newStocks;
		portfolioSize--;
		
	}
	
	 

}

