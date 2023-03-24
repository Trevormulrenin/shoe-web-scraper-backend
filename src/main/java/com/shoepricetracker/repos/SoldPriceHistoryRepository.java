package com.shoepricetracker.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.models.SoldPriceHistory;

@Repository
public interface SoldPriceHistoryRepository extends JpaRepository<SoldPriceHistory, Long>{
	
	@Query(value="SELECT * FROM sold_price_history WHERE shoe_id = :shoeId", nativeQuery = true)
	List<SoldPriceHistory> getPriceHistory(int shoeId);

}
