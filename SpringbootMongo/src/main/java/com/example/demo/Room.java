package com.example.demo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "rooms")
public class Room {

    private String ownerEmail;

   // @Indexed(unique = true)
    @Id
    private Integer roomID;

    private List<String> participantsIDs = new ArrayList<>();
    private String description;
    private Double value;
    private String status;

    public Room(){

    }

    public Room (String ownerEmail, Integer roomID, List<String> participantsIDs, String description, Double value, String status){
        this.ownerEmail = ownerEmail;
        this.roomID = roomID;
        this.participantsIDs = participantsIDs;
        this.description = description;
        this.value = value;
        this.status = status;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public List<String> getParticipantsIDs() {
        return participantsIDs;
    }

    public void setParticipantsIDs(List<String> participantsIDs) {
        this.participantsIDs = participantsIDs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
