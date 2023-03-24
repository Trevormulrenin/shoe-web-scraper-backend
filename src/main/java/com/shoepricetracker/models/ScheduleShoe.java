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
@Table(name = "schedule_shoe")
@NoArgsConstructor
@AllArgsConstructor
public @Data class ScheduleShoe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "date_and_time")
	private String dateAndTime;
	
	@Column(name = "threshold")
	private int threshold;
	
	@Column(name = "original_price")
	private double originalPrice;
	
	@Column(name = "newPrice")
	private double newPrice;
}	