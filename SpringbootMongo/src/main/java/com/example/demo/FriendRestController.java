package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
public class FriendRestController {

    @Autowired
    private FriendRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/friends")
    public List<Friend> getInfo() { return repository.findAll(); }

    @PostMapping("/friends")
    public ResponseEntity<Friend> createFriend(@RequestBody Friend friend){
        //Friend friendExists = repository.findByEmail(friend.getEmail());
        User userExists = userRepository.findByEmail(friend.getEmail());
        Friend friendExists = repository.findByEmailAndRoomIDs(friend.getEmail(), friend.getRoomIDs());

        if (friendExists == null) {
                if (userExists != null) {
                    String newName = userExists.getName();
                    String newSurname = userExists.getSurname();

                    friend.setName(newName);
                    friend.setSurname(newSurname);
                }
                repository.save(friend);
                return ResponseEntity.ok().body(friend);

        } else {
            return ResponseEntity.ok().body(friend);
        }


    }

    @GetMapping("/friends/room/{number}")
    public ResponseEntity<List<Friend>> findByRoomIDs(@PathVariable(value = "number") String roomIDs)
            throws ResourceNotFoundException{
        List<Friend> friend = repository.findByRoomIDs(roomIDs);
        return ResponseEntity.ok().body(friend);
    }

    @GetMapping("/friends/{email}")
    public ResponseEntity<Friend> findByEmail(@PathVariable(value = "email")String email)
            throws ResourceNotFoundException{
        Friend friend = repository.findByEmail(email);
        return ResponseEntity.ok().body(friend);
    }

    @GetMapping("/friends/existing/{email}/{roomID}")
    public ResponseEntity<Boolean> findByEmailAndRoomIDs(@PathVariable(value = "email")String email, @PathVariable(value = "roomID") String roomID)
            throws ResourceNotFoundException{
        Friend  friend = repository.findByEmailAndRoomIDs(email, roomID);
        if(friend != null ) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }
}
