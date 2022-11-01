package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.service.RouteService;
import ua.nure.danielost.shuttler.service.StopService;
import ua.nure.danielost.shuttler.service.VehicleService;

@RestController
@RequestMapping("/api/v1/organizer")
public class OrganizerControllerV1 {

    @Autowired
    private RouteService routeService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private StopService stopService;

    @PostMapping("/createRoute")
    public ResponseEntity<String> addRoute(
            @RequestBody Route route,
            @RequestParam long id
    ) {
        try {
            routeService.saveRoute(route, id);
            return ResponseEntity.ok("Route saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteRoute/{id}")
    public ResponseEntity<String> deleteRouteById(@PathVariable long id) {
        try {
            routeService.deleteRoute(id);
            return ResponseEntity.ok("Route deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateRoute/{id}")
    public ResponseEntity<String> updateRouteById(
            @PathVariable long id,
            @RequestBody Route route
    ) {
        try {
            routeService.updateRouteNumber(id, route);
            return ResponseEntity.ok("Route updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addVehicle/{routeId}")
    public ResponseEntity<Vehicle> addVehicle(
            @RequestBody Vehicle vehicle,
            @PathVariable long routeId,
            @RequestParam long userId
    ) {
        try {
            return ResponseEntity.ok(vehicleService.addVehicle(vehicle, routeId, userId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/updateVehicle")
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

    @DeleteMapping("deleteVehicle/{vin}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String vin) {
        try {
            vehicleService.deleteVehicle(vin);
            return ResponseEntity.ok("Vehicle has been deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/saveStop/{stopId}/toRoute/{routeId}")
    public ResponseEntity<String> saveStopToRoute(
            @PathVariable long stopId,
            @PathVariable long routeId
    ) {
        try {
            stopService.saveStopToRoute(stopId, routeId);
            return ResponseEntity.ok("Stop {id " + stopId + "} saved to route {id " + routeId + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/removeStop/{stopId}/fromRoute/{routeId}")
    public ResponseEntity<String> removeStopFromRoute(
            @PathVariable long stopId,
            @PathVariable long routeId
    ) {
        try {
            stopService.removeStopFromRoute(stopId, routeId);
            return ResponseEntity.ok("Stop has been removed from the route");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
