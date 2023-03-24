package com.shoepricetracker.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sold_price_history")
@NoArgsConstructor
@AllArgsConstructor
public @Data class SoldPriceHistory {
	
	@Id
	@Column(name="history_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long historyId;

	@Column(name="shoe_id")
	private int shoeId;
	
	@Column(name = "shoe_name")
	private String shoeName;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "price")
	private String price;
	
    public SoldPriceHistory(int shoeId, String shoeName, String date, String price) {
        this.shoeId = shoeId;
        this.shoeName = shoeName;
        this.date = date;
        this.price = price;
    }


}
