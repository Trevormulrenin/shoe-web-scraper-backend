package com.shoepricetracker.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.models.JordanShoe;

@Repository
public interface JordanRepository extends JpaRepository<JordanShoe, String> {

}
