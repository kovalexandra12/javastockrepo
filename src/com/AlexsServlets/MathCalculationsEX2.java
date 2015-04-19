package com.AlexsServlets;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MathCalculationsEX2 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		int num1=4;
		int num2=3;
		int num3=7;
		
		int result=(num3*(num2+num1));	
	    String resultSt1r= "<h1>Result of ("+num3+ "*("+num2+"+"+num1+"))="+result+"</h1>";
	    System.out.println("resultSt1r = "+resultSt1r);
		resp.getWriter().println(resultSt1r);
		
	}
}
