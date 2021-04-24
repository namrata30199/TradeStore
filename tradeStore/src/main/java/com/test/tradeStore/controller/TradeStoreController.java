package com.test.tradeStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.tradeStore.model.TradeStore;
import com.test.tradeStore.service.TradeStoreService;

@RestController
public class TradeStoreController {
	
	@Autowired
	private TradeStoreService tradeStoreService;
	
	@RequestMapping("/getTrades")
	public List<TradeStore> getAllTrades(){
		return tradeStoreService.getAllTrades();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/validateTrade")
	public void createTrade(@RequestBody TradeStore trade) {
		tradeStoreService.createAndValidateTrade(trade);
	}

}
