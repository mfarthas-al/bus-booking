package com.busbooking.backend.controller;

import com.busbooking.backend.entity.Seat;
import com.busbooking.backend.repository.SeatRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/seats")
public class SeatController {
    private SeatRepository seatRepository;

    public SeatController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @GetMapping
    public List<Seat> getSeatsBySchedule(@RequestParam Long scheduleId) {
        return seatRepository.findByBusScheduleId(scheduleId);
    }
}
