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
@Table(name = "shoe")
@NoArgsConstructor
@AllArgsConstructor
public @Data class Shoe {

	@Id
	@Column(name = "shoe_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shoeId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private String price;
	
	@Column(name = "date_and_time")
	private String dateAndTime;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "brand")
	private String brand;
	
	public Shoe(String name, String price, String dateAndTime, String imageUrl, String brand) {
        this.name = name;
        this.price = price;
        this.dateAndTime = dateAndTime;
        this.imageUrl = imageUrl;
        this.brand = brand;
    }
	
	public Shoe(String name, String price, String dateAndTime, String imageUrl) {
        this.name = name;
        this.price = price;
        this.dateAndTime = dateAndTime;
        this.imageUrl = imageUrl;
	}

}

