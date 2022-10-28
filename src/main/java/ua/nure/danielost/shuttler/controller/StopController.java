package ua.nure.danielost.shuttler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.danielost.shuttler.service.StopService;

@RestController
@RequestMapping("/stops")
public class StopController {

    @Autowired
    private StopService stopService;

}
