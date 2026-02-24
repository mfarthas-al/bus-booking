package com.busbooking.backend.controller;

import com.busbooking.backend.entity.BusSchedule;
import com.busbooking.backend.repository.BusScheduleRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/schedules")
public class BusScheduleController {

    private final BusScheduleRepository scheduleRepository;

    public BusScheduleController(BusScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // FR2 - Get schedules by date
    @GetMapping("/by-date")
    public List<BusSchedule> getSchedulesByDate(@RequestParam String date) {
        return scheduleRepository.findByTravelDate(LocalDate.parse(date));
    }
}
