package com.shoepricetracker.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoepricetracker.models.SoldPriceHistory;
import com.shoepricetracker.repos.SoldPriceHistoryRepository;

@Service
public class SoldPriceHistoryService {
	
	@Autowired
	SoldPriceHistoryRepository priceHistoryRepo;
	
	//add exception handling
	public List<SoldPriceHistory> viewSoldPriceHistory(int shoeId, String email) throws IOException {
		
		List<SoldPriceHistory> soldPriceHistoryList = new ArrayList<>();
		
		soldPriceHistoryList = priceHistoryRepo.getPriceHistory(shoeId);
		
		return soldPriceHistoryList;

	}
}