package com.test.tradeStore;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.tradeStore.model.TradeStore;
import com.test.tradeStore.service.TradeStoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
class TradeStoreApplicationTests {

	@Autowired
	private TradeStoreService tradeStoreService;
	
	@Test
	public void createAndValidateTrade_ValidTrade() {
		TradeStore tradeStore = new TradeStore("T4", 1, "CP-3", "B2", LocalDate.of(2021, 5, 20), LocalDate.now(), true);
		tradeStoreService.createAndValidateTrade(tradeStore);
		Assert.assertEquals(5, tradeStoreService.getAllTrades().size());
	}
	
	@Test
	public void createAndValidateTrade_lessMaturityDate() {
		TradeStore tradeStore = new TradeStore("T3", 3, "CP-3", "B2", LocalDate.of(2014, 5, 20), LocalDate.now(), true);
		tradeStoreService.createAndValidateTrade(tradeStore);
		Assert.assertEquals(4, tradeStoreService.getAllTrades().size());
	}
	
	@Test
	public void createAndValidateTrade_HavingLowerVersion() {
		TradeStore tradeStore = new TradeStore("T3", 2, "CP-3", "B2", LocalDate.of(2021, 5, 20), LocalDate.now(), true);
		try {
			tradeStoreService.createAndValidateTrade(tradeStore);
		}catch(RuntimeException e) {
			Assert.assertEquals("Exception occured as received version is lower than current", e.getMessage());
		}
	}

}
