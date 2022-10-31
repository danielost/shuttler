package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.service.VehicleService;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleControllerV1 {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/add/{id}")
    public ResponseEntity<Vehicle> addVehicle(
            @RequestBody Vehicle vehicle, @PathVariable long id
    ) {
        try {
            return ResponseEntity.ok(vehicleService.addVehicle(vehicle, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        try {
            return ResponseEntity.ok(vehicleService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/findByVin")
    public ResponseEntity<Vehicle> getVehicleByVin(@RequestParam String vin) {
        try {
            return ResponseEntity.ok(vehicleService.getByVin(vin));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getRoute")
    public ResponseEntity<Route> getVehiclesRoute(@RequestParam String vin) {
        try {
            return ResponseEntity.ok(vehicleService.getRoute(vin));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateVehicle(
            @RequestBody Vehicle vehicle,
            @RequestParam String vin,
            @RequestParam long route_id
    ) {
        try {
            vehicleService.updateVehicle(vehicle, vin, route_id);
            return ResponseEntity.ok("Vehicle updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/increasePassQuantity")
    public ResponseEntity<String> increasePassengerQuantity(@RequestParam String vin) {
        try {
            vehicleService.increaseAmountOfPassengers(vin);
            return ResponseEntity.ok("Amount increased");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/decreasePassQuantity")
    public ResponseEntity<String> decreasePassengerQuantity(@RequestParam String vin) {
        try {
            vehicleService.decreaseAmountOfPassengers(vin);
            return ResponseEntity.ok("Amount decreased");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{vin}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String vin) {
        try {
            vehicleService.deleteVehicle(vin);
            return ResponseEntity.ok("Vehicle has been deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
