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
		Portfolio portfolio1 = portfolioManager .getPortfolio();
		portfolio1.setTitle("Portfolio #1");

		Portfolio portfolio2 = new Portfolio(portfolio1);
		
		portfolio2.setTitle("Portfolio #2");
		resp.getWriter().println(portfolio1.getHtmlString());
		resp.getWriter().println(portfolio2.getHtmlString());
		portfolio1.removeStockAtIndex(0);
		resp.getWriter().println(portfolio1.getHtmlString());
		resp.getWriter().println(portfolio2.getHtmlString());
		portfolio2.getStocks()[1].setBid(55.55);
		resp.getWriter().println(portfolio1.getHtmlString());
		resp.getWriter().println(portfolio2.getHtmlString());
	
	}
}
