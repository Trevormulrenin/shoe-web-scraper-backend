package com.shoepricetracker.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoepricetracker.exceptions.ShoeNotFoundException;
import com.shoepricetracker.models.ComparedShoe;
import com.shoepricetracker.models.SavedShoe;
import com.shoepricetracker.repos.ComparedShoeRepository;
import com.shoepricetracker.repos.SavedShoeRepository;

@Service
public class ComparedShoeService {

	@Autowired
	ComparedShoeRepository comparedShoeRepo;

	@Autowired
	SavedShoeRepository savedShoeRepo;

	final String url = "https://stockx.com";

	final String searchUrl = "https://stockx.com/search?s=";

	public ComparedShoe comparePrice(int shoeId, String email) throws ShoeNotFoundException, IOException {

	    SavedShoe originalShoe = new SavedShoe();
	    SavedShoe newShoe = new SavedShoe();
	    ComparedShoe comparedShoe = new ComparedShoe();

	    try {
	        originalShoe = savedShoeRepo.comparePrice(shoeId);
	    } catch (ShoeNotFoundException e) {
	        System.out.println("Shoe not found with id: " + shoeId);
	    }

	    System.out.println(originalShoe.toString());

	    String shoeName = originalShoe.getShoeName();
	    shoeName = shoeName.toLowerCase();
	    shoeName = shoeName.replaceAll(" ", "%20");

	    System.out.println(shoeName);

	    final Document doc = Jsoup.connect(searchUrl + shoeName).userAgent(
	            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
	            .referrer("http://google.com").get();

	    Element name = doc.select("div.css-0 > p[class*=chakra-text css-3lpefb]").first();
	    System.out.println(name.text());
	    Element price = doc.select("div.css-aduuu0 > p[class*=chakra-text css-9ryi0c]").first();
	    System.out.println(price.text());

	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
	    LocalDateTime date = LocalDateTime.now();
	    String formattedDate = dtf.format(date);

	    newShoe.setShoeName(name.text());
	    newShoe.setShoePrice(price.text());
	    newShoe.setDateAndTime(formattedDate);
	    newShoe.setEmail(email);

	    System.out.println(newShoe.toString());

	    StringBuilder originalShoeConverted = new StringBuilder(originalShoe.getShoePrice());
	    originalShoeConverted = originalShoeConverted.deleteCharAt(0);

	    StringBuilder newShoeConverted = new StringBuilder(newShoe.getShoePrice());
	    newShoeConverted = newShoeConverted.deleteCharAt(0);

	    int difference = Integer.parseInt(newShoeConverted.toString())
	            - Integer.parseInt(originalShoeConverted.toString());
	    String priceDifference = String.valueOf(difference);
	    char dollarSign = '$';
	    String priceDifferenceFinal = dollarSign + priceDifference;
	    ;
	    long noOfDaysBetween = ChronoUnit.DAYS.between(LocalDateTime.parse(originalShoe.getDateAndTime(), dtf), LocalDateTime.parse(newShoe.getDateAndTime(), dtf));

	    shoeName = shoeName.replaceAll("%20", " ");

	    comparedShoe.setShoeName(shoeName);
	    comparedShoe.setPriceDifference(priceDifferenceFinal);
	    comparedShoe.setNoOfDaysBetween(noOfDaysBetween);
	    comparedShoe.setEmail(email);

	    comparedShoeRepo.save(comparedShoe);

	    return comparedShoe;

	}

}
