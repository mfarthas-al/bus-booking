package com.busbooking.backend.repository;

import com.busbooking.backend.entity.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long>{
}
