package com.busbooking.backend.controller;

import com.busbooking.backend.entity.BusRoute;
import com.busbooking.backend.repository.BusRouteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/routes")
public class BusRouteController {

    private final BusRouteRepository routeRepository;

    public BusRouteController(BusRouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping
    public List<BusRoute> getAllRoutes() {
        return routeRepository.findAll();
    }
}
