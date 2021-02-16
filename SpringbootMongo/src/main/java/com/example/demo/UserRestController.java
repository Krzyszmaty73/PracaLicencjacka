package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "https://localhost:4200")
@RestController
public class UserRestController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<User> getInfo() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") String userId)
            throws ResourceNotFoundException {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        System.out.println("Bylem tu");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users/name/{email}")
    public ResponseEntity<String> getUserName (@PathVariable(value = "email") String email) {
        User user =  repository.findByEmail(email);

        if (user != null) {
            return ResponseEntity.ok().body(user.getName());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//    @PutMapping("/users/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable(value = "id") String userId,
//                                           @RequestBody User userDetails) throws ResourceNotFoundException {
//        User user = repository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
//
//        user.setName(userDetails.getName());
//        final User updatedUser = repository.save(user);
//        return ResponseEntity.ok(updatedUser);
//    }

    /** LOGOWANIE **/

    @PostMapping("/users/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody Map<String, Object> body) {

        String email = (String) body.get("email");
        String password = (String) body.get("password");
        /*System.out.println("haslo: "+ passwordEncoder.encode(password));
        System.out.println(password);*/

        //User user = repository.findByEmailAndPassword(email, passwordEncoder.encode(password)) ;

        User user = repository.findByEmail(email) ;

        if (user != null) {
        	checkPassword(password, user.getPassword());
        	
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/users/register")
    public ResponseEntity register(@RequestBody User user) {
        User userExists = repository.findByEmail(user.getEmail());

        if (userExists != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
        	user.setPassword(hashPassword(user.getPassword()));
            repository.save(user);
            return ResponseEntity.ok().body(user);
        }
    }
    
    /** 
     * 
     * Dodane hashowanie hasel wraz z soleniem.
     * https://gist.github.com/craSH/5217757
     * 
     */
    
 // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
 	private static int workload = 12;

    public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);
		return(hashed_password);
	}
    
	public static boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return(password_verified);
	}
    
    
    /*@PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody Map<String, Object> body)
            throws ResourceNotFoundException {

        String name = (String) body.get("name");
        String surname = (String) body.get("surname");
        String email = (String) body.get("email");
        String password = (String) body.get("password");


       haslo otrzymuje juz hashowane, teraz musze dodac sol i zrobic nowy hash
        salt = generate()
         saltedhash = hash (password, salt)
        insert (saltedhash, salt, name, surname, email)

        User user = repository.insert(new User(name, surname, email, password));

        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }*/




}
