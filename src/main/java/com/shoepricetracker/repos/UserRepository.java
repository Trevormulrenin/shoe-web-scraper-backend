package com.shoepricetracker.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.models.StockXUser;

@Repository
public interface UserRepository extends JpaRepository<StockXUser, String> {

}
