package com.busbooking.backend.service;

import com.busbooking.backend.entity.*;
import com.busbooking.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusScheduleService {

    private final BusScheduleRepository scheduleRepository;
    private final BusRepository busRepository;
    private final SeatRepository seatRepository;

    public BusScheduleService(
            BusScheduleRepository scheduleRepository,
            BusRepository busRepository,
            SeatRepository seatRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.busRepository = busRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public BusSchedule createSchedule(Long busId, BusSchedule schedule) {

        // 1️⃣ Find Bus
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        // 2️⃣ Attach bus to schedule
        schedule.setBus(bus);

        // 3️⃣ Save schedule
        BusSchedule savedSchedule = scheduleRepository.save(schedule);

        // 4️⃣ Auto-generate seats
        for (int i = 1; i <= bus.getSeatCapacity(); i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setIsBooked(false);
            seat.setBusSchedule(savedSchedule);
            seatRepository.save(seat);
        }

        return savedSchedule;
    }
}
