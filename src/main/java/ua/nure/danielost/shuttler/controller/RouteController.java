package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/add")
    public ResponseEntity<String> addRoute(@RequestBody Route route) {
        routeService.saveRoute(route);
        return ResponseEntity.ok("Route saved");
    }

    @GetMapping("")
    public ResponseEntity<List<Route>> getAllRoutes() {
        try {
            return ResponseEntity.ok(routeService.getAllRoutes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
