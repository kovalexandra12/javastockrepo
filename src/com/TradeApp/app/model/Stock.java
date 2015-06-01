package com.TradeApp.app.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.algo.model.StockInterface;

import com.TradeApp.app.model.Stock.ALGO_RECOMMENDATION;

/**
 * 
 * this class holding stock elemnts
 *
 */

public class Stock implements StockInterface {

	private String symbol;
	private float ask;
	private double bid;
	private Date date;
	private int stockQuantity;
	private String recommendation;

	public enum ALGO_RECOMMENDATION {
		BUY, SELL, REMOVE, HOLD ,NORECOMMENDATION
	}

	// /////////////////////////////////constructors
	// /////////////////////////////////////

	public Stock(String symbol, float ask, float bid, Date date ,String recommendation) {
		this.symbol = symbol;
		this.ask = ask;
		this.bid = bid;
		this.date = date;
		this.recommendation =recommendation;
	}

	public Stock() {
		this.symbol = " ";
		this.ask = 0;
		this.bid = 0;
		this.date = new Date();
		this.stockQuantity = 0;
		this.recommendation="";
	}

	public Stock(String symbol, float ask, float bid) {
		new Stock(symbol, ask, bid);
		this.symbol = symbol;
		this.ask = ask;
		this.bid = bid;
		this.recommendation="";
	}

	public Stock(Stock oldStock) {
		this.symbol = oldStock.symbol;
		this.ask = oldStock.ask;
		this.bid = oldStock.bid;
		this.date = oldStock.date;
		this.stockQuantity = oldStock.stockQuantity;
		this.recommendation=oldStock.recommendation;

	}

	// ////////////////////////////////Methods////////////////////////////////////////////
	// Symbol setter and getter
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	// Ask setter and getter
	public float getAsk() {
		return ask;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}

	// Bid setter and getter
	public float getBid() {
		return (float) bid;
	}

	public void setBid(double d) {
		this.bid = d;
	}

	// Date setter and getter
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDate(long time) {
		this.date.setTime(time);

	}

	public void setQuantity(int quantity) {
		this.stockQuantity = quantity;
	}

	public int getQuantity() {
		return stockQuantity;
	}

	
	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;

	}

	/**
	 * this method returns stock HTML summary
	 */
	public String getHtmlDescription() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String dateStr = dateFormat.format(getDate());

		String output = "<b>The stock details are: </br> Symbol: </b> "
				+ this.getSymbol() + " </br> Ask: </b>" + this.getAsk()
				+ "</br> Bid: </b>" + this.getBid() + "</br> Date: </b>"
				+ dateStr + "</br> Quantity: </b>" + stockQuantity;

		return output;

	}

}
