package com.busbooking.backend.controller;

import com.busbooking.backend.entity.BusSchedule;
import com.busbooking.backend.service.BusScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class BusScheduleController {

    private final BusScheduleService scheduleService;

    public BusScheduleController(BusScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public BusSchedule createSchedule(
            @RequestParam Long busId,
            @RequestBody BusSchedule schedule
    ) {
        return scheduleService.createSchedule(busId, schedule);
    }
}
