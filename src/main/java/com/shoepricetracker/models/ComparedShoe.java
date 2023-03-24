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
@Table(name = "compared_shoe")
@NoArgsConstructor
@AllArgsConstructor
public @Data class ComparedShoe {
	
	@Id
	@Column(name = "shoe_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shoeId;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "shoe_name")
	private String shoeName;
	
	@Column(name = "price_difference")
	private String priceDifference;
	
	@Column(name = "number_of_days_between")
	private long noOfDaysBetween;
	
	@Column(name = "image_url")
	private String imageUrl;
}
