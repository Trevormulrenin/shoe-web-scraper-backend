package com.shoepricetracker.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.shoepricetracker.exceptions.ShoeNotFoundException;
import com.shoepricetracker.models.OverallShoe;
import com.shoepricetracker.models.SavedShoe;
import com.shoepricetracker.models.ScheduleShoe;
import com.shoepricetracker.models.SoldPriceHistory;
import com.shoepricetracker.repos.OverallRepository;
import com.shoepricetracker.repos.SavedShoeRepository;
import com.shoepricetracker.repos.ScheduleShoeRepository;
import com.shoepricetracker.repos.SoldPriceHistoryRepository;

@Service
public class OverallService {

	@Autowired
	OverallRepository overallRepo;

	@Autowired
	SavedShoeRepository savedShoeRepo;

	@Autowired
	SoldPriceHistoryRepository priceHistoryRepo;

	@Autowired
	ScheduleShoeRepository scheduleRepo;

	@Autowired
	private JavaMailSender mailSender;

	private static final String BASE_URL = "https://stockx.com";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36";
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

	public OverallShoe getNewLowest() throws IOException {
		Document doc = Jsoup.connect(BASE_URL + "/sneakers/recent-asks").userAgent(USER_AGENT)
				.referrer("http://google.com").get();

		String shoeName = doc.select("div.css-0 > p[class*=chakra-text css-3lpefb]").first().text();
		String shoePrice = doc.select("div.css-11pc7me > p[class*=chakra-text css-nsvdd9]").first().text();
		String imageUrl = doc.select("div.css-tkc8ar > img").first().absUrl("src");
		System.out.println(imageUrl.toString());

		String formattedDate = dtf.format(LocalDateTime.now());

		OverallShoe newLowestShoe = new OverallShoe(shoeName, shoePrice, formattedDate, imageUrl);
		return newLowestShoe;
	}

	public OverallShoe getLatestSaleByShoeInput() throws IOException {
		boolean retryInput = true;
		OverallShoe overallShoe = new OverallShoe();

		while (retryInput) {
			try (Scanner scanner = new Scanner(System.in)) {
				System.out.println("Please type in the entire name of the shoe you are looking for: ");
				String shoeName = scanner.nextLine().toLowerCase().replaceAll("[.() ]", "-");

				Document doc = Jsoup.connect(BASE_URL + "/" + shoeName).userAgent(USER_AGENT)
						.referrer("http://google.com").get();

				String name = doc.select("div.css-0 > h1.chakra-heading.css-twoo7s").text();
				String price = doc.select("div.css-0 > div.css-1o0bwrj > div.css-wpoilv > p.chakra-text.css-13uklb6")
						.text();
				String imageUrl = doc.select("div.css-mis8h8 > img").first().absUrl("src");
				String formattedDate = dtf.format(LocalDateTime.now());

				overallShoe = new OverallShoe(name, price, formattedDate, imageUrl);
				retryInput = false;
			} catch (HttpStatusException e) {
				System.out.println("Could not find product with that name, please try again");
			}
		}
		return overallShoe;
	}

	public OverallShoe searchByShoeName(String shoeName) throws IOException {

		OverallShoe overallShoe = new OverallShoe();
		Document doc = null;
		String formattedShoeName = shoeName.toLowerCase().replaceAll("[ .()]", "+");
		System.out.println("formattedShoeName =" + formattedShoeName);

		if (formattedShoeName.startsWith("jordan")) {
			doc = Jsoup.connect("https://stockx.com/search?s=" + formattedShoeName).userAgent(USER_AGENT)
					.referrer("http://google.com").get();
		} else {
			doc = Jsoup.connect("https://stockx.com/search?s=" + formattedShoeName).userAgent(USER_AGENT)
					.referrer("http://google.com").get();
		}

		Element name = doc.select("div.css-0 > p[class*=chakra-text css-3lpefb]").first();
		Element price = doc.select("div.css-11pc7me > p[class*=chakra-text css-nsvdd9]").first();
		Element imageElement = doc.select("div.css-tkc8ar > img").first();

		System.out.println(name.text());
		System.out.println(price.text());
		System.out.println(imageElement.text());

		String imageUrl = imageElement.absUrl("src");

		LocalDateTime date = LocalDateTime.now();
		String formattedDate = dtf.format(date);

		overallShoe.setShoeName(name.text());
		overallShoe.setShoePrice(price.text());
		overallShoe.setDateAndTime(formattedDate);
		overallShoe.setImageUrl(imageUrl);

		return overallShoe;
	}

	public List<OverallShoe> mostPopular() throws IOException {

		List<OverallShoe> mostPopularList = new ArrayList<>();
		Document doc = Jsoup.connect(BASE_URL + "/sneakers/most-popular").userAgent(USER_AGENT)
				.referrer("http://google.com").get();
		Elements names = doc.select("div.css-0 > p[class*=chakra-text css-3lpefb]");
		Elements prices = doc.select("div.css-11pc7me > p[class*=chakra-text css-nsvdd9]");
		Elements images = doc.select("div.css-tkc8ar > img");

		String formattedDate = dtf.format(LocalDateTime.now());

		for (int i = 0; i < 4; i++) {
			String name = names.get(i).text();
			String price = prices.get(i).text();
			String imageUrl = images.get(i).absUrl("src");
			mostPopularList.add(new OverallShoe(name, price, formattedDate, imageUrl));
		}

		return mostPopularList;
	}

