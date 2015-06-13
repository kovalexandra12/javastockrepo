package com.TradeApp.app.exception;

import org.algo.exception.PortfolioException;

public class PortfolioFullException extends PortfolioException {

	public PortfolioFullException() {
		super("Can not add new stock portfolio size is on MAX size");

	}

}
