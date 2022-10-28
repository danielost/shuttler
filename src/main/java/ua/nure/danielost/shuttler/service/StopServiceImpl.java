package ua.nure.danielost.shuttler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.repository.StopRepository;

@Service
public class StopServiceImpl implements StopService {

    @Autowired
    private StopRepository stopRepository;

}
