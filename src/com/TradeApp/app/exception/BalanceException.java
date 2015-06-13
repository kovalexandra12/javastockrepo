package com.TradeApp.app.exception;

import org.algo.exception.PortfolioException;

public class BalanceException extends PortfolioException {

	public BalanceException(float amount) {
		super("Balance can not be negetive" + amount);
	}
}
