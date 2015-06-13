package com.TradeApp.app.exception;

import org.algo.exception.PortfolioException;

import com.TradeApp.app.model.Stock;

public class StockAlreadyExistsException extends PortfolioException {

	public StockAlreadyExistsException (String symbol){
		super ("Stock " +symbol+ " already exists in the portfolio");
		
	}

}
