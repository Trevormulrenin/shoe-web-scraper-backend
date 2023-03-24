package com.shoepricetracker.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stockx_user")
@NoArgsConstructor
@AllArgsConstructor
public @Data class StockXUser {
	
	@Id
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
}
