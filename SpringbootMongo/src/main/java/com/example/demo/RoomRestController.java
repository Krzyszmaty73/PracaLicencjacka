package com.example.demo;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
public class RoomRestController {

    @Autowired
    private RoomRepository repository;

    @GetMapping("/rooms")
    public List<Room> getInfo() { return repository.findAll(); }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> findByRoomID(@PathVariable Integer id){
        Room optionalRoom = repository.findByRoomID(id);
        return ResponseEntity.ok().body(optionalRoom);
    }

    @GetMapping("/rooms/owner/{ownerEmail}")
    public ResponseEntity<List<Room>> findByEmail(@PathVariable(value = "ownerEmail")String ownerEmail) {
        List<Room> rooms = repository.findByOwnerEmail(ownerEmail);
        return ResponseEntity.ok().body(rooms);
    }

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody Room room){
        Room tmpRoom = repository.findByRoomID(room.getRoomID());
        if(tmpRoom == null)
         return repository.save(room);
        else
            return null;
    }

    @PatchMapping("/updateRoom/description/{roomID}")
    public void updateRoomDescription (@RequestBody String description, @PathVariable(value = "roomID") Integer roomID) {

        Room tmpRoom = repository.findByRoomID(roomID);
        tmpRoom.setDescription(description);
        repository.save(tmpRoom);
    }

    @PatchMapping("/updateRoom/addParticipant/{roomID}")
    public void updateRoomParticipants (@RequestBody String email, @PathVariable(value = "roomID") Integer roomID) {

        Room tmpRoom = repository.findByRoomID(roomID);

        if(tmpRoom != null){
            List<String> tmpList = tmpRoom.getParticipantsIDs();
            tmpList.add(email);
            tmpRoom.setParticipantsIDs(tmpList);
            repository.save(tmpRoom);
        }

    }

    @PatchMapping("/updateRoom/value/{roomID}")
    public void updateRoomValue (@RequestBody Receipt receipt, @PathVariable(value = "roomID") Integer roomID) {

        Room tmpRoom = repository.findByRoomID(roomID);
        Double oldValue = tmpRoom.getValue();
        Double tmpValue = receipt.getValue();

        if(receipt.getCurrency().equals("USD")){
            tmpValue = tmpValue * 3.72;
        } else if(receipt.getCurrency().equals("EUR")) {
            tmpValue = tmpValue * 4.52;
        }

        tmpValue += oldValue;

        tmpValue *= 100;
        tmpValue = Double.valueOf(Math.round(tmpValue));
        tmpValue /= 100;

        tmpRoom.setValue(tmpValue);

        repository.save(tmpRoom);
    }

    @PatchMapping("/updateRoom/status/{roomID}")
    public void updateRoomStatus (@RequestBody String status, @PathVariable(value = "roomID") Integer roomID) {
        Room tmpRoom = repository.findByRoomID(roomID);
        tmpRoom.setStatus(status);
        repository.save(tmpRoom);
    }

    @GetMapping("/rooms/description/{roomID}")
    public ResponseEntity<String> findDescriptionByRoomID(@PathVariable(value = "roomID")Integer roomID) {
        Room tmpRoom = repository.findDescriptionByRoomID(roomID);
        if(tmpRoom != null){
            return ResponseEntity.ok().body("\""+tmpRoom.getDescription()+"\"");
        } else{
            return ResponseEntity.ok().body("");
        }
    }

    @GetMapping("/room/owner/{roomID}")
    public ResponseEntity<String> findRoomByOwnerEmail(@PathVariable(value = "roomID")Integer roomID) {
        Room tmpRoom = repository.findOwnerEmailByRoomID(roomID);
        if (tmpRoom != null){
            return ResponseEntity.ok().body("\""+tmpRoom.getOwnerEmail()+"\"");
        } else{
            return ResponseEntity.ok().body("");
        }

    }
}
