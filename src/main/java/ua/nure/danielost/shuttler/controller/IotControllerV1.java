package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.danielost.shuttler.service.VehicleService;

@RestController
@RequestMapping("/api/v1/controller")
public class IotControllerV1 {

    @Autowired
    private VehicleService vehicleService;

    @PutMapping("/increasePassQuantity")
    public ResponseEntity<String> increasePassengerQuantity(@RequestParam String vin) {
        try {
            vehicleService.increaseAmountOfPassengers(vin);
            int passengers = vehicleService.getByVin(vin).getCurrent_capacity();
            return ResponseEntity.ok(
                    "Amount of passengers of vehicle {vin: " + vin + "} incremented by 1:"
                            + (passengers - 1) + " -> " + passengers
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/decreasePassQuantity")
    public ResponseEntity<String> decreasePassengerQuantity(@RequestParam String vin) {
        try {
            vehicleService.decreaseAmountOfPassengers(vin);
            int passengers = vehicleService.getByVin(vin).getCurrent_capacity();
            return ResponseEntity.ok(
                    "Amount of passengers of vehicle {vin: " + vin + "} decremented by 1:"
                            + (passengers + 1) + " -> " + passengers
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
