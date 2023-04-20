package com.shoepricetracker.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shoe")
@NoArgsConstructor
@AllArgsConstructor
public @Data class Shoe {

	@Id
	@Column(name = "name")
	private String shoeName;
	
	@Column(name = "price")
	private String shoePrice;
	
	@Column(name = "date_and_time")
	private String dateAndTime;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "brand")
	private String brand;
}

