package com.ChaTop.Rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ChaTop.Rental.entity.Rental;

public interface RentalsRepository extends JpaRepository<Rental, Integer>, JpaSpecificationExecutor<Rental> {
    
}
