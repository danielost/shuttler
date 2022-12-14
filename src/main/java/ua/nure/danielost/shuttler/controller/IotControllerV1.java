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

    @PutMapping("/changeAmount")
    public ResponseEntity<String> increasePassengerQuantity(
            @RequestParam String vin,
            @RequestParam int amount
    ) {
        try {
            vehicleService.modifyAmountOfPassengers(vin, amount);
            int passengers = vehicleService.getByVin(vin).getCurrentCapacity();
            return ResponseEntity.ok(
                    "Amount of passengers of vehicle {vin: " + vin + "} modified: "
                            + (passengers - amount) + " -> " + passengers
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
