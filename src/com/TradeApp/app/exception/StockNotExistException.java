package com.TradeApp.app.exception;

import org.algo.exception.PortfolioException;

public class StockNotExistException extends PortfolioException {
	public StockNotExistException(String symbol) {
		super("Stock " + symbol + "does not exist");

	}

}
