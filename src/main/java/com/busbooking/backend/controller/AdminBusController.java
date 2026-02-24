package com.busbooking.backend.controller;

import com.busbooking.backend.entity.Bus;
import com.busbooking.backend.repository.BusRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/buses")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminBusController {

    private final BusRepository busRepository;

    public AdminBusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    // CREATE BUS
    @PostMapping
    public Bus createBus(@RequestBody Bus bus) {
        return busRepository.save(bus);
    }

    // VIEW ALL BUSES
    @GetMapping
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // UPDATE BUS
    @PutMapping("/{id}")
    public Bus updateBus(@PathVariable Long id, @RequestBody Bus updatedBus) {

        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        bus.setBusNumber(updatedBus.getBusNumber());
        bus.setSeatCapacity(updatedBus.getSeatCapacity());
        bus.setBusType(updatedBus.getBusType());
        bus.setRouteNumber(updatedBus.getRouteNumber());
        bus.setFromCity(updatedBus.getFromCity());
        bus.setToCity(updatedBus.getToCity());

        return busRepository.save(bus);
    }

    // DELETE BUS
    @DeleteMapping("/{id}")
    public String deleteBus(@PathVariable Long id) {
        busRepository.deleteById(id);
        return "Bus deleted successfully";
    }
}
