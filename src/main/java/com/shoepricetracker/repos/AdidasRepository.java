package com.shoepricetracker.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.models.AdidasShoe;

@Repository
public interface AdidasRepository extends JpaRepository<AdidasShoe, String> {

}
