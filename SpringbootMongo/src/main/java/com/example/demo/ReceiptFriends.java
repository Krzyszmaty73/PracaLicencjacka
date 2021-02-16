package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class ReceiptFriends extends Receipt{
    List<Friend> participantsIDs = new ArrayList<Friend>();
    public List<Friend> getParticipantsIDs() {
        return participantsIDs;
    }
    public void setParticipantsIDs(List<Friend> participantsIDs) {
        this.participantsIDs = participantsIDs;
    }
}
