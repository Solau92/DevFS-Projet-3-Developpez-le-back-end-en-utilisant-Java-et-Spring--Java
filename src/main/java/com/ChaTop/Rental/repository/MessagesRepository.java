package com.ChaTop.Rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ChaTop.Rental.entity.Message;

public interface MessagesRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message>{
    
}
