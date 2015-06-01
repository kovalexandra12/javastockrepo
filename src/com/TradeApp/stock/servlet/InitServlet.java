package com.TradeApp.stock.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.algo.service.ServiceManager;

import com.TradeApp.app.model.Portfolio;
import com.TradeApp.app.model.Stock;
import com.TradeApp.app.service.PortfolioManager;


@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	
	public void init() throws ServletException
	{
	   // Do required initialization
		super.init();
		PortfolioManager pm = new PortfolioManager();
		ServiceManager.setPortfolioManager(pm);
	}
	
}
