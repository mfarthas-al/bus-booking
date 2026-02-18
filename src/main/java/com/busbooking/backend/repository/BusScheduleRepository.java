package com.busbooking.backend.repository;

import com.busbooking.backend.entity.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long> {

    // Find schedules by travel date
    List<BusSchedule> findByTravelDate(LocalDate travelDate);

    // Find schedules by route ID (via bus → route)
    List<BusSchedule> findByBusRouteId(Long routeId);
}
