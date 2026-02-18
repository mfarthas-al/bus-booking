package com.busbooking.backend.repository;

import com.busbooking.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookingRepository extends JpaRepository<Booking, Long>{
}
