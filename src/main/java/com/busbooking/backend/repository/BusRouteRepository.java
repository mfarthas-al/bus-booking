package com.busbooking.backend.repository;

import com.busbooking.backend.entity.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BusRouteRepository extends JpaRepository<BusRoute, Long>{
}
