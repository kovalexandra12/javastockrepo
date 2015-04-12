package com.test;

import java.io.IOException;
import javax.servlet.http.*;
import math.calculation.*;

@SuppressWarnings("serial")
public class EX3Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		//circle calculations
		double radius;
		double area;
		radius=50;
		Circle circle=new Circle();
		circle.setRadius(radius);
		area = circle.getArea();
		
		String line1 = new String ("<br>Calculation 1: Area of circle with radius " +radius+ " is : " +area+ " square-cm <br>");
		
		// triangle calculations
		double angle_B;
		double hypotenuse;
		double opposite;
		angle_B = 30;
		hypotenuse = 50;
		double radians_angle_B = Math.toRadians(angle_B);
		double sinResult=Math.sin(radians_angle_B);
		opposite = hypotenuse * sinResult;
		
		String line2 = new String ("Calculation 2: Length of opposite where angle B is" +angle_B+ " is: " +opposite+ " cm <br>");
		
		//power calculations
		
		int base;
		int exp;
		double result;
		base = 20;
		exp = 13;	
		result = Math.pow(base, exp);		
		
		String line3 = new String ("Calculation 3; Power of " +base+ " with exp of " +exp+ " is: "+result );
		
		
		//define a string to include all calculations:

		String resultStr = line1 + "<br>" +line2+ "<br>" +line3;
		//add this string inside the response print line:
		
		resp.getWriter().println(resultStr);
	}
}
