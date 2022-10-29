package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.service.RouteService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("")
    public ResponseEntity<List<Route>> getAllRoutes() {
        try {
            return ResponseEntity.ok(routeService.getAllRoutes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Route> getRouteById(@RequestParam long id) {
        try {
            return ResponseEntity.ok(routeService.getRouteById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getByNumber")
    public ResponseEntity<Route> getRouteByNumber(@RequestParam int number) {
        try {
            return ResponseEntity.ok(routeService.getRouteByNumber(number));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getVehicles/{id}")
    public ResponseEntity<Set<Vehicle>> getRoutesVehicles(@PathVariable long id) {
        try {
            return ResponseEntity.ok(routeService.getVehicles(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/type")
    public ResponseEntity<List<Route>> getRoutesOfType(@RequestParam Route.VehicleType type ) {
        try {
            return ResponseEntity.ok(routeService.getRoutes(type));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/congestion/{id}")
    public ResponseEntity<Double> getCongestion(@PathVariable long id) {
        try {
            return ResponseEntity.ok(routeService.getCongestion(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoute(@RequestBody Route route) {
        try {
            routeService.saveRoute(route);
            return ResponseEntity.ok("Route saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRouteById(@PathVariable long id) {
        try {
            routeService.deleteRoute(id);
            return ResponseEntity.ok("Route deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRouteById(
            @PathVariable long id, @RequestBody Route route
    ) {
        try {
            routeService.updateRouteNumber(id, route);
            return ResponseEntity.ok("Route updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/getOptimal/{routeId}")
    public ResponseEntity<List<Route>> getOptimalRoutes(
            @PathVariable long routeId,
            @RequestParam long departId,
            @RequestParam long destinationId
    ) {
        try {
            return ResponseEntity.ok(routeService.getOptimalRoutes(routeId, departId, destinationId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
