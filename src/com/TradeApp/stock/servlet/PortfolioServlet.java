package com.TradeApp.stock.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.*;

import com.TradeApp.app.model.Portfolio;
import com.TradeApp.app.model.Stock;
import com.TradeApp.app.service.PortfolioManager;


@SuppressWarnings("serial")
public class PortfolioServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");

		PortfolioManager portfolioManager = new PortfolioManager();
		Portfolio portfolio = portfolioManager .getPortfolio();
		portfolio.setTitle("Portfolio");

		resp.getWriter().println(portfolio.getHtmlString());
	
	}
}
