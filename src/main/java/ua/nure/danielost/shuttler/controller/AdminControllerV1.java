package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.Stop;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.service.AdminService;
import ua.nure.danielost.shuttler.service.RouteService;
import ua.nure.danielost.shuttler.service.StopService;
import ua.nure.danielost.shuttler.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminControllerV1 {

    @Autowired
    private UserService userService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/deleteRoute/{id}")
    public ResponseEntity<String> deleteRouteById(@PathVariable long id) {
        try {
            adminService.deleteRoute(id);
            return ResponseEntity.ok("Route {id: " + id + "} deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("deleteVehicle/{vin}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String vin) {
        try {
            adminService.deleteVehicle(vin);
            return ResponseEntity.ok("Vehicle {vin: " + vin + "} deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/organizers")
    public ResponseEntity<List<User>> getOrganizers() {
        try {
            return ResponseEntity.ok(userService.getAllUsersByType("ROLE_ORGANIZER"));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/controllers")
    public ResponseEntity<List<User>> getControllers() {
        try {
            return ResponseEntity.ok(userService.getAllUsersByType("ROLE_CONTROLLER"));
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
            return ResponseEntity.ok(
                    "Role {id: " + roleId + "} has been set to user {id: " + userId + "}"
            );
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
            return ResponseEntity.ok(
                    "Role {id: " + roleId + "} has been unset from user {id: " + userId + "}"
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            adminService.deleteUser(id);
            return ResponseEntity.ok("User {id: " + id + "} has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addStop")
    public ResponseEntity<String> addStop(@RequestBody Stop stop) {
        try {
            stopService.addStop(stop);
            return ResponseEntity.ok("Stop created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateStop/{id}")
    public ResponseEntity<String> updateStop(
            @RequestBody Stop stop,
            @PathVariable long id
    ) {
        try {
            stopService.updateStop(stop, id);
            return ResponseEntity.ok("Stop {id: " + id + "} updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteStop/{id}")
    public ResponseEntity<String> deleteStop(@PathVariable long id) {
        try {
            stopService.deleteStop(id);
            return ResponseEntity.ok("Stop {id: " + id + "} deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
