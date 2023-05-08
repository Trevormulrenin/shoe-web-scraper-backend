package com.shoepricetracker.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoepricetracker.exceptions.ShoeNotFoundException;
import com.shoepricetracker.models.SavedShoe;
import com.shoepricetracker.repos.SavedShoeRepository;

@Service
public class SavedShoeService {

	@Autowired
	SavedShoeRepository savedShoeRepo;

	final String url = "https://stockx.com";

	final String searchUrl = "https://stockx.com/search?s=";

	public SavedShoe saveShoe(String shoeName, String email) throws IOException {

		SavedShoe savedShoe = new SavedShoe();

		shoeName = shoeName.toLowerCase();
		shoeName = shoeName.replaceAll(" ", "+");

		final Document doc = Jsoup.connect(searchUrl + shoeName).userAgent(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
				.referrer("http://google.com").get();

		Element name = doc.select("div.css-0 > p[class*=chakra-text css-3lpefb]").first();
		Element price = doc.select("div.css-1i6xaee > p[class*=chakra-text css-nsvdd9]").first();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		String formattedDate = dtf.format(date);

		Element imageElement = doc.select("div.css-tkc8ar > img").first();
		String imageUrl = imageElement.absUrl("src"); // get the absolute image URL
		System.out.println(imageUrl);

		savedShoe.setShoeName(name.text());
		savedShoe.setShoePrice(price.text());
		savedShoe.setDateAndTime(formattedDate);
		savedShoe.setImageUrl(imageUrl);
		savedShoe.setEmail(email);

		savedShoeRepo.save(savedShoe);

		return savedShoe;
	}

	public SavedShoe getSavedShoe(int shoeId, String email) throws ShoeNotFoundException {

		return savedShoeRepo.findSavedShoe(shoeId, email);
	}

	public TreeSet<SavedShoe> getAllSavedShoes(String email) {
	    TreeSet<SavedShoe> savedShoes = new TreeSet<>();
	    savedShoes.addAll(savedShoeRepo.getAllSavedShoes(email));
	    return savedShoes;
	}
}
