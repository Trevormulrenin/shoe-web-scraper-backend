package com.shoepricetracker.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.models.OverallShoe;

@Repository
public interface OverallRepository extends JpaRepository<OverallShoe, String> {

}
