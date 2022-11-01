package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.service.VehicleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleControllerV1 {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        try {
            return ResponseEntity.ok(vehicleService.getAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/findByVin")
    public ResponseEntity<Vehicle> getVehicleByVin(@RequestParam String vin) {
        try {
            return ResponseEntity.ok(vehicleService.getByVin(vin));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getRoute")
    public ResponseEntity<Route> getVehiclesRoute(@RequestParam String vin) {
        try {
            return ResponseEntity.ok(vehicleService.getRoute(vin));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
