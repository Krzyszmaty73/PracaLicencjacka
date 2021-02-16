package com.example.demo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@Document(collection = "friends")
public class Friend {

    private String name;
    private String surname;
    private String email;
    private String roomIDs;

    public Friend(){

    }

    public Friend(String name, String surname, String email, String roomIDs) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roomIDs = roomIDs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomIDs() {
        return roomIDs;
    }

    public void setRoomIDs(String roomIDs) {
        this.roomIDs = roomIDs;
    }
}
