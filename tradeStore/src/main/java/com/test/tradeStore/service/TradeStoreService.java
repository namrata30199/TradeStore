package com.test.tradeStore.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Service;

import com.test.tradeStore.model.TradeStore;

@Service
public class TradeStoreService {
	
	private List<TradeStore> listOfTrades = new ArrayList<TradeStore>(
			Arrays.asList(
					new TradeStore("T1", 1, "CP-1", "B1", LocalDate.of(2020, 5, 20), LocalDate.now(), false),
					new TradeStore("T2", 2, "CP-2", "B1", LocalDate.of(2021, 5, 20), LocalDate.now(), false),
					new TradeStore("T2", 1, "CP-1", "B1", LocalDate.of(2021, 5, 20), LocalDate.of(2015, 3, 14), false),
					new TradeStore("T3", 3, "CP-3", "B2", LocalDate.of(2014, 5, 20), LocalDate.now(), false)
					));
	
	public List<TradeStore> getAllTrades(){
		return listOfTrades;
	}
	
	public void validate(TradeStore trade) {
		listOfTrades.stream().filter(t-> t.getTradeId().equalsIgnoreCase(trade.getTradeId()))
		.forEach(t->{
			if(trade.getVersion()<t.getVersion()) {
				//Rejecting the trade as received version is lower than current
				throw new RuntimeException("Exception occured as received version is lower than current");
			}
		});
	}

	public void createAndValidateTrade(TradeStore trade) {
		if(trade.getMaturityDate().compareTo(LocalDate.now())<0) {
			//Trade having less maturity date than today's date
			return;
		}
		validate(trade);
		ListIterator<TradeStore> iterator = listOfTrades.listIterator();
		iterator.add(trade);
		
		listOfTrades.stream().filter(t-> t.getMaturityDate().compareTo(LocalDate.now())<0)
		.forEach(t-> t.setExpired(true));
	}

}
