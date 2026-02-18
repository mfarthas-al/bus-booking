package com.busbooking.backend.repository;

import com.busbooking.backend.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long>{
    List<Seat> findByBusScheduleId(Long scheduleId);
}
