package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
