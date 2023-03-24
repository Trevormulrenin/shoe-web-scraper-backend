package com.shoepricetracker.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.exceptions.ShoeNotFoundException;
import com.shoepricetracker.models.SavedShoe;

@Repository
public interface SavedShoeRepository extends JpaRepository<SavedShoe, Integer>{
	
	@Query(value="SELECT * FROM saved_shoe WHERE shoe_id = :shoeId", nativeQuery = true)
	SavedShoe comparePrice(@Param("shoeId") int shoeId) throws ShoeNotFoundException;

	@Query(value = "SELECT * FROM saved_shoe WHERE email = :email", nativeQuery = true)
	List<SavedShoe> getAllSavedShoes(String email);

	@Query(value="SELECT * FROM saved_shoe WHERE shoe_id = :shoeId AND email = :email", nativeQuery = true)
	SavedShoe findSavedShoe(int shoeId, String email) throws ShoeNotFoundException;

	@Query(value="SELECT * FROM saved_shoe WHERE shoe_id = :shoeId", nativeQuery = true)
	SavedShoe findSavedShoeById(int shoeId) throws ShoeNotFoundException;
	
}