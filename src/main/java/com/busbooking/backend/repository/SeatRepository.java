package com.busbooking.backend.repository;

import com.busbooking.backend.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long>{
    List<Seat> findByBusScheduleId(Long scheduleId);
    Optional<Seat> findById(Long id);
}
