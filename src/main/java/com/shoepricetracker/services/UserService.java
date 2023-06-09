package com.shoepricetracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.shoepricetracker.exceptions.UserNotFoundException;
import com.shoepricetracker.models.StockXUser;
import com.shoepricetracker.repos.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;

	public StockXUser login(StockXUser user) throws UserNotFoundException {
		
		Example<StockXUser> userEx = Example.of(user);
		StockXUser userLoggedIn = userRepo.findOne(userEx)
				.orElseThrow(() -> new UserNotFoundException("User not found. Please try again."));
		return userLoggedIn;
	}

	public String createUser(String email, String password) {
		
		StockXUser user = new StockXUser(email, password);
		
		StockXUser savedUser = userRepo.save(user);
		if (savedUser != null) {
			return "User created successfully";
		} else {
			return "Failed to create user";
		}
	}
}
