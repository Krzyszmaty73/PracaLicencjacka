package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FriendRepository extends MongoRepository<Friend, String> {
    Friend findByEmail (String email);
    List<Friend> findByRoomIDs (String roomIDs);
    Friend findByEmailAndRoomIDs (String email, String roomIDs);
}
