package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.exception.EmailTakenException;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return ResponseEntity.ok("User has been saved.");
        } catch (EmailTakenException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<User> getUserById(@RequestParam long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
