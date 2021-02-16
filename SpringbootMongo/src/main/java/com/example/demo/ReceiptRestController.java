package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
public class ReceiptRestController {

    @Autowired
    private ReceiptRepository repository;
    @Autowired
    private SummaryRepository summaryRepository;

    @GetMapping("/receipts")
    public List<Receipt> getInfo() {
        List<Receipt> list = repository.findAll();
        return list;
    }

    @GetMapping("/receipts/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable(value = "id") String receiptID) {
        Receipt receipt = repository.findByReceiptID(receiptID);
        return ResponseEntity.ok().body(receipt);
    }

    @GetMapping("/receipts/room/{number}")
    public ResponseEntity<List<ReceiptSimple>> getReceiptByRoomID(@PathVariable(value = "number") Integer roomID) {
        List<ReceiptSimple> receipt = repository.findByRoomID(roomID);
        return ResponseEntity.ok().body(receipt);
    }

    @PostMapping("/receipts/close/room")
    public ResponseEntity summary (@RequestBody String roomID){

        List<ReceiptSimple> receipts = repository.findByRoomID(Integer.parseInt(roomID));
        HashMap<String, Double> toReturn = new HashMap<>();

        for (ReceiptSimple receipt : receipts) {
            double divideBy = receipt.getParticipantsIDs().size();

            for (String participant : receipt.getParticipantsIDs()) {
                double currentValue = receipt.getValue();
                String currency = receipt.getCurrency();

                if(currency.equals("USD")){
                    currentValue = currentValue * 3.72;
                } else if(currency.equals("EUR")) {
                    currentValue = currentValue * 4.52;
                }


                double valueToReturn;
                if (participant.equals(receipt.getPaidBy())) {
                    valueToReturn = (divideBy - 1) * currentValue / divideBy;
                } else {
                    valueToReturn = (-1 * currentValue) / divideBy;
                }

                updateToReturn(toReturn, participant,valueToReturn);
            }
        }

        //usuniecie powrotrzen w bazie kiedy cofniemy z podsumowania
        summaryRepository.deleteByRoomID(Integer.parseInt(roomID));

        //dodanie do bazy summary, iterowanie po hashmapie
        for (String name : toReturn.keySet()){
            summaryRepository.save(new Summary(Integer.parseInt(roomID), name,  toReturn.get(name), "PLN"));
        }

        return ResponseEntity.ok().body("ok");
    }

    /**
     * This method updates the Hashmap with values that has to be returned.
     * @param toReturn - HashMap with participants and values to return
     * @param participant - participant to be modified with the hash map
     * @param value - value to be updated
     */
    private void updateToReturn(HashMap<String, Double> toReturn, String participant, Double value)
    {
            if (toReturn.get(participant) != null) {
                double valueToReturn = value +  toReturn.get(participant);
                valueToReturn *= 100;
                valueToReturn = (double)Math.round(valueToReturn);
                valueToReturn /= 100;
                toReturn.put(participant, valueToReturn );
            } else{
                value *= 100;
                value = (double)Math.round(value);
                value /= 100;
                toReturn.put(participant, value);
            }
    }


    @PostMapping("/receipts")
    public Receipt createReceipt(@RequestBody ReceiptFriends receipt) {
        System.out.println("Dodaje rachunek");

        ReceiptSimple simple = new ReceiptSimple();
        simple.setCurrency(receipt.getCurrency());
        simple.setReceiptID(receipt.getReceiptID());
        simple.setDescription(receipt.getDescription());
        simple.setRoomID(receipt.getRoomID());
        simple.setValue(receipt.getValue());
        simple.setTitle(receipt.getTitle());
        simple.setPaidBy(receipt.getPaidBy());

        List<String> participantsIDs = new ArrayList<String>();
        receipt.getParticipantsIDs().forEach((participant) -> {
            participantsIDs.add(participant.getEmail());
        });
        simple.setParticipantsIDs(participantsIDs);

        return repository.save(simple);

    }
}


