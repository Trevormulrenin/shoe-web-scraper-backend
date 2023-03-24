package com.shoepricetracker.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoepricetracker.models.ScheduleShoe;

@Repository
public interface ScheduleShoeRepository extends JpaRepository<ScheduleShoe, Long> {

}
