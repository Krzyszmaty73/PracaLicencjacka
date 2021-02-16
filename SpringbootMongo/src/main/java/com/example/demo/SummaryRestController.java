package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
public class SummaryRestController {

    @Autowired
    private SummaryRepository repository;

    @GetMapping("/summary/{roomID}")
    public ResponseEntity<List<Summary>> getRoomSummary(@PathVariable(value = "roomID")Integer roomID)
            throws ResourceNotFoundException{
        List<Summary> summaries = repository.findByRoomID(roomID);
        return ResponseEntity.ok().body(summaries);
    }
}
