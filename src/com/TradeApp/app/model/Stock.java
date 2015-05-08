
package com.TradeApp.app.model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * this class holding stock elemnts
 *
 */

public class Stock {
	
	private String symbol;
	private float ask;
	private double bid;
	private Date date;
	private int recommendation;
	private int stockQuantity;
	
	final int BUY = 0;
	final int SELL = 1;
	final int REMOVE = 2;
	final int HOLD = 3;
	

	
///////////////////////////////////constructors /////////////////////////////////////

	
	public Stock(String symbol,float ask,float bid,Date date)
	{
		this.symbol = symbol;
		this.ask = ask;
		this.bid = bid;
		this.date = date;
	}
	public Stock()
	{
		this.symbol=" ";
		this.ask = 0;
		this.bid = 0;
		this.date = new Date();
		this.stockQuantity = 0;
		this.recommendation=-1;
	}
	
	public Stock(String symbol,float ask,float bid)
	{
		new Stock(symbol,ask,bid);
		this.symbol = symbol;
		this.ask = ask;
		this.bid = bid;
	}
	
	public Stock(Stock oldStock)
	{
		this.symbol=oldStock.symbol;
		this.ask=oldStock.ask;
		this.bid=oldStock.bid;
		this.date=oldStock.date;
		this.recommendation=oldStock.recommendation;
		this.stockQuantity=oldStock.stockQuantity;

	}
	

//////////////////////////////////Methods////////////////////////////////////////////	
//Symbol setter and getter 
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
//Ask setter and getter 
    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }
    
//Bid setter and getter 
    public double getBid() {
        return bid;
    }

    public void setBid(double d) {
        this.bid = d;
    }
    
//Date setter and getter 
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }   
    
    
    
	/**
	 * this method returns stock HTML summary 
	 */
    public String getHtmlDescription(){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String dateStr = dateFormat.format(getDate());
    		
    		String output = "<b>The stock details are: </br> Symbol: </b> "+this.getSymbol()+
    						" </br> Ask: </b>" +this.getAsk()+
    						"</br> Bid: </b>" +this.getBid()+
    						"</br> Date: </b>" +dateStr;
    
    		return output;
    	
    	}
    	
    
    
}
