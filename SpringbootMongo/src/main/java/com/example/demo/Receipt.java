package com.example.demo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "receipts")
public class Receipt {

    private String receiptID;

    @Indexed(unique = true)
    private Integer roomID;

    private String title;
    private Double value;
    private String currency;
    private String paidBy;
    private String description;

    public Receipt(){

    }

    public Receipt(String receiptID, Integer roomID, String title, Double value, String currency, String paidBy, String description) {
        this.receiptID = receiptID;
        this.roomID = roomID;
        this.title = title;
        this.value = value;
        this.currency = currency;
        this.paidBy = paidBy;
        this.description = description;

    }

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaidBy() {
         return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
