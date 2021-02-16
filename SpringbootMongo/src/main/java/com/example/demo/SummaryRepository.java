package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepository extends MongoRepository<Summary, String> {
    List<Summary> findByRoomID(Integer roomID);
    void deleteByRoomID(Integer roomID);
}
