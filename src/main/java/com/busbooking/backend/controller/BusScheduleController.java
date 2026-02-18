package com.busbooking.backend.controller;

import com.busbooking.backend.entity.BusSchedule;
import com.busbooking.backend.repository.BusScheduleRepository;
import com.busbooking.backend.service.BusScheduleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/schedules")
public class BusScheduleController {

    private final BusScheduleService scheduleService;
    private final BusScheduleRepository scheduleRepository;

    public BusScheduleController(BusScheduleService scheduleService,
                                 BusScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }

    // Create schedule
    @PostMapping
    public BusSchedule createSchedule(
            @RequestParam Long busId,
            @RequestBody BusSchedule schedule
    ) {
        return scheduleService.createSchedule(busId, schedule);
    }

    // FR2 - Get schedules by date
    @GetMapping("/by-date")
    public List<BusSchedule> getSchedulesByDate(@RequestParam String date) {
        return scheduleRepository.findByTravelDate(LocalDate.parse(date));
    }

    // FR2 - Get schedules by route
    @GetMapping("/by-route")
    public List<BusSchedule> getSchedulesByRoute(@RequestParam Long routeId) {
        return scheduleRepository.findByBusRouteId(routeId);
    }
}
