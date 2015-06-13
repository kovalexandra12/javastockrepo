package com.TradeApp.app.exception;

import org.algo.exception.PortfolioException;

public class StockQuantityException extends PortfolioException{
	public StockQuantityException (int quantity){
		super ("Not enough stocks to sell you have " +quantity+ " of this stock");
	}
	

}
