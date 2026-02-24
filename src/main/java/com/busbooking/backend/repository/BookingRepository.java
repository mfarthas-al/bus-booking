package com.busbooking.backend.repository;

import com.busbooking.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    Optional<Booking> findByBookingCode(String bookingCode);
    Optional<Booking> findById(Long id);
    Optional<Booking> findBySeatId(Long seatId);
}
