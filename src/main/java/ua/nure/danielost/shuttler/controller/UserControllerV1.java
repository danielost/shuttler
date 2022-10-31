package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.service.UserService;

@RestController
@RequestMapping("/users")
public class UserControllerV1 {

    @Autowired
    private UserService userService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("User id" + id + " has been deleted.");
        } catch (NoSuchUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable long id, @RequestBody User user
    ) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok("User id" + id + " has been updated.");
        } catch (NoSuchUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{userId}/saveRoute")
    public ResponseEntity<String> saveRouteToProfile(
            @PathVariable long userId, @RequestParam long routeId
    ) {
        try {
            userService.saveRoute(userId, routeId);
            return ResponseEntity.ok("Route " + routeId + " successfully saved for user " + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{userId}/deleteRoute")
    public ResponseEntity<String> deleteRouteFromProfile(
            @PathVariable long userId, @RequestParam long routeId
    ) {
        try {
            userService.deleteRoute(userId, routeId);
            return ResponseEntity.ok("Route " + routeId + " successfully removed for user " + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
