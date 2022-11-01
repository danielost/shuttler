package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.danielost.shuttler.model.Stop;
import ua.nure.danielost.shuttler.service.StopService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stops")
public class StopControllerV1 {

    @Autowired
    private StopService stopService;

    @GetMapping("")
    public ResponseEntity<List<Stop>> getAllStops() {
        try {
            return ResponseEntity.ok(stopService.getAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
