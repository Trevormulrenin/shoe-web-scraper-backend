package com.shoepricetracker.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.models.ComparedShoe;

@Repository
public interface ComparedShoeRepository extends JpaRepository<ComparedShoe, Integer>{

}
