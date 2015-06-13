package com.TradeApp.app.model;

import org.algo.exception.PortfolioException;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;

import com.TradeApp.app.exception.BalanceException;
import com.TradeApp.app.exception.PortfolioFullException;
import com.TradeApp.app.exception.StockAlreadyExistsException;
import com.TradeApp.app.exception.StockNotExistException;
import com.TradeApp.app.exception.StockQuantityException;

public class Portfolio implements PortfolioInterface {
	private String title;
    public final static int MAX_PORTFOLIO_SIZE=5;
	private Stock[] stocks;
	private int portfolioSize;
	private float balance;

	
	public Portfolio()
	{
		title=" ";
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		portfolioSize=0;
	}
	public Portfolio(Portfolio OldPortfolio){
		this.title=OldPortfolio.title;
		this.stocks =  new Stock[MAX_PORTFOLIO_SIZE];
		this.portfolioSize=OldPortfolio.portfolioSize;
		for (int i=0 ;i< portfolioSize ;i++ )
		{
			this.stocks[i]=new Stock((Stock) OldPortfolio.getStocks()[i]);
		}
	}
	
	public Portfolio(Stock[] stockArray) {
		this.stocks= new Stock[MAX_PORTFOLIO_SIZE];
		portfolioSize=stockArray.length;
		for (int i=0 ;i< stockArray.length ;i++ )
		{
			this.stocks[i]=new Stock((Stock)stockArray[i]);
		}
	}
	
	public StockInterface[] getStocks(){
		return stocks;
	}
	
	/**
	 * this method checking if the symbol already exist in the array
	 */
	private boolean isStockExist(String symbol) {
		boolean isExistingSymbol=false;
		for(int i=0; i<portfolioSize;i++)
		{
			if(symbol.equals(stocks[i].getSymbol()) ){
				isExistingSymbol=true;
			}
		}
		return isExistingSymbol;
	}
	
	public boolean IsPortfolioHasMaxSize() {
		if (portfolioSize == MAX_PORTFOLIO_SIZE) {
            
			System.out.println("Can’t add new stock, portfolio can have only"
					+ MAX_PORTFOLIO_SIZE + "stocks");
			return true;
		}
		return false;
	}
	
	/**
	 * this method adding new stock to stock array
	 * @throws PortfolioException 
	 * @throws PortfolioFullException 
	 * @throws StockAlreadyExistsException 
	 */
	public void addStock(Stock stockToAdd) throws PortfolioFullException,
			StockAlreadyExistsException {

		if (portfolioSize == MAX_PORTFOLIO_SIZE) {

			// System.out.println("Can’t add new stock, portfolio can have only"
			// +MAX_PORTFOLIO_SIZE+ "stocks");
			throw new PortfolioFullException();
		} else {
			if (isStockExist(stockToAdd.getSymbol()) == false
					&& stockToAdd.getSymbol() != null) {
				stockToAdd.setQuantity(0);
				stocks[portfolioSize] = stockToAdd;
				portfolioSize++;
			} else {
				throw new StockAlreadyExistsException(stockToAdd.getSymbol());
			}
		}
	}		
	
	  
	/**
	 * this method returns portfolio HTML summary 
	 */
	public String getHtmlString(){
		
		String readDescription = "<h1>"+title+"</h1> </br>";
		for (int i =0; i< portfolioSize; i++)
		{
			readDescription+= stocks[i].getHtmlDescription()+"</br>";
		}
		readDescription+= "</br> Portfolio Value: " +getTotalValue()+ "$</br>";
		readDescription+= "</br> Total Stocks value: " +getStocksValue()+ "$</br>";
		readDescription+= "</br> Balance: " +getBalance()+ "$</br>";

		return readDescription;
		
	}
	public void setTitle(String title){
		this.title=title;
	}
	
	
	/**
	 * this method removes stock from specific index place from stocks array
	 */
	public void removeStockAtIndex(int index)
	{
		Stock[] newStocks= new Stock[MAX_PORTFOLIO_SIZE];
		int j=0;
		for (int i =0; i<MAX_PORTFOLIO_SIZE ;i++)
		{
			if (i!=index)
			{
				newStocks[j]=stocks[i];
				j++;
			}
			
		}
		stocks=newStocks;
		portfolioSize--;	
	}


	/**
	 * this method removes stock and returns an update (true or false) on success.
	 * return value: success= true
	 * 				failure= false
	 * @throws Exception 
	 */	
	public void removeStock(String symbol) throws Exception
	{
		int index;
		if (isStockExist(symbol)== true){
			sellStock(symbol,-1);
			index=getStockIndexBySymbol(symbol);
			removeStockAtIndex(index);
			}
		else{
			throw new StockNotExistException(symbol);
		}
	}

