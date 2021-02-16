package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByOwnerEmail (String ownerEmail);
    Room findByRoomID (Integer roomID);
    Room findDescriptionByRoomID(Integer roomID);
    Room findOwnerEmailByRoomID(Integer roomID);
}
