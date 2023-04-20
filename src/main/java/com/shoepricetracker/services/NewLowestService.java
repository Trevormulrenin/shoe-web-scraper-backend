package com.shoepricetracker.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.shoepricetracker.models.Shoe;

@Service
public class NewLowestService {

	public Shoe getNewLowest(String brand) throws IOException {
		final String url = "https://stockx.com";

		if (brand.equals("overall")) {
			brand = "sneakers";
		}
		
		final Document doc = Jsoup.connect(url + "/" + brand + "/recent-asks").userAgent(
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36").referrer("http://google.com").get();

		String name = doc.select("div.css-0 > p[class*=chakra-text css-3lpefb]").first().text();
		String price = doc.select("div.css-1i6xaee > p[class*=chakra-text css-nsvdd9]").first().text();

		Element imageElement = doc.select("div.css-tkc8ar > img").first();
		String imageUrl = imageElement.absUrl("src"); // get the absolute image URL
		System.out.println(imageUrl);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		String formattedDate = dtf.format(date);

		Shoe shoe = new Shoe(name, price, formattedDate, imageUrl);

		return shoe;
	}
}

