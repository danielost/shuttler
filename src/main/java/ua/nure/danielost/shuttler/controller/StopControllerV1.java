package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.Stop;
import ua.nure.danielost.shuttler.service.StopService;

import java.util.List;

@RestController
@RequestMapping("/stops")
public class StopController {

    @Autowired
    private StopService stopService;

    @GetMapping("")
    public ResponseEntity<List<Stop>> getAllStops() {
        try {
            return ResponseEntity.ok(stopService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/addStop")
    public ResponseEntity<String> addStop(@RequestBody Stop stop) {
        try {
            stopService.addStop(stop);
            return ResponseEntity.ok("Stop saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addStop/{routeId}")
    public ResponseEntity<String> addStop(
            @RequestBody Stop stop, @PathVariable long routeId
    ) {
        try {
            stopService.addStop(stop, routeId);
            return ResponseEntity.ok("Stop saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{stopId}")
    public ResponseEntity<String> saveStopToRoute(
            @PathVariable long stopId, @RequestParam long routeId
    ) {
        try {
            stopService.saveStopToRoute(stopId, routeId);
            return ResponseEntity.ok("Stop {id " + stopId + "} saved to route {id " + routeId + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateStop/{id}")
    public ResponseEntity<String> updateStop(
            @RequestBody Stop stop, @PathVariable long id
    ) {
        try {
            stopService.updateStop(stop, id);
            return ResponseEntity.ok("Stop saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/removeStop/{stopId}/fromRoute/{routeId}")
    public ResponseEntity<String> removeStopFromRoute(
            @PathVariable long stopId, @PathVariable long routeId
    ) {
        try {
            stopService.removeStopFromRoute(stopId, routeId);
            return ResponseEntity.ok("Stop has been removed from the route");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStop(@PathVariable long id) {
        try {
            stopService.deleteStop(id);
            return ResponseEntity.ok("Stop has been deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