	/**
	 * this method sells stock and returns an update (true or false) on success and updates the balance accordingly
	 * return value: success= true
	 * 				failure= false
	 * @throws Exception 
	 */	
	public void sellStock(String symbol, int sellQuantity) throws Exception {
	Stock stock = getStockBySymbol(symbol);
	double refund;
	int currentQuantity;
	currentQuantity=stock.getQuantity();
	if (sellQuantity == -1){	
		refund=currentQuantity*stock.getBid();
		updateBalance(refund);
		stock.setQuantity(0);
	}
	else if (sellQuantity<-1 ){
		throw new Exception("quantity can't be less than -1");
	}
	else if (stock.getQuantity()< sellQuantity){
		System.out.println("Not enough stocks to sell, you have " +currentQuantity+ " of this stock");
		throw new StockQuantityException(currentQuantity);
	}
	else if (stock.getQuantity()> sellQuantity)
	{
		refund= sellQuantity*stock.getBid();
		updateBalance(refund);
		int leftQuantity=stock.getQuantity()-sellQuantity;
		stock.setQuantity(leftQuantity);
	}	
	
		
	}
	
	/**
	 * this method return stock by symbol
	 */
	public Stock getStockBySymbol(String symbol) {
		int index;
		index=getStockIndexBySymbol(symbol);
		if (index == -1)
		{
			return null;
		}
		else
		{
		return stocks[index];
		}
		
	}
	/**
	 * this method updates portfolio balance
	 * @throws BalanceException 
	 */
	public void updateBalance(double refund) throws BalanceException{
		double newBalance= balance+refund;
		if (newBalance<0){
			throw new BalanceException((float) refund);
		}
		else{
			balance= (float) newBalance;
		}
	}	 
	/**
	 * this method checks if the portfolio has enough balance
	 */
	public boolean checkIfEnoughBalance (Stock stock,int quantity){
		boolean isEnough=false;
		double difference = balance-(quantity*stock.getAsk());
		if (difference>0){
			isEnough=true;
		}
		return isEnough;
	}
	/**
	 * this method performs an action of stock buy .
	 * @throws PortfolioException 
	 * @throws PortfolioFullException 
	 * @throws StockAlreadyExistsException 
	 * @throws BalanceException 
	 */
	public void buyStock(Stock stock, int quantity) throws  PortfolioFullException, StockAlreadyExistsException, BalanceException{
		
		String symbol = stock.getSymbol();
		if (checkIfStockExist(symbol)==true){
			buyStockHelper(stock,quantity);
		}else{
			addStock(stock);
			buyStockHelper(stock,quantity);
		}
		
	}

	private boolean buyStockHelper (Stock stock,int quantity) throws BalanceException{
		boolean isSuccess=true;
		float payment;
		int buyingAmount;
		if (balance>0){
			if (quantity==-1){
				buyingAmount=(int) GetAmountForBuy(stock);
				if (checkIfEnoughBalance(stock,buyingAmount) ==true ){
					payment=GetAmountForBuy(stock)*stock.getAsk(); 
					payment = payment*-1;
					stock.setQuantity(buyingAmount);
					updateBalance(payment);
				}
			}else if (quantity< -1){
			System.out.println("The value of the quantity is elegal ");
				isSuccess= false;
			}else if (quantity> 0){
					if (checkIfEnoughBalance(stock,quantity) ==true ){
						payment=quantity*stock.getAsk(); 
						payment = payment*-1;
						buyingAmount=quantity+stock.getQuantity();
						stock.setQuantity(buyingAmount);
						updateBalance(payment);
					}
			}	
		}else{
			System.out.println("Not enough balance to complete purchase");
		}
		return isSuccess;
	}


	//receive stock returns quantity
	private int GetAmountForBuy(Stock stock) {
		int amount;
		amount= (int) (balance/stock.getAsk());
		return amount;
	}
	private boolean checkIfStockExist(String symbol) {
		return getStockBySymbol(symbol)!=null;
	}	

	private float getStocksValue (){
		float total=0;
		for (int i=0;i<portfolioSize; i++)
		{
			total+=stocks[i].getQuantity()*stocks[i].getBid();
		}
		return total;
	}
	
	public float getBalance(){
		return balance;
	}
	public float getTotalValue (){
		float total=getBalance()+getStocksValue();
		return total;
	}

	private int getStockIndexBySymbol(String symbol){
		int index=-1;
		if (isStockExist(symbol)== true){
			for (int i=0;i<portfolioSize;i++){
				if(symbol.equals(stocks[i].getSymbol()) ){
				index =i;
				}
			}
		}
		return index;	
	}

	@Override
	public String getTitle() {
		
		return title;
	}
	public static int getMaxSize() {
		
		return MAX_PORTFOLIO_SIZE ;
	}
}