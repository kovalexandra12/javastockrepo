package com.TradeApp.app.service;

import java.util.Calendar;
import java.util.Date;

import com.TradeApp.app.model.Portfolio;
import com.TradeApp.app.model.Stock;

public class PortfolioManager {

	
	
	public Portfolio getPortfolio(){
		
		///date declaration ///
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 10, 15);
		Date date1 = cal.getTime();	
		Date date2 = cal.getTime();
		Date date3 = cal.getTime();
	
		///set stocks////
		Stock stock1 = new Stock();
		stock1.setSymbol("PIH");
		stock1.setAsk((float) 13.1);
		stock1.setBid((float) 12.4);
		stock1.setDate(date1);
		
		Stock stock2 = new Stock("AAL",(float)5.78,(float)5.5,date2);
		
		Stock stock3 = new Stock("CAAS",(float)32.2,(float)31.5,date3);
		
		////////////create new portfolio and add the stocks inside /////////////////
		Portfolio portfolio = new Portfolio();
		portfolio.addStock(stock1);
		portfolio.addStock(stock2);
		portfolio.addStock(stock3);
		
		return portfolio;
		
	}
		
	
}
