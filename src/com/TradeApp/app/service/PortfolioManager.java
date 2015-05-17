package com.TradeApp.app.service;

import java.util.Calendar;
import java.util.Date;

import com.TradeApp.app.model.Portfolio;
import com.TradeApp.app.model.Stock;

public class PortfolioManager {

	
	
	public Portfolio getPortfolio(){
		
		///date declaration ///
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 12, 15);
		Date date1 = cal.getTime();	
		Date date2 = cal.getTime();
		Date date3 = cal.getTime();
	
		///set stocks////
		Stock stock1 = new Stock();
		stock1.setSymbol("PIH");
		stock1.setAsk((float) 10.1);
		stock1.setBid((float) 8.5);
		stock1.setDate(date1);
		//stock1.setQuantity(20);
		
		Stock stock2 = new Stock("AAL",(float)30.0,(float)25.5,date2);
		//stock2.setQuantity(30);
		
		Stock stock3 = new Stock("CAAS",(float)20.0,(float)15.5,date3);
		//stock3.setQuantity(40);
		
		////////////create new portfolio and add the stocks inside /////////////////
		Portfolio portfolio = new Portfolio();
		portfolio.setTitle("Exercise 7 portfolio");
		portfolio.updateBalance(10000);
		
		portfolio.buyStock(stock1,20);
		portfolio.buyStock(stock2,30);
		portfolio.buyStock(stock3,40);
		
		portfolio.sellStock("AAL",-1);
		portfolio.removeStock("CAAS");
		
		
		return portfolio;
		
	}
		
	
}
