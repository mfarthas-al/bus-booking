package com.busbooking.backend.controller;

import com.busbooking.backend.entity.BusSchedule;
import com.busbooking.backend.repository.BusScheduleRepository;
import com.busbooking.backend.service.BusScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/schedules")
public class AdminScheduleController {

    private final BusScheduleService scheduleService;
    private final BusScheduleRepository scheduleRepository;

    public AdminScheduleController(BusScheduleService scheduleService,
                                   BusScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }

    // CREATE
    @PostMapping
    public BusSchedule createSchedule(
            @RequestParam Long busId,
            @RequestBody BusSchedule schedule
    ) {
        return scheduleService.createSchedule(busId, schedule);
    }

    // VIEW ALL
    @GetMapping
    public List<BusSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // UPDATE
    @PutMapping("/{id}")
    public BusSchedule updateSchedule(
            @PathVariable Long id,
            @RequestBody BusSchedule updatedSchedule
    ) {
        BusSchedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        existing.setTravelDate(updatedSchedule.getTravelDate());
        existing.setDepartureTime(updatedSchedule.getDepartureTime());
        existing.setArrivalTime(updatedSchedule.getArrivalTime());

        return scheduleRepository.save(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        scheduleRepository.deleteById(id);
        return "Schedule deleted successfully";
    }
}
