package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends MongoRepository<Receipt, String> {
    Receipt findByReceiptID (String receiptID);
    List<ReceiptSimple> findByRoomID (Integer roomID);
}
