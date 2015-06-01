package com.TradeApp.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.algo.dto.PortfolioDto;
import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;
import org.algo.service.ServiceManager;
import org.algo.service.DatastoreService;

import com.TradeApp.app.model.Portfolio;
import com.TradeApp.app.model.Stock;

public class PortfolioManager implements PortfolioManagerInterface{

	private DatastoreService datastoreService = ServiceManager.datastoreService();
	
	
//////////////////////Class methods///////////////////////////////
	
	public PortfolioInterface getPortfolio(){
		
		PortfolioDto portfolioDto = datastoreService.getPortfolilo();
		return fromDto(portfolioDto);
		
	}
	/**
	 * Update portfolio with stocks
	 */
	@Override
	public void update() {
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.getMaxSize());
		for (StockInterface si : stocks) {
			symbols.add(si.getSymbol());
		}

		List<Stock> update = new ArrayList<>(Portfolio.getMaxSize());
		List<Stock> currentStocksList = new ArrayList<Stock>();
		try {
			List<StockDto> stocksList = MarketService.getInstance().getStocks(symbols);
			for (StockDto stockDto : stocksList) {
				Stock stock = fromDto(stockDto);
				currentStocksList.add(stock);
			}

			for (Stock stock : currentStocksList) {
				update.add(new Stock(stock));
			}

			datastoreService.saveToDataStore(toDtoList(update));

		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println(e.getMessage());
		}
	}

	
	/**
	 * toDtoList - convert List of Stocks to list of Stock DTO
	 * @param stocks
	 * @return stockDto
	 */
	private List<StockDto> toDtoList(List<Stock> stocks) {

		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}
	
	

	
	/**
	 * fromDto - converts portfolioDto to Portfolio
	 * @param dto
	 * @return portfolio
	 */
	private Portfolio fromDto(PortfolioDto dto) {
		StockDto[] stocks = dto.getStocks();
		Portfolio ret;
		if(stocks == null) {
			ret = new Portfolio();			
		}else {
			List<Stock> stockList = new ArrayList<Stock>();
			for (StockDto stockDto : stocks) {
				stockList.add(fromDto(stockDto));
			}

			Stock[] stockArray = stockList.toArray(new Stock[stockList.size()]);
			ret = new Portfolio(stockArray);
		}

		ret.setTitle(dto.getTitle());
		try {
			ret.updateBalance(dto.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * get portfolio totals
	 */
	@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus () {

		Portfolio portfolio = (Portfolio) getPortfolio();
		Map<Date, Float> map = new HashMap<>();

		//get stock status from db.
		StockInterface[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			StockInterface stock = stocks[i];

			if(stock != null) {
				List<StockDto> stockHistory = null;
				try {
					stockHistory = datastoreService.getStockHistory(stock.getSymbol(),30);
				} catch (Exception e) {
					return null;
				}
				for (StockDto stockDto : stockHistory) {
					Stock stockStatus = fromDto(stockDto);
					float value = stockStatus.getBid()*stockStatus.getQuantity();

					Date date = stockStatus.getDate();
					Float total = map.get(date);
					if(total == null) {
						total = value;
					}else {
						total += value;
					}

					map.put(date, value);
				}
			}
		}

		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];

		int index = 0;
		//create dto objects
		for (Date date : map.keySet()) {
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}

		//sort by date ascending.
		Arrays.sort(ret);

		return ret;
	}
	
	/**
	 * fromDto - get stock from Data Transfer Object
	 * @param stockDto
	 * @return Stock
	 */
	private Stock fromDto(StockDto stockDto) {
		Stock newStock = new Stock();

		newStock.setSymbol(stockDto.getSymbol());
		newStock.setAsk(stockDto.getAsk());
		newStock.setBid(stockDto.getBid());
		newStock.setDate(stockDto.getDate().getTime());
		newStock.setQuantity(stockDto.getQuantity());
		if(stockDto.getRecommendation() != null) newStock.setRecommendation(stockDto.getRecommendation());

		return newStock;
	}

//////////////////////my methods///////////////////////////////
	/**
	 * this method updates portfolio title
	 */
	public void setTitle(String title){
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.setTitle(title);
		flush( portfolio);
	}
	/**
	 * this method updates portfolio balance
	 */
	
	public void updateBalance(float refund) throws PortfolioException
	{
		Portfolio portfolio = (Portfolio) getPortfolio();
		if (portfolio.updateBalance(refund))
		{
			flush( portfolio);
		}
		else {
			throw new PortfolioException("refund " +refund+ "is invalid");
		}
		
	}	
	
	/**
	 * Add stock to portfolio 
	 * @throws PortfolioException 
	 */
	@Override
	public void addStock(String symbol) throws PortfolioException {
		Portfolio portfolio = (Portfolio) getPortfolio();

		try {
			StockDto stockDto = ServiceManager.marketService().getStock(symbol);
			if (stockDto.getAsk()!=0 && stockDto.getBid()!=0 ){
			Stock stock = new Stock();
			stock = fromDto(stockDto);
			portfolio.addStock(stock);
			//second thing, save the new stock to the database.
			datastoreService.saveStock(stockDto);			
			flush( portfolio);
			}
			else {
				System.out.println("Stock " +symbol+ "not exists");
				throw new PortfolioException("Stock " +symbol+ "not exists");
			}
		} catch (PortfolioException | SymbolNotFoundInNasdaq e) {
			throw new PortfolioException("ERROR");
			
		}
	}


	/**
	 * update database with new portfolio's data
	 * @param portfolio
	 */
	private void flush(Portfolio portfolio) {
		datastoreService.updatePortfolio(toDto(portfolio));
	}
	
	
	/**
	 * toDto - covert Stock to Stock DTO
	 * @param inStock
	 * @return
	 */
	private StockDto toDto(StockInterface inStock) {
		if (inStock == null) {
			return null;
		}
		
		Stock stock = (Stock) inStock;
		return new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(), 
				stock.getDate(), stock.getQuantity(),stock.getRecommendation());
	}

	/**
	 * Buy stock
	 */
	@Override
	public void buyStock(String symbol, int quantity) throws PortfolioException{
		try {
			Portfolio portfolio = (Portfolio) getPortfolio();
			
			Stock stock = (Stock) portfolio.getStockBySymbol(symbol);
			if(stock == null) {
				stock = fromDto(ServiceManager.marketService().getStock(symbol));				
			}
			
			portfolio.buyStock(stock, quantity);
			flush(portfolio);
		}catch (Exception e) {
			System.out.println("Exception: "+e);
		}
	}

	/**
	 * this method removes stock and returns an update (true or false) on success.
	 * return value: success= true
	 * 				failure= false
	 */	
	public void sellStock(String symbol, int quantity){
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.sellStock(symbol, quantity);
		flush( portfolio);
		}
			
	/**
	 * this method removes stock and returns an update (true or false) on success.
	 * return value: success= true
	 * 				failure= false
	 */	
	public void removeStock(String symbol) {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.removeStock(symbol);
		flush( portfolio);
		}

	
	
	/**
	 * toDto - converts Portfolio to Portfolio DTO
	 * @param portfolio
	 * @return
	 */
	private PortfolioDto toDto(Portfolio portfolio) {
		StockDto[] array = null;
		StockInterface[] stocks = portfolio.getStocks();
		if(stocks != null) {
			array = new StockDto[stocks.length];
			for (int i = 0; i < stocks.length; i++) {
				array[i] = toDto(stocks[i]);
			}
		}
		return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
	}

		
	}
		
	