	public SavedShoe updatePrice(int shoeId, String email) throws IOException, ShoeNotFoundException {

		Document doc = null;

		SavedShoe savedShoe = new SavedShoe();

		try {
			// retrieve current shoe record
			savedShoe = savedShoeRepo.findSavedShoe(shoeId, email);

			// save current price to price history db
			// SoldPriceHistory oldPrice = new SoldPriceHistory(shoeId,
			// savedShoe.getShoeName(),
			// savedShoe.getDateAndTime(), savedShoe.getShoePrice());
			// priceHistoryRepo.save(oldPrice);

			// format shoe name
			String shoeName = savedShoe.getShoeName();
			String formattedShoeName = shoeName.toLowerCase().replaceAll("[ .()]", "+");
			System.out.println("formattedShoeName =" + formattedShoeName);

			if (formattedShoeName.startsWith("jordan")) {
				doc = Jsoup.connect("https://stockx.com/search?s=" + formattedShoeName).userAgent(USER_AGENT)
						.referrer("http://google.com").get();
			} else {
				doc = Jsoup.connect("https://stockx.com/search?s=" + formattedShoeName).userAgent(USER_AGENT)
						.referrer("http://google.com").get();
			}

			// retrieve current date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
			LocalDateTime date = LocalDateTime.now();
			String formattedDate = dtf.format(date);

			// scrape new price and set
			Element price = doc.select("div.css-11pc7me > p[class*=chakra-text css-nsvdd9]").first();
			savedShoe.setShoePrice(price.text());
			savedShoe.setDateAndTime(formattedDate);

			// save new price history object with updated price to db
			SoldPriceHistory priceHistoryCurrent = new SoldPriceHistory(shoeId, savedShoe.getShoeName(),
					savedShoe.getDateAndTime(), savedShoe.getShoePrice());

			priceHistoryRepo.save(priceHistoryCurrent);
			savedShoeRepo.save(savedShoe);
		} catch (ShoeNotFoundException e) {
			e.getMessage();
		}

		return savedShoe;
	}

	public ScheduleShoe shoeScheduler(int shoeId, int threshold) throws IOException, ShoeNotFoundException {
		ScheduleShoe scheduleShoe = new ScheduleShoe();

		try {
			SavedShoe savedShoe = savedShoeRepo.findSavedShoeById(shoeId);

			String price = savedShoe.getShoePrice();
			String priceString = price.replace("$", "");
			double newPrice = Double.parseDouble(priceString);

			scheduleShoe.setName(savedShoe.getShoeName());
			scheduleShoe.setEmail(savedShoe.getEmail());
			scheduleShoe.setOriginalPrice(newPrice);
			scheduleShoe.setThreshold(threshold);
			scheduleShoe.setDateAndTime(savedShoe.getDateAndTime());

			scheduleRepo.save(scheduleShoe);

		} catch (ShoeNotFoundException e) {
			e.getMessage();
		}

		return scheduleShoe;

	}

	@Scheduled(fixedRate = 1800000) // runs every 30 minutes (30 * 60 * 1000)
	public void checkShoePrice() {
		final String searchUrl = "https://stockx.com/search?s=";
		List<ScheduleShoe> scheduleShoes = scheduleRepo.findAll();
		for (ScheduleShoe scheduleShoe : scheduleShoes) {
			try {
				String name = scheduleShoe.getName();
				name = name.toLowerCase();
				name = name.replaceAll(" ", "+");

				final Document doc = Jsoup.connect(searchUrl + name).userAgent(
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
						.referrer("http://google.com").get();

				Element price = doc.select("div.css-11pc7me > p[class*=chakra-text css-nsvdd9]").first();
				String priceString = price.text().replace("$", ""); // remove currency symbol
				double convertedPrice = Double.parseDouble(priceString);

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
				LocalDateTime date = LocalDateTime.now();
				String formattedDate = dtf.format(date);

				// Compare price with original price
				double originalPrice = scheduleShoe.getOriginalPrice();
				double threshold = scheduleShoe.getThreshold();

				// Update the original price in the database
				if (originalPrice == 0) {
					scheduleShoe.setOriginalPrice(convertedPrice);
					scheduleRepo.save(scheduleShoe);
				}

				// Send email if price drops below a given point
				if (convertedPrice < threshold) {
					sendEmail(scheduleShoe.getEmail(), scheduleShoe.getName(), price, originalPrice, formattedDate,
							threshold);
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
	}

	private void sendEmail(String email, String name, Element price, double originalPrice, String date,
			double threshold) {
		String subject = "Price drop alert for " + name;
		String message = "The price for " + name + " has dropped below the threshold of $" + threshold + ".\n\n"
				+ "Current price: " + price.text() + "\n" + "Original price: $" + originalPrice + "\n" + "Date: "
				+ date;

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("your-email@example.com"); // replace with your email address
		msg.setTo(email);
		msg.setSubject(subject);
		msg.setText(message);
		mailSender.send(msg);
		System.out.println("Email sent successfully.");
	}

}
