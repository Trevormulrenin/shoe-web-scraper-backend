package com.shoepricetracker.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoepricetracker.exceptions.ShoeNotFoundException;
import com.shoepricetracker.exceptions.UserNotFoundException;
import com.shoepricetracker.models.ComparedShoe;
import com.shoepricetracker.models.OverallShoe;
import com.shoepricetracker.models.SavedShoe;
import com.shoepricetracker.models.ScheduleShoe;
import com.shoepricetracker.models.Shoe;
import com.shoepricetracker.models.SoldPriceHistory;
import com.shoepricetracker.models.StockXUser;
import com.shoepricetracker.services.ComparedShoeService;
import com.shoepricetracker.services.NewLowestService;
import com.shoepricetracker.services.OverallService;
import com.shoepricetracker.services.SavedShoeService;
import com.shoepricetracker.services.SoldPriceHistoryService;
import com.shoepricetracker.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class PriceTrackerController {

	public PriceTrackerController() {
	}

	@Autowired
	private OverallService overallService;

	@Autowired
	private NewLowestService newLowestService;

	@Autowired
	private SavedShoeService savedShoeService;

	@Autowired
	private ComparedShoeService comparedShoeService;

	@Autowired
	private UserService userService;

	@Autowired
	private SoldPriceHistoryService soldPriceHistoryService;

	final String url = "https://stockx.com";

	@PostMapping("/login")
	public StockXUser loginUser(@RequestBody StockXUser user) throws UserNotFoundException {
		return userService.login(user);
	}

	@GetMapping("/newLowest/{brand}")
	public Shoe getNewLowest(@PathVariable("brand") String brand) throws IOException {

		Shoe newLowestShoe = newLowestService.getNewLowest(brand);
		System.out.println(newLowestShoe.toString());
		return newLowestShoe;
	}

	// needs look
	@PostMapping("/searchByShoeInput")
	public OverallShoe getLatestSaleByShoeInput() throws IOException {

		OverallShoe overallShoe = overallService.getLatestSaleByShoeInput();

		return overallShoe;
	}

	@GetMapping("/searchByShoe/{shoeName}")
	public Shoe searchByShoeName(@PathVariable("shoeName") String shoeName) throws IOException {

		Shoe searchShoe = overallService.searchByShoeName(shoeName);

		return searchShoe;
	}

	// Only displays images for first 4 results, the rest are blank
	@GetMapping("/mostPopular")
	public List<Shoe> mostPopular() throws IOException {

		List<Shoe> mostPopularList = overallService.mostPopular();

		return mostPopularList;

	}

	@PostMapping("/saveShoe/{shoeName}/{email}")
	public SavedShoe saveShoe(@PathVariable("shoeName") String shoeName, @PathVariable("email") String email)
			throws IOException {
		SavedShoe saveShoe = savedShoeService.saveShoe(shoeName, email);

		return saveShoe;
	}

	@GetMapping("/getSavedShoe/{shoeId}/{email}")
	public SavedShoe getSavedShoe(@PathVariable("shoeId") int shoeId, @PathVariable("email") String email)
			throws ShoeNotFoundException {
		SavedShoe getSavedShoe = savedShoeService.getSavedShoe(shoeId, email);

		return getSavedShoe;
	}

	@GetMapping("/getAllSavedShoes/{email}")
	public List<SavedShoe> getAllSavedShoes(@PathVariable("email") String email) throws IOException {
		List<SavedShoe> getAllSavedShoes = savedShoeService.getAllSavedShoes(email);

		return getAllSavedShoes;
	}

	@PostMapping("/comparePrice/{shoeId}/{email}")
	public ComparedShoe comparePrice(@PathVariable("shoeId") int shoeId, @PathVariable("email") String email)
			throws ShoeNotFoundException, IOException {
		ComparedShoe comparePrice = comparedShoeService.comparePrice(shoeId, email);

		return comparePrice;
	}

	@GetMapping("/priceHistory/{shoeId}/{email}")
	public List<SoldPriceHistory> viewSoldPriceHistory(@PathVariable("shoeId") int shoeId,
			@PathVariable("email") String email) throws IOException {
		List<SoldPriceHistory> soldPriceHistory = soldPriceHistoryService.viewSoldPriceHistory(shoeId, email);

		return soldPriceHistory;
	}

	@PutMapping("/updatePrice/{shoeId}/{email}")
	public SavedShoe updatePrice(@PathVariable("shoeId") int shoeId, @PathVariable("email") String email)
			throws ShoeNotFoundException, IOException {
		SavedShoe savedShoe = overallService.updatePrice(shoeId, email);
		return savedShoe;
	}

	@PostMapping("/schedule-shoe/{shoeId}/{threshold}")
	public ScheduleShoe shoeScheduler(@PathVariable("shoeId") int shoeId, @PathVariable("threshold") int threshold)
			throws ShoeNotFoundException, IOException {
		ScheduleShoe scheduleShoe = overallService.shoeScheduler(shoeId, threshold);
		return scheduleShoe;
	}
}
