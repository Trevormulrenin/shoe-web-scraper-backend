package com.shoepricetracker.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.models.ReebokShoe;

@Repository
public interface ReebokRepository extends JpaRepository<ReebokShoe, String>{

}
