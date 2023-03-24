package com.shoepricetracker;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication	
@EnableScheduling
public class ShoePriceTrackerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ShoePriceTrackerApplication.class, args);
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Trevor\\Desktop\\ShoeTracker\\ChromeDriver\\chromedriver.exe");
//		final String url = "https://stockx.com";
//		final Document doc = Jsoup.connect(url + "/adidas/recent-asks").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
//			      .referrer("http://google.com").get();
//		Element ele = doc.select("div.css-10qtu60 > p").first();
//		System.out.println(ele.text());
	}

}
