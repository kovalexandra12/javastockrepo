package com.AlexsServlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.*;

import com.TradeApp.app.model.Stock;

import math.calculation.*;

@SuppressWarnings("serial")
public class StockDetailsServletEX4 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(2014, 10, 15);
		Date date1 = cal.getTime();	
		Date date2 = cal.getTime();
		Date date3 = cal.getTime();
		
		
		Stock stock1 = new Stock();
		stock1.setSymbol("PIH");
		stock1.setAsk((float) 13.1);
		stock1.setBid((float) 12.4);
		stock1.setDate(date1);
		
		Stock stock2 = new Stock("AAL",(float)5.78,(float)5.5,date2);
		Stock stock3 = new Stock("CAAS",(float)32.2,(float)31.5,date3);

		
		///print//
		resp.getWriter().println(stock1.getHtmlDescription());
		
		resp.getWriter().println("<br>");
		resp.getWriter().println("<br>");
		
		resp.getWriter().println(stock2.getHtmlDescription());	
		
		resp.getWriter().println("<br>");
		resp.getWriter().println("<br>");
		
		resp.getWriter().println(stock3.getHtmlDescription());	
		
	}
}
