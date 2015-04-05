package com.test;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		int num1=4;
		int num2=3;
		int num3=7;
		String num1Str=String.valueOf(num1);
		int result=(num3*(num2+num1));	
	    String resultSt1r= "<h1>Result of ("+num3+ "*("+num2+"+"+num1+"))="+result+"</h1>";
		resp.getWriter().println(resultSt1r);
		
	}
}
