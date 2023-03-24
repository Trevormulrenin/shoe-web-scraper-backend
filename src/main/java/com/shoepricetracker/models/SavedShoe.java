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
@Table(name = "saved_shoe")
@NoArgsConstructor
@AllArgsConstructor
public @Data class SavedShoe {

	@Id
	@Column(name = "shoe_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shoeId;

	@Column(name = "shoe_name")
	private String shoeName;

	@Column(name = "shoe_price")
	private String shoePrice;
	
	@Column(name = "email")
	private String email;

	@Column(name = "date_and_time")
	private String dateAndTime;
	
	@Column(name = "image_url")
	private String imageUrl;
}
