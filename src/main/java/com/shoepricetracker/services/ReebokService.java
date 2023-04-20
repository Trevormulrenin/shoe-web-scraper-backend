package com.shoepricetracker.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoepricetracker.models.ReebokShoe;
import com.shoepricetracker.repos.ReebokRepository;

@Service
public class ReebokService {

	@Autowired
	ReebokRepository reebokRepo;

	final String url = "https://stockx.com";

	public ReebokShoe getReebokNewLowestAskingPrice() throws IOException {

		final Document doc = Jsoup.connect(url + "/reebok/recent-asks").userAgent(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
				.referrer("http://google.com").get();

		String name = doc.select("div.css-0 > p[class*=chakra-text css-3lpefb]").first().text();
		String price = doc.select("div.css-1i6xaee > p[class*=chakra-text css-nsvdd9]").first().text();
		
		Element imageElement = doc.select("div.css-tkc8ar > img").first();
	    String imageUrl = imageElement.absUrl("src"); // get the absolute image URL
	    System.out.println(imageUrl);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		String formattedDate = dtf.format(date);

		ReebokShoe reebokShoe = new ReebokShoe(name, price, formattedDate, imageUrl);

		return reebokShoe;

	}
}
