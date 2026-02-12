package com.busbooking.backend.repository;

import com.busbooking.backend.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SeatRepository extends JpaRepository<Seat, Long>{
}
