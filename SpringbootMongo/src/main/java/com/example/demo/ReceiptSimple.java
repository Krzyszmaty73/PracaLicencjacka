package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class ReceiptSimple extends Receipt {
    List<String> participantsIDs = new ArrayList<String>();

    public List<String> getParticipantsIDs() {
        return participantsIDs;
    }

    public void setParticipantsIDs(List<String> participantsIDs) {
        this.participantsIDs = participantsIDs;
    }
}
