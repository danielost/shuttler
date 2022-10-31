package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users/findById")
    public ResponseEntity<User> getUserById(@RequestParam long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users/findByUsername")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(userService.findByUsername(username));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/setRole/{roleId}/toUser/{userId}")
    public ResponseEntity<String> addRole(
            @PathVariable long roleId,
            @PathVariable long userId
    ) {
        try {
            userService.setRole(roleId, userId);
            return ResponseEntity.ok("Role {id: " + roleId + "} has been set to user {id: " + userId + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/unsetRole/{roleId}/fromUser/{userId}")
    public ResponseEntity<String> unsetRole(
            @PathVariable long roleId,
            @PathVariable long userId
    ) {
        try {
            userService.unsetRole(roleId, userId);
            return ResponseEntity.ok("Role {id: " + roleId + "} has been unset from user {id: " + userId + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
